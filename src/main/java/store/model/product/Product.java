package store.model.product;

import java.util.Objects;

public class Product {
    private String name;
    private Price price;
    private Quantity storeQuantity;
    private Quantity saleQuantity;
    private String promotionName;
    private boolean isPromotion;

    public Product(String name, Price price, Quantity storeQuantity, Quantity saleQuantity, String promotionName, boolean isPromotion) {
        this.name = name;
        this.storeQuantity = storeQuantity;
        this.saleQuantity = saleQuantity;
        this.price = price;
        this.promotionName = promotionName;
        this.isPromotion = isPromotion;
    }

    public static Product from(String input) {
        String[] productInfos = input.split(",");
        if (productInfos.length == 3) {
            return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), "", false);
        }
        if(productInfos[3] == null) {
            return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), productInfos[3], false);
        }
        return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), Quantity.from(0), productInfos[3], true);
    }

    public void sale(Product product, int saleCount) {
        int storeQuantity = product.getStoreQuantity() - saleCount;
        int saleQuantity = product.getSaleQuantity() + saleCount;
        this.storeQuantity = Quantity.from(storeQuantity);
        this.saleQuantity = Quantity.from(saleQuantity);
    }


    public String getName() {
        return name;
    }

    public String getPromotionName() {
        return promotionName;
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
                ", promotionName='" + promotionName + '\'' +
                ", isPromotion=" + isPromotion +
                '}';
    }

}
