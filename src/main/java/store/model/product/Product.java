package store.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
    private String name;
    private Price price;
    private Quantity storeQuantity;
    private Quantity saleQuantity;
    private String promotionName;
    private int currentQuantity;

    public Product(String name, Price price, Quantity storeQuantity, Quantity saleQuantity, String promotionName) {
        this.name = name;
        this.storeQuantity = storeQuantity;
        this.saleQuantity = saleQuantity;
        this.price = price;
        this.promotionName = promotionName;

    }

    public static Product createInputFrom(String input) {
        String[] productInfos = input.split(" ");
        System.out.println(productInfos[0]);
        if(productInfos.length==4) {
            return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(productInfos[3]), "null");
        }
        return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(productInfos[3]), productInfos[4]);
    }

    public static Product from(String input) {
        String[] productInfos = input.split(",");

        return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), productInfos[3]);
    }

    public void sale(int saleCount) {
        this.storeQuantity.minus(saleCount);
        this.saleQuantity.plus(saleCount);
    }


    public String getName() {
        return name;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getStoreQuantity() {
        return this.storeQuantity.getQuantity();
    }

    public int getSaleQuantity() {
        return saleQuantity.getQuantity();
    }

    public boolean isPromotion() {
        return !promotionName.equals("null") ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", storeQuantity=" + storeQuantity +
                ", saleQuantity=" + saleQuantity +
                ", promotionName='" + promotionName + '\'' +
                '}';
    }

}
