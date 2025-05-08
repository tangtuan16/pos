package Controllers;

import Models.Customer;
import Models.Product;
import Services.SaleService;

import java.util.ArrayList;
import java.util.List;

public class SaleController {
    private SaleService saleService;
    private List<Product> cart;

    public SaleController() {
        saleService = new SaleService();
        cart = new ArrayList<>();
    }

    public List<Product> getProducts(String keyword) {
        return saleService.getFilteredProducts(keyword);
    }

    public void addToCart(int productId, String name, String category, int stock) {
        for (Product product : cart) {
            if (product.getId() == productId) {
                if (product.getQuantity() < stock) {
                    product.setQuantity(product.getQuantity() + 1);
                } else {
                    System.out.println("Không thể mua quá số lượng tồn kho!");
                }
                return;
            }
        }
        cart.add(new Product(productId, name, saleService.getProductPrice(productId), 1, "", category, ""));
    }

    public void updateQuantity(int productId, int amount) {
        for (Product product : cart) {
            if (product.getId() == productId) {
                int newQty = Math.min(product.getQuantity() + amount, saleService.getStock(productId));
                product.setQuantity(Math.max(newQty, 0));
                break;
            }
        }
    }

    public void removeFromCart(int productId) {
        cart.removeIf(product -> product.getId() == productId);
    }

    public List<Product> getCart() {
        return new ArrayList<>(cart);
    }

    public double checkout(String phone) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống, không thể thanh toán!");
            return 0;
        }
        double total = saleService.checkout(cart, phone);
        cart.clear();
        return total;
    }

    public void clearCart() {
        cart.clear();
    }


    public Customer findCustomerByPhone(String phone) {
        return saleService.findByPhone(phone);
    }

    public double getDiscountRateByPhone(String phone) {
        Customer customer = findCustomerByPhone(phone);
        if (customer == null) return 0;
        return customer.getDiscount();
    }



    public double getProductPrice(int productId) {
        return saleService.getProductPrice(productId);
    }

    public int getStock(int productId) {
        return saleService.getStock(productId);
    }
}
