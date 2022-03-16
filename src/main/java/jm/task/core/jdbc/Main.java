package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
            userService.saveUser("Иван", "Сидоров", (byte) 28);
            userService.saveUser("Петр", "Петров", (byte) 31);
            userService.saveUser("Илья", "Иванов", (byte) 21);
            userService.saveUser("Дима", "Ильинов", (byte) 44);
            userService.getAllUsers();
            userService.cleanUsersTable();
            userService.dropUsersTable();
        }
}


