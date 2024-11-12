package Backend.noteApp.controller;

import Backend.noteApp.entity.Word;
import Backend.noteApp.external.GrammarChecker;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/grammar")
public class GrammarCheckController {

    private GrammarChecker grammarChecker;

    public GrammarCheckController(GrammarChecker grammarChecker) {
        this.grammarChecker = grammarChecker;
    }


    @PostMapping("")
    public List<Word> grammarCheck(@RequestParam(value = "text") String text) throws IOException, InterruptedException {

        return grammarChecker.check(text);
    }


}
