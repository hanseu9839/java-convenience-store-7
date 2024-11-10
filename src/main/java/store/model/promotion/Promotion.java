package store.model.promotion;

import store.model.product.Product;
import store.model.product.Quantity;
import store.strategy.PromotionStrategy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Promotion implements PromotionStrategy {
    private final String name;
    private final Quantity buy;
    private final Quantity get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, Quantity buy, Quantity get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String promotion) {
        String[] promotionArr = parse(promotion);
        LocalDateTime startDate = makeLocalDateTime(promotionArr[3]);
        LocalDateTime endDate = makeLocalDateTime(promotionArr[4]);

        return new Promotion(promotionArr[0], Quantity.from(promotionArr[1]), Quantity.from(promotionArr[2]),startDate, endDate);
    }

    private static String[] parse(String promotion) {
        return promotion.split(",");
    }

    private static LocalDateTime makeLocalDateTime(String dateTime) {
        int[] dateTimeArr = Arrays.stream(dateTime.split("-")).mapToInt(Integer::parseInt).toArray();
        return LocalDateTime.of(dateTimeArr[0], dateTimeArr[1], dateTimeArr[2],0,0);
    }

    public String getName() {
        return name;
    }

    @Override
    public int promotion(String event, int saleQuantity) {
        return 0;
    }

    public int countNonDiscountPromotion(Product product) {
        System.out.println(product);

        System.out.println(product.getSaleQuantity() % buy.getQuantity() + get.getQuantity());
       return product.getSaleQuantity() % buy.getQuantity() + get.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(name, promotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
