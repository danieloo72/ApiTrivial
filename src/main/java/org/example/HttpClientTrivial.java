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

public class HttpClientTrivial {

    private final static Gson gson = new Gson();

    public static JsonObject getPregunta(String response) {
        JsonObject jsonRaiz = gson.fromJson(response, JsonObject.class);
        JsonArray results = jsonRaiz.getAsJsonArray("results");

        JsonObject responseJson = new JsonObject();
        JsonArray listaPreguntas = new JsonArray();

        if (results != null) {
            for (JsonElement element : results) {
                JsonObject item = element.getAsJsonObject();

                JsonObject pregunta = new JsonObject();
                pregunta.addProperty("type", item.get("tipo").getAsString());
                pregunta.addProperty("difficulty", item.get("dificultad").getAsString());
                pregunta.addProperty("category", item.get("categoria").getAsString());
                pregunta.addProperty("question", item.get("pregunta").getAsString());

                pregunta.add("correct_answer", item.get("respuesta correcta"));
                pregunta.add("incorrect_answers", item.getAsJsonArray("respuesta incorrecta"));

                listaPreguntas.add(pregunta);
            }
        }

        responseJson.add("preguntas", listaPreguntas);
        return responseJson;
    }

    public static String getImages(String path, HttpClient client) throws IOException, InterruptedException {
        String[] parts = path.split("/");
        String num = parts[parts.length - 1];

        String apiUrl = "https://dog.ceo/api/breeds/image/random/" + num;

        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}