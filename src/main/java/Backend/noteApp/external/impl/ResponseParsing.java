package Backend.noteApp.external.impl;

import Backend.noteApp.entity.Word;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ResponseParsing {

    private final ObjectMapper objectMapper;

    public ResponseParsing(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public  List<Word> parseJsonToPojo(String response) throws JsonProcessingException {

        List<Word> suggestions = new ArrayList<>();



        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode matches = rootNode.path("matches");

        for (JsonNode match : matches) {

            Word word = new Word();
            List<String> suggest = new ArrayList<>();

            word.setMessage(match.path("message").asText());

            JsonNode replacements = match.path("replacements");

            for (JsonNode replace : replacements) {

                suggest.add(replace.path("value").asText());

            }
            word.setSuggestions(suggest);
            suggestions.add(word);
        }


        return suggestions;
    }

}
