package store.view;

import store.model.membership.MemberShip;
import store.model.product.Product;
import store.model.product.Products;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.model.sale.SaleProduct;
import store.model.store.Store;
import store.strategy.DateStrategy;
import store.strategy.DateStrategyImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputView {

    private static final String HELLO_SENTENCE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String HEADER_STORE = "==============W 편의점================";
    private static final String PROMOTION_HEADER = "=============증\t정==================";
    private static final String PROMOTION_BOTTOM = "====================================";

    public static void storeList(Store store) {
        System.out.println(HELLO_SENTENCE);
        System.out.println();

        Map<String, Products> stores = store.getStores();
        Set<String> productNames = store.getProductNames();
        for (String productName : productNames) {
            Products products = stores.get(productName);
            storeProduct(products);
        }

    }

    public static void storeProduct(Products products) {
        products.getProducts().forEach(product -> {
            DecimalFormat format = new DecimalFormat("#,###원");
            String quantity = zeroStoreQuantity(product.getStoreQuantity());
            String promotion = promotionName(product.getPromotionName());
            System.out.println("- " + product.getName() + " " + format.format(product.getPrice()) + " " + quantity + " " + promotion);
        });
    }

    public static String zeroStoreQuantity(int storeQuantity) {
        if (storeQuantity == 0) {
            return "재고 없음";
        }
        return storeQuantity + "개";
    }

    public static String promotionName(String name) {
        if (name.equals("null")) {
            return "";
        }
        return name;
    }

    public static void receiptResultView(Set<SaleProduct> saleProducts, Map<String, Products> stores ,MemberShip memberShip) {
        System.out.println(HEADER_STORE);
        System.out.printf("%-10s %6s %10s\n", "상품명", "수량", "금액");
        totalSaleQuantityReceiptResultView(saleProducts);
        promotionOutputView(saleProducts, stores);
        calculateReceiptResultView(saleProducts, stores, memberShip);
    }

    private static void promotionOutputView(Set<SaleProduct> saleProducts, Map<String, Products> stores) {
        System.out.println(PROMOTION_HEADER);

        for (SaleProduct saleProduct : saleProducts) {
            Products products = stores.get(saleProduct.getName());
            int promotionQuantity = makePromotionQuantity(products, new DateStrategyImpl());
            promotionOutputView(saleProduct.getName(), promotionQuantity);
        }
        System.out.println(PROMOTION_BOTTOM);
    }

    private static int makePromotionQuantity(Products products, DateStrategy dateStrategy) {
        return products.promotionCount(dateStrategy);
    }

    private static void promotionOutputView(String productName, int promotionQuantity) {
        if (promotionQuantity > 0) {
            System.out.printf("%-10s %6d\n", productName, promotionQuantity);
        }

    }

    private static void calculateReceiptResultView(Set<SaleProduct> saleProducts, Map<String, Products> products, MemberShip memberShip) {
        int totalPrice = 0;
        int totalQuantity = 0;
        int notPromotionPrice = 0;
        List<Integer> promotionDisCountPrice = new ArrayList<>();
        for (SaleProduct saleProduct : saleProducts) {
           totalQuantity += saleProduct.getQuantity();
           totalPrice += saleProduct.getPrice() * saleProduct.getQuantity();
           int promotionCount = products.get(saleProduct.getName()).promotionCount(new DateStrategyImpl());
           promotionDisCountPrice.add(promotionCount*saleProduct.getPrice());

           if(promotionCount==0) {
               notPromotionPrice += saleProduct.getPrice() * saleProduct.getQuantity();
           }

        }

        DecimalFormat format = new DecimalFormat("#,###원");
        System.out.printf("%-10s %6d %10s\n", "총구매액", totalQuantity, format.format(totalPrice));
        int totalPromotionDisCountPrice = promotionDisCountPrice.stream().mapToInt(Integer::intValue).sum();
        System.out.printf("%-10s %13s\n", "행사할인", format.format(totalPromotionDisCountPrice * -1));
        totalPrice -= totalPromotionDisCountPrice;
        int memberShipDisCount = memberShip.disCountMemberShip(notPromotionPrice);
        System.out.printf("%-10s %17s\n", "멤버십할인", format.format(memberShipDisCount));
        totalPrice -= memberShipDisCount;
        System.out.printf("%-10s %16s\n", "내실돈", format.format(totalPrice));
    }

    private static void totalSaleQuantityReceiptResultView(Set<SaleProduct> saleProducts) {
        for(SaleProduct saleProduct : saleProducts) {
            int totalQuantity = saleProduct.getQuantity();
            int totalPrice = saleProduct.getQuantity() * saleProduct.getPrice();
            System.out.printf("%-10s %6d %10d\n", saleProduct.getName(), totalQuantity, totalPrice);
        }
    }

}
