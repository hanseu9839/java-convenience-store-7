package store.model.product;

import java.util.Objects;

public class Quantity {
    private static final String QUANTITY_ERROR_MESSAGE = "수량을 잘못 입력하였습니다.";
    private static final String QUANTITY_COUNT_ERROR_MESSAGE = "수량은 0미만으로 떨어질 수 없습니다.";
    private static final int QUANTITY_MIN_VALUE = 0;

    private int quantity;

    private Quantity(int quantity) {
        this.quantity = quantity;
    }

    public static Quantity from(String quantity) {
        int number = parse(quantity);
        isValid(number);
        return new Quantity(number);
    }

    public static Quantity from(int quantity) {
        isValid(quantity);
        return new Quantity(quantity);
    }

    private static int parse(String quantity) {
        quantity = quantity.replaceAll("[^0-9]", "");
        try{
            return Integer.parseInt(quantity);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(QUANTITY_ERROR_MESSAGE);
        }
    }

    private static void isValid(int quantity) {
        if(quantity < QUANTITY_MIN_VALUE) {
           throw new IllegalArgumentException(QUANTITY_COUNT_ERROR_MESSAGE);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void minus(int saleCount) {
        this.quantity -= saleCount;
    }

    public void plus(int saleCount) {
        this.quantity += saleCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "quantity=" + quantity +
                '}';
    }

    public void applySale(int quantity) {
        this.quantity = quantity;
    }


}
