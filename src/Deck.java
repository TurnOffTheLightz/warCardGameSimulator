import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deckList;
    private int amountOfCards;
    private boolean deckEnded=false;

    public boolean isDeckEnded() {
        return deckEnded;
    }

    public int getAmountOfCards() {
        return amountOfCards;
    }

    public void increaseAmountOfCards() {
        this.amountOfCards++;
    }
    public void decreaseAmountOfCards() {
        this.amountOfCards--;
        if(this.amountOfCards<0){
            this.amountOfCards=0;
            this.isAlive = false;
        }
    }

    public Deck(ArrayList createdDeck) {
        deckList = new ArrayList<>();
        for(int i=0;i<createdDeck.size();i++){
            Card c = (Card)createdDeck.get(i);
            deckList.add(c);
        }
        this.amountOfCards = deckList.size();
    }
    public void removeIndex(int index){
        try {
            deckList.remove(index);
        }catch(IndexOutOfBoundsException e){
            this.isAlive = false;
            System.out.println("deckList ended. (removeIndex)");
        }
    }
    public void addCard(Card c){
        deckList.add(deckList.size(),c);
    }
    public void swap(){
        Card c = deckList.get(0);
        deckList.remove(0);
        deckList.add(deckList.size(),c);
    }
    public Card getIndex(int index){
        try {
            return deckList.get(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("deckList ended. (getIndex)");
            this.isAlive = false;
            return null;
        }
    }
    private boolean isAlive = true;
    public boolean isDeckAlive(){
        if(isAlive){
            return true;
        }else{
            return false;
        }
    }
}
