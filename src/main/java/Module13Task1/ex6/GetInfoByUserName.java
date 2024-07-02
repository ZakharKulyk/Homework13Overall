package Module13Task1.ex6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GetInfoByUserName {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder newUrl = new StringBuilder();

        String userName;
        String url = "https://jsonplaceholder.typicode.com/users?username=";

        System.out.println("enter username");

        userName = scanner.nextLine();

        newUrl.append(url);
        newUrl.append(userName);

        System.out.println(newUrl.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(newUrl.toString()))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());


    }
}
