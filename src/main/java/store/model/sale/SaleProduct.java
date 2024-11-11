package store.model.sale;

import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;
import store.model.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaleProduct {
    private static final String EMPTY_DELIMITER = "";
    private static final String PRODUCTS_REPLACE_DELIMITER = "\\[|\\]";
    private static final String PRODUCTS_SPLIT_DELIMITER = ",";
    private static final String PRODUCT_SPLIT_DELIMITER = "-";
    private static final String INPUT_ERROR = "잘못된 입력입니다. 다시 입력해 주세요.";

    private String name;
    private Price price;
    private Quantity quantity;
    private String promotionName;

    public SaleProduct(String name, int price, int quantity, String promotionName) {
        this.name = name;
        this.quantity = Quantity.from(quantity);
        this.price = Price.from(price);
        this.promotionName = promotionName;
    }

    public SaleProduct(String name, Price price, Quantity quantity, String promotionName) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.promotionName = promotionName;
    }

    public static List<SaleProduct> createSalesFrom(String input, Store store) {
        input = input.replaceAll(PRODUCTS_REPLACE_DELIMITER, EMPTY_DELIMITER);
        String[] products = input.split(PRODUCTS_SPLIT_DELIMITER);

        List<SaleProduct> makeProductList = new ArrayList<>();
        try {
            for (String product : products) {
                String[] productArr = product.split(PRODUCT_SPLIT_DELIMITER);
                Product targetProduct = store.getStores().get(productArr[0]).findProduct(productArr[0]);
                makeProductList.add(new SaleProduct(productArr[0], targetProduct.getPrice(), Integer.parseInt(productArr[1]), EMPTY_DELIMITER));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }

        return makeProductList;
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
