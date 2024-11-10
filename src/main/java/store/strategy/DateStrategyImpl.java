package store.strategy;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;

public class DateStrategyImpl implements DateStrategy {
    @Override
    public LocalDateTime now() {
        return DateTimes.now();
    }

}
