package HomeWork;

import HomeWork.dto.User;

public class Demo {
    public static void main(String[] args) {
        User user = new User("Zakhar","Zakhar1283@gmia",9);

        ApiService service = new ApiService();
        service.getOpenedTasksForSpecificUser(1);
    }
}
