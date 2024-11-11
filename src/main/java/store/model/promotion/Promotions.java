package store.model.promotion;


import store.model.product.Product;
import store.strategy.DateStrategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.util.FileUtil.readFile;

public class Promotions {
    private static final String PROMOTION_FILE_PATH = "/Users/han/Desktop/project/java-convenience-store-7/src/main/resources/promotions.md";

    private static final Map<String, Promotion> promotionMap = new HashMap<>();

    static {
        savePromotion(pull());
    }

    public Promotions() {
    }

    public Promotion getEqualsPromotion(String promotionName) {
        return promotionMap.get(promotionName);
    }

    public static void savePromotion(List<String> promotions) {
        for (int i = 1; i < promotions.size(); i++) {
            Promotion targetPromotion = Promotion.from(promotions.get(i));
            promotionMap.put(targetPromotion.getName(), targetPromotion);
        }
    }

    public static List<String> pull() {
        return readFile(PROMOTION_FILE_PATH);
    }

    public int countNonDiscountPromotions(Product product) {
        Promotion promotion = getEqualsPromotion(product.getPromotionName());

        if (product.getPromotionName().equals("null")) {
            return product.getSaleQuantity();
        }

        return promotion.countNonDiscountPromotion(product);
    }

    public static Map<String, Promotion> getPromotionMap() {
        return promotionMap;
    }

    public boolean isDaysBetween(Product product, DateStrategy dateStrategy) {
        Promotion promotion = promotionMap.get(product.getPromotionName());
        if(promotion==null) {
            return false;
        }

        LocalDateTime now = dateStrategy.now();
        LocalDateTime startDate = promotion.getStartDate();
        LocalDateTime endDate = promotion.getEndDate();
        return (now.isEqual(startDate) || now.isAfter(startDate)) && (now.isEqual(endDate) || now.isBefore(endDate));
    }

    public int remainCountAvailableDiscountPromotions(Product product) {
        Promotion promotion = getEqualsPromotion(product.getPromotionName());

        if (product.getPromotionName().equals("null")) {
            return 0;
        }

        return promotion.remainCountAvailableDiscountPromotion(product);
    }

    public static Promotion getPromotion(String promotionName) {
        return promotionMap.get(promotionName);
    }


    public int promotionCount(List<Product> products) {
        int totalCount = 0;
        int totalPromotionQuantity = 0;
        Promotion promotion = null;
        for (Product product : products) {
            totalCount += product.getSaleQuantity();
            promotion = promotionMap.get(product.getPromotionName());
            if(promotion!= null) {
                totalPromotionQuantity += promotion.promotionQuantity(totalCount);
            }
        }
        return totalPromotionQuantity;
    }

}
