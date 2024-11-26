package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * A SoloRed Game Controller.
 */
public class SoloRedTextController<C extends Card> implements RedGameController<C> {
  private final Readable rd;
  private final Appendable ap;
  private boolean quit;

  /**
   * Build a SoloRed Game Controller.
   *
   * @param rd  A Readable class
   * @param ap  An Appendable class
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException();
    }
    this.rd = rd;
    this.ap = ap;
    quit = false;
  }

  protected void writeMessage(String message) throws IOException {
    try {
      ap.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }


  @Override
  public void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes,
                       int handSize) throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalArgumentException("Error initializing the game: " + e.getMessage(), e);
    }
    RedGameView view = new SoloRedGameTextView(model,ap);
    Scanner sc = new Scanner(rd);
    try {
      while (!model.isGameOver() && !quit) {
        view.render();
        writeMessage(System.lineSeparator());
        writeMessage("Number of cards in deck: " + model.numOfCardsInDeck()
                + System.lineSeparator());
        if (!sc.hasNext()) {
          throw new IllegalStateException("No more input");
        }
        String input = sc.next();
        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("Q")) {
          handleQuit(model);
          quit = true;
          return;
        } else {
          processCommand(input, model, sc);
        }
      }
      if (model.isGameOver() && !quit) {
        if (!model.isGameWon()) {
          writeMessage("Game lost." + System.lineSeparator());
        } else {
          writeMessage("Game won." + System.lineSeparator());
        }
        view.render();
        writeMessage(System.lineSeparator());
        writeMessage("Number of cards in deck: " + model.numOfCardsInDeck()
                + System.lineSeparator());
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error during transmission", e);
    }
  }

  private void processCommand(String input,RedGameModel<C> model, Scanner sc) throws IOException {
    switch (input) {
      case "palette":
        int paletteIndex = getValidInt(sc,model);
        int cardIndex = getValidInt(sc,model);
        if (quit) {
          break;
        }
        try {
          model.playToPalette(paletteIndex - 1, cardIndex - 1);
          if (!model.isGameOver()) {
            model.drawForHand();
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          writeMessage("Invalid move. Try again. " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "canvas":
        int cardIndexCanvas = getValidInt(sc,model);
        if (quit) {
          break;
        }
        try {
          model.playToCanvas(cardIndexCanvas - 1);
        } catch (IllegalArgumentException | IllegalStateException e) {
          writeMessage("Invalid move. Try again. " + e.getMessage() + System.lineSeparator());
        }
        break;
      default:
        writeMessage("Invalid command. Try again." + System.lineSeparator());
    }
  }

  // Method to handle quitting the game
  private void handleQuit(RedGameModel<C> model) throws IOException {
    writeMessage("Game quit!\n");
    writeMessage("State of game when quit:\n");
    RedGameView view = new SoloRedGameTextView(model,ap);
    view.render();
    writeMessage(System.lineSeparator());
    writeMessage("Number of cards in deck: " + model.numOfCardsInDeck() + System.lineSeparator());

  }

  // Helper method to retrieve valid integers
  private int getValidInt(Scanner scanner, RedGameModel<C> model) throws IOException {
    try {
      while (true) {
        String input = scanner.next();
        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("Q")) {
          handleQuit(model);
          quit = true;
          return -1;
        }
        try {
          int value = Integer.parseInt(input);
          if (value >= 0) {
            return value;
          }
        } catch (NumberFormatException e) {
          continue;
        }
      }
    } catch (IOException | NoSuchElementException e) {
      throw new IllegalStateException("Error during transmission", e);
    }
  }
}
