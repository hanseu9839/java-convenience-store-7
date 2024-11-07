package store.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PriceTest {

    @Test
    void 가격_저장_성공_테스트() {
        Price price = Price.from("1100원");
        assertThat(price).isEqualTo(Price.from(1100));
    }

    @Test
    void 가격_저장_실패_테스트() {
        assertThatThrownBy(() -> Price.from(-1100))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
