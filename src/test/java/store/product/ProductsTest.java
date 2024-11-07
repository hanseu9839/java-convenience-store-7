package store.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProductsTest {

    @Test
    void 상품들_저장_테스트() {
        Products products = new Products(new ArrayList<>());

        products.add("콜라 1,000원 10개 탄산2+1");
        products.add("오렌지주스 1,800원 재고 없음 주스2+1");

        assertThat(products.getProducts()).isEqualTo(List.of(new Product("콜라", Price.from(1000), Quantity.from(10), "탄산2+1"), new Product("오렌지주스", Price.from(1800), Quantity.from(0), "주스2+1")));
    }



}
