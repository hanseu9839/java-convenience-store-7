package store.model.product;

import java.util.Objects;

public class Product {
    private String name;
    private Price price;
    private Quantity storeQuantity;
    private Quantity saleQuantity;
    private String description;
    private boolean isPromotion;

    public Product(String name, Price price, Quantity storeQuantity, Quantity saleQuantity, String description, boolean isPromotion) {
        this.name = name;
        this.storeQuantity = storeQuantity;
        this.saleQuantity = saleQuantity;
        this.price = price;
        this.description = description;
        this.isPromotion = isPromotion;
    }

    public static Product from(String input) {
        input = hasZeroQuantity(input);
        String[] productInfos = input.split(" ");
        if (productInfos.length == 3) {
            return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), "", false);
        }
        return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), productInfos[3], true);
    }

    public void sale(Product product, int saleCount) {
        int storeQuantity = product.getStoreQuantity() - saleCount;
        int saleQuantity = product.getSaleQuantity() + saleCount;
        this.storeQuantity = Quantity.from(storeQuantity);
        this.saleQuantity = Quantity.from(saleQuantity);
    }

    private static String hasZeroQuantity(String input) {
        if (input.contains("재고 없음")) {
            return input.replaceAll("재고 없음", "0개");
        }
        return input;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public int getStoreQuantity() {
        return storeQuantity.getQuantity();
    }

    public int getSaleQuantity() {
        return saleQuantity.getQuantity();
    }

    public boolean isPromotion() {
        return isPromotion;
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
                ", description='" + description + '\'' +
                ", isPromotion=" + isPromotion +
                '}';
    }

}
