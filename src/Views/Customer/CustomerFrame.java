package Views.Customer;

import Controllers.CustomerController;
import Models.Customer;
import Utils.FormatVND;
import Views.BaseFrame;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CustomerFrame extends BaseFrame {
    private JTable customerTable;
    private JTextField nameField, emailField, phoneField, addressField, totalBillField, discountField;
    private JTextField searchPhoneField;
    private JButton addButton, updateButton, deleteButton;
    private CustomerController controller;

    public CustomerFrame() {
        setTitle("Quản lý Khách hàng");
        setSize(800, 600);
        setLocationRelativeTo(null);

        controller = new CustomerController();
        initUI();
        refreshCustomerTable();
    }

    public void initUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Tìm kiếm theo SĐT:"));
        searchPhoneField = new JTextField(15);
        searchPanel.add(searchPhoneField);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(searchButton);
        add(searchPanel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Tên:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("SĐT:"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        addressField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Tổng hóa đơn:"), gbc);
        totalBillField = new JTextField(20);
        totalBillField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(totalBillField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Chiết khấu:"), gbc);
        discountField = new JTextField(20);
        discountField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(discountField, gbc);

        add(formPanel);

        customerTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = customerTable.getSelectedRow();
                if (row != -1) {
                    showCustomerDetails(row);
                }
            }
        });
        add(tableScrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm khách hàng");
        updateButton = new JButton("Cập nhật");
        deleteButton = new JButton("Xóa khách hàng");
        addButton.setBackground(new Color(59, 253, 0));
        updateButton.setBackground(new Color(255, 255, 0));
        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel);

        searchButton.addActionListener(e -> searchCustomerByPhone());
        addButton.addActionListener(e -> new AddCustomerFrame(this).setVisible(true));
        updateButton.addActionListener(e -> updateCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
    }

    private void showCustomerDetails(int row) {
        nameField.setText(customerTable.getValueAt(row, 1).toString());
        emailField.setText(customerTable.getValueAt(row, 2).toString());
        phoneField.setText(customerTable.getValueAt(row, 3).toString());
        addressField.setText(customerTable.getValueAt(row, 4).toString());
        totalBillField.setText(customerTable.getValueAt(row, 5).toString());
        discountField.setText(customerTable.getValueAt(row, 6).toString());
    }

    public void refreshCustomerTable() {
        List<Customer> customers = controller.loadCustomers();
        setCustomerList(customers);
    }

    public int getSelectedCustomerId() {
        int row = customerTable.getSelectedRow();
        if (row != -1) {
            return (int) customerTable.getValueAt(row, 0);
        }
        return -1;
    }

    public void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        totalBillField.setText("0");
        discountField.setText("0");
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

    public String getSearchPhone() {
        return searchPhoneField.getText().trim();
    }

    private void searchCustomerByPhone() {
        String phone = getSearchPhone();
        List<Customer> customers = controller.searchCustomerByPhone(phone);
        if (customers.isEmpty()) {
            showError("Không tìm thấy khách hàng với số điện thoại này.");
        } else {
            setCustomerList(customers);
        }
    }

    private void updateCustomer() {
        int id = getSelectedCustomerId();
        if (id == -1) {
            showError("Vui lòng chọn khách hàng để sửa!");
            return;
        }

        String name = getName();
        String email = getEmail();
        String phone = getPhone();
        String address = getAddress();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showError("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        Customer customer = new Customer(id, name, email, phone, address);
        boolean isUpdated = controller.updateCustomer(customer);

        if (isUpdated) {
            clearForm();
            refreshCustomerTable();
            showMessage("Thông tin khách hàng đã được cập nhật!");
        } else {
            showError("Lỗi khi cập nhật khách hàng.");
        }
    }

    private void deleteCustomer() {
        int id = getSelectedCustomerId();
        if (id == -1) {
            showError("Vui lòng chọn khách hàng để xóa!");
            return;
        }

        boolean confirm = confirmYesNo("Bạn chắc chắn muốn xóa chứ ?");
        if (!confirm) return;
        boolean isDeleted = controller.deleteCustomer(id);

        if (isDeleted) {
            refreshCustomerTable();
            showMessage("Khách hàng đã được xóa!");
        } else {
            showError("Lỗi khi xóa khách hàng.");
        }
    }

    private void setCustomerList(List<Customer> customers) {
        String[] columns = {"ID", "Tên", "Email", "SĐT", "Địa chỉ", "Tổng hóa đơn", "Chiết khấu"};
        Object[][] data = new Object[customers.size()][7];

        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getName();
            data[i][2] = c.getEmail();
            data[i][3] = c.getPhone();
            data[i][4] = c.getAddress();
            data[i][5] = FormatVND.format(c.getTotalBill());
            data[i][6] = c.getDiscount();
        }

        customerTable.setModel(new DefaultTableModel(data, columns));
    }
}
