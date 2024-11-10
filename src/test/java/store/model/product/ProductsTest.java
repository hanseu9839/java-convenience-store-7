package store.model.product;

import org.junit.jupiter.api.Test;
import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProductsTest {

    @Test
    void 상품들_저장_테스트() {
        Products products = new Products(new ArrayList<>());

        products.add("콜라,1,000원,10개,탄산2+1");
        products.add("오렌지주스,1,800원,재고 없음,주스2+1");

        assertThat(products.getProducts()).isEqualTo(List.of(new Product("콜라", Price.from(1000), Quantity.from(10), Quantity.from(0), "탄산2+1", true), new Product("오렌지주스", Price.from(1800), Quantity.from(0), Quantity.from(0), "주스2+1", true)));
    }


}
