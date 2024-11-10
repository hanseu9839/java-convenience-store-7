package store.model.promotion;

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

    public static void savePromotion(List<String> promotions) {
        for(int i=1; i< promotions.size(); i++) {
            Promotion targetPromotion = Promotion.from(promotions.get(i));
            promotionMap.put(targetPromotion.getName(), targetPromotion);
        }
    }

    public static List<String> pull() {
        return readFile(PROMOTION_FILE_PATH);
    }

    public static Map<String, Promotion> getPromotionMap() {
        return promotionMap;
    }

}
