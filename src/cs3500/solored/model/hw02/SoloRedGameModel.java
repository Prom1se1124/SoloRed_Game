package cs3500.solored.model.hw02;

import java.util.Random;


/**
 * Behaviors for a normal solo game of RedSeven.
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
public class SoloRedGameModel extends AbstractRedGameModel {
  /**
   * Construct a SoloRed Game that does not shuffle randomly.
   */
  public SoloRedGameModel() {
    super();
  }

  /**
   * Construct a SoloRed Game that shuffles randomly.
   */
  public SoloRedGameModel(Random r) {
    super(r);
  }

  @Override
  public void drawForHand() {
    {
      if (isGameOver() || !isGameStarted()) {
        throw new IllegalStateException();
      }
      while (super.getHand().size() < super.getHandSize() && numOfCardsInDeck() > 0) {
        drawForHandOnce();
      }
      setHasPlayedThisTurn(false);
    }
  }

}
