package store.model.product;

import java.util.Objects;

public class Price {
    private static final int MIN_PRICE = 0;
    private static final String MIN_PRICE_ERROR_MESSAGE = "가격은 "+ MIN_PRICE +"원 미만일 수 없습니다.";
    private static final String PRICE_ERROR_MESSAGE = "가격은 숫자로 입력해야합니다.";

    private int price;

    private Price(int price) {
        this.price = price;
    }

    public static Price from(int price) {
        isValid(price);
        return new Price(price);
    }

    public static Price from(String price) {
        int number = parse(price);
        isValid(number);
        return new Price(number);
    }

    private static void isValid(int price) {
        if(price < MIN_PRICE) {
            throw new IllegalArgumentException(MIN_PRICE_ERROR_MESSAGE);
        }
    }

    private static int parse(String price) {
        price = price.replaceAll("[^0-9]", "");
        try{
            return Integer.parseInt(price);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(PRICE_ERROR_MESSAGE);
        }
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                '}';
    }

}
