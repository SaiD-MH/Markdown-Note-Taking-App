package Backend.noteApp.service.uploadingfiles.storage.impl;

import Backend.noteApp.entity.FileMetadata;
import Backend.noteApp.exception.storage.StorageException;
import Backend.noteApp.exception.storage.StorageFileNotFoundException;
import Backend.noteApp.service.uploadingfiles.storage.StorageProperties;
import Backend.noteApp.service.uploadingfiles.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {


    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {

        if (properties.getLocation().trim().isEmpty())
            throw new StorageException("Upload file location can't be empty");

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException exception) {
            throw new StorageException("Can't initialize storage location", exception);
        }

    }


    public FileMetadata store(MultipartFile file) {

        FileMetadata fileMetadata = new FileMetadata();

        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                throw new StorageException("File can't be empty");
            }


            String newFileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            String originalFileName = file.getOriginalFilename();


            Path destinationFile = this.rootLocation.resolve(Paths.get(newFileName))
                    .normalize().toAbsolutePath();

            // Check if the file is being stored inside the root directory
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Can't store file outside of current directory");
            }

            // Store the file with the new name
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }


            fileMetadata.setType(getFileExtension(originalFileName));
            fileMetadata.setOriginalName(originalFileName);
            fileMetadata.setNewName(newFileName);
        } catch (IOException exception) {
            throw new StorageException("Fail to store the file", exception);
        }

        return fileMetadata;
    }

    private String getFileExtension(String fileName) {

        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex == -1)
            throw new StorageException("Invalid file name");

        return fileName.substring(dotIndex);

    }


    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        } catch (StorageFileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
