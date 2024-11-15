package Backend.noteApp.markdown;

import Backend.noteApp.payload.noteQuery.NoteRecord;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkdownToHtml {


    public String parse(NoteRecord noteRecord) {

        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        String note = noteRecord.getTitle() + '\n' + noteRecord.getContent();


        Node document = parser.parse(note);
        return renderer.render(document);

    }

    public String appendDocumentsToEndOfHtmlNote(String htmlNote, List<Resource> resources) {


        StringBuilder htmlNoteBuilder = new StringBuilder(htmlNote);
        for (Resource resource : resources) {
            String link = String.format("<a href='download/%s'> %s</a> ", resource.getFilename(), resource.getFilename());

            htmlNoteBuilder.append("<br>").append(link);

        }
        htmlNote = htmlNoteBuilder.toString();

        return htmlNote;
    }

}
