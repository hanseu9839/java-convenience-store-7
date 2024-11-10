package store.model.promotion;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionsTest {

    @Test
    void 프로모션_할인율_저장_확인_테스트() {
         assertThat(Promotions.getPromotionMap()).isEqualTo(Map.of("탄산2+1", Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31"), "MD추천상품", Promotion.from("MD추천상품,1,1,2024-01-01,2024-12-31"), "반짝할인", Promotion.from("반짝할인,1,1,2024-11-01,2024-11-30")));
    }

}
