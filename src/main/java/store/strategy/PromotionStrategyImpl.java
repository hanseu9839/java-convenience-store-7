package store.strategy;

public class PromotionStrategyImpl implements PromotionStrategy{

    @Override
    public int promotion(String event, boolean isPromotion, int saleQuantity) {
        if(!isPromotion) {
            return 0;
        }
        makeEvent(event);
        return 2;
    }

    private int makeEvent(String event) {
        event = event.replaceAll("[^0-9]", "");
        int promotionQuantity = Integer.parseInt(event.substring(0,1));
        int promotionPlusQuantity = Integer.parseInt(event.substring(1,2));
        System.out.println("promotionQuantity :: "+ promotionQuantity);
        System.out.println("promotionPlusQuantity :: " + promotionPlusQuantity);
        return  1;
    }
}
