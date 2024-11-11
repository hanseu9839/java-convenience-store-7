package store.strategy;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateStrategy {
    LocalDateTime now();
}
