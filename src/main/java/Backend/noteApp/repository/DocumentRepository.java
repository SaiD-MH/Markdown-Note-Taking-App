package Backend.noteApp.repository;

import Backend.noteApp.entity.Document;
import Backend.noteApp.payload.noteQuery.DocumentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    @Query(value = "SELECT name , path , type FROM document WHERE note_id=:noteId", nativeQuery = true)
    List<DocumentRecord> findDocumentsByNoteId(@Param("noteId") int noteId);

}
