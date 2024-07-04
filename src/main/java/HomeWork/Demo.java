package HomeWork;

import HomeWork.dto.User;

public class Demo {
    public static void main(String[] args) {
        User user = new User("zakhar", "asdasd", 1, "Zak");


        ApiService service = new ApiService();

        System.out.println(service.createNewUser(user));
        System.out.println(service.updateUser(user));
        System.out.println(service.deleteUser(1));
        System.out.println(service.getAllUsers());
        System.out.println(service.getUserById(1));
        System.out.println(service.getUserByUserName("Bret"));
        service.readCommentsFromEveryPostOfSpecificUser(1);
        System.out.println(service.getOpenedTasksForSpecificUser(1));


    }
}
