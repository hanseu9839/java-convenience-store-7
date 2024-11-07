package store.product;

import java.util.Objects;

public class Product {
    private String name;
    private Price price;
    private final Quantity quantity;
    private String description;

    public Product(String name, Price price, Quantity quantity, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public static Product from(String input) {
        input = hasZeroQuantity(input);
        String[] productInfos = input.split(" ");

        return new Product(productInfos[0], Price.from(productInfos[1]), Quantity.from(productInfos[2]), productInfos[3]);
    }

    private static String hasZeroQuantity(String input) {
        if (input.contains("재고 없음")) {
            return input.replaceAll("재고 없음", "0개");
        }
        return input;
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
                ", quantity=" + quantity +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

}
