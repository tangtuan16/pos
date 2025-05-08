package Services;

import Models.Customer;
import Models.Product;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    public List<Product> getFilteredProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock"),
                        "", rs.getString("category"), ""
                ));
            }
            DBConnection.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public double getProductPrice(int productId) {
        double price = 0;
        String sql = "SELECT price FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("price");
            }
            DBConnection.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public int getStock(int productId) {
        int stock = 0;
        String sql = "SELECT stock FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("stock");
            }
            DBConnection.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public double checkout(List<Product> cart, String phoneNumber) {
        double total = 0;
        double discount = 0;
        double finalTotal = 0;
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                PreparedStatement customerStmt = conn.prepareStatement(
                        "SELECT discount FROM customer WHERE phone = ?");
                customerStmt.setString(1, phoneNumber);
                ResultSet customerRs = customerStmt.executeQuery();
                if (customerRs.next()) {
                    discount = customerRs.getDouble("discount");
                } else {
                    System.out.println("Không tìm thấy khách hàng!");
                }
            }
            PreparedStatement invoiceStmt = conn.prepareStatement(
                    "INSERT INTO invoices (total_price) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            invoiceStmt.setDouble(1, 0);
            invoiceStmt.executeUpdate();

            ResultSet rs = invoiceStmt.getGeneratedKeys();
            rs.next();
            int invoiceId = rs.getInt(1);

            for (Product product : cart) {
                int quantity = product.getQuantity();
                double itemTotal = product.getPrice() * quantity;
                total += itemTotal;

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO invoice_details (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)");
                stmt.setInt(1, invoiceId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, quantity);
                stmt.setDouble(4, itemTotal);
                stmt.executeUpdate();

                PreparedStatement updateStockStmt = conn.prepareStatement(
                        "UPDATE products SET stock = stock - ? WHERE id = ?");
                updateStockStmt.setInt(1, quantity);
                updateStockStmt.setInt(2, product.getId());
                updateStockStmt.executeUpdate();
            }

            double discountAmount = total * (discount / 100);
            finalTotal = total - discountAmount;

            PreparedStatement updateInvoiceStmt = conn.prepareStatement(
                    "UPDATE invoices SET total_price = ? WHERE id = ?");
            updateInvoiceStmt.setDouble(1, finalTotal);
            updateInvoiceStmt.setInt(2, invoiceId);
            updateInvoiceStmt.executeUpdate();

            if (discount > 0 && phoneNumber != null && !phoneNumber.isEmpty()) {
                PreparedStatement updateCustomerStmt = conn.prepareStatement(
                        "UPDATE customer SET total_bill = total_bill + ? WHERE phone = ?");
                updateCustomerStmt.setDouble(1, finalTotal);
                updateCustomerStmt.setString(2, phoneNumber);
                updateCustomerStmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        return finalTotal;
    }


    public Customer findByPhone(String phone) {
        String sql = "SELECT * FROM customer WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                c.setTotalBill(rs.getDouble("total_bill"));
                c.setDiscount(rs.getDouble("discount"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
