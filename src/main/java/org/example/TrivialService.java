package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrivialService {

    private final static Gson gson = new Gson();
    private final static String videogame = new String("https://opentdb.com/api.php?amount=10&category=15&difficulty=easy");
    private final static String boardgame = new String("https://opentdb.com/api.php?amount=10&category=15&difficulty=easy");
    private final static String sport = new String("https://opentdb.com/api.php?amount=10&category=15&difficulty=easy");

    public static JsonObject getPregunta(String path, HttpClient client) throws IOException, InterruptedException {

//        String[] urlPart = path.split("&quot;");

        if (path.endsWith("/videogame")) {
            path = videogame;
        } else if (path.endsWith("/boardgame")) {
            path = boardgame;
        } else if (path.endsWith("/sport")) {
            path = sport;
        }

        String urlRequest = getRequest(path, client);
        JsonObject jsonRaiz = gson.fromJson(urlRequest, JsonObject.class);
        JsonArray results = jsonRaiz.getAsJsonArray("results");

        JsonObject responseJson = new JsonObject();
        JsonArray listaPreguntas = new JsonArray();

            for (JsonElement element : results) {
                JsonObject pregunta = element.getAsJsonObject();
                JsonObject enunciado = new JsonObject();

                enunciado.addProperty("question", pregunta.get("question").getAsString());
                System.out.println(pregunta.get("question").getAsString());


                enunciado.addProperty("correct_answer", pregunta.get("correct_answer").getAsString());
                enunciado.add("incorrect_answers", pregunta.get("incorrect_answers").getAsJsonArray());

                String respuestas = pregunta.get("correct_answer").getAsString() + pregunta.get("incorrect_answers").getAsString();
                System.out.println(respuestas);

                JsonArray opciones = new JsonArray();
                opciones.add(respuestas);
                for (JsonElement opcion : pregunta.get("opciones").getAsJsonArray()) {
                    System.out.println(opcion.toString());
                }
                listaPreguntas.add(enunciado);
            }

        responseJson.add("preguntas", listaPreguntas);

        return responseJson;
    }

    public static String getRequest(String path, HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}