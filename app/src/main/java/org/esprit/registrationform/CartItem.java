package org.esprit.registrationform;

public class CartItem {
   private int id;
    private String productTitle;
    private String description;
    private int price;
    private int quantity;

    public CartItem(String productTitle, String description, int price, int quantity) {
        this.productTitle = productTitle;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id= id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
    public int id() {
        return id;
    }

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

