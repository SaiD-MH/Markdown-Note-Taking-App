package Backend.noteApp;

import Backend.noteApp.service.uploadingfiles.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class MarkdownNoteTakingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarkdownNoteTakingAppApplication.class, args);
	}

}
