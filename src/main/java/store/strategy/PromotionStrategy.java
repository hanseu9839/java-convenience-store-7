package store.strategy;

@FunctionalInterface
public interface PromotionStrategy {
    int event(String input);
}
