package cs3500.solored.model.hw04;

import java.util.Random;

import cs3500.solored.model.hw02.AbstractRedGameModel;

/**
 * Behaviors for an advanced solo game of RedSeven.
 * The game consists of four structures:
 * <ul>
 *   <li>A deck of cards to draw from</li>
 *   <li>A hand to play from</li>
 *   <li>Four palettes to play to</li>
 *   <li>A canvas that dictates the winning rule</li>
 * </ul>
 * The goal of the game is to use all the cards in the deck
 * while ensuring exactly one palette is winning each round.
 *
 */
public class AdvancedSoloRedGameModel extends AbstractRedGameModel {
  private int extraDraws = 1;

  /**
   * Construct a SoloRed Game that does not shuffle randomly.
   */
  public AdvancedSoloRedGameModel() {
    super();
  }

  /**
   * Construct a SoloRed Game that shuffles randomly.
   */
  public AdvancedSoloRedGameModel(Random r) {
    super(r);
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand);
    if (getCanvas().getValue() > this.getPalette(this.winningPaletteIndex()).size()) {
      extraDraws = 2;
    }
  }

  @Override
  public void drawForHand() {
    if (isGameOver() || !isGameStarted()) {
      throw new IllegalStateException();
    }
    for (int i = 0; i < extraDraws; i ++) {
      if (numOfCardsInDeck() > 0 && getHand().size() < getHandSize()) {
        drawForHandOnce();
      }
    }
    setHasPlayedThisTurn(false);
    extraDraws = 1;
  }

}
