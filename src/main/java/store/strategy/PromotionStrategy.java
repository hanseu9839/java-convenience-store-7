package store.strategy;

@FunctionalInterface
public interface PromotionStrategy {
    int promotion(String event, boolean isPromotion, int saleQuantity);
}
