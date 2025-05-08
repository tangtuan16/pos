package Models;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int quantity;
    private String supplier;
    private String category;
    private String barcode;

    public Product(int id, String name, double price, int stock, String supplier, String category, String barcode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.quantity = 1;
        this.supplier = supplier;
        this.category = category;
        this.barcode = barcode;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getQuantity() { return quantity; }
    public String getSupplier() { return supplier; }
    public String getCategory() { return category; }
    public String getBarcode() { return barcode; }

    public void setStock(int stock) { this.stock = stock; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " (" + category + ") - " + price + " VND [" + supplier + "]";
    }
}
