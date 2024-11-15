package Backend.noteApp.service.notes;

import Backend.noteApp.payload.NoteDto;
import Backend.noteApp.payload.noteQuery.NoteRecord;
import Backend.noteApp.payload.UploadNoteDto;
import org.springframework.core.io.Resource;

public interface NoteService {


    String saveNote(UploadNoteDto uploadedNote);

    String getNote(int userId, int noteId);

    Resource downloadResource(String fileName);
}
