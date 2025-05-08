import Views.Account.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("DB_USER: " + System.getenv("DB_USER"));
        System.out.println("DB_PASS: " + System.getenv("DB_PASS"));
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
