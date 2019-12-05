package com.Final_OOP_2019.blackjack.deck.cards;

import com.Final_OOP_2019.blackjack.util.StringUtil;

public enum Suit {

	HEARTS, SPADES, DIAMONDS, CLUBS;

	public String getName() {
		return StringUtil.upperCaseFirst(name());
	}

	@Override
	public String toString() {
		return getName();
	}
}
