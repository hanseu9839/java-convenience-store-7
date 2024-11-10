package store.model.product;

import store.model.promotion.Promotions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Products {

    private final List<Product> products;
    private final Promotions promotions;


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
        purchaseProductCount -= salePromotionProduct(purchaseProductCount);
        saleNotPromotionProduct(purchaseProductCount);
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
            product.sale(product, purchaseProductCount);
            return purchaseProductCount;
        }

        if (product.getPromotionName() != null && product.getStoreQuantity() > 0) {
            int productSaleCount = product.getStoreQuantity();
            product.sale(product, productSaleCount);
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
            product.sale(product, purchaseProductCount);
        }
        if (product.getPromotionName() == null && product.getStoreQuantity() > 0) {
            int productSaleCount = product.getStoreQuantity();
            product.sale(product, productSaleCount);
            return productSaleCount;
        }
        return 0;
    }

    public int isPromotionButNotDiscountProductCount() {
        int notDiscountProductCount = 0;
        for (Product product : products) {
            notDiscountProductCount += promotions.isPromotionButNotDiscountProductCount(product);
        }
        return notDiscountProductCount;
    }

    public boolean isPromotions() {
        return products.stream()
                .anyMatch(Product::isPromotion);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
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
