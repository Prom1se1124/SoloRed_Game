package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete card played in the solo red game.
 */
public class ConcreteCard implements ACard {
  private final String color;
  private final int number;

  /**
   *  A concrete card played in the solo red game.
   * @param color the color of card
   * @param number the value of card
   */
  public ConcreteCard(String color, int number) {
    if (!(color.equals("Red") || color.equals("Orange") || color.equals("Blue")
            || color.equals("Indigo") || color.equals("Violet"))) {
      throw new IllegalArgumentException("Invalid color");
    }
    this.color = color;
    this.number = number;
  }

  @Override
  public String toString() {
    return color.substring(0,1) + number;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ConcreteCard) {
      ConcreteCard other = (ConcreteCard) o;
      return color.equals(other.color) && number == other.number;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public int getValue() {
    return this.number;
  }

  @Override
  public String getColor() {
    return this.color.substring(0,1);
  }

  @Override
  public ACard compare(ACard card) {
    if (this.getValue() > card.getValue()) {
      return this;
    }
    else if (this.getValue() < card.getValue()) {
      return card;
    }
    else {
      List<String> color = new ArrayList<String>(List.of("R","O","B","I","V"));
      if (color.indexOf(this.getColor()) < color.indexOf(card.getColor())) {
        return this;
      }
      else {
        return card;
      }
    }
  }
}
