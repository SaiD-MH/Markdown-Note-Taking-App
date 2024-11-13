package Backend.noteApp.service.uploadingfiles.storage;

import Backend.noteApp.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    FileMetadata store(MultipartFile file);


    Path load(String filename);

    Resource loadAsResource(String filename);

}

