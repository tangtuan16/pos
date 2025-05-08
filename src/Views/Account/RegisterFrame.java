package Views.Account;

import Controllers.UserController;
import Views.BaseFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends BaseFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private UserController userController;
    private UserFrame UserFrame;

    public RegisterFrame(UserFrame UserFrame) {
        this.UserFrame = UserFrame;
        userController = new UserController();

        setTitle("Đăng ký tài khoản");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"admin", "staff"});
        panel.add(roleComboBox);

        add(panel, BorderLayout.CENTER);

        registerButton = new JButton("Đăng ký");
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> registerUser());

        setVisible(true);
    }


    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        if (username.length() == 0 || password.length() == 0) {
            showError("Dữ liệu rỗng !");
            return;
        }

        boolean success = userController.register(username, password, role);
        if (success) {
            showMessage("Đăng ký thành công! Chào mừng " + username + "!");
            if (UserFrame != null) {
                UserFrame.loadUserData();
            }
            dispose();
        } else {
            showError("Lỗi: Username đã tồn tại. Hãy thử lại.");
        }
    }
}
