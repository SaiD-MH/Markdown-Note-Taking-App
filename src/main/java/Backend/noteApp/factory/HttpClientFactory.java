package Backend.noteApp.factory;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpClientFactory {

    @Bean
    public HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }

}
