package com.Final_OOP_2019.blackjack.players;

import com.Final_OOP_2019.blackjack.deck.cards.Card;
import java.util.ArrayList;

public class Player {

	public Hand hand = new Hand();
	private Moves moves;
	public boolean isSplit = false;
	public Hand splitHand = new Hand();

	public Card drawCard(ArrayList<Card> hand) {
		int index = hand.size() - 1;

		Card card = hand.get(index);

		this.hand.addCard(card);

		hand.remove(index);

		return card;
	}

	public Card drawSplitCard(ArrayList<Card> splitHand) {
		int index = splitHand.size() - 1;

		Card card = splitHand.get(index);

		this.splitHand.addCard(card);

		splitHand.remove(index);

		return card;
	}

	public boolean hasBlackJack() {
		return hand.getValue() == 21 && hand.size() == 2;
	}

	public boolean hasSplitBlackJack() {
		return splitHand.getValue() == 21 && splitHand.size() == 2;
	}

	public Hand getHand() {
		return hand;
	}

	public Hand getSplitHand() {
		return splitHand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Moves getMoves() {
		return moves;
	}

	public void setMoves(Moves moves) {
		this.moves = moves;
	}

	public boolean notStanding() {
		if (moves == null || moves != Moves.STAND)
			return true;

		return false;
	}

	public boolean splittable() {
		return hand.size() == 2 && hand.get(0).getRank().getValue() == hand.get(1).getRank().getValue() && !isSplit;
	}

	public Hand split() {
		if (!splittable()) {
			return null;
		} else {
			splitHand.bet = hand.bet;
			splitHand.addCard(hand.remove(1));
			isSplit = true;
			return splitHand;
		}
	}
}