package cs3500.solored;

import java.io.InputStreamReader;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.AbstractRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

import static cs3500.solored.model.hw04.RedGameCreator.createGame;


/**
 * A solo game of RedSeven.
 * The game consists of four structures:
 * <ul>
 *   <li>A deck of cards to draw from</li>
 *   <li>A hand to play from</li>
 *   <li>Four palettes to play to</li>
 *   <li>A canvas that dictates the winning rule</li>
 * </ul>
 * The goal of the game is to use all the cards in the deck
 * while ensuring exactly one palette is winning each round.
 */
public final class SoloRed {
  /**
   * Start a game by enter basic or advanced following by optional two numbers.
   * Two numbers represents number of palettes and number of hand size.
   * @param args the input command to initiate the game
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Please specify a game type (basic or advanced).");
    }

    // Parse the game type
    String gameType = args[0];

    // Default values for number of palettes and hand size
    int numPalettes = 4;
    int handSize = 7;
    try {
      if (args.length > 1) {
        numPalettes = Integer.parseInt(args[1]);
        if (numPalettes < 2) {
          numPalettes = 4;
        }
      }
      if (args.length > 2) {
        handSize = Integer.parseInt(args[2]);
        if (handSize < 1) {
          numPalettes = 7;
        }
      }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      System.out.println("You must enter an integer");
      System.exit(1);
    }
    AbstractRedGameModel model;
    if (gameType.equals("basic")) {
      model = createGame(RedGameCreator.GameType.BASIC);
    }
    else if (gameType.equals("advanced")) {
      model = createGame(RedGameCreator.GameType.ADVANCED);
    }
    else {
      throw new IllegalArgumentException("Invalid game type: " + gameType);
    }
    SoloRedTextController controller = new SoloRedTextController(
            new InputStreamReader(System.in), System.out);
    try {
      controller.playGame(model,model.getAllCards(), true, numPalettes, handSize);
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }
}