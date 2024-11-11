package store;


import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static final String SENTENCE_PRODUCT_PURCHASE_QUESTION = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        while(true) {
            StoreGame storeGame = new StoreGame();
            storeGame.play();
            System.out.println(SENTENCE_PRODUCT_PURCHASE_QUESTION);
            System.out.println();
            String continueSentence = Console.readLine();
            if(continueSentence.equals("N")) {
                break;
            }
        }
    }

}
