/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.util.*;

// line 51 "model.ump"
// line 126 "model.ump"
public class Hand
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Hand Associations
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Hand()
  {
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCard(Card aCard)
  {
    boolean wasAdded = true;
    if (cards.contains(aCard)) { return false; }
    cards.add(aCard);
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    if (!cards.contains(aCard))
    {
      return wasRemoved;
    }

    int oldIndex = cards.indexOf(aCard);
    cards.remove(oldIndex);
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }

}