package Backend.noteApp.service.notes.Impl;

import Backend.noteApp.repository.DocumentRepository;
import Backend.noteApp.entity.Document;
import Backend.noteApp.entity.FileMetadata;
import Backend.noteApp.entity.Note;
import Backend.noteApp.payload.UploadNoteDto;
import Backend.noteApp.repository.NoteRepository;
import Backend.noteApp.service.notes.NoteService;
import Backend.noteApp.service.uploadingfiles.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;
    private final StorageService storageService;
    private final DocumentRepository documentRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, StorageService storageService, DocumentRepository documentRepository) {
        this.noteRepository = noteRepository;
        this.storageService = storageService;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public String saveNote(UploadNoteDto uploadedNote) {


        Note note = new Note(uploadedNote.getUserId(), uploadedNote.getTitle(), uploadedNote.getContent());

        note = noteRepository.save(note);

        for (MultipartFile file : uploadedNote.getDocuments()) {

            FileMetadata fileMetadata = storageService.store(file);

            Document document = new Document(note, fileMetadata.getOriginalName(), fileMetadata.getNewName(), fileMetadata.getType());
            documentRepository.save(document);

        }

        return "uploaded note saved successfully";

    }
}
