package store;

import camp.nextstep.edu.missionutils.Console;
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
    public static final String SENTENCE_PRODUCT_PURCHASE_QUESTION = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public void play() {
        Store store = new Store(new HashMap<>(), new LinkedHashSet<>());
        store.fileStores();
        while(true) {
            try {
                storeList(store);
                MemberShip memberShip = new MemberShip(false);
                Set<SaleProduct> salesProduct = productQuestion(store, memberShip);
                receiptResultView(salesProduct, store.getStores(), memberShip);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
            System.out.println(SENTENCE_PRODUCT_PURCHASE_QUESTION);
            System.out.println();
            String continueSentence = Console.readLine();
            if(continueSentence.equals("N")) {
                break;
            }
        }
    }

}
