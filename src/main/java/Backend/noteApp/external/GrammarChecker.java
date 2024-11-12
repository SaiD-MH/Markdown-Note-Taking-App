package Backend.noteApp.external;

import Backend.noteApp.entity.Word;

import java.io.IOException;
import java.util.List;

public interface GrammarChecker {

    List<Word> check(String statement) throws IOException, InterruptedException;

}
