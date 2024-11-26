package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.solored.model.hw02.ConcreteCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Test Cases for Advanced SoloRed Game Model.
 */
public class AdvancedSoloRedGameModelTest {
  private RedGameModel<ConcreteCard> game1;
  private List<ConcreteCard> deck1;
  private List<ConcreteCard> deck2;
  private List<ConcreteCard> deckNotUnique;
  ConcreteCard r1;
  ConcreteCard r2;
  ConcreteCard r3;
  ConcreteCard r4;
  ConcreteCard r5;
  ConcreteCard o1;
  ConcreteCard o5;
  ConcreteCard v5;
  ConcreteCard b2;
  ConcreteCard b5;
  ConcreteCard i6;

  // examples

  @Before
  public void setUp() {
    game1 = new AdvancedSoloRedGameModel();
    deck1 = game1.getAllCards();
    deck2 = new ArrayList<>(List.of(new ConcreteCard("Red",2)));
    deckNotUnique = new ArrayList<>(Arrays.asList(deck1.get(0), deck1.get(0),
            deck1.get(1), deck1.get(2), deck1.get(3)));
    r1 = new ConcreteCard("Red",1);
    r2 = new ConcreteCard("Red",2);
    r3 = new ConcreteCard("Red",3);
    r4 = new ConcreteCard("Red",4);
    r5 = new ConcreteCard("Red",5);
    o1 = new ConcreteCard("Orange",1);
    o5 = new ConcreteCard("Orange",5);
    v5 = new ConcreteCard("Violet",5);
    b2 = new ConcreteCard("Blue",2);
    b5 = new ConcreteCard("Blue",5);
    i6 = new ConcreteCard("Indigo",6);
  }

  // Test Start Game

  @Test
  public void testStartGame() {
    game1.startGame(deck1,false,3,4);
    Assert.assertEquals(game1.numPalettes(), 3);
    Assert.assertEquals(game1.getHand().size(), 4);
  }

  @Test (expected = IllegalStateException.class)
  public void testStartAStartedGame() {
    game1.startGame(deck1,false,3,4);
    game1.startGame(deck2,false,3,4);
  }

  @Test
  public void testShuffle() {
    game1.startGame(deck1,true,3,4);
    Assert.assertNotEquals(game1.getHand().get(0), r5);
  }

