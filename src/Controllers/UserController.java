package Controllers;

import Models.User;
import Services.UserService;

import java.util.List;

public class UserController {
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void updateUsername(int userId, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username mới không được để trống.");
        }
        userService.updateUsername(userId, newUsername.trim());
    }

    public void updatePassword(int userId, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được để trống.");
        }
        userService.updatePassword(userId, newPassword.trim());
    }

    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }

    public User login(String username, String password) {
        return userService.checkLogin(username, password);
    }

    public boolean register(String username, String password, String role) {
        return userService.registerUser(username, password, role);
    }
}
