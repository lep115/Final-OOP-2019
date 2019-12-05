package com.Final_OOP_2019.blackjack;

import com.Final_OOP_2019.blackjack.players.*;
import com.Final_OOP_2019.blackjack.deck.*;
import com.Final_OOP_2019.blackjack.deck.cards.Card;

import java.util.Scanner;
import java.lang.*;

public class Game {

	private Dealer dealer;
	private Player player;
	int cash = Cash.setCash();
	int bet, splitBet, net;
	private Deck deck;

	public Game() {
		this.dealer = new Dealer();
		this.player = new Player();
		this.deck = new Deck();
	}

	public void start() throws InterruptedException {
		player.hand.clear();
		dealer.hand.clear();
		player.setMoves(null);
		player.hand.won = false;
		player.splitHand.won = false;
		player.splitHand.standing = false;
		player.isSplit = false;
		if (cash == 0) {
			cash = Cash.setCash();
		}
		if (!(player.splitHand == null)) {
			player.splitHand.clear();
		}
		System.out.println("You have: $" + cash + ".");
		bet = Hand.bet();
		while (bet > cash) {
			System.out.println("You don't have that much money! Please bet less or deposit more.");
			Thread.sleep(500);
			System.out.println("How much money do you want to bet?");
			bet = readUserIntInput();
		}
		cash -= bet;

		System.out.println("You bet: $" + bet + ". You now have: $" + cash + ".");

		dealCards();

		if (player.hasBlackJack()) {
			if (!dealer.hasBlackJack()) {
				Thread.sleep(750);
				System.out.println("You win: Blackjack.");
				player.hand.won = true;
				cash += bet + bet * 1.5;
				net += bet * 1.5;
				playAgain();
			}
			if (dealer.hasBlackJack()) {
				Thread.sleep(750);
				System.out.println("You and the dealer have Blackjack. It's a push.");
				player.hand.won = false;
				cash += bet;
				playAgain();
			}
		}

		if (dealer.hasBlackJack()) {
			if (!player.hasBlackJack()) {
				Thread.sleep(750);
				System.out.println("The dealer reveals their second card: the " + dealer.getHiddenCard() + ".");
				Thread.sleep(750);
				System.out.println("The dealer wins: Blackjack.");
				player.hand.won = false;
				net -= bet;
				playAgain();
			}
		}
		while (player.notStanding()) {
			askPlayer();
		}

		runDealersTurn();

		readUserInput();

	}

	private void runDealersTurn() throws InterruptedException {
		
		Thread.sleep(750);
		System.out.println("The dealer reveals their second card: the " + dealer.getHiddenCard() + ".");
		Thread.sleep(1000);
		printCurrentHands();
		if (player.isSplit == true) {
			while (dealer.shouldDraw()) {
				Card drawnCard = dealer.drawCard(deck.getCards());
				Thread.sleep(750);
				System.out.println("The dealer hits and gets: " + drawnCard + ".");

				if (dealer.getHand().bust()) {
					if (!player.getHand().bust() && !player.getSplitHand().bust()) {
						System.out.println("The dealer busts. You won with both hands.");
						cash += 2 * bet + 2 * splitBet;
						player.hand.won = true;
						player.splitHand.won = true;
						net += bet + splitBet;
						playAgain();
					} else if ((player.getHand().bust() && !player.getSplitHand().bust())) {
						System.out.println("The dealer busts. You won with your split hand.");
						player.hand.won = false;
						player.splitHand.won = true;
						cash += 2 * bet;
						playAgain();
					} else if ((!player.getHand().bust() && player.getSplitHand().bust())) {
						System.out.println("The dealer busts. You won with your first hand.");
						player.hand.won = true;
						player.splitHand.won = false;
						cash += 2 * bet;
						playAgain();
					} else {
						player.hand.won = false;
						player.splitHand.won = false;
						net -= bet + splitBet;
						System.out.println("You and the dealer busted.");
						playAgain();
					}
				}
			}
			if (player.hasBlackJack()) {
				if (player.hasSplitBlackJack()) {
					if (!(dealer.hasBlackJack())) {
						Thread.sleep(1000);
						System.out.println("Blackjack for both hands! You win!");
						player.hand.won = true;
						player.splitHand.won = true;
						cash += 2 * bet + bet * 1.5 + bet * 1.5;
						net += 2 * bet * 1.5;
						playAgain();
					} else {
						Thread.sleep(750);
						player.hand.won = false;
						player.splitHand.won = false;
						System.out.println("Everyone has blackjack! It's a push!");
						cash += 2 * bet;
						playAgain();
					}
				}
				if (player.getSplitHand().bust()) {
					Thread.sleep(750);
					System.out.println("You win with your first hand: Blackjack. You bust with your split.");
					player.hand.won = true;
					player.splitHand.won = false;
					cash += bet + bet * 1.5;
					net += bet * 1.5 - bet;
					playAgain();
				} else if (player.getSplitHand().getValue() > dealer.getHand().getValue()) {
					Thread.sleep(750);
					player.hand.won = true;
					player.splitHand.won = true;
					cash += bet * 1.5 + 3 * bet;
					net += bet * 1.5 + bet;
					System.out.println("You win with your first hand: Blackjack. Your split hand also wins.");
				}
				if (dealer.hasBlackJack()) {
					Thread.sleep(750);
					player.hand.won = false;
					player.splitHand.won = false;
					System.out.println("You and the dealer have Blackjack. It's a push.");
					cash += bet;
					playAgain();
				}
			}

			System.out.println("The dealer cannot draw more cards.");

			printCurrentHands();

			if (dealer.getHand().getValue() > player.getHand().getValue()) {
				Thread.sleep(750);
				System.out.println("Your first hand lost. The dealer has " + dealer.getHand().getValue() + ".");
				Thread.sleep(750);
				player.hand.won = false;
				net -= bet;
				System.out.println("You only have " + player.getHand().getValue() + ".");
			}
			if (dealer.getHand().getValue() > player.getSplitHand().getValue()) {
				Thread.sleep(750);
				System.out.println("Your split hand lost. The dealer has " + dealer.getHand().getValue() + ".");
				net -= splitBet;
				player.splitHand.won = false;
				Thread.sleep(750);
				System.out.println("You only have " + player.getSplitHand().getValue() + ".");
			}
			if (dealer.getHand().getValue() > player.getSplitHand().getValue()
					&& dealer.getHand().getValue() > player.getHand().getValue()) {
				Thread.sleep(750);
				System.out.println("The dealer beat both of your hands.");
				playAgain();
			}
			if (dealer.getHand().getValue() == player.getHand().getValue()) {
				Thread.sleep(750);
				player.hand.won = false;
				cash += bet;
				System.out.println("You and the dealer both have " + dealer.getHand().getValue()
						+ ". It's a push for your first hand.");
			}
			if (dealer.getHand().getValue() == player.getSplitHand().getValue()) {
				Thread.sleep(750);
				player.splitHand.won = false;
				cash += bet;
				System.out.println("You and the dealer both have " + dealer.getHand().getValue()
						+ ". It's a push for your split hand.");
			}
			if (dealer.getHand().getValue() == player.getHand().getValue()
					&& dealer.getHand().getValue() == player.getSplitHand().getValue()) {
				Thread.sleep(750);
				System.out.println("You and the dealer both have " + dealer.getHand().getValue()
						+ " for all cards. What are the odds? It's a push!");
			} else {
				if (!player.getHand().bust() && !player.getSplitHand().bust()
						&& player.getHand().getValue() > dealer.getHand().getValue()
						&& player.getSplitHand().getValue() > dealer.getHand().getValue()) {
					player.hand.won = true;
					player.splitHand.won = true;
					System.out.println("You won with both hands.");
					cash += 2 * bet + 2 * splitBet;
					net += bet + splitBet;
					playAgain();
				} else if (!player.getHand().bust() && !player.getSplitHand().bust()
						&& player.getHand().getValue() > dealer.getHand().getValue()
						&& !(player.getSplitHand().getValue() > dealer.getHand().getValue())) {
					System.out.println("You won with your first hand.");
					player.hand.won = true;
					player.splitHand.won = false;
					cash += 2 * bet;
					playAgain();
				} else if (!player.getHand().bust() && !player.getSplitHand().bust()
						&& !(player.getHand().getValue() > dealer.getHand().getValue())
						&& player.getSplitHand().getValue() > dealer.getHand().getValue()) {
					System.out.println("You won with your split hand.");
					player.hand.won = false;
					player.splitHand.won = true;
					cash += 2 * bet;
					playAgain();
				} else {
					System.out.println("You won with none of your hands.");
					player.hand.won = false;
					player.splitHand.won = false;
					net -= bet + splitBet;
					playAgain();
				}
			}
		} else if (!(player.isSplit == true)) {

			{
				while (dealer.shouldDraw()) {
					Card drawnCard = dealer.drawCard(deck.getCards());
					Thread.sleep(750);
					System.out.println("The dealer hits and gets: " + drawnCard + ".");

					if (dealer.getHand().bust()) {
						printCurrentHands();
						Thread.sleep(750);
						System.out.println("The dealer busts. You won!");
						player.hand.won = true;
						cash += 2 * bet;
						net += bet;
						playAgain();
					}
				}
			}

			System.out.println("The dealer cannot draw more cards.");

			printCurrentHands();

			if (dealer.getHand().getValue() > player.getHand().getValue()) {
				player.hand.won = false;
				Thread.sleep(750);
				System.out.println("You lost. The dealer has " + dealer.getHand().getValue() + ".");
				Thread.sleep(750);
				System.out.println("You only have " + player.getHand().getValue() + ".");
				net -= bet;
				playAgain();
			} else if (dealer.getHand().getValue() == player.getHand().getValue()) {
				player.hand.won = false;
				Thread.sleep(750);
				System.out.println("You and the dealer both have " + dealer.getHand().getValue() + ". It's a push.");
				cash += bet;
				playAgain();
			} else {
				player.hand.won = true;
				Thread.sleep(750);
				System.out.println("You won. The dealer has " + dealer.getHand().getValue() + ".");
				Thread.sleep(750);
				System.out.println("You have " + player.getHand().getValue() + ".");
				cash += bet * 2;
				net += bet;
				playAgain();
			}
		}
	}

