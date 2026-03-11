package controllers;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.example.TrivialService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TrivialController {

    private final HttpClient client = HttpClient.newHttpClient();

    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try {

            if (path.equals("/trivial/videogame")) {
                JsonObject result = TrivialService.getPregunta(path, client);
                sendResponse(exchange, 200, result.toString());
                return;
            }

            if (path.equals("/trivial/boardGame")) {
                JsonObject result = TrivialService.getPregunta(path, client);
                sendResponse(exchange, 200, result.toString());
                return;
            }

            if (path.equals("/trivial/sport")) {
                JsonObject result = TrivialService.getPregunta(path, client);
                sendResponse(exchange, 200, result.toString());
                return;
            }

            sendResponse(exchange, 404, "{\"error\": \"Endpoint no válido\"}");

        } catch (Exception e) {
            sendResponse(exchange, 500, "{\"error\": \"Error llamando a la API externa\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}