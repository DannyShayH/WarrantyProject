package app.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiFetcher {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String getXml(String url){
        HttpRequest request = null;
        try {
            request = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .header("Accept", "application/xml")
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("Error in: " + response.statusCode());
        }
        return null;
    }
}
