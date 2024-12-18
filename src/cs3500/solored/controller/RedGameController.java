package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * The controller of RedGame.
 */
public interface RedGameController<C extends Card> {

  /**
   * Play the SoloRed Game.
   */
  void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes,
                int handSize) throws IllegalArgumentException, IllegalStateException;
}
