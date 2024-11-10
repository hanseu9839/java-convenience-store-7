package store.model.store;

import org.junit.jupiter.api.Test;
import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StoreTest {

    @Test
    void 상품_저장_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        assertThat(store.getProductNames()).isEqualTo(Set.of("콜라","사이다", "오렌지주스", "탄산수", "물", "비타민워터", "감자칩", "초코바", "에너지바", "정식도시락", "컵라면"));

    }

    @Test
    void 상품_판매_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();

        assertThat(store.sale("[콜라-4],[초코바-1]")).isEqualTo(List.of(new Products(List.of(new Product("콜라", Price.from(1000), Quantity.from(6), Quantity.from(4), "탄산2+1", true), new Product("콜라", Price.from(1000), Quantity.from(10), Quantity.from(0), null, true))), new Products(List.of(new Product("초코바", Price.from(1200), Quantity.from(4), Quantity.from(1), "MD추천상품", true), new Product("초코바", Price.from(1200), Quantity.from(5), Quantity.from(0), null, true)))));
    }

    @Test
    void 상품_판매_실패_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();

        assertThatThrownBy(() -> store.sale("[과자-3],[오렌지주스-1]"))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
