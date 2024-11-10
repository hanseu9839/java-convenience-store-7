package store.model.store;

import org.junit.jupiter.api.Test;
import store.strategy.DateStrategyImpl;

import java.util.*;

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
        assertThat(store.isPromotions(Set.of("콜라", "물"), new DateStrategyImpl())).isEqualTo(Map.of("물", false, "콜라", true));
    }

    @Test
    void 프로모션_상품_판매__프로모션이_아닌_개수_여부_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        Set<String> saleProducts = store.sale("[콜라-12]");
        assertThat(store.countNonDiscountPromotions(saleProducts)).isEqualTo(Map.of("콜라", 3));
    }

    @Test
    void 프로모션_상품_미_추가_상품_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        Set<String> saleProducts = store.sale("[초코바-1]");
        assertThat(store.remainCountAvailableDiscountPromotions(saleProducts)).isEqualTo(Map.of("초코바",1));
    }


    @Test
    void 프로모션_상품_판매_에러_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.stores();
        assertThatThrownBy(() -> store.sale("초코바"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
