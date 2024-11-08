package store.model.store;

import store.model.product.Price;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.Quantity;

import java.util.*;

public class Store {
    private static final String EMPTY_DELIMITER = "";
    private static final String PRODUCTS_REPLACE_DELIMITER = "\\[|\\]";
    private static final String PRODUCTS_SPLIT_DELIMITER = ",";
    private static final String PRODUCT_SPLIT_DELIMITER = "-";
    private static final String NOT_EXIST_PRODUCT_ERROR = "해당 상품은 편의점에 존재하지 않습니다.";

    private final Map<String, Products> stores;

    public Store(Map<String, Products> stores) {
        this.stores = stores;
    }

    public void store(String input) {
        Product product = Product.from(input);
        Products targetProducts = stores.getOrDefault(product.getName(), new Products(new ArrayList<>()));
        targetProducts.add(product);

    }

    public List<Products> sale(String product) {
        List<Product> saleProducts = parse(product);
        List<Products> saleProductStores = new ArrayList<>();

        for (Product saleProduct : saleProducts) {
            Products products = isValid(saleProduct.getName());
            products.sale(saleProduct.getStoreQuantity());
            saleProductStores.add(stores.get(saleProduct.getName()));
        }

        return saleProductStores;
    }

    private List<Product> parse(String input) {
        input = input.replaceAll(PRODUCTS_REPLACE_DELIMITER, EMPTY_DELIMITER);
        String[] products = input.split(PRODUCTS_SPLIT_DELIMITER);

        List<Product> makeProductList = new ArrayList<>();
        for (String product : products) {
            String[] productArr = product.split(PRODUCT_SPLIT_DELIMITER);
            makeProductList.add(new Product(productArr[0], Price.from(0), Quantity.from(Integer.parseInt(productArr[1])), Quantity.from(0), EMPTY_DELIMITER, false));
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

}
