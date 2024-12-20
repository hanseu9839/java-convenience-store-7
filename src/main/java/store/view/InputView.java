package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.membership.MemberShip;
import store.model.sale.SaleProduct;
import store.model.store.Store;
import store.strategy.DateStrategyImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputView {
    private static final String PRODUCT_QUESTION = "구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_QUESTION = "멤버십 할인을 받으시겠습니까? (Y/N)";

    public static Set<SaleProduct> productQuestion(Store store, MemberShip memberShip) {
        System.out.println(PRODUCT_QUESTION);
        String saleProductsStr = Console.readLine();

        List<SaleProduct> saleProducts = SaleProduct.createSalesFrom(saleProductsStr, store);
        Set<SaleProduct> sales = store.sale(saleProducts);
        Map<String, Boolean> promotions = store.isPromotions(sales, new DateStrategyImpl());

        for (SaleProduct saleProduct : saleProducts) {
            if (promotions.get(saleProduct.getName())) {
                Map<String, Integer> countNonDiscountPromotionsMap = store.countNonDiscountPromotions(saleProduct);

                if(nonDiscountProMotionsQuestion(saleProduct.getName(), countNonDiscountPromotionsMap.get(saleProduct.getName()))) {
                    continue;
                }

                Map<String, Integer> remainCountAvailableMap = store.remainCountAvailableDiscountPromotions(saleProduct);
                remainCountAvailable(store, saleProduct, remainCountAvailableMap);
            }
        }

        System.out.println(MEMBERSHIP_QUESTION);
        String isMemberShip = Console.readLine();
        if (isMemberShip.equals("Y")) {
            memberShip.isNotMemberShip();
        }

        return sales;
    }

    private static void remainCountAvailable(Store store, SaleProduct saleProduct, Map<String, Integer> remainCountAvailableMap) {
        remainCountAvailableQuestion(store, remainCountAvailableMap, saleProduct.getName());
    }

    private static void remainCountAvailableQuestion(Store store, Map<String, Integer> remainCountAvailableMap, String sale) {
        int remainCountAvailableCount = remainCountAvailableMap.get(sale);
        if (remainCountAvailableCount > 0) {
            System.out.println(sale + "은(는) " + remainCountAvailableCount + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?(Y/N)");
            String remainPromotion = Console.readLine();
            if (remainPromotion.equals("Y")) {
                store.promotionSale(sale);
            }
        }
    }

    private static boolean nonDiscountProMotionsQuestion(String sale, int nonDiscountPromotionsCount) {
        if (nonDiscountPromotionsCount > 0) {
            System.out.println("현재 " + sale + " " + nonDiscountPromotionsCount + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
            String nonPromotion = Console.readLine();
            return nonPromotion.equals("Y");
        }

        return false;
    }


}
