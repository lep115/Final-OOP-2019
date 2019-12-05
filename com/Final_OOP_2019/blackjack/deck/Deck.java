package com.Final_OOP_2019.blackjack.deck;

import com.Final_OOP_2019.blackjack.deck.cards.Card;
import com.Final_OOP_2019.blackjack.deck.cards.Rank;
import com.Final_OOP_2019.blackjack.deck.cards.Suit;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();

	public Deck() {
		addCards();

		shuffleDeck();
	}

	private void addCards() {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				deck.add(new Card(rank, suit));
			}
		}
	}

	private void shuffleDeck() {
		Collections.shuffle(deck);
	}

	public ArrayList<Card> getCards() {
		return deck;
	}

}
