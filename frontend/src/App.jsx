import { useCallback, useEffect, useState } from "react";
import { Graph } from "react-d3-graph";

const COLOR_CIUDAD = "#e8590c";
const COLOR_PERSONA = "#1c7ed6";

// Configuracion de react-d3-graph
const CONFIG = {
  width: 780,
  height: 520,
  directed: false,
  nodeHighlightBehavior: true,
  node: {
    labelProperty: "nombre", // texto que se muestra en cada nodo
    fontSize: 12,
    highlightFontSize: 14,
    highlightStrokeColor: "#212529",
  },
  link: { highlightColor: "#212529" },
};

async function llamarApi(url, opciones) {
  const respuesta = await fetch(url, opciones);
  const datos = await respuesta.json().catch(() => ({}));
  if (!respuesta.ok) {
    throw new Error(datos.error || "Error en la peticion");
  }
  return datos;
}

function enviar(url, cuerpo) {
  return llamarApi(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(cuerpo),
  });
}

export default function App() {
  const [grafo, setGrafo] = useState({ nodes: [], links: [] });
  const [error, setError] = useState("");

  // formularios
  const [nuevaCiudad, setNuevaCiudad] = useState("");
  const [persona, setPersona] = useState({ nombre: "", edad: "", ciudad: "" });
  const [amistad, setAmistad] = useState({ persona1: "", persona2: "" });

  // consulta: personas que viven en una ciudad
  const [ciudadConsulta, setCiudadConsulta] = useState("");
  const [habitantes, setHabitantes] = useState([]);

  const ciudades = grafo.nodes.filter((n) => n.tipo === "CIUDAD");
  const personas = grafo.nodes.filter((n) => n.tipo === "PERSONA");

  const cargarGrafo = useCallback(async () => {
    try {
      setGrafo(await llamarApi("/api/grafo"));
    } catch (e) {
      setError("No se pudo conectar con el backend. Esta corriendo en el puerto 8080?");
    }
  }, []);

  useEffect(() => {
    cargarGrafo();
  }, [cargarGrafo]);

  const consultarHabitantes = useCallback(async (ciudad) => {
    if (!ciudad) {
      setHabitantes([]);
      return;
    }
    try {
      setHabitantes(await llamarApi(`/api/ciudades/${encodeURIComponent(ciudad)}/personas`));
    } catch (e) {
      setError(e.message);
    }
  }, []);

  // Ejecuta una accion, recarga el grafo y muestra errores si los hay
  const accion = async (fn) => {
    setError("");
    try {
      await fn();
      await cargarGrafo();
      await consultarHabitantes(ciudadConsulta);
    } catch (e) {
      setError(e.message);
    }
  };

  // react-d3-graph necesita color/forma por nodo: se los damos segun el tipo
  const datosGrafo = {
    nodes: grafo.nodes.map((n) => ({
      ...n,
      color: n.tipo === "CIUDAD" ? COLOR_CIUDAD : COLOR_PERSONA,
      symbolType: n.tipo === "CIUDAD" ? "square" : "circle",
      size: n.tipo === "CIUDAD" ? 900 : 450,
    })),
    links: grafo.links.map((l) => ({ ...l })),
  };

  return (
    <div className="app">
      <h1>Grafo de personas y ciudades</h1>
      <p className="sub">Reto 10 - Estructuras de Datos</p>

      {error && <div className="error">{error}</div>}

      <div className="layout">
        <div className="card graph">
          {grafo.nodes.length > 0 ? (
            <Graph
              id="grafo-personas-ciudades"
              key={`${grafo.nodes.length}-${grafo.links.length}`}
              data={datosGrafo}
              config={CONFIG}
            />
          ) : (
            <p style={{ padding: 16 }}>El grafo esta vacio.</p>
          )}
        </div>

        <div>
          <div className="card leyenda">
            <h2>Leyenda</h2>
            <div><span style={{ background: COLOR_CIUDAD }} />Ciudad</div>
            <div><span style={{ background: COLOR_PERSONA, borderRadius: "50%" }} />Persona</div>
          </div>

          <div className="card">
            <h2>Personas que viven en una ciudad</h2>
            <select
              value={ciudadConsulta}
              onChange={(e) => {
                setCiudadConsulta(e.target.value);
                consultarHabitantes(e.target.value);
              }}
            >
              <option value="">Elige una ciudad...</option>
              {ciudades.map((c) => (
                <option key={c.id} value={c.nombre}>{c.nombre}</option>
              ))}
            </select>
            <ul style={{ marginTop: 8 }}>
              {habitantes.map((p) => (
                <li key={p.id}>{p.nombre} ({p.edad} anios)</li>
              ))}
            </ul>
          </div>

          <div className="card">
            <h2>Agregar ciudad</h2>
            <div className="row">
              <input
                placeholder="Nombre"
                value={nuevaCiudad}
                onChange={(e) => setNuevaCiudad(e.target.value)}
              />
              <button
                onClick={() =>
                  accion(async () => {
                    await enviar("/api/ciudades", { nombre: nuevaCiudad });
                    setNuevaCiudad("");
                  })
                }
              >
                Agregar
              </button>
            </div>
          </div>

          <div className="card">
            <h2>Agregar persona</h2>
            <div className="row">
              <input
                placeholder="Nombre"
                value={persona.nombre}
                onChange={(e) => setPersona({ ...persona, nombre: e.target.value })}
              />
              <input
                type="number"
                placeholder="Edad"
                style={{ width: 80 }}
                value={persona.edad}
                onChange={(e) => setPersona({ ...persona, edad: e.target.value })}
              />
            </div>
            <div className="row">
              <select
                value={persona.ciudad}
                onChange={(e) => setPersona({ ...persona, ciudad: e.target.value })}
              >
                <option value="">Ciudad donde vive...</option>
                {ciudades.map((c) => (
                  <option key={c.id} value={c.nombre}>{c.nombre}</option>
                ))}
              </select>
              <button
                onClick={() =>
                  accion(async () => {
                    await enviar("/api/personas", {
                      nombre: persona.nombre,
                      edad: Number(persona.edad),
                      ciudad: persona.ciudad,
                    });
                    setPersona({ nombre: "", edad: "", ciudad: "" });
                  })
                }
              >
                Agregar
              </button>
            </div>
          </div>

          <div className="card">
            <h2>Conectar amigos</h2>
            <div className="row">
              <select
                value={amistad.persona1}
                onChange={(e) => setAmistad({ ...amistad, persona1: e.target.value })}
              >
                <option value="">Persona 1</option>
                {personas.map((p) => (
                  <option key={p.id} value={p.nombre}>{p.nombre}</option>
                ))}
              </select>
              <select
                value={amistad.persona2}
                onChange={(e) => setAmistad({ ...amistad, persona2: e.target.value })}
              >
                <option value="">Persona 2</option>
                {personas.map((p) => (
                  <option key={p.id} value={p.nombre}>{p.nombre}</option>
                ))}
              </select>
            </div>
            <button
              onClick={() =>
                accion(async () => {
                  await enviar("/api/amistades", amistad);
                  setAmistad({ persona1: "", persona2: "" });
                })
              }
            >
              Conectar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