  @Test
  public void testDuplicateCards() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game1.startGame(deckNotUnique,false,3,4));
  }

  @Test
  public void testNotEnoughDeck() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game1.startGame(deck2,false,3,4));
  }

  @Test
  public void testDeckSize() {
    game1.startGame(deck1,false,4,7);
    Assert.assertEquals(game1.numOfCardsInDeck(),24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHandSize() {
    game1.startGame(deck1, false, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPaletteSize() {
    game1.startGame(deck1, false, 1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeckSize() {
    game1.startGame(null, false, 2, 5);
  }

  // test playToPalette

  @Test
  public void testPlayToPalette() {
    game1.startGame(deck1,false,3,4);
    game1.playToPalette(0,0);
    Assert.assertEquals(game1.getPalette(0).get(0), r1);
    Assert.assertEquals(game1.numPalettes(), 3);
    Assert.assertEquals(game1.getHand().size(), 3);
    Assert.assertEquals(game1.getHand().get(0), r5);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteNotStartedGame() {
    game1.playToPalette(0,0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToWinningPalette() {
    game1.startGame(deck1,false,3,4);
    game1.playToPalette(game1.winningPaletteIndex(),0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteInvalidPaletteIndex() {
    game1.startGame(deck1,false,3,4);
    game1.playToPalette(-1,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteInvalidHandIndex() {
    game1.startGame(deck1,false,3,4);
    game1.playToPalette(0,4);
  }

  // test PlayToCanvas

  @Test
  public void testPlayToCanvas() {
    game1.startGame(deck1,false,4,4);
    game1.playToCanvas(3);
    Assert.assertEquals(game1.getCanvas(),o1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayToCanvasTwice() {
    game1.startGame(deck1,false,4,4);
    game1.playToCanvas(0);
    game1.playToCanvas(0);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayToCanvasWithOnlyOneHandCard() {
    game1.startGame(deck1,false,4,1);
    game1.playToCanvas(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayToCanvasNegativeIndex() {
    game1.startGame(deck1,false,4,4);
    game1.playToCanvas(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayToCanvasOverIndex() {
    game1.startGame(deck1,false,4,4);
    game1.playToCanvas(4);
  }

  // test drawForHand
  @Test
  public void testDrawForHand() {
    game1.startGame(deck1,false,4,4);
    game1.playToCanvas(0);
    game1.playToPalette(0,0);
    game1.playToPalette(1,0);
    game1.drawForHand();
    Assert.assertEquals(game1.getHand().size(), 3); // play three cards and get 2 cards
    game1.playToCanvas(1);
    game1.drawForHand();
    Assert.assertEquals(game1.getHand().size(), 3); // play one cards and get 1 cards
  }


  // test numOfCardsInDeck
  @Test
  public void testNumOfCardsInDeck() {
    game1.startGame(deck1,false,4,4);
    Assert.assertEquals(game1.numOfCardsInDeck(),35 - 8);
  }

  @Test (expected = IllegalStateException.class)
  public void testNumOfCardsInDeckBeforeStart() {
    game1.numOfCardsInDeck();
  }

  // test numPalettes
  @Test
  public void testNumPalettes() {
    game1.startGame(deck1,false,4,4);
    Assert.assertEquals(game1.numPalettes(),4);
  }

  @Test (expected = IllegalStateException.class)
  public void testNumPalettesBeforeStart() {
    game1.numPalettes();
  }

  // test WinningPalette, isGameWon, and isGameOver
  @Test
  public void testWinningPaletteRed() {
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,r5,b5));
    game1.startGame(deck,false,2,1);
    /* Canvas: R
       P1: R1
       > P2: R2
       Hand: R5
       Deck: B5*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // Put R5 to P1, so P1 wins now
    game1.playToPalette(0,0);
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    game1.drawForHand();

    // Put B5 to P2. 5 = 5 but Red > Blue, so P1 still wins.
    game1.playToPalette(1,0);
    /* Canvas: R
       > P1: R1 R5
       P2: R2 B5
       Hand: R5
       Deck: B5*/
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    // deck is empty and hand is empty, game is over but loses
    Assert.assertTrue(game1.isGameOver());
    Assert.assertFalse(game1.isGameWon());
  }


  @Test
  public void testWinningPaletteOrange() {
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,o1,r5,b2));

    game1.startGame(deck,false,2,2);
    /* Canvas: R
       P1: R1
       > P2: R2
       Hand: O1,R5
       Deck: B2*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // put O1 to canvas
    game1.playToCanvas(0);
    // Put R5 to P1, there is a tie but R5 > R2
    game1.playToPalette(0,0);
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    game1.drawForHand();

    // Put B2 to P2. There are two value-2 cards in P2, so P2 wins
    game1.playToPalette(1,0);
    /* Canvas: O
       > P1: R1 R5
       P2: R2 B2
       Hand:
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // deck is empty and hand is empty, game is over but loses
    Assert.assertTrue(game1.isGameOver());
    Assert.assertTrue(game1.isGameWon());
  }

  @Test
  public void testWinningPaletteBlue() {
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,b5,b2));

    game1.startGame(deck,false,2,2);
    /* Canvas: R
       P1: R1
       > P2: R2
       Hand: B5,B2
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // put B5 to canvas
    game1.playToCanvas(0);
    // Put B2 to P1. There are two colors in P1 now, so P1 wins.
    game1.playToPalette(0,0);
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    /* Canvas: B
       > P1: R1 B2
       P2: R2
       Hand:
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    // deck is empty and hand is empty, game is over but loses
    Assert.assertTrue(game1.isGameOver());
    Assert.assertTrue(game1.isGameWon());
  }

  @Test
  public void testWinningPaletteIndigo() {
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,i6,b2));

    game1.startGame(deck,false,2,2);
    /* Canvas: R
       P1: R1
       > P2: R2
       Hand: I6,B2
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // put I6 to canvas
    game1.playToCanvas(0);
    // Put B2 to P1. There are two consecutive value in P1, so P1 wins now
    game1.playToPalette(0,0);
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    /* Canvas: I
       > P1: R1 B2
       P2: R2
       Hand:
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),0);
    // deck is empty and hand is empty, game is over but loses
    Assert.assertTrue(game1.isGameOver());
    Assert.assertTrue(game1.isGameWon());
  }

  @Test
  public void testWinningPaletteViolet() {
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,v5,b2,o1));

    game1.startGame(deck,false,2,3);
    /* Canvas: R
       P1: R1
       > P2: R2
       Hand: V5,B2,O1
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // put V5 to canvas
    game1.playToCanvas(0);
    Assert.assertFalse(game1.isGameOver());
    // Put B2 to P1. There are two cards below 4 in P1, so P1 wins now.
    game1.playToPalette(0,0);
    // Put O1 to P2.
    game1.playToPalette(1,0);
    // P1 and P2 both has 2 cards below 4 but R2 > R1, so P2 wins now
    /* Canvas: V
       P1: R1 B2
       > P2: R2 O1
       Hand:
       Deck:*/
    Assert.assertEquals(game1.winningPaletteIndex(),1);
    // deck is empty and hand is empty, game is over but loses
    Assert.assertTrue(game1.isGameOver());
    Assert.assertTrue(game1.isGameWon());
  }

  @Test (expected = IllegalStateException.class)
  public void testIsOverBeforeStart() {
    game1.isGameOver();
  }

  @Test (expected = IllegalStateException.class)
  public void testIsWonBeforeStart() {
    game1.isGameWon();
  }

  @Test (expected = IllegalStateException.class)
  public void testGetHandBeforeStart() {
    game1.getHand();
  }

  @Test
  public void testGetHand() {
    List<ConcreteCard> deck = new ArrayList<>(List.of(r1,r2,r3,o1));
    game1.startGame(deck,false,2,1);
    Assert.assertEquals(game1.getHand(),new ArrayList<>(List.of(r3)));
  }

  @Test (expected = IllegalStateException.class)
  public void testGetPaletteBeforeStart() {
    game1.getPalette(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetPaletteInvalidIndex() {
    List<ConcreteCard> deck = new ArrayList<>(List.of(r1,r2,r3,o1));
    game1.startGame(deck,false,2,1);
    game1.getPalette(3);
  }

  @Test
  public void testGetPalette() {
    List<ConcreteCard> deck = new ArrayList<>(List.of(r1,r2,r3,o1));
    game1.startGame(deck,false,2,1);
    Assert.assertEquals(game1.getPalette(1),new ArrayList<>(List.of(r2)));
  }

  @Test (expected = IllegalStateException.class)
  public void testGetCanvasBeforeStart() {
    game1.getCanvas();
  }

  @Test
  public void testGetCanvas() {
    List<ConcreteCard> deck = new ArrayList<>(List.of(r1,r2,r3,o1));
    game1.startGame(deck,false,2,2);
    game1.playToCanvas(1);
    Assert.assertEquals(game1.getCanvas(),o1);
  }

  @Test
  public void testGetAllCards() {
    game1.startGame(deck1,false,2,1);
    List<ConcreteCard> cards = game1.getAllCards();
    Assert.assertEquals(cards.size(), 35);
    cards.remove(0);
    Assert.assertEquals(game1.getAllCards().size(), 35);
  }

  // test Text View
  @Test
  public void testTextView() {
    game1.startGame(deck1, false, 2, 2);
    SoloRedGameTextView view = new SoloRedGameTextView(game1);
    Assert.assertEquals(view.toString(), "Canvas: R\n"
            + "P1: R1\n" + "> P2: R2\n" + "Hand: R3 R4");
  }

}