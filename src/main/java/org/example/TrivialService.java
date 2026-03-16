package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class TrivialService {

    private final static Gson gson = new Gson();
    private final static String videogame = new String("https://opentdb.com/api.php?amount=10&category=15&difficulty=easy");
    private final static String boardgame = new String("https://opentdb.com/api.php?amount=10&category=16&difficulty=medium");
    private final static String sport = new String("https://opentdb.com/api.php?amount=10&category=21&difficulty=hard");

    public static JsonObject getPregunta(String path, HttpClient client) throws IOException, InterruptedException {

//        String[] urlPart = path.split("&quot;");

        if (path.endsWith("/easy")) {
            path = videogame;
        } else if (path.endsWith("/medium")) {
            path = boardgame;
        } else if (path.endsWith("/hard")) {
            path = sport;
        }

        String urlRequest = getRequest(path, client);
        JsonObject jsonRaiz = gson.fromJson(urlRequest, JsonObject.class);
        JsonArray results = jsonRaiz.getAsJsonArray("results");

        JsonObject responseJson = new JsonObject();
        JsonArray listaPreguntas2 = new JsonArray();

        for (JsonElement element : results) {
            JsonObject pregunta = element.getAsJsonObject();
            JsonObject listaPreguntas = new JsonObject();

            String question = pregunta.get("question").getAsString();
            listaPreguntas.addProperty("question", question);
            System.out.println(question);

            String correcto = pregunta.get("correct_answer").getAsString();
            listaPreguntas.addProperty("correct_answer", correcto);
            System.out.println(correcto);

            JsonArray incorrecto = pregunta.get("incorrect_answers").getAsJsonArray();
            listaPreguntas.add("incorrect_answers", incorrecto);
            System.out.println(incorrecto);

//                String[] quest = question.split("&quot;");
//                String q = quest[0];
            String respuesta = correcto;

            JsonArray opciones = new JsonArray();
            opciones.add(respuesta);
            for (JsonElement opcion : incorrecto) {
                opciones.add(opcion);
            }

            System.out.println(opciones);

            listaPreguntas.add("opciones", opciones);
//            listaPreguntas.add("opciones", opcionesEnunciado);
                listaPreguntas2.add(listaPreguntas);
        }

        responseJson.add("preguntas", listaPreguntas2);

        return responseJson;
    }

    public static String getRequest(String path, HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(path)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}