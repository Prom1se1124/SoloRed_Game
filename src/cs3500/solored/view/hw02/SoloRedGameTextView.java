package cs3500.solored.view.hw02;

import java.io.IOException;
import java.util.List;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.ConcreteCard;

/**
 * View the SoloRed Game.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private final Appendable ap;

  /**
   * View the SoloRed Game.
   * @param model the SoloRedGameModel
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
    this.ap = null;
  }

  /**
   * View the SoloRed Game.
   * @param model the SoloRedGameModel
   * @param ap the Appendable class
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable ap) throws IllegalArgumentException {
    if (ap == null) {
      throw new IllegalArgumentException("Appendable may not be null.");
    }
    this.model = model;
    this.ap = ap;
  }

  /**
   * Turning lists of cards to string.
   * @param cards list of cards
   * @return a string which can represents the cards
   */
  private String cardsToString(List<ConcreteCard> cards) {
    StringBuilder paletteString = new StringBuilder();
    for (int i = 0; i < cards.size(); i++) {
      if (cards.size() == i + 1) {
        paletteString.append(cards.get(i).toString());
      }
      else {
        paletteString.append(cards.get(i).toString()).append(" ");
      }
    }
    return paletteString.toString();
  }

  @Override
  public void render() throws IOException {
    if (ap == null) {
      throw new IllegalArgumentException("Appendable may not be null.");
    }
    ap.append(this.toString());

  }

  @Override
  public String toString() {
    String paletteString = "";
    for (int i = 0; i < model.numPalettes(); i++) {
      if (model.winningPaletteIndex() == i) {
        paletteString = paletteString + "> P" + (i + 1)
                + ": " + cardsToString((List<ConcreteCard>) model.getPalette(i)) + "\n";
      }
      else {
        paletteString = paletteString + "P" + (i + 1)
                + ": " + cardsToString((List<ConcreteCard>) model.getPalette(i)) + "\n";
      }
    }
    String handString = cardsToString((List<ConcreteCard>) model.getHand());

    return "Canvas: " + model.getCanvas().toString().charAt(0) + "\n"
            + paletteString + "Hand: " + handString;
  }
}