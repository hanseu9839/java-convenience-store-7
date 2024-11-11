package store.model.sale;

import store.model.product.Price;
import store.model.product.Quantity;

import java.util.List;
import java.util.Objects;

public class SaleProduct {

    private String name;
    private Price price;
    private Quantity quantity;
    private String promotionName;


    public SaleProduct(String name, Price price, Quantity quantity, String promotionName) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.promotionName = promotionName;
    }

    public SaleProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public String getPromotionName() {
        return promotionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProduct that = (SaleProduct) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity) && Objects.equals(promotionName, that.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, promotionName);
    }

    @Override
    public String toString() {
        return "SaleProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotionName='" + promotionName + '\'' +
                '}';
    }

}
