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

        JsonObject jsonRaiz = gson.fromJson(response, JsonObject.class);
        JsonArray results = jsonRaiz.getAsJsonArray("results");

        JsonObject responseJson = new JsonObject();
        JsonArray listaRespuestas = new JsonArray();

            for (JsonElement element : results) {
                JsonArray opciones = results.getAsJsonArray();
                    JsonObject pregunta = new JsonObject();

                    pregunta.addProperty("type", pregunta.get("tipo").getAsString());
                    pregunta.addProperty("difficulty", pregunta.get("difficulty").getAsString());
                    pregunta.addProperty("category", pregunta.get("category").getAsString());
                    pregunta.addProperty("question", pregunta.get("pregunta").getAsString());

                    pregunta.addProperty("correct_answer", pregunta.get("respuesta correcta").getAsString());
                    pregunta.add("incorrect_answers", pregunta.get("opciones").getAsJsonArray());

                    listaRespuestas.add(pregunta);
            }

        responseJson.add("preguntas", listaRespuestas);

        String apiUrl = "https://opentdb.com/api.php?amount=10&category=15&difficulty=easy";

        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

//    public static String getImages(String path, HttpClient client) throws IOException, InterruptedException {
//        String[] parts = path.split("/");
//        String num = parts[parts.length - 1];
//
//        String apiUrl = "https://dog.ceo/api/breeds/image/random/" + num;
//
//        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl)).GET().build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        return response.body();
//    }
}