package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


/**
 * Behaviors for an abstracted solo game of RedSeven.
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
public abstract class AbstractRedGameModel implements RedGameModel<ConcreteCard> {

  private List<ConcreteCard> deck;
  private final List<ConcreteCard> hand;
  private final List<List<ConcreteCard>> palettes;
  private final List<ConcreteCard> canvas;
  private int handSize;
  private boolean isStarted;
  private boolean isOver;
  private boolean isWon;
  private boolean hasPlayedThisTurn;
  private final Random randomShuffle;

  /**
   * A zero argument constructor of SoloRed Game Model.
   * Initialize the game to make it ready to start
   */
  public AbstractRedGameModel() {
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.canvas = new ArrayList<>(List.of(new ConcreteCard("Red",1)));
    this.handSize = 0;
    this.isStarted = false;
    this.isOver = false;
    this.isWon = false;
    this.hasPlayedThisTurn = false;
    this.randomShuffle = null;
  }

  /**
   * A one argument constructor of SoloRed Game Model.
   * Initialize the game to make it ready to start.
   * Cards will be randomly shuffled.
   */
  public AbstractRedGameModel(Random r) {
    if (r == null) {
      throw new IllegalArgumentException("Random object is null!");
    }
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.canvas = new ArrayList<>(List.of(new ConcreteCard("Red",1)));
    this.handSize = 0;
    this.isStarted = false;
    this.isOver = false;
    this.isWon = false;
    this.hasPlayedThisTurn = false;
    this.randomShuffle = r;
  }



  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (!isStarted || isOver) {
      throw new IllegalStateException();
    }
    if (paletteIdx < 0 || paletteIdx >= numPalettes()
            || cardIdxInHand < 0 || cardIdxInHand >= getHand().size()) {
      throw new IllegalArgumentException();
    }
    if (paletteIdx == winningPaletteIndex()) {
      throw new IllegalStateException();
    }
    this.palettes.get(paletteIdx).add(this.hand.remove(cardIdxInHand));
    if (this.winningPaletteIndex() != paletteIdx) {
      this.isOver = true;
      this.isWon = false;
    }
    if (hand.isEmpty() && deck.isEmpty() && this.winningPaletteIndex() == paletteIdx) {
      isOver = true;
      isWon = true;
    }
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    if (isOver || !isStarted) {
      throw new IllegalStateException();
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException();
    }
    if (hasPlayedThisTurn) {
      throw new IllegalStateException();
    }
    if (getHand().size() == 1) {
      throw new IllegalStateException();
    }
    this.canvas.add(this.hand.remove(cardIdxInHand));
    hasPlayedThisTurn = true;
  }

  @Override
  public abstract void drawForHand();

  /**
   * check if the deck has duplicated cards.
   */
  private boolean hasDuplicateCard(List<ConcreteCard> cards) {
    Set<ConcreteCard> set = new HashSet<>(cards);
    return set.size() < cards.size();
  }

  @Override
  public void startGame(List<ConcreteCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (isOver || isStarted) {
      throw new IllegalStateException();
    }
    if (numPalettes < 2 || handSize <= 0) {
      throw new IllegalArgumentException();
    }
    if (deck == null || hasDuplicateCard(deck)) {
      throw new IllegalArgumentException();
    }
    if (deck.size() < numPalettes + handSize) {
      throw new IllegalArgumentException();
    }
    if (shuffle) {
      if (randomShuffle != null) {
        Collections.shuffle(deck, randomShuffle);
      }
      else {
        Collections.shuffle(deck);
      }
    }
    this.deck = new ArrayList<>(deck);
    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<>());
      palettes.get(i).add(this.deck.remove(0));
    }

    for (int i = 0; i < handSize; i++) {
      hand.add(this.deck.remove(0));
    }
    this.isStarted = true;
    this.handSize = handSize;
  }

  @Override
  public int numOfCardsInDeck() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    return deck.size();
  }

  @Override
  public int numPalettes() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    return palettes.size();
  }

  /**
   * find the max-value card in the palette.
   * @param palette a Palette
   * @return the max-value card in the palette
   */
  private ConcreteCard findMax(List<ConcreteCard> palette) {
    ConcreteCard max = palette.get(0);
    for (int i = 1; i < palette.size(); i++) {
      max = (ConcreteCard) palette.get(i).compare(max);
    }
    return max;
  }

  /**
   * find the max number of cards with same value.
   * @param palette a Palette
   * @return number of cards with most same value in the palette
   */
  private int sameValue(List<ConcreteCard> palette) {
    int maxCount = 0;
    for (int i = 0; i < palette.size(); i++) {
      int count = 0;
      for (ConcreteCard card : palette) {
        if (palette.get(i).getValue() == card.getValue()) {
          count++;
        }
      }
      if (count > maxCount) {
        maxCount = count;
      }
    }
    return maxCount;
  }

  /**
   * find the different color numbers in the palette.
   * @param palette a Palette
   * @return the different color numbers in the palette
   */
  private int differentColor(List<ConcreteCard> palette) {
    List<String> colorList = new ArrayList<>();
    for (ConcreteCard card : palette) {
      if (!colorList.contains(card.getColor())) {
        colorList.add(card.getColor());
      }
    }
    return colorList.size();
  }


  /**
   * find the longest run in the palette.
   * @param palette a Palette
   * @return the longest run in the palette
   */
  private int longestRun(List<ConcreteCard> palette) {
    List<ConcreteCard> sortedPalette = new ArrayList<>(palette);
    sortedPalette.sort(new Comparator<ConcreteCard>() {
      @Override
      public int compare(ConcreteCard o1, ConcreteCard o2) {
        return o1.getValue() - o2.getValue();
      }
    });
    int maxCount = 1;
    int count = 1;
    // Find the longest run of consecutive values
    for (int i = 1; i < sortedPalette.size(); i++) {
      if (sortedPalette.get(i).getValue() == sortedPalette.get(i - 1).getValue() + 1) {
        count++;
      } else {
        maxCount = Math.max(maxCount, count);
        count = 1;
      }
    }
    return Math.max(maxCount, count);
  }

  /**
   * find count of number below 4.
   * @param palette a Palette
   * @return count of number below 4
   */
  private int numberBelow4(List<ConcreteCard> palette) {
    int count = 0;
    for (ConcreteCard card : palette) {
      if (card.getValue() < 4) {
        count ++;
      }
    }
    return count;
  }


  @Override
  public int winningPaletteIndex() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    if (getCanvas().getColor().equals("R")) {
      return redRule();
    }
    else if (getCanvas().getColor().equals("O")) {
      return orangeRule();
    }
    else if (getCanvas().getColor().equals("B")) {
      return blueRule();
    }
    else if (getCanvas().getColor().equals("I")) {
      return indigoRule();
    }
    else if (getCanvas().getColor().equals("V")) {
      return violetRule();
    }
    return 0;
  }

  /**
   * Find the winning palette based on violet rule.
   * @return the palette index of winning palette
   */
  private int violetRule() {
    int idx = 0;
    for (int i = 1; i < palettes.size(); i++) {
      if (numberBelow4(palettes.get(i)) > numberBelow4(palettes.get(idx))) {
        idx = i;
      }
      else if (numberBelow4(palettes.get(i)) == numberBelow4(palettes.get(idx))) {
        ConcreteCard winner = (ConcreteCard)findMax(
                palettes.get(i)).compare(findMax(palettes.get(idx)));
        if (palettes.get(i).contains(winner)) {
          idx = i;
        }
      }
    }
    return idx;
  }

  /**
   * Find the winning palette based on Indigo rule.
   * @return the palette index of winning palette
   */
  private int indigoRule() {
    int idx = 0;
    for (int i = 1; i < palettes.size(); i++) {
      if (longestRun(palettes.get(i)) > longestRun(palettes.get(idx))) {
        idx = i;
      }
      else if (longestRun(palettes.get(i)) == longestRun(palettes.get(idx))) {
        ConcreteCard winner = (ConcreteCard)findMax(
                palettes.get(i)).compare(findMax(palettes.get(idx)));
        if (palettes.get(i).contains(winner)) {
          idx = i;
        }
      }
    }
    return idx;
  }

  /**
   * Find the winning palette based on Blue rule.
   * @return the palette index of winning palette
   */
  private int blueRule() {
    int idx = 0;
    for (int i = 1; i < palettes.size(); i++) {
      if (differentColor(palettes.get(i)) > differentColor(palettes.get(idx))) {
        idx = i;
      }
      else if (differentColor(palettes.get(i)) == differentColor(palettes.get(idx))) {
        ConcreteCard winner = (ConcreteCard)findMax(
                palettes.get(i)).compare(findMax(palettes.get(idx)));
        if (palettes.get(i).contains(winner)) {
          idx = i;
        }
      }
    }
    return idx;
  }

  /**
   * Find the winning palette based on Orange rule.
   * @return the palette index of winning palette
   */
  private int orangeRule() {
    int idx = 0;
    for (int i = 1; i < palettes.size(); i ++) {
      if (sameValue(palettes.get(i)) > sameValue(palettes.get(idx))) {
        idx = i;
      }
      else if (sameValue(palettes.get(i)) == sameValue(palettes.get(idx))) {
        ConcreteCard winner = (ConcreteCard)findMax(
                palettes.get(i)).compare(findMax(palettes.get(idx)));
        if (palettes.get(i).contains(winner)) {
          idx = i;
        }
      }
    }
    return idx;
  }

  /**
   * Find the winning palette based on Red rule.
   * @return the palette index of winning palette
   */
  private int redRule() {
    List<ConcreteCard> maxList = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i ++) {
      maxList.add(findMax(palettes.get(i)));
    }
    return maxList.indexOf(findMax(maxList));
  }

  @Override
  public boolean isGameOver() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    return isOver;
  }

  @Override
  public boolean isGameWon() {
    if (!isStarted || !isOver) {
      throw new IllegalStateException();
    }
    return hand.isEmpty() && deck.isEmpty() && isWon;
  }

  protected boolean isGameStarted() {
    return this.isStarted;
  }

  @Override
  public List<ConcreteCard> getHand() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    return new ArrayList<>(hand);
  }

  @Override
  public List<ConcreteCard> getPalette(int paletteNum) {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    if (paletteNum < 0 || paletteNum > palettes.size()) {
      throw new IllegalArgumentException();
    }
    return new ArrayList<>(palettes.get(paletteNum));
  }

  @Override
  public ConcreteCard getCanvas() {
    if (!isStarted) {
      throw new IllegalStateException();
    }
    return canvas.get(canvas.size() - 1);

  }

  @Override
  public List<ConcreteCard> getAllCards() {
    List<ConcreteCard> allCards = new ArrayList<>();
    List<String> colors = new ArrayList<>(List.of("Red","Orange","Blue","Indigo","Violet"));
    for (int i = 0; i < 5; i ++) {
      for (int j = 1; j < 8; j ++) {
        allCards.add(new ConcreteCard(colors.get(i),j));
      }
    }
    return allCards;
  }

  protected int getHandSize() {
    return handSize;
  }

  protected void drawForHandOnce() {
    hand.add(deck.remove(0));
  }

  protected void setHasPlayedThisTurn(boolean set) {
    hasPlayedThisTurn = set;
  }
}

