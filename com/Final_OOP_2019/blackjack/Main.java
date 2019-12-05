package com.Final_OOP_2019.blackjack;

import java.util.*;
import java.lang.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like to play a new game of Blackjack? (yes or no)");

		String newGame = scanner.nextLine();

		while (!newGame.equalsIgnoreCase("yes") && !newGame.equalsIgnoreCase("no")) {
			System.out.println("Invalid input. Please answer with yes or no.");
			Thread.sleep(500);
			System.out.println("Would you like to play a new game of Blackjack?");
			newGame = scanner.nextLine();
		}
		if (newGame.equalsIgnoreCase("yes")) {
			System.out.println("Would you like to read the rules (there are quite a few)?");
			String rules = scanner.nextLine();

			while (!rules.equalsIgnoreCase("yes") && !rules.equalsIgnoreCase("no")) {
				System.out.println("Invalid input. Please answer with yes or no.");
				Thread.sleep(500);
				System.out.println("Would you like to read the rules (there are quite a few)?");
				rules = scanner.nextLine();
			}
			if (rules.equalsIgnoreCase("yes")) {
				System.out
						.println("These are the rules: Your goal is to beat the dealer's hand without going over 21.");
				Thread.sleep(2000);
				System.out.println(
						"Face cards (Jacks, Queens, Kings) are worth 10, Aces are worth 11 or 1, whichever makes for a better hand.");
				Thread.sleep(2000);
				System.out.println("Numerical cards are worth their numerical value.");
				Thread.sleep(2000);
				System.out.println(
						"Each player starts by drawing two cards, one of the dealer's cards is hidden until you stand.");
				Thread.sleep(2000);
				System.out.println(
						"To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.");
				Thread.sleep(2000);
				System.out.println("If you go over 21, you bust and the dealer wins regardless of their hand.");
				Thread.sleep(2000);
				System.out.println("If the dealer busts, you win regardless of your hand.");
				Thread.sleep(2000);
				System.out.println(
						"If you are dealt 21 from the start (an Ace & 10), you automatically win with a Blackjack.");
				Thread.sleep(2000);
				System.out.println(
						"If the dealer is dealt 21 from the start, they automatically win if you do not have a Blackjack");
				Thread.sleep(2000);
				System.out.println("If you win with a Blackjack, you win 2.5x your initial bet.");
				Thread.sleep(2000);
				System.out.println("The dealer will hit until their card total is 17 or higher.");
				Thread.sleep(2000);
				System.out.println(
						"You can double, which is like a hit except your bet is doubled and you only receive one more card.");
				Thread.sleep(2000);
				System.out.println(
						"Splitting can be done if you have two of the same card - the pair is split into two hands.");
				Thread.sleep(2000);
				System.out.println("Splitting also doubles your bet because each new hand is worth your original bet.");
				Thread.sleep(2000);
				System.out.println(
						"You can only double or split on your first move OR the first move of a hand made by a split.");
				Thread.sleep(2000);
				System.out.println(
						"You must bet at least $50 to play the game. If you beat the dealer, you get double your initial bet.");

				Thread.sleep(2000);
				System.out.println("Are you ready now?");
				String ready = scanner.nextLine();

				while (!ready.equalsIgnoreCase("yes") && !ready.equalsIgnoreCase("no")) {
					System.out.println("Invalid input. Please answer with yes or no.");
					Thread.sleep(500);
					System.out.println("Are you ready?");
					ready = scanner.nextLine();
				}
				if (ready.equalsIgnoreCase("yes")) {
					Game game = new Game();
					game.start();
				} else if (ready.equalsIgnoreCase("no")) {
					Thread.sleep(500);
					System.out.println("Too bad!");
					Thread.sleep(1000);
					Game game = new Game();
					game.start();
				}
			}
			if (rules.equalsIgnoreCase("no")) {
				Game game = new Game();
				game.start();
			}

		} else if (newGame.equalsIgnoreCase("no")) {
			System.out.println("Alright! Wish you could play!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
			System.exit(0);
		}
		scanner.close();
	}
}