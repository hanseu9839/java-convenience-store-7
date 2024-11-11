package store;

import store.model.membership.MemberShip;
import store.model.sale.SaleProduct;
import store.model.store.Store;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import static store.view.InputView.productQuestion;
import static store.view.OutputView.receiptResultView;
import static store.view.OutputView.storeList;

public class StoreGame {

    public void play() {
        try{
            Store store = new Store(new HashMap<>(), new LinkedHashSet<>());
            Set<String> strings = store.fileStores();
            storeList(store);
            MemberShip memberShip = new MemberShip(false);
            Set<SaleProduct> salesProduct = productQuestion(store, memberShip);
            receiptResultView(salesProduct, store.getStores(), memberShip);
        } catch(IllegalArgumentException e) {
            System.out.println("[ERROR] " +e.getMessage());
        }

    }

}
