package store;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void 상품_할인_저장_테스트() {
        String input = "콜라 1,000원 10개 탄산2+1";
       Product product = new Product();

        assertThat(product).isEqualTo(new Product("콜라", 1000, 10, "탄산2+1"));
    }

}
