package cs3500.solored.model.hw02;

/**
 * A Card that extends Card interface.
 */
public interface ACard extends Card {
  /**
   * get the value of card.
   * @return value of card
   */
  int getValue();

  /**
   * get the color of card.
   * @return color of card
   */
  String getColor();

  /**
   * compare with another card based on Red Rule.
   * @param card another card
   * @return the greater card
   */
  ACard compare(ACard card);
}
