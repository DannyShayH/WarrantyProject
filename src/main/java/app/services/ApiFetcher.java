package app.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiFetcher {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String getXml(String url) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(url))
                .header("Accept", "application/xml")
                .GET()
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("Error in: " + response.statusCode());
        }
        return null;
    }
}
