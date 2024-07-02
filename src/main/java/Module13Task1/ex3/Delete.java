package Module13Task1.ex3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Delete {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        System.out.println(status);
        if(status>=200&&status<300){
            System.out.println("Object was deleted !");
        }
        else {
            System.out.println("deletion was not successful");
        }


    }
}
