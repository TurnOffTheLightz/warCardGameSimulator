import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private Deck deck;

    public Deck getDeck() {
        return deck;
    }

    public Player(){
        deck = new Deck(createDeck());
    }
    public ArrayList createDeck(){
        ArrayList <Card> deckhalfrdy = new ArrayList();
        for(int i=0;i<54;i++){
            Card c=null;
            if(i>=0&&i<=3){
                c = new Card(0);
                if(i==0)
                    c.setPath("2_of_clubs");
                if(i==1)
                    c.setPath("2_of_hearts");
                if(i==2)
                    c.setPath("2_of_diamonds");
                if(i==3)
                    c.setPath("2_of_spades");

            }else if(i>=4&&i<=7){
                c = new Card(1);;
                if(i==4)
                    c.setPath("3_of_clubs");
                if(i==5)
                    c.setPath("3_of_hearts");
                if(i==6)
                    c.setPath("3_of_diamonds");
                if(i==7)
                    c.setPath("3_of_spades");

            }else if(i>=8&&i<=11){
                c = new Card(2);
                if(i==8)
                    c.setPath("4_of_clubs");
                if(i==9)
                    c.setPath("4_of_hearts");
                if(i==10)
                    c.setPath("4_of_diamonds");
                if(i==11)
                    c.setPath("4_of_spades");

            }else if(i>=12&&i<=15){
                c = new Card(3);
                if(i==12)
                    c.setPath("5_of_clubs");
                if(i==13)
                    c.setPath("5_of_hearts");
                if(i==14)
                    c.setPath("5_of_diamonds");
                if(i==15)
                    c.setPath("5_of_spades");
            }else if(i>=16&&i<=19){
                c = new Card(4);
                if(i==16)
                    c.setPath("6_of_clubs");
                if(i==17)
                    c.setPath("6_of_hearts");
                if(i==18)
                    c.setPath("6_of_diamonds");
                if(i==19)
                    c.setPath("6_of_spades");

            }else if(i>=20&&i<=23){
                c = new Card(5);
                if(i==20)
                    c.setPath("7_of_clubs");
                if(i==21)
                    c.setPath("7_of_hearts");
                if(i==22)
                    c.setPath("7_of_diamonds");
                if(i==23)
                    c.setPath("7_of_spades");

            }else if(i>=24&&i<=27){
                c = new Card(6);
                if(i==24)
                    c.setPath("8_of_clubs");
                if(i==25)
                    c.setPath("8_of_hearts");
                if(i==26)
                    c.setPath("8_of_diamonds");
                if(i==27)
                    c.setPath("8_of_spades");

            }else if(i>=28&&i<=31){
                c = new Card(7);
                if(i==28)
                    c.setPath("9_of_clubs");
                if(i==29)
                    c.setPath("9_of_hearts");
                if(i==30)
                    c.setPath("9_of_diamonds");
                if(i==31)
                    c.setPath("9_of_spades");

            }else if(i>=32&&i<=35){
                c = new Card(8);
                if(i==32)
                    c.setPath("10_of_clubs");
                if(i==33)
                    c.setPath("10_of_hearts");
                if(i==34)
                    c.setPath("10_of_diamonds");
                if(i==35)
                    c.setPath("10_of_spades");

            }else if(i>=36&&i<=39){
                c = new Card(9);
                if(i==36)
                    c.setPath("jack_of_clubs");
                if(i==37)
                    c.setPath("jack_of_hearts");
                if(i==38)
                    c.setPath("jack_of_diamonds");
                if(i==39)
                    c.setPath("jack_of_spades");

            }else if(i>=40&&i<=43){
                c = new Card(10);
                if(i==40)
                    c.setPath("queen_of_clubs");
                if(i==41)
                    c.setPath("queen_of_hearts");
                if(i==42)
                    c.setPath("queen_of_diamonds");
                if(i==43)
                    c.setPath("queen_of_spades");

            }else if(i>=44&&i<=47){
                c = new Card(11);
                if(i==44)
                    c.setPath("king_of_clubs");
                if(i==45)
                    c.setPath("king_of_hearts");
                if(i==46)
                    c.setPath("king_of_diamonds");
                if(i==47)
                    c.setPath("king_of_spades");

            }else if(i>=48&&i<=51){
                c = new Card(12);
                if(i==48)
                    c.setPath("ace_of_clubs");
                if(i==49)
                    c.setPath("ace_of_hearts");
                if(i==50)
                    c.setPath("ace_of_diamonds");
                if(i==51)
                    c.setPath("ace_of_spades");

            }else if(i==52||i==53){
                c = new Card(13);
                if(i==52)
                    c.setPath("red_joker");
                if(i==53)
                    c.setPath("black_joker");

            }
            deckhalfrdy.add(c);
        }
        //shuffle:
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i=0;i<54;i++){
            numbers.add(i);
        }
        ArrayList<Card>deckrdy= new ArrayList<>();
        for(int i=0;i<54;i++){
            int randNum = rand.nextInt(numbers.size());
            Card c = deckhalfrdy.get(randNum);
            deckhalfrdy.remove(randNum);
            deckrdy.add(c);
            numbers.remove(randNum);
        }
        return deckrdy;
    }
}