	private void askPlayer() throws InterruptedException {
		printCurrentHands();
		if (player.isSplit == true) {
			while (!player.hand.standing) {
				Thread.sleep(1000);
				System.out.println("What would you like to do for your first hand: " + player.getHand() + " ("
						+ player.getHand().getValue() + ")" + " (Hit or Stand)");
				String move = readUserInput();
				if (move.equalsIgnoreCase("hit")) {
					player.setMoves(Moves.HIT);
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						if (player.getSplitHand().bust()) {
							Thread.sleep(1000);
							System.out.println("Both hands have busted!");
							net -= bet + splitBet;
							playAgain();
						} else {
							Thread.sleep(1000);
							System.out.println("Your first hand busted. " + player.getHand().getValue() + " -> "
									+ player.getHand());
							net -= bet;
							player.hand.standing = true;
							player.setMoves(Moves.STAND);
						}
					}
				}
				if (move.equalsIgnoreCase("stand")) {
					player.hand.standing = true;
					player.setMoves(Moves.STAND);
				}
			}

			while (!player.splitHand.standing) {
				Thread.sleep(1000);
				System.out.println("What would you like to do for your split hand: " + player.getSplitHand() + " ("
						+ player.getSplitHand().getValue() + ")" + " (Hit or Stand)");
				String move = readUserInput();
				if (move.equalsIgnoreCase("hit")) {
					player.setMoves(Moves.HIT);
					Card drawnCard = player.drawSplitCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getSplitHand().bust()) {
						if (player.getHand().bust()) {
							Thread.sleep(1000);
							System.out.println("Both hands have busted!");
							net -= bet + splitBet;
							playAgain();
						} else {
							Thread.sleep(1000);
							System.out.println("Your split hand busted. " + player.getHand().getValue() + " -> "
									+ player.getHand());
							net -= splitBet;
							player.splitHand.standing = true;
							player.setMoves(Moves.STAND);
						}
					}

				}
				if (move.equalsIgnoreCase("stand")) {
					player.splitHand.standing = true;
					player.setMoves(Moves.STAND);
				}
			}
			printCurrentHands();
		} else if (player.isSplit == false) {
			if (2 * bet < cash && player.getHand().size() == 2 && !(player.getHand().get(0).getRank()
					.getValue() == player.getHand().get(1).getRank().getValue())) {
				System.out.println("What would you like to do? (Hit, Stand or Double)");
				String move = readUserInput();
				Thread.sleep(1000);
				if (move.equalsIgnoreCase("hit")) {
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						System.out.println("You busted. " + player.getHand().getValue() + " -> " + player.getHand());
						net -= bet;
						playAgain();
					}

					player.setMoves(Moves.HIT);
				}

				if (move.equalsIgnoreCase("stand")) {
					player.setMoves(Moves.STAND);
				}
				if (move.equalsIgnoreCase("double")) {
					cash -= bet;
					bet = bet * 2;
					System.out.println("Because you doubled, you now have: $" + cash + ".");
					player.setMoves(Moves.DOUBLE);
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						System.out.println("You busted. " + player.getHand().getValue() + " -> " + player.getHand());
						net -= bet;
						playAgain();
					}
					player.setMoves(Moves.STAND);
				}
			} else if (2 * bet < cash && player.getHand().size() == 2
					&& player.getHand().get(0).getRank().getValue() == player.getHand().get(1).getRank().getValue()) {
				System.out.println("What would you like to do? (Hit, Stand, Double or Split)");
				Thread.sleep(1000);
				String move = readUserInput();
				if (move.equalsIgnoreCase("hit")) {
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						System.out.println("You busted. " + player.getHand().getValue() + " -> " + player.getHand());
						net -= bet;
						playAgain();
					}

					player.setMoves(Moves.HIT);
				}

				if (move.equalsIgnoreCase("stand")) {
					player.setMoves(Moves.STAND);
				}
				if (move.equalsIgnoreCase("split")) {
					cash -= bet;

					player.splitHand = player.split();
					player.setMoves(Moves.SPLIT);
					splitBet = bet;

					Thread.sleep(1000);

					System.out.println("Your hand has been split. You have a " + player.getSplitHand() + " and a "
							+ player.getHand());

					while (!player.hand.standing) {
						Thread.sleep(1000);
						System.out.println("What would you like to do for your first hand: " + player.getHand()
								+ " (Hit or Stand)");
						move = readUserInput();
						if (move.equalsIgnoreCase("hit")) {
							player.setMoves(Moves.HIT);
							Card drawnCard = player.drawCard(deck.getCards());
							System.out.println("You draw: " + drawnCard + ".");
							if (player.getHand().bust()) {
								if (player.getSplitHand().bust()) {
									Thread.sleep(1000);
									net -= bet + splitBet;
									System.out.println("Both hands have busted!");
									playAgain();
								} else {
									Thread.sleep(1000);
									System.out.println("Your first hand busted. " + player.getHand().getValue() + " -> "
											+ player.getHand());
									net -= bet;
									player.hand.standing = true;
									player.setMoves(Moves.STAND);
								}
							}
						}
						if (move.equalsIgnoreCase("stand")) {
							player.hand.standing = true;
							player.setMoves(Moves.STAND);
						}
					}

					while (!player.splitHand.standing) {
						Thread.sleep(1000);
						System.out.println("What would you like to do for your split hand: " + player.getSplitHand()
								+ " (Hit or Stand)");
						move = readUserInput();
						if (move.equalsIgnoreCase("hit")) {
							player.setMoves(Moves.HIT);
							Card drawnCard = player.drawSplitCard(deck.getCards());
							System.out.println("You draw: " + drawnCard + ".");
							if (player.getSplitHand().bust()) {
								if (player.getHand().bust()) {
									Thread.sleep(1000);
									net -= bet + splitBet;
									System.out.println("Both hands have busted!");
									playAgain();
								} else {
									Thread.sleep(1000);
									System.out.println("Your split hand busted. " + player.getHand().getValue() + " -> "
											+ player.getHand());
									net -= splitBet;
									player.splitHand.standing = true;
									player.setMoves(Moves.STAND);
								}
							}
						}
						if (move.equalsIgnoreCase("stand")) {
							player.splitHand.standing = true;
							player.setMoves(Moves.STAND);
						}
					}
				}
				if (move.equalsIgnoreCase("double")) {
					cash -= bet;
					bet = bet * 2;
					System.out.println("Because you doubled, you now have: $" + cash + ".");
					player.setMoves(Moves.DOUBLE);
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						Thread.sleep(1000);
						net -= bet;
						System.out.println("You busted. " + player.getHand().getValue() + " -> " + player.getHand());
						playAgain();
					}

					player.setMoves(Moves.STAND);
				}
			} else {
				System.out.println("What would you like to do? (Hit or Stand)");
				String move = readUserInput();
				if (move.equalsIgnoreCase("hit")) {
					Card drawnCard = player.drawCard(deck.getCards());
					System.out.println("You draw: " + drawnCard + ".");
					if (player.getHand().bust()) {
						Thread.sleep(1000);
						System.out.println("You busted. " + player.getHand().getValue() + " -> " + player.getHand());
						net -= bet;
						playAgain();
					}

					player.setMoves(Moves.HIT);
				}

				if (move.equalsIgnoreCase("stand")) {
					player.setMoves(Moves.STAND);
				}
			}
		}
	}

	private void printCurrentHands() throws InterruptedException {
		Thread.sleep(1000);
		if (player.isSplit == true) {
			System.out.println("Your first hand: " + player.getHand().getValue() + " -> " + player.getHand());
			Thread.sleep(1000);
			System.out.println("Your split hand: " + player.getSplitHand().getValue() + " -> " + player.getSplitHand());
			Thread.sleep(1000);
			if (player.getMoves() == Moves.STAND) {
				System.out.println("The dealer's hand: " + dealer.getHand().getValue() + " -> " + dealer.getHand());
				Thread.sleep(1000);
			} else {
				System.out.println(
						"The dealer's hand: " + dealer.getHiddenHand().getValue() + " -> " + dealer.getHiddenHand());
				Thread.sleep(1000);
			}
		} else if (player.isSplit == false) {
			System.out.println("Your hand: " + player.getHand().getValue() + " -> " + player.getHand());
			if (player.getMoves() == Moves.STAND) {
				Thread.sleep(1000);
				System.out.println("The dealer's hand: " + dealer.getHand().getValue() + " -> " + dealer.getHand());
				Thread.sleep(1000);
			} else {
				System.out.println(
						"The dealer's hand: " + dealer.getHiddenHand().getValue() + " -> " + dealer.getHiddenHand());
				Thread.sleep(1000);
			}
		}
	}

	private String readUserInput() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	private int readUserIntInput() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}

	private void dealCards() throws InterruptedException {
		Card drawnCard;

		drawnCard = player.drawCard(deck.getCards());
		System.out.println("First card! You draw: " + drawnCard + " (" + drawnCard.getRank().getValue() + ")");
		Thread.sleep(1500);
		drawnCard = dealer.drawCard(deck.getCards());
		System.out.println("The dealer's first card: " + drawnCard + " (" + drawnCard.getRank().getValue() + ")");
		Thread.sleep(1500);
		drawnCard = player.drawCard(deck.getCards());
		System.out.println("Second card! You draw: " + drawnCard + " (" + drawnCard.getRank().getValue() + ")");
		Thread.sleep(1500);
		dealer.drawCard(deck.getCards());
		System.out.println("The dealer draws their second card.");
	}

	private void playAgain() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		if (cash >= 50) {
			System.out.println("You have: $" + cash + ".");
			System.out.println("Would you like to play again? (yes or no)");
			String playAgain = scanner.nextLine();

			while (!playAgain.equalsIgnoreCase("yes") && !playAgain.equalsIgnoreCase("no")) {
				System.out.println("Invalid input. Please answer with yes or no.");
				Thread.sleep(750);
				System.out.println("Would you like to play again? (yes or no)");
				playAgain = scanner.nextLine();
			}
			if (playAgain.equalsIgnoreCase("yes")) {
				start();
			} else if (playAgain.equalsIgnoreCase("no")) {
				if (net > 0) {
					System.out.println("Nice job! While you played, you made: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				} else if (net < 0) {
					System.out.println("Oh no! While you played, you lost: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				} else {
					System.out.println("Well, you win some, you lose some, but you... you made: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				}
			}
			scanner.close();
		} else {
			System.out.println("Sorry, you're out of cash! Would you like to deposit more? (yes or no)");
			String depositMore = scanner.nextLine();

			while (!depositMore.equalsIgnoreCase("yes") && !depositMore.equalsIgnoreCase("no")) {
				System.out.println("Invalid input. Please answer with yes or no.");
				Thread.sleep(1000);
				System.out.println("Would you like to deposit more? (yes or no)");
				depositMore = scanner.nextLine();
			}
			if (depositMore.equalsIgnoreCase("yes")) {
				start();
			} else if (depositMore.equalsIgnoreCase("no")) {
				if (net > 0) {
					System.out.println("Nice job! While you played, you made: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				} else if (net < 0) {
					System.out.println("Oh no! While you played, you lost: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				} else {
					System.out.println("Well, you win some, you lose some, but you... you made: $" + net
							+ ". Thank you for playing!\nThis was made by Laura Pearlstein, Alex Whitman, Sang Won & Keyur Palan for OOP.");
					System.exit(0);
				}
			}
			scanner.close();
		}
	}
}