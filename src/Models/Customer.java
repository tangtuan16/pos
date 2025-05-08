package Models;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalBill = 0;
    private double discount = 0;

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Customer() {
    }

    public Customer(int id, String name, String email, String phone, String address, double totalBill, double discount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalBill = totalBill;
        this.discount = calculateDiscount();
    }

    public Customer(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


    private double calculateDiscount() {
        double discountPoints = totalBill / 1000000;
        return discountPoints >= 1 ? Math.min(discountPoints, 10) : 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
        this.discount = calculateDiscount();
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return name + " - " + discount + "%";
    }
}

