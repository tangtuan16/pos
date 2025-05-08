package Services;

import Models.Customer;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("total_bill"),
                        rs.getDouble("discount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, email, phone, address, total_bill, discount) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setDouble(5, customer.getTotalBill());
            stmt.setDouble(6, customer.getDiscount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE customer SET name=?, email=?, phone=?, address=?, total_bill=?, discount=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            ps.setDouble(5, c.getTotalBill());
            ps.setDouble(6, c.getDiscount());
            ps.setInt(7, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        String backupSql = "INSERT INTO customer_backup SELECT * FROM customer WHERE id=?";
        String deleteSql = "DELETE FROM customer WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement backupStmt = conn.prepareStatement(backupSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            backupStmt.setInt(1, id);
            deleteStmt.setInt(1, id);
            backupStmt.executeUpdate();
            return deleteStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Customer> searchCustomerByPhone(String phone) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE phone LIKE ? ORDER BY id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + phone + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDouble("total_bill"),
                            rs.getDouble("discount")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
