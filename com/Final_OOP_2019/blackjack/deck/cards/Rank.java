package com.Final_OOP_2019.blackjack.deck.cards;

import com.Final_OOP_2019.blackjack.util.StringUtil;
public enum Rank {

	two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), ten(10), jack(10), queen(10), king(10),
	ace(11);

	private int value;

	Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return StringUtil.upperCaseFirst(name());
	}

	@Override
	public String toString() {
		return getName();
	}
}
