package store.model.product;

import store.model.promotion.Promotions;
import store.strategy.DateStrategy;

import java.util.List;
import java.util.Objects;

public class Products {

    private static final String STORE_QUANTITY_ERROR = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    private List<Product> products;
    private Promotions promotions;


    public Products(List<Product> products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public void add(Product product) {
        products.add(product);
    }

    public void add(String input) {
        Product product = Product.from(input);
        products.add(product);
    }

    public void sale(int purchaseProductCount) {
        // 7개 - 10개\
        checkSaleProductCount(purchaseProductCount);
        purchaseProductCount -= salePromotionProduct(purchaseProductCount);
        saleNotPromotionProduct(purchaseProductCount);
    }

    private void checkSaleProductCount(int purchaseProductCount) {
        int totalStoreProductQuantity = products.stream()
                .mapToInt(Product::getStoreQuantity)
                .sum();

        if (totalStoreProductQuantity < purchaseProductCount) {
            throw new IllegalArgumentException(STORE_QUANTITY_ERROR);
        }
    }

    private int salePromotionProduct(int purchaseProductCount) {
        int count = 0;
        for (Product product : products) {
            count += salePromotionProductCount(product, purchaseProductCount);
            purchaseProductCount -= count;
        }
        return count;
    }

    private int salePromotionProductCount(Product product, int purchaseProductCount) {
        if (product.getPromotionName() != null && product.getStoreQuantity() > 0 && product.getStoreQuantity() >= purchaseProductCount) {
            product.sale(purchaseProductCount);
            return purchaseProductCount;
        }

        if (product.getPromotionName() != null && product.getStoreQuantity() > 0) {
            int productSaleCount = product.getStoreQuantity();
            product.sale(productSaleCount);
            return productSaleCount;
        }

        return 0;
    }

    private int saleNotPromotionProduct(int purchaseProductCount) {
        int count = 0;
        for (Product product : products) {
            count += saleNotPromotionProductCount(product, purchaseProductCount);
            purchaseProductCount -= count;
        }
        return count;
    }

    private int saleNotPromotionProductCount(Product product, int purchaseProductCount) {
        if (product.getPromotionName() == null && product.getStoreQuantity() > 0 && product.getStoreQuantity() >= purchaseProductCount) {
            product.sale(purchaseProductCount);
        }
        if (product.getPromotionName() == null && product.getStoreQuantity() > 0) {
            int productSaleCount = product.getStoreQuantity();
            product.sale(productSaleCount);
            return productSaleCount;
        }
        return 0;
    }

    public int countNonDiscountPromotions() {
        int notDiscountProductCount = 0;
        for (Product product : products) {
            notDiscountProductCount += promotions.countNonDiscountPromotions(product);
        }
        return notDiscountProductCount;
    }

    public boolean isPromotions(DateStrategy dateStrategy) {
        boolean productPromotion = products.stream()
                .anyMatch(Product::isPromotion);

        if (!productPromotion) {
            return false;
        }

        return products.stream()
                .anyMatch(product -> promotions.isDaysBetween(product, dateStrategy));
    }

    public int remainCountAvailableDiscountPromotions() {
        int remainCountAvailableDiscount = 0;
        for (Product product : products) {
            remainCountAvailableDiscount += promotions.remainCountAvailableDiscountPromotions(product);
        }
        return remainCountAvailableDiscount;
    }

    public int promotionCount(DateStrategy dateStrategy) {
        if(!isPromotions(dateStrategy)) {
            return 0;
        }

       return promotions.promotionCount(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getSize() {
        return products.size();
    }

    public Product findProduct(String productName) {
        return products.stream().filter(product -> product.getName().equals(productName)).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products1 = (Products) o;
        return Objects.equals(products, products1.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "Products{" +
                "products=" + products +
                ", promotions=" + promotions +
                '}';
    }


}
