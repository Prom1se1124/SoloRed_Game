package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.ConcreteCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Test cases for SoloRed Controller.
 */
public class SoloRedTextControllerTest {
  private SoloRedTextController<ConcreteCard> controller;
  private RedGameModel<ConcreteCard> game1;
  private List<ConcreteCard> deck1;
  private Appendable ap;
  private Readable rd;
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

  @Before
  public void setUp() throws Exception {
    game1 = new SoloRedGameModel();
    deck1 = game1.getAllCards();
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

  @Test
  public void testPlayPalette() {
    ap = new StringBuilder();
    rd = new StringReader("palette 1 1\nq");
    controller = new SoloRedTextController<>(rd, ap);

    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertEquals(game1.getPalette(0).size(), 2);
    Assert.assertEquals(game1.getPalette(1).size(), 1);
  }

  @Test
  public void testPlayCanvas() {
    ap = new StringBuilder();
    rd = new StringReader("canvas 8\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 8);
    Assert.assertEquals(game1.getCanvas().toString(), "O3");
  }

  @Test
  public void testQuitInMid() {
    ap = new StringBuilder();
    rd = new StringReader("canvas q\n");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  @Test
  public void testPlayCanvasInvalid() {
    ap = new StringBuilder();
    rd = new StringReader("canvas 3\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Invalid move"));
  }

  @Test
  public void testPlayPaletteInvalid() {
    ap = new StringBuilder();
    rd = new StringReader("palette 3 3\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Invalid move"));
  }

  @Test
  public void testInputNegativeNumber() {
    ap = new StringBuilder();
    rd = new StringReader("canvas -1 -1 -1\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  @Test
  public void testPaletteInvalidNumber() {
    ap = new StringBuilder();
    rd = new StringReader("palette -1 1 1\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
    Assert.assertEquals(game1.getPalette(0).size(), 2);
  }

  @Test
  public void testInvalidCommand() {
    ap = new StringBuilder();
    rd = new StringReader("end\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Invalid command."));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {
    ap = new StringBuilder();
    rd = new StringReader("canvas 1\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(null, deck1, false, 2, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testErrorStartingGame() {
    ap = new StringBuilder();
    rd = new StringReader("canvas 1\nq");
    controller = new SoloRedTextController<>(rd,ap);
    controller.playGame(game1, deck1, false, 1, 2);
  }

  @Test
  public void testLostGame() {
    ap = new StringBuilder();
    rd = new StringReader("palette 1 1\npalette 2 1\n");
    controller = new SoloRedTextController<>(rd,ap);
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,r5,b5));
    controller.playGame(game1, deck, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Game lost"));
  }

  @Test
  public void testWonGame() {
    ap = new StringBuilder();
    rd = new StringReader("canvas 1\npalette 1 1\npalette 2 1\n");
    controller = new SoloRedTextController<>(rd,ap);
    List<ConcreteCard> deck = new ArrayList<ConcreteCard>(List.of(r1,r2,o1,r5,b2));
    controller.playGame(game1, deck, false, 2, 2);
    Assert.assertTrue(ap.toString().contains("Game won"));
  }



}