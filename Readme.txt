# Solo Red Game

**Solo Red Game** is a single-player card game implemented in Java, inspired by the mechanics of **Red7**. The game challenges players to strategically play cards to palettes and a central canvas, adhering to changing rules to maintain a winning position.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Gameplay](#gameplay)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Future Improvements](#future-improvements)

---

## Features

- **Dynamic Game Modes:** Supports both **basic** and **advanced** gameplay rules.
- **Model-View-Controller (MVC) Architecture:** Clean separation of game logic, rendering, and user interaction for maintainability and scalability.
- **Flexible Views:** Provides both textual and graphical interfaces for diverse user experiences.
- **Customizable Settings:** Allows configuration of the number of palettes and hand sizes at runtime.

---
# Gameplay

The game revolves around:

- **Palettes:** Four collections of cards where players place their cards.
- **Canvas:** The central rule that determines the winning condition.
- **Deck:** A finite set of cards drawn during the game.
- **Hand:** The player's cards available for play.

Our game uses a special set of playing cards for the game. Each card consists of a color and a number. The valid colors are as follows:

- **Red (R)**
- **Orange (O)**
- **Blue (B)**
- **Indigo (I)**
- **Violet (V)**

### Objective:
Use all the cards in your deck and hand while ensuring that a different palette will win after placing a card.

### Concept of Winning
Red: The palette with the highest card wins. Let C, D be cards. C is higher than D if the number of C is greater than the number of D OR if the numbers are equal, C’s color is closer to Red than D’s color. We use the rainbow ordering when it comes to declaring closeness. So this means R > O > B > I > V.
For example, Blue 7 is higher than Red 1 because 7 is greater than 1. With our example palettes, that means Q is winning. Q has a Violet 7 as it’s highest card and P has a Red 6 as it’s highest card. Since 7 > 6, Violet 7 is higher.

Orange: The palette with the most of a single number wins. With our example palettes, Q is winning because it has two cards with the number 3. P has one card with the same number.

Blue: The palette with the most cards of different colors wins. With our example palettes, Q is winning because it has 4 cards with different colors. P only has 2 cards with different colors.

Indigo: The palette with the cards that form the longest run wins. A run is an unbroken increasing sequence of consecutive numbers after sorting. For instance 1-2-3 is a run as is the single number 4. However, 4-6 is not a run because it is missing the number 5 and 2-2 is not a run because it is not increasing. By extension, 3-1-2 is a run under this rule but 6-4 is not. With our current example, P is winning because it has a run of length 3 made of Red 4, Green 5, and Red 6. Q only has a run of length 2 made of the colors Orange 6 and Violet 7.

Violet: The palette with the most cards below the value of 4 wins. With our example palettes, Q wins. Q has 2 cards with a number below 4: Blue 3 and Indigo 3. P has none.

---

# Project Structure

The project follows an **MVC architecture** with the following key components:

- **Model:** Implements game logic, state, and rules. Supports basic and advanced gameplay modes.
  - Handles card comparisons, game state transitions, and rule enforcement.
- **View:** Provides multiple interfaces for interacting with the game.
  - Textual representation for terminal-based play.
- **Controller:** Manages user inputs, processes commands, and updates the game state.
  - Communicates between the model and the view.

---

# Technologies Used

- **Java:** Core programming language for implementation.
- **MVC Architecture:** Ensures separation of concerns.
- **Command-Line Interface (CLI):** For easy interaction with the game.

---
