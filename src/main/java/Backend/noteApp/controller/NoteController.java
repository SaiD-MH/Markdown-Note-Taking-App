package Backend.noteApp.controller;

import Backend.noteApp.exception.storage.StorageException;
import Backend.noteApp.payload.NoteDto;
import Backend.noteApp.payload.noteQuery.NoteRecord;
import Backend.noteApp.payload.UploadNoteDto;
import Backend.noteApp.repository.NoteRepository;
import Backend.noteApp.service.notes.NoteService;
import Backend.noteApp.service.uploadingfiles.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {


    private final NoteService noteService;

    private final StorageService storageService;


    public NoteController(NoteService noteService, StorageService storageService) {
        this.noteService = noteService;
        this.storageService = storageService;
    }

    @PostMapping("/{userId}/notes")
    public String createNote(@PathVariable("userId") int userId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) {

        if (files == null) {
            files = List.of();
        }
        UploadNoteDto uploadedNote = new UploadNoteDto(

                userId,
                title,
                content,
                files

        );

        return noteService.saveNote(uploadedNote);

    }

    @GetMapping("/{userId}/notes/{noteId}")
    public ResponseEntity<String> getNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId) {


        String note = noteService.getNote(userId, noteId);

        return ResponseEntity.ok().contentType(MediaType.valueOf("text/html")).body(note);

    }

    @GetMapping("/{user_id}/notes/download/{fileName}")
    public ResponseEntity<Resource> downloadResource(@PathVariable String fileName) {

        Resource resource = noteService.downloadResource(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }
}
