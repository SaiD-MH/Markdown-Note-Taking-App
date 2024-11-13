package Backend.noteApp.service.notes;

import Backend.noteApp.payload.UploadNoteDto;

public interface NoteService {


    String saveNote(UploadNoteDto uploadedNote);

}
