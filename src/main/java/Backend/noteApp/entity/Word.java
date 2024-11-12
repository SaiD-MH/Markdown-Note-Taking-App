package Backend.noteApp.entity;

import java.util.List;

public class Word {

    private String message;
    private List<String> suggestions;

    public Word() {
    }

    public Word(String message, List<String> suggestions) {
        this.message = message;
        this.suggestions = suggestions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }


}
