package store.model.membership;

import store.model.product.Product;
import store.model.product.Products;
import store.model.sale.SaleProduct;

import java.util.Map;
import java.util.Set;

public class MemberShip {
    private boolean isMemberShip;

    public MemberShip(boolean isMemberShip) {
        this.isMemberShip = isMemberShip;
    }

    public int disCountMemberShip(Map<String, Products> map, Set<SaleProduct> saleProducts) {
        int priceMoney = 0;

        if(!isMemberShip) {
            return priceMoney;
        }

        for(SaleProduct saleProduct : saleProducts) {
            Products products = map.get(saleProduct.getName());
            priceMoney += products.getProducts().stream().mapToInt(Product::getPrice).sum();
        }
        priceMoney -= (int) (priceMoney * 0.3);
        return priceMoney;
    }

    public void isNotMemberShip() {
        isMemberShip = true;
    }

}
