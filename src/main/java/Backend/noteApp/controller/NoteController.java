package Backend.noteApp.controller;

import Backend.noteApp.BasicSample;
import Backend.noteApp.payload.UploadNoteDto;
import Backend.noteApp.service.notes.NoteService;
import Backend.noteApp.service.uploadingfiles.storage.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/{userId}/notes")
public class NoteController {


    private NoteService noteService;
    private StorageService storageService;

    public NoteController(NoteService noteService, StorageService storageService) {
        this.noteService = noteService;
        this.storageService = storageService;
    }

    @PostMapping("")
    public String createNote(@PathVariable("userId") int userId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("files") List<MultipartFile> files
    ) {


        UploadNoteDto uploadedNote = new UploadNoteDto(

                userId,
                title,
                content,
                files

        );

        return noteService.saveNote(uploadedNote);

    }

    @GetMapping()
    public ResponseEntity<String> getNote() {

        return ResponseEntity.ok().contentType(MediaType.valueOf("text/html")).body(BasicSample.getNote());

    }




}
