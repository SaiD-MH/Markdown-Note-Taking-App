package Backend.noteApp.payload;

import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class NoteDto {


    private String markdown;
    private List<Resource> resources = new ArrayList<>();

    public NoteDto() {
    }

    public NoteDto(String markdown, List<Resource> resources) {
        this.markdown = markdown;
        this.resources = resources;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
