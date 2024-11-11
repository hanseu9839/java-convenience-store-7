package store.model.store;

import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;
import store.model.promotion.Promotions;
import store.model.sale.SaleProduct;
import store.strategy.DateStrategy;
import store.strategy.DateStrategyImpl;

import java.util.*;

import static store.util.FileUtil.readFile;

public class Store {
    private static final String NOT_EXIST_PRODUCT_ERROR = "존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private static final String INPUT_ERROR = "잘못된 입력입니다. 다시 입력해 주세요.";
    private static final String PRODUCTS_FILE_PATH = "/Users/han/Desktop/project/java-convenience-store-7/src/main/resources/products.md";

    private final Map<String, Products> stores;
    private final Set<String> productNames;

    public Store(Map<String, Products> stores, Set<String> productNames) {
        this.stores = stores;
        this.productNames = productNames;
    }

    public Set<String> inputStores(String[] products) {
        for (String productStr : products) {
            Product product = Product.createInputFrom(productStr);
            Products targetProducts = stores.getOrDefault(product.getName(), new Products(new ArrayList<>(), new Promotions()));
            targetProducts.add(product);
            stores.put(product.getName(), targetProducts);
            productNames.add(product.getName());
        }
        return productNames;
    }

    public Set<String> fileStores() {
        List<String> products = pull();
        for (int i = 1; i < products.size(); i++) {
            Product product = Product.from(products.get(i));
            Products targetProducts = stores.getOrDefault(product.getName(), new Products(new ArrayList<>(), new Promotions()));
            targetProducts.add(product);
            stores.put(product.getName(), targetProducts);
            productNames.add(product.getName());
        }

        for (String productName : productNames) {
            Products targetProduct = stores.get(productName);
            boolean promotions = targetProduct.isPromotions(new DateStrategyImpl());
            Product product = targetProduct.getProducts().getFirst();

            if (promotions && targetProduct.getSize() < 2) {
                targetProduct.add(new Product(product.getName(), Price.from(product.getPrice()), Quantity.from(0), Quantity.from(0), "null"));
            }

        }

        return productNames;
    }

    public List<String> pull() {
        return readFile(PRODUCTS_FILE_PATH);
    }

    public Set<SaleProduct> sale(List<Product> saleProducts) {
        Set<SaleProduct> saleProductNames = new LinkedHashSet<>();

        for (Product saleProduct : saleProducts) {
            Products products = isValid(saleProduct.getName());
            products.sale(saleProduct.getStoreQuantity());
            SaleProduct saleProductTarget = new SaleProduct(saleProduct.getName(), Price.from(products.getProducts().get(0).getPrice()), Quantity.from(saleProduct.getSaleQuantity()), products.getProducts().get(0).getPromotionName());
            saleProductNames.add(saleProductTarget);
        }

        return saleProductNames;
    }

    public void promotionSale(String sale) {
        Products products = stores.get(sale);
        products.sale(1);
    }

    private Products isValid(String productName) {
        Products products = stores.get(productName);

        if (products == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERROR);
        }

        return products;
    }

    public Map<String, Boolean> isPromotions(Set<SaleProduct> saleProducts, DateStrategy dateStrategy) {
        Map<String, Boolean> isPromotions = new HashMap<>();

        for (SaleProduct saleProduct : saleProducts) {
            Products products = stores.get(saleProduct.getName());
            checkExistsProduct(products);
            boolean promotions = products.isPromotions(dateStrategy);
            isPromotions.put(saleProduct.getName(), promotions);
        }

        return isPromotions;
    }

    private void checkExistsProduct(Products products) {
        if (products == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERROR);
        }
    }

    public Map<String, Integer> countNonDiscountPromotions(Set<SaleProduct> saleProducts) {
        Map<String, Integer> nonDisCountPromotionsMap = new HashMap<>();
        for (SaleProduct saleProduct : saleProducts) {
            Products products = stores.get(saleProduct.getName());
            nonDisCountPromotionsMap.put(saleProduct.getName(), products.countNonDiscountPromotions());
        }

        return nonDisCountPromotionsMap;
    }

    public Map<String, Integer> remainCountAvailableDiscountPromotions(Set<SaleProduct> saleProducts) {
        Map<String, Integer> remainCountAvailableMap = new HashMap<>();
        for (SaleProduct saleProduct : saleProducts) {
            Products products = stores.get(saleProduct.getName());
            remainCountAvailableMap.put(saleProduct.getName(), products.remainCountAvailableDiscountPromotions());
        }

        return remainCountAvailableMap;
    }

    public Set<String> getProductNames() {
        return productNames;
    }

    public Map<String, Products> getStores() {
        return stores;
    }


}
