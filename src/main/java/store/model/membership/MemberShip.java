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

    public int disCountMemberShip(int price) {
        if(isMemberShip) {
            return (int) (price * 0.3);
        }
        return 0;
    }

    public void isNotMemberShip() {
        isMemberShip = true;
    }

}
