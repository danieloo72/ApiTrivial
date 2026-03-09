package org.example;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import router.RouterHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        // Se crea el servior y desde qué puerto va estar escuchando
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // cada vez que arranca y se pone (http://localhost:8080)
        server.createContext("/", new RouterHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en http://localhost:8080");
    }
}