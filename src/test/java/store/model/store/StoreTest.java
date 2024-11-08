package store.model.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class StoreTest {

    @Test
    void 상품_판매_테스트() {
        Map<String, Products> productList = Map.of("콜라", new Products(List.of(new Product("콜라", Price.from(1000), Quantity.from(3), Quantity.from(0), "탄산2+1", true), new Product("콜라", Price.from(1000), Quantity.from(4), Quantity.from(0), "탄산2+1", true))), "오렌지주스", new Products(List.of(new Product("오렌지주스", Price.from(1800), Quantity.from(2), Quantity.from(0), "주스2+1", true))));

        Store store = new Store(productList);

        assertThat(store.sale("[콜라-4]")).isEqualTo(List.of(new Products(List.of(new Product("콜라", Price.from(1000), Quantity.from(0), Quantity.from(3), "탄산2+1", true), new Product("콜라", Price.from(1000), Quantity.from(3), Quantity.from(1), "탄산2+1", true)))));

    }

    @Test
    void 상품_판매_실패_테스트() {
        Map<String, Products> productList = Map.of("콜라", new Products(List.of(new Product("콜라", Price.from(1000), Quantity.from(10), Quantity.from(0), "탄산2+1", true))), "오렌지주스", new Products(List.of(new Product("오렌지주스", Price.from(1800), Quantity.from(2), Quantity.from(0), "주스2+1", true))));

        Store store = new Store(productList);

        assertThatThrownBy(() -> store.sale("[과자-3],[오렌지주스-1]"))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
