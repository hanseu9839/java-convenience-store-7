package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @Test
    void 상품_할인_저장_테스트() {
        String input = "콜라 1,000원 10개 탄산2+1";
       Product product = Product.from(input);

        assertThat(product).isEqualTo(new Product("콜라", 1000, 10, "탄산2+1"));
    }

    @Test
    void 싱품_재고_없음_저장_테스트() {
        String input = "오렌지주스 1,800원 재고 없음 주스2+1";
        Product product = Product.from(input);
        assertThat(product).isEqualTo(new Product("오렌지주스", 1800, 0, "주스2+1"));
    }

}
