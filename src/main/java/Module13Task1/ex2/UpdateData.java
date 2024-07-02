package Module13Task1.ex2;

import Module13Task1.User;
import com.google.gson.Gson;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateData {

    public static void main(String[] args) throws IOException, InterruptedException {

        User user = new User();
        user.setName("Zakhar");
        user.setEmail("Zakhar918309@gmail.com");
        user.setId(1);
        Gson gson = new Gson();
        String json = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(send.body());


    }
}
