package com.Final_OOP_2019.blackjack.players;

import com.Final_OOP_2019.blackjack.deck.cards.Card;
import com.Final_OOP_2019.blackjack.deck.cards.Rank;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Hand {
	private ArrayList<Card> hand;
	public int handValue = 0;
	public static int bet;
	public boolean standing = false;
	public boolean won;

	public Hand() {
		this.hand = new ArrayList<>();
		this.handValue = 0;
	}

	public Hand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public ArrayList<Card> getCards() {
		return hand;
	}

	public void addCard(Card card) {
		if (card == null) {
			return;
		}
		hand.add(card);
		handValue += card.getRank().getValue();
	}

	public boolean bust() {
		return getValue() > 21;
	}

	public int getValue() {
		int sum = 0;

		for (Card card : hand) {
			sum += card.getRank().getValue();
		}

		int decreasedAceValue = 0;

		while (sum > 21 && containsAce() && getAceCount() != decreasedAceValue) {
			sum -= 10;
			decreasedAceValue++;
		}

		return sum;
	}

	private int getAceCount() {
		int count = 0;

		for (Card card : hand) {
			if (card.getRank() == Rank.ace) {
				count++;
			}
		}

		return count;
	}

	public boolean containsAce() {
		for (Card card : hand) {
			if (card.getRank() == Rank.ace) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(hand);
	}

	public int size() {
		return hand.size();
	}

	public Card get(int i) {
		return hand.get(i);
	}

	public Card remove(int i) {
		return hand.remove(i);
	}

	public static int bet() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How much cash would you like to bet?");

		while (!scanner.hasNextInt()) {
			try {
				bet = scanner.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Invalid input. Please answer with an integer value.");
				System.out.println("\nHow much cash would you like to start with?");
				scanner.nextLine();
			}
		}
		bet = scanner.nextInt();
		if (bet < 50) {
			bet += 50 - bet;
			System.out.println("The minimum bet is $50. Your bet has been set to $50.");
		}
		return bet;
	}

	public void clear() {
		hand.clear();
	}

}
