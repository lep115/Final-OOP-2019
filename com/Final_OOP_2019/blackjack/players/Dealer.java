package com.Final_OOP_2019.blackjack.players;

import com.Final_OOP_2019.blackjack.deck.cards.Card;
import java.util.ArrayList;

public class Dealer extends Player {

	public Hand splitHand = null;
	
    public Dealer() {
        super();
    }

    public boolean shouldDraw() {
        return getHand().getValue() <= 16;
    }

    public Hand getHiddenHand() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(getHand().getCards().get(0));

        return new Hand(hand);
    }

    public Card getHiddenCard() {
        return getHand().getCards().get(1);
    }

}