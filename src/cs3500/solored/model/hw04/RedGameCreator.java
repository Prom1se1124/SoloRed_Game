package cs3500.solored.model.hw04;

import java.util.Random;
import cs3500.solored.model.hw02.AbstractRedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Creator of a basic SoloRed Game or an advanced SoloRed Game.
 */
public class RedGameCreator {
  /**
   * The game type can be basic or advanced.
   */
  public enum GameType {
    BASIC("basic"),
    ADVANCED("advanced");

    private final String name;
    GameType(String name) {
      this.name = name;
    }

    public String getDisplayName() {
      return name;
    }
  }

  /**
   * Create a type of game.
   *
   * @param type type of game
   */
  public static AbstractRedGameModel createGame(GameType type) {
    Random r = new Random();
    if (type == GameType.BASIC) {
      return new SoloRedGameModel(r);
    }
    else {
      return new AdvancedSoloRedGameModel(r);
    }
  }

}
