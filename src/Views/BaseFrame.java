package Views;

import javax.swing.*;

public class BaseFrame extends JFrame {

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmYesNo(String message) {
        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }
}
