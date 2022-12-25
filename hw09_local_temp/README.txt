=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: liukaily
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array - displays a grid-like structure, which is similar to the structure of a game
  board. This allows for each element of the 2D array to represent an element on the game board.

  2. Collections - used a LinkedList that holds 2D arrays representing previous and current game
  boards. Because the undo function only requires the collection to be accessed from one end,
  the collection should allow for easy access of the first element. A LinkedList is a collection
  we learned about in class that has "pop" and "push" functions for adding and removing elements
  to the front of the list. Initially, I thought to use a LinkedList of directions/strings,
  however, this would be hard to implement because there would also need to be ways to remember
  which random number was generated, the positions of each element, and which elements were
  combined. This can all easily be done using a LinkedList that stores 2D arrays that represent
  previous game boards.

  3. JUnit Testing - used JUnit testing to test for functions of the game including: shifting
  elements up/down/left/right, generating a random number, undoing a move, and resetting the game
  board. I also tested for edge cases, like shifting when the state of the board would not change,
  winning when the entire board is full (but 2048 is present), not losing when the board is full
  but can be reduced, and undoing when no moves have been made. JUnit Testing allows me to test the
  methods that change the state of the game board without building the Java Swing components, which
  allows me to determine whether an issue is due to my GUI or my algorithms.

  4. I/O - used I/O to store the state of the current board in a txt file and read from the
  txt file. This feature is appropriate for saving the game and loading it because I/O uses a
  writer to write the state of the board onto a file. Then, I/O is able to read the file, meaning
  that even if the program is exited, the file can be read, and the previous state of the game
  board can be replicated.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Blocks - Represents the state of each element in the game board. Based on the numerical value
  of the element, the font and color of the block are determined.

  BoardUI - Updates the model of the game, based on user input by repainting the state of the board
  every time the user makes a move.

  Direction - enum for directions UP, DOWN, LEFT, RIGHT.

  Game - Main class that store a LinkedList of all the 2d arrays that represent the boards. This
  class also includes all the methods that deal with adding/removing elements from the list like
  adding new boards, undoing, restart, and saving/loading the board.

  Board - Class that stores the representation of the board. It includes methods for shifting the
  blocks on the board and retrieving elements from the board.

  Run2048 - sets up the top level frame and widgets for the board

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  No significant stumbling blocks.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think there is a good separation of functionality because the model view and controller are
  separated in the Game/Board UI/Run2048 classes. I think that the private state is also
  encapsulated well because many of the variables and objects I instantiated I declared as private
  in my code (if they were able to be private). For instance, my 2D array and LinkedList are both
  private, and can only be altered using helper functions.

  If given the chance, I would refactor , I would separate the board and the collection of boards into two separate classes.
  Specifically, I would make a class for a board, and then create a LinkedList of boards.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

- https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
- https://docs.oracle.com/javase/7/docs/api/javax/swing/JTextArea.html
- https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
- https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html
- https://docs.oracle.com/javase/7/docs/api/java/io/PrintStream.html