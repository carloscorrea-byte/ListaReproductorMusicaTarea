package com.example.menu;

import com.example.menu.model.ItemMenu;
import com.example.menu.structure.ArbolMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Se ejecuta al arrancar: arma el menu, lo imprime y prueba las operaciones. */
@Component
public class DemoConsola implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ArbolMenu menu = new ArbolMenu(new ItemMenu("Menu principal", "/", "AppLayout"));

        // Primer nivel
        menu.agregar("Menu principal", new ItemMenu("Inicio", "/inicio", "InicioPage"));
        menu.agregar("Menu principal", new ItemMenu("Productos", "/productos", "ProductosPage"));
        menu.agregar("Menu principal", new ItemMenu("Servicios", "/servicios", "ServiciosPage"));
        menu.agregar("Menu principal", new ItemMenu("Contacto", "/contacto", "ContactoPage"));

        // Submenus de Productos
        menu.agregar("Productos", new ItemMenu("Electronica", "/productos/electronica", "ElectronicaPage"));
        menu.agregar("Productos", new ItemMenu("Hogar", "/productos/hogar", "HogarPage"));

        // Submenus de Electronica (tercer nivel)
        menu.agregar("Electronica", new ItemMenu("Celulares", "/productos/electronica/celulares", "CelularesPage"));
        menu.agregar("Electronica", new ItemMenu("Portatiles", "/productos/electronica/portatiles", "PortatilesPage"));

        // Submenus de Servicios
        menu.agregar("Servicios", new ItemMenu("Soporte", "/servicios/soporte", "SoportePage"));
        menu.agregar("Servicios", new ItemMenu("Capacitaciones", "/servicios/capacitaciones", "CapacitacionesPage"));

        System.out.println("\n===== MENU (ARBOL N-ARIO) =====");
        System.out.print(menu.imprimir());

        System.out.println("\n--- Informacion ---");
        System.out.println("Total de items: " + menu.contar());
        System.out.println("Altura del arbol: " + menu.altura());
        System.out.println("Por niveles: " + menu.porNiveles());

        System.out.println("\n--- Busquedas ---");
        System.out.println("Buscar 'Soporte':  " + menu.buscar("Soporte"));
        System.out.println("Buscar 'Blog':     " + menu.buscar("Blog"));
        System.out.println("Ruta de 'Celulares': " + menu.ruta("Celulares"));

        System.out.println("\n--- Agregar 'Blog' en Menu principal y 'Ofertas' en Hogar ---");
        System.out.println("Agregar Blog:    " + menu.agregar("Menu principal", new ItemMenu("Blog", "/blog", "BlogPage")));
        System.out.println("Agregar Ofertas: " + menu.agregar("Hogar", new ItemMenu("Ofertas", "/productos/hogar/ofertas", "OfertasPage")));
        System.out.println("Agregar repetido (Blog): " + menu.agregar("Contacto", new ItemMenu("Blog", "/x", "X")));
        System.out.println("Agregar a padre inexistente: " + menu.agregar("Nada", new ItemMenu("Otro", "/otro", "Otro")));

        System.out.println("\n--- Eliminar 'Electronica' (se van tambien sus submenus) ---");
        System.out.println("Eliminado: " + menu.eliminar("Electronica"));

        System.out.println("\n===== MENU FINAL =====");
        System.out.print(menu.imprimir());
        System.out.println("Total de items: " + menu.contar());
        System.out.println("===============================\n");
    }
}
