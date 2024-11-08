package store.model.product;

import org.junit.jupiter.api.Test;
import store.model.product.Price;

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
