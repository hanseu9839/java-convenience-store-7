package store.view;

import store.model.membership.MemberShip;
import store.model.product.Product;
import store.model.product.Products;
import store.model.sale.SaleProduct;
import store.model.store.Store;
import store.strategy.DateStrategy;
import store.strategy.DateStrategyImpl;

import java.text.DecimalFormat;
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
        int promotionDiscountPrice = 0;
        for (SaleProduct saleProduct : saleProducts) {
            int saleQuantity = products.get(saleProduct.getName()).getProducts()
                    .stream()
                    .mapToInt(Product::getSaleQuantity)
                    .sum();
            int price = products.get(saleProduct.getName()).getProducts().get(0).getPrice();
            totalPrice += (price * saleQuantity);
            totalQuantity += saleQuantity;

            promotionDiscountPrice += makePromotionQuantity(products.get(saleProduct.getName()), new DateStrategyImpl()) * price;
        }

        DecimalFormat format = new DecimalFormat("#,###원");
        System.out.printf("%-10s %6d %10s\n", "총구매액", totalQuantity, format.format(totalPrice));
        System.out.printf("%-10s %13s\n", "행사할인", format.format(promotionDiscountPrice * -1));
        totalPrice -= promotionDiscountPrice;
        int memberShipDisCount = memberShip.disCountMemberShip(products, saleProducts);
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


    private static int makeTotalPrice(Products products) {
        return products.getProducts().get(0).getPrice();
    }

}
