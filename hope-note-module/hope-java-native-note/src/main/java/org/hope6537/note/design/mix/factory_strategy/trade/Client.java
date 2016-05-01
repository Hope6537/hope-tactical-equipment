package org.hope6537.note.design.mix.factory_strategy.trade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 交易系统场景类
 * 工厂模式+策略模式
 */
public class Client {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        Card card = initIC();
        showCard(card);
        boolean flag = true;
        while (flag) {
            Trade trade = createTrade();
            //进行交易
            DeductionFacade.deduct(card, trade);
            System.out.println(trade.getTradeNo() + "Success!");
            System.out.println("Amount is " + trade.getAmount() / 100.0);
            showCard(card);
            System.out.println("exit? y/n");
            if (getInput().equalsIgnoreCase("y")) {
                flag = false;
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static Card initIC() {
        Card card = new Card();
        card.setCardNo("011111" + (int) (Math.random() * 100000));
        card.setFreeMoney(100000);
        card.setSteadyMoney(80000);
        return card;
    }

    private static Trade createTrade() {
        Trade trade = new Trade();
        System.out.println("Trade Number");
        trade.setTradeNo(getInput());
        System.out.println("Trade Amount");
        trade.setAmount((int) (Double.parseDouble(getInput()) * 100));
        return trade;
    }


    public static void showCard(Card card) {
        System.out.println("No. " + card.getCardNo());
        System.out.println("Free: " + card.getFreeMoney() / 100.0);
        System.out.println("Steady: " + card.getSteadyMoney() / 100.0);
    }


    private static String getInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
