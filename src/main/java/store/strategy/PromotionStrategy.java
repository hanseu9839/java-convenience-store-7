package store.strategy;

@FunctionalInterface
public interface PromotionStrategy {
    int promotion(String event, int saleQuantity);
}
