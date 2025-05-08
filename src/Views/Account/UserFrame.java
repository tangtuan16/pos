package Views.Account;

import Controllers.UserController;
import Models.User;
import Views.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserFrame extends BaseFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserController userController;

    public UserFrame() {
        userController = new UserController();

        setTitle("Danh sách tài khoản");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        userTable.getTableHeader().setBackground(new Color(0, 123, 255));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton createAccountButton = new JButton("Tạo tài khoản");
        createAccountButton.setBackground(new Color(0, 123, 255));
        createAccountButton.setForeground(Color.WHITE);

        JButton editUserButton = new JButton("Sửa tài khoản");
        editUserButton.setBackground(new Color(40, 167, 69));
        editUserButton.setForeground(Color.WHITE);

        JButton changePasswordButton = new JButton("Cập nhật Mật khẩu");
        changePasswordButton.setBackground(new Color(40, 167, 69));
        changePasswordButton.setForeground(Color.WHITE);

        JButton deleteUserButton = new JButton("Xóa Tài khoản");
        deleteUserButton.setBackground(Color.RED);
        deleteUserButton.setForeground(Color.WHITE);

        createAccountButton.addActionListener(e -> new RegisterFrame(this).setVisible(true));
        editUserButton.addActionListener(e -> editUsername());
        changePasswordButton.addActionListener(e -> updatePassword());
        deleteUserButton.addActionListener(e -> deleteUser());

        buttonPanel.add(createAccountButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(deleteUserButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadUserData();
        setVisible(true);
    }

    public void loadUserData() {
        List<User> users = userController.getAllUsers();
        tableModel.setRowCount(0);
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }
    }

    private void editUsername() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn một tài khoản để sửa!");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String newUsername = JOptionPane.showInputDialog(this, "Nhập username mới:");
        if (newUsername == null) return;
        try {
            userController.updateUsername(userId, newUsername);
            loadUserData();
            showMessage("Username đã được cập nhật!");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void updatePassword() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn một tài khoản để cập nhật mật khẩu!");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String newPassword = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:");
        if (newPassword == null) return;

        try {
            userController.updatePassword(userId, newPassword);
            showMessage("Mật khẩu đã được cập nhật!");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn một tài khoản để xóa!");
            return;
        }

        boolean confirm = confirmYesNo("Bạn có chắc muốn xóa tài khoản này?");
        if (!confirm) return;
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            userController.deleteUser(userId);
            loadUserData();
            showMessage("Tài khoản đã bị xóa!");
        } catch (Exception ex) {
            showError("Không thể xóa tài khoản: " + ex.getMessage());
        }
    }
}
