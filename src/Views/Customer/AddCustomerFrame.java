package Views.Customer;

import Controllers.CustomerController;
import Models.Customer;
import Views.BaseFrame;

import javax.swing.*;
import java.awt.*;

public class AddCustomerFrame extends BaseFrame {
    private JTextField nameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextField phoneField = new JTextField(20);
    private JTextField addressField = new JTextField(20);
    private JButton saveButton = new JButton("Lưu");
    private CustomerController controller;
    private CustomerFrame customerFrame;

    public AddCustomerFrame(CustomerFrame customerFrame) {
        this.customerFrame = customerFrame;
        this.controller = new CustomerController();

        setTitle("Thêm Khách Hàng");
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        saveButton.setBackground(new Color(0, 123, 255));
        saveButton.setForeground(Color.WHITE);

        saveButton.addActionListener(e -> {
            String name = getName();
            String email = getEmail();
            String phone = getPhone();
            String address = getAddress();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                showError("Vui lòng điền đầy đủ thông tin!");
                return;
            }

            Customer customer = new Customer(name, email, phone, address);
            controller.addCustomer(customer);
            customerFrame.refreshCustomerTable();
            JOptionPane.showMessageDialog(this, "Khách hàng đã được thêm!");
            dispose();
        });

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }
}
