package Module13Task1.ex5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GetInfoById {

    public static void main(String[] args) throws IOException, InterruptedException {
         int id;
         String url = "https://jsonplaceholder.typicode.com/users";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id for the search");
        id = scanner.nextInt();
        StringBuilder updatedUrl = new StringBuilder();
        updatedUrl.append(url);
        updatedUrl.append('/');
        updatedUrl.append(id);
        System.out.println(updatedUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(updatedUrl.toString()))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


    }


}
