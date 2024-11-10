package store.model.product;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @Test
    void 상품_할인_저장_테스트() {
        String input = "콜라,1,000원,10개,탄산2+1";
        Product product = Product.from(input);

        assertThat(product).isEqualTo(new Product("콜라", Price.from(1000), Quantity.from(10), Quantity.from(0), "탄산2+1", true));
    }

    @Test
    void 싱품_재고_없음_저장_테스트() {
        String input = "오렌지주스,1,800원,0,주스2+1";
        Product product = Product.from(input);
        assertThat(product).isEqualTo(new Product("오렌지주스", Price.from(1800), Quantity.from(0), Quantity.from(0), "주스2+1", true));
    }

    @Test
    void 싱품_재고_저장_테스트() {
        String input = "물,500원,10개";
        Product product = Product.from(input);
        assertThat(product).isEqualTo(new Product("물", Price.from(500), Quantity.from(10), Quantity.from(0), "", false));
    }

}
