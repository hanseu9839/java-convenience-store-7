package store.model.membership;

import store.model.product.Product;
import store.model.product.Products;

import java.util.Map;
import java.util.Set;

public class MemberShip {
    private boolean isMemberShip;

    public MemberShip(boolean isMemberShip) {
        this.isMemberShip = isMemberShip;
    }

    public int disCountMemberShip(Map<String, Products> map, Set<String> productNames) {
        int priceMoney = 0;

        if(!isMemberShip) {
            return priceMoney;
        }

        for(String productName : productNames) {
            Products products = map.get(productName);
            priceMoney += products.getProducts().stream().mapToInt(Product::getPrice).sum();
        }
        priceMoney -= (int) (priceMoney * 0.3);
        return priceMoney;
    }

    public void isNotMemberShip() {
        isMemberShip = true;
    }

}
