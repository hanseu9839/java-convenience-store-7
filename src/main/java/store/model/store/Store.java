package store.model.store;

import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;
import store.model.promotion.Promotions;

import java.util.*;

import static store.util.FileUtil.readFile;

public class Store {
    private static final String EMPTY_DELIMITER = "";
    private static final String PRODUCTS_REPLACE_DELIMITER = "\\[|\\]";
    private static final String PRODUCTS_SPLIT_DELIMITER = ",";
    private static final String PRODUCT_SPLIT_DELIMITER = "-";
    private static final String NOT_EXIST_PRODUCT_ERROR = "해당 상품은 편의점에 존재하지 않습니다.";
    private static final String PRODUCTS_FILE_PATH = "/Users/han/Desktop/project/java-convenience-store-7/src/main/resources/products.md";



    private final Map<String, Products> stores;
    private final Set<String> productNames;


    public Store(Map<String, Products> stores, Set<String> productNames) {
        this.stores = stores;
        this.productNames = productNames;
    }

    public Set<String> stores() {
        List<String> products = pull();
        for(int i=1; i<products.size(); i++) {
            Product product = Product.from(products.get(i));
            Products targetProducts = stores.getOrDefault(product.getName(), new Products(new ArrayList<>(), new Promotions()));
            targetProducts.add(product);
            stores.put(product.getName(), targetProducts);
            productNames.add(product.getName());
        }
        return productNames;
    }

    public List<String> pull() {
        return readFile(PRODUCTS_FILE_PATH);
    }


    public Set<String> sale(String product) {
        List<Product> saleProducts = parse(product);
        Set<String> saleProductNames = new HashSet<>();

        for (Product saleProduct : saleProducts) {
            Products products = isValid(saleProduct.getName());
            products.sale(saleProduct.getStoreQuantity());
            saleProductNames.add(saleProduct.getName());
        }

        return saleProductNames;
    }

    private List<Product> parse(String input) {
        input = input.replaceAll(PRODUCTS_REPLACE_DELIMITER, EMPTY_DELIMITER);
        String[] products = input.split(PRODUCTS_SPLIT_DELIMITER);

        List<Product> makeProductList = new ArrayList<>();
        for (String product : products) {
            String[] productArr = product.split(PRODUCT_SPLIT_DELIMITER);
            makeProductList.add(new Product(productArr[0], Price.from(0), Quantity.from(Integer.parseInt(productArr[1])), Quantity.from(0), EMPTY_DELIMITER));
        }

        return makeProductList;
    }

    private Products isValid(String productName) {
        Products products = stores.get(productName);

        if (products == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERROR);
        }

        return products;
    }

    public List<Boolean> isPromotions(Set<String> productNames) {
        List<Boolean> isPromotions = new ArrayList<>();

        for(String productName : productNames) {
            Products products = stores.get(productName);
            checkExistsProduct(products);
            boolean promotions = products.isPromotions();
            isPromotions.add(promotions);
        }

        return  isPromotions;
    }

    private void checkExistsProduct(Products products) {
        if(products == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERROR);
        }
    }

    public int isPromotionButNotDiscountProductCount(Set<String> productNames) {
        int notDisCountPromotionCounts = 0;
        for(String productName : productNames) {
            Products products = stores.get(productName);
            notDisCountPromotionCounts += products.isPromotionButNotDiscountProductCount();
        }

        return notDisCountPromotionCounts;
    }

    public Set<String> getProductNames() {
        return productNames;
    }

    public Map<String, Products> getStores() {
        return stores;
    }

}
