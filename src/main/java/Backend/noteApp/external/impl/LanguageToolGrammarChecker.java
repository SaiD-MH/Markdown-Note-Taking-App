package Backend.noteApp.external.impl;

import Backend.noteApp.entity.Word;
import Backend.noteApp.external.GrammarChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class LanguageToolGrammarChecker implements GrammarChecker {


    private final HttpClient client;
    private final ResponseParsing responseParsing;

    @Value("${grammar.checker.languageTool.url}")
    private String apiUrl;


    public LanguageToolGrammarChecker(HttpClient client, ResponseParsing responseParsing) {
        this.client = client;
        this.responseParsing = responseParsing;
    }

    private String constructRequestBody(String text) {
        return "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&language=en-Us";
    }

    private String sendRequest(String text) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(constructRequestBody(text)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Can't get Valid response");
        }

        return response.body();
    }


    @Override
    public List<Word> check(String statement) throws IOException, InterruptedException {

        String response = sendRequest(statement);
        return responseParsing.parseJsonToPojo(response);

    }
}
