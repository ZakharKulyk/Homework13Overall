package HomeWork;

import HomeWork.constants.Path;
import HomeWork.constants.UrlOperations;
import HomeWork.dto.Post;
import HomeWork.dto.Todo;
import HomeWork.dto.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiService {

    public User createNewUser(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users"))
                .header("Content-Type", "application/json; utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            User result = gson.fromJson(send.body(), User.class);
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> send = null;
        try {
            send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(send.body(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(int userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users/" + userId))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int status = response.statusCode();
        System.out.println(status);
        if (status >= 200 && status < 300) {
            System.out.println("User was deleted !");
            return true;
        } else {
            System.out.println("deletion was not successful");
            return false;
        }
    }

    public List<User> getAllUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type usersListType = new TypeToken<List<User>>() {}.getType();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<User>result = gson.fromJson(response.body(), usersListType);
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUserById(int userId) {
        Gson gson = new Gson();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users" + UrlOperations.DELIMITER + userId))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUserByUserName(String userName) {
        Type usersListType = new TypeToken<List<User>>() {}.getType();
        Gson gson = new Gson();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users?username=" + userName))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), usersListType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCommentsFromEveryPostOfSpecificUser(int userId) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type postListType = new TypeToken<List<Post>>() {
        }.getType();
        List<Post> listOfUserPosts = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users/" + userId + "/posts"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> userPosts = client.send(request, HttpResponse.BodyHandlers.ofString());
            listOfUserPosts = gson.fromJson(userPosts.body(), postListType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Integer> listOfPostIds = listOfUserPosts.stream()
                .map(post -> post.getId())
                .collect(Collectors.toList());


        listOfPostIds.stream()
                .forEach((number) -> {
                    HttpRequest request1 = HttpRequest.newBuilder()
                            .uri(URI.create(Path.BASE_URL + "posts/" + number + "/comments"))
                            .GET()
                            .build();
                    HttpClient client1 = HttpClient.newHttpClient();
                    try {
                        HttpResponse<String> response = client1.send(request1, HttpResponse.BodyHandlers.ofString());
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user-" + userId + "-post-" + number + "-comments.json"))) {
                            writer.write(response.body());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                });
    }

    public List<Todo> getOpenedTasksForSpecificUser(int userId) {
        Type TodoListType = new TypeToken<List<Todo>>() {}.getType();
        Gson gson = new Gson();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Path.BASE_URL + "users/" + Integer.toString(userId) + "/todos"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Todo> responseAsList = gson.fromJson(response.body(), TodoListType);

        List<Todo> result = responseAsList.stream()
                .filter(todo -> !(todo.isCompleted()))
                .collect(Collectors.toList());

        return  result;
    }


}




