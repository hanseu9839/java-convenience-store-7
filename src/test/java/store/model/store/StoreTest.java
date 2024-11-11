package store.model.store;

import org.junit.jupiter.api.Test;
import store.model.product.Product;
import store.model.sale.SaleProduct;
import store.strategy.DateStrategyImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StoreTest {

    @Test
    void 상품_저장_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        assertThat(store.fileStores()).isEqualTo(Set.of("콜라", "사이다", "오렌지주스", "탄산수", "물", "비타민워터", "감자칩", "초코바", "에너지바", "정식도시락", "컵라면"));

    }

    @Test
    void 상품_판매_실패_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.fileStores();
        List<SaleProduct> sales = SaleProduct.createSalesFrom("[과자-3],[오렌지주스-1]", store);
        assertThatThrownBy(() -> store.sale(sales))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 상품_판매_프로모션_여부_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.fileStores();
        assertThat(store.isPromotions(Set.of(new SaleProduct("물"), new SaleProduct("콜라")), new DateStrategyImpl())).isEqualTo(Map.of("물", false, "콜라", true));
    }

    @Test
    void 프로모션_상품_판매__프로모션이_아닌_개수_여부_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.fileStores();
        List<SaleProduct> sales = SaleProduct.createSalesFrom("[콜라-12]", store);
        Set<SaleProduct> saleProducts = store.sale(sales);
        assertThat(store.countNonDiscountPromotions(saleProducts)).isEqualTo(Map.of("콜라", 3));
    }

    @Test
    void 프로모션_상품_미_추가_상품_테스트() {
        Store store = new Store(new HashMap<>(), new HashSet<>());
        store.fileStores();
        List<SaleProduct> sales = SaleProduct.createSalesFrom("[초코바-1]", store);
        Set<SaleProduct> saleProducts = store.sale(sales);
        assertThat(store.remainCountAvailableDiscountPromotions(saleProducts)).isEqualTo(Map.of("초코바", 1));
    }

}
