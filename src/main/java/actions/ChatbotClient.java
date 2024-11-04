package actions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatbotClient {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String PROMPT = "You are a senior software engineer. Help me solve this coding error: ";

    //TODO: paste your API key
    private final String apiKey = "";

    public ChatbotClient() {
    }

    public String promptLLM(String message) {
        JsonObject jsonPayload = new JsonObject();
        JsonArray messagesArray = new JsonArray();
        JsonObject messageObject = new JsonObject();

        messageObject.addProperty("role", "user");
        messageObject.addProperty("content", PROMPT + " " + message);
        messagesArray.add(messageObject);

        jsonPayload.add("messages", messagesArray);
        jsonPayload.addProperty("model", "llama3-8b-8192");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString(), StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body().toString());
                JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray choices = responseJson.getAsJsonArray("choices");
                JsonObject choice = choices.get(0).getAsJsonObject();
                JsonObject messageObj = choice.getAsJsonObject("message");
                return messageObj.get("content").getAsString();
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
                return "Error: Unable to get response from LLM.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: Exception occurred while calling the LLM API.";
        }
    }
}
