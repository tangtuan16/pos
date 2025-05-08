package Views;

import Models.User;
import Views.Account.LoginFrame;
import Views.Account.UserFrame;
import Views.Customer.CustomerFrame;
import Views.Sales.SaleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomePage extends JFrame {
    private User user;
    private JSplitPane splitPane;

    public HomePage(User user) {
        this.user = user;

        setTitle("POS - Trang Chủ");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel imagePanel = createImagePanel();
        Font buttonFont = new Font("Quicksand", Font.PLAIN, 16);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnProduct = new JButton("Quản lý sản phẩm");
        btnProduct.setFont(buttonFont);
        JButton btnSale = new JButton("Bán hàng");
        btnSale.setFont(buttonFont);
        JButton btnInvoice = new JButton("Hóa đơn");
        btnInvoice.setFont(buttonFont);
        JButton btnCustomer = new JButton("Khách hàng thân thiết");
        btnCustomer.setFont(buttonFont);
        JButton btnUser = new JButton("Quản lý tài khoản");
        btnUser.setFont(buttonFont);
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setFont(buttonFont);

        if ("admin".equals(user.getRole())) {
            buttonPanel.add(btnProduct);
            buttonPanel.add(btnUser);
        }

        buttonPanel.add(btnSale);
        buttonPanel.add(btnCustomer);
        buttonPanel.add(btnInvoice);
        buttonPanel.add(btnLogout);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, buttonPanel);
        splitPane.setDividerLocation(3 * getWidth() / 5);
        splitPane.setDividerSize(0);

        add(splitPane, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(3 * getWidth() / 5);
            }
        });

        btnProduct.addActionListener(e -> showMessage("Đi đến Quản lý sản phẩm"));
        btnSale.addActionListener(e -> new SaleFrame().setVisible(true));
        btnUser.addActionListener(e -> new UserFrame().setVisible(true));
        btnCustomer.addActionListener(e -> new CustomerFrame().setVisible(true));
        btnInvoice.addActionListener(e -> showMessage("Xem hóa đơn"));
        btnLogout.addActionListener(e -> logout());
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/Images/bgr.jpeg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        return imagePanel;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void logout() {
        dispose();
        JOptionPane.showMessageDialog(this, "Bạn đã đăng xuất!");
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
