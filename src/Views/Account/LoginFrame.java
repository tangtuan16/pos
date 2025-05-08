package Views.Account;

import Controllers.UserController;
import Models.User;
import Views.BaseFrame;
import Views.HomePage;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends BaseFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private UserController userController;

    public LoginFrame() {
        userController = new UserController();
        setTitle("Đăng nhập");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> loginUser());

        setVisible(true);
    }

    //View
    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        User user = userController.login(username, password);

        if (user != null) {
            showMessage("Đăng nhập thành công!");
            setVisible(false);
            new HomePage(user).setVisible(true);
        } else {
            showError("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
}
