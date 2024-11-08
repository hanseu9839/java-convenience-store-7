package store.model.product;

import org.junit.jupiter.api.Test;
import store.model.product.Quantity;

import static org.assertj.core.api.Assertions.*;

public class QuantityTest {

    @Test
    void 수량_저장_성공_테스트() {
        Quantity quantity = Quantity.from("10");

        assertThat(quantity).isEqualTo(Quantity.from(10));
    }

    @Test
    void 수량_저장_실패_테스트() {
        assertThatThrownBy(() -> Quantity.from(-10)).isInstanceOf(IllegalArgumentException.class);
    }

}
