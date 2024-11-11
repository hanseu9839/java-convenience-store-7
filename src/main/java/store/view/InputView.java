package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.membership.MemberShip;
import store.model.product.Product;
import store.model.store.Store;
import store.strategy.DateStrategyImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputView {
    private static final String PRODUCT_QUESTION = "구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_QUESTION = "멤버십 할인을 받으시겠습니까? (Y/N)";

    public static String[] productInput() {
        return Console.readLine().split(" ");
    }

    public static Set<String> productQuestion(Store store, MemberShip memberShip) {
        System.out.println(PRODUCT_QUESTION);
        String saleProductsStr = Console.readLine();
        List<Product> saleProducts = Product.createSalesFrom(saleProductsStr);
        Set<String> sales = store.sale(saleProducts);
        Map<String, Boolean> promotions = store.isPromotions(sales, new DateStrategyImpl());
        boolean isPromotionFlag = isPromotionFlag(sales, promotions);

        if (isPromotionFlag) {
            Map<String, Integer> countNonDiscountPromotionsMap = store.countNonDiscountPromotions(sales);
            boolean flag = countNonDiscountPromotionsMap(sales, countNonDiscountPromotionsMap);

            if(!flag) {
                Map<String, Integer> remainCountAvailableMap = store.remainCountAvailableDiscountPromotions(sales);
                remainCountAvailable(store, sales, remainCountAvailableMap);
            }
        }

        System.out.println(MEMBERSHIP_QUESTION);
        String isMemberShip = Console.readLine();
        if(isMemberShip.equals("Y")) {
            System.out.println("memberShip");
            memberShip.isNotMemberShip();
        }

        return sales;
    }

    private static void remainCountAvailable(Store store, Set<String> sales, Map<String, Integer> remainCountAvailableMap) {
        for (String sale : sales) {
            remainCountAvailableQuestion(store, remainCountAvailableMap, sale);
        }
    }

    private static void remainCountAvailableQuestion(Store store, Map<String, Integer> remainCountAvailableMap, String sale) {
        int remainCountAvailableCount = remainCountAvailableMap.get(sale);
        if (remainCountAvailableCount > 0) {
            System.out.println(sale +"은(는) " + remainCountAvailableCount + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?(Y/N)");
            String remainPromotion = Console.readLine();
            if(remainPromotion.equals("Y")) {
                store.promotionSale(sale);
            }
        }
    }

    private static boolean countNonDiscountPromotionsMap(Set<String> sales, Map<String, Integer> countNonDiscountPromotionsMap) {
        AtomicBoolean flag = new AtomicBoolean(false);

        sales.forEach(sale -> {
            int nonDiscountPromotionsCount = countNonDiscountPromotionsMap.get(sale);
            flag.set(nonDiscountProMotionsQuestion(sale, nonDiscountPromotionsCount));
        });

        return flag.get();
    }

    private static boolean nonDiscountProMotionsQuestion(String sale, int nonDiscountPromotionsCount) {
        if (nonDiscountPromotionsCount > 0) {
            System.out.println("현재 " + sale + " " + nonDiscountPromotionsCount + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
            String nonPromotion = Console.readLine();
            return nonPromotion.equals("Y");
        }

        return false;
    }

    private static boolean isPromotionFlag(Set<String> sales, Map<String, Boolean> promotions) {
        for (String sale : sales) {
            if (promotions.get(sale)) {
                return true;
            }
        }
        return false;
    }


}
