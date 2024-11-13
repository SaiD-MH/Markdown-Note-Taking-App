package Backend.noteApp.payload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UploadNoteDto {


    private int UserId;
    private String title;
    private String content;
    private List<MultipartFile> documents;

    public UploadNoteDto() {
    }

    public UploadNoteDto(int userId, String title, String content, List<MultipartFile> documents) {
        UserId = userId;
        this.title = title;
        this.content = content;
        this.documents = documents;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MultipartFile> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MultipartFile> documents) {
        this.documents = documents;
    }
}
