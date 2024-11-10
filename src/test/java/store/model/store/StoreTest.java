package store.model.store;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StoreTest {

    @Test
    void 상품_저장_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        assertThat(store.stores()).isEqualTo(Set.of("콜라", "사이다", "오렌지주스", "탄산수", "물", "비타민워터", "감자칩", "초코바", "에너지바", "정식도시락", "컵라면"));

    }

    @Test
    void 상품_판매_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();

        assertThat(store.sale("[콜라-4],[초코바-1]")).isEqualTo(Set.of("콜라", "초코바"));
    }

    @Test
    void 상품_판매_실패_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();

        assertThatThrownBy(() -> store.sale("[과자-3],[오렌지주스-1]"))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 상품_판매_프로모션_여부_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        assertThat(store.isPromotions(Set.of("콜라", "물"))).isEqualTo(List.of(true, false));
    }

    @Test
    void 프로모션_상품_판매__프로모션이_아닌_개수_여부_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        Set<String> saleProducts = store.sale("[콜라-12]");
        assertThat(store.countNonDiscountPromotions(saleProducts)).isEqualTo(3);
    }


}
