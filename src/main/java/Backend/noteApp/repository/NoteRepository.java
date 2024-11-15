package Backend.noteApp.repository;

import Backend.noteApp.entity.Note;
import Backend.noteApp.payload.noteQuery.NoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query(value = "SELECT id, title,content FROM note WHERE user_id=:userId AND id=:noteId", nativeQuery = true)
    NoteRecord findByUserIdAndId(@Param("userId") int userId, @Param("noteId") int noteId);


}


