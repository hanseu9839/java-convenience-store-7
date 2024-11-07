package store.purchase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.product.Price;
import store.product.Product;
import store.product.Products;
import store.product.Quantity;

import java.util.List;

public class PurchaseTest {

    @Test
    void 물품_구매_성공_테스트() {
        Products products = new Products(List.of(new Product("콜라", Price.from(1000), Quantity.from(10), "탄산2+1"), new Product("오렌지주스", Price.from(1800), Quantity.from(0), "주스2+1")));

        Purchase purchase = new Purchase(products);
        String inputPurchase = "[콜라-1]";
        purchase.productPurchase(inputPurchase);

        Assertions.assertThat(purchase.getProducts()).isEqualsTo(List.of(new Product("콜라", Price.from(1000), Quantity.from(10), "탄산2+1")));
    }

}
