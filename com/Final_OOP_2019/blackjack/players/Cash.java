package com.Final_OOP_2019.blackjack.players;

import java.util.*;

public class Cash {
	static int cash;

	public static int setCash() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("How much cash would you like to deposit? (minimum $50)");

		while (!scanner.hasNextInt()) {
			try {
				cash = scanner.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Invalid input. Please answer with an integer value.");
				System.out.println("\nHow much cash would you like to deposit?");
				scanner.nextLine();
			}
		}
		cash = scanner.nextInt();

		if (cash < 50) {
			int compensation = 50 - cash;
			cash += 50 - cash;
			System.out.println(
					"Since you entered with less than $50, we decided to give you $" + compensation + " to help you!");
		}
		return cash;
	}
}