package store;

import java.util.Objects;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String description;

    public Product(String name, int price, int quantity, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public static Product from(String input) {
        input = hasZeroQuantity(input);
        System.out.println(input);
        String[] productInfos = input.split(" ");

        return new Product(productInfos[0], covertNumber(productInfos[1]), covertNumber(productInfos[2]), productInfos[3]);
    }

    private static String hasZeroQuantity(String input) {
        if (input.contains("재고 없음")) {
            return input.replaceAll("재고 없음", "0개");
        }
        return input;
    }

    private static int covertNumber(String productInfos) {
        String number = productInfos.replaceAll("[^0-9]", "");
        System.out.println(number);
        return Integer.parseInt(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return quantity == product.quantity && price == product.price && Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, price, description);
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
