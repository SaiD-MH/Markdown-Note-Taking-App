package Backend.noteApp.service.notes.Impl;

import Backend.noteApp.exception.storage.StorageException;
import Backend.noteApp.markdown.MarkdownToHtml;
import Backend.noteApp.payload.noteQuery.DocumentRecord;
import Backend.noteApp.payload.noteQuery.NoteRecord;
import Backend.noteApp.repository.DocumentRepository;
import Backend.noteApp.entity.Document;
import Backend.noteApp.entity.FileMetadata;
import Backend.noteApp.entity.Note;
import org.springframework.core.io.Resource;
import Backend.noteApp.payload.UploadNoteDto;
import Backend.noteApp.repository.NoteRepository;
import Backend.noteApp.service.notes.NoteService;
import Backend.noteApp.service.uploadingfiles.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;
    private final StorageService storageService;
    private final DocumentRepository documentRepository;

    private final MarkdownToHtml parseStringToHtml;

    public NoteServiceImpl(NoteRepository noteRepository, StorageService storageService, DocumentRepository documentRepository, MarkdownToHtml parseStringToHtml) {
        this.noteRepository = noteRepository;
        this.storageService = storageService;
        this.documentRepository = documentRepository;
        this.parseStringToHtml = parseStringToHtml;
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


    @Override
    public String getNote(int userId, int noteId) {

        NoteRecord note = noteRepository.findByUserIdAndId(userId, noteId);
        List<DocumentRecord> docsMetadata = documentRepository.findDocumentsByNoteId(noteId);

        return parseStringToHtml.appendDocumentsToEndOfHtmlNote(parseStringToHtml.parse(note), loadDocumentsContent(docsMetadata));


    }

    private List<Resource> loadDocumentsContent(List<DocumentRecord> docsMetadata) {
        List<Resource> resources = new ArrayList<>();
        for (DocumentRecord doc : docsMetadata) {

            Resource resource = storageService.loadAsResource(doc.getPath());
            resources.add(resource);

        }
        return resources;
    }


    @Override
    public Resource downloadResource(String fileName) {

        Resource resource = storageService.loadAsResource(fileName);

        if (resource.exists())
            return resource;
        else
            throw new StorageException("File Not Found");


    }
}
