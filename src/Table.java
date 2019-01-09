import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Table extends JPanel{
    private STATE State;
    private MouseInputListener mouseListener;
    private Color darkGreen = new Color(0,100,0);

    private BufferedImage playButtonImage;
    private int movesDone=0;


    private ImageIcon cardAIcon;
    private ImageIcon cardBIcon;
    private ImageIcon fightButtonIcon;

    private Rectangle fightButton = new Rectangle(200,100);
    private Rectangle toEndButton = new Rectangle(365,450,220,86);
    private Rectangle playButton = new Rectangle(800,310);
    private Rectangle checkButton = new Rectangle(375,225,200,200);

    private Point playButtonPoint = new Point(100,100);
    private Point fightButtonPoint = new Point(375,100);
    private Point cardAChords = new Point(100,100);
    private Point cardBChords = new Point(600,100);

    private boolean isWar;
    private boolean isChecked;
    private boolean isCheckButtonToShowUp;
    private boolean isAWinner = false;
    private boolean isBWinner = false;
    private boolean isFirstStep = true;

    Timer timer;
    int delay=999;
    private boolean isNextButtonToShowUp;
    private boolean playerAWins;
    private boolean playerBWins;

    private enum STATE{
        MENU,
        GAME,
        STATS;
    };

    Player playerA,playerB;
    public Table(){
        initializeGame();
    }
    public void paintComponent(Graphics g){
        g.setColor(darkGreen);
        g.fillRect(0,0,1000,800);
        if(State == STATE.MENU){
            try {
                paintMenu(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(State == STATE.GAME){
            paintGame(g);
        }else if(State ==STATE.STATS){
            paintStats(g);
        }
    }

    private void paintStats(Graphics g) {
        //ładnaramka, napisac kto wygral, ile ruchow bylo potrzeba, przycisk END GAME i REMATCH
        g.setColor(Color.WHITE);
        g.drawString("Moves Done: "+movesDone,500,500);
    }

    private boolean cleanUpWar = false;
    public void paintGame(Graphics g){
        fightButtonIcon = new ImageIcon("graphics/fightbutton.png");
        fightButtonIcon  = resizeImageIcon(fightButtonIcon,fightButton.width,fightButton.height);
        fightButtonIcon.paintIcon(this,g,fightButtonPoint.x,fightButtonPoint.y);

        if(isCheckButtonToShowUp){
            paintCheckButton(g);
        }
        paintToEndButton(g);

        Deck dA = playerA.getDeck();
        Deck dB = playerB.getDeck();
        Card cA = dA.getIndex(0);
        Card cB = dB.getIndex(0);

        if(isWar){
            for(int i=0;i<warCardsA.size();i++){
                ImageIcon aa,bb;
                Card a = warCardsA.get(i);
                Card b = warCardsB.get(i);
                if(i%2==1){
                    a.setFront(false);
                    b.setFront(false);
                }else{
                    a.setFront(true);
                    b.setFront(true);
                }
                if(a.isFront()&&b.isFront()){
                    aa = new ImageIcon("playing_cards/"+a.getPath()+".png");
                    aa = resizeImageIcon(aa,250,363);
                    aa.paintIcon(this,g,100,100+50*i);

                    bb = new ImageIcon("playing_cards/"+b.getPath()+".png");
                    bb = resizeImageIcon(bb,250,363);
                    bb.paintIcon(this,g,600,100+50*i);
                }else{
                    aa = new ImageIcon("playing_cards/cardback.png");
                    aa = resizeImageIcon(aa,350,350);
                    aa.paintIcon(this,g,48,100+50*i);

                    bb = new ImageIcon("playing_cards/cardback.png");
                    bb = resizeImageIcon(bb,350,350);
                    bb.paintIcon(this,g,548,100+50*i);
                }
            }
            if(isChecked){
                for (int kk=0;kk<warCardsA.size();kk++){
                    ImageIcon aa,bb;
                    Card a = warCardsA.get(kk);
                    Card b = warCardsB.get(kk);

                    aa = new ImageIcon("playing_cards/"+a.getPath()+".png");
                    aa = resizeImageIcon(aa,250,363);
                    aa.paintIcon(this,g,100,100+50*kk);

                    bb = new ImageIcon("playing_cards/"+b.getPath()+".png");
                    bb = resizeImageIcon(bb,250,363);
                    bb.paintIcon(this,g,600,100+50*kk);
                }
            }
        }else{
            cardAIcon = new ImageIcon("playing_cards/"+cA.getPath()+".png");
            cardAIcon = resizeImageIcon(cardAIcon,250,363);
            cardAIcon.paintIcon(this,g,cardAChords.x,cardAChords.y);

            cardBIcon = new ImageIcon("playing_cards/"+cB.getPath()+".png");
            cardBIcon = resizeImageIcon(cardBIcon,250,363);
            cardBIcon.paintIcon(this,g,cardBChords.x,cardBChords.y);
        }
        g.setColor(Color.white);
        g.drawString("Amount of cards: "+Integer.toString(dA.getAmountOfCards()),100,50);
        g.drawString("Amount of cards: "+Integer.toString(dB.getAmountOfCards()),600,50);
        g.drawString("Fights done:"+ Integer.toString(movesDone),350,50);

        if(isNextButtonToShowUp){
            g.setColor(Color.WHITE);
            ImageIcon btn = new ImageIcon("graphics/nextbutton.png");
            resizeImageIcon(btn,200,200).paintIcon(this,g,checkButton.x,checkButton.y);
        }

        if(isAWinner){
            ImageIcon winner = new ImageIcon("graphics/winner.png");
            winner = resizeImageIcon(winner, 160,90);
            winner.paintIcon(this,g,0,40);
        }else if(isBWinner){
            ImageIcon winner = new ImageIcon("graphics/winner.png");
            winner = resizeImageIcon(winner, 192,108);
            winner.paintIcon(this,g,800,40);
        }
    }

    private void paintToEndButton(Graphics g){
        ImageIcon btn = new ImageIcon("graphics/toendbutton.png");
        btn.paintIcon(this,g,366,425);
    }


    public void paintMenu(Graphics g) throws IOException {
        playButtonImage = ImageIO.read(new File("graphics/playbutton.png"));
        g.drawImage(playButtonImage,playButtonPoint.x,playButtonPoint.y,null);
    }
    public void paintCheckButton(Graphics g){
        g.setColor(Color.WHITE);
        if(!cleanUpWar){
            ImageIcon btn = new ImageIcon("graphics/checkbutton.png");
            resizeImageIcon(btn,200,200).paintIcon(this,g,checkButton.x,checkButton.y);
        }else{
            ImageIcon btn = new ImageIcon("graphics/nextbutton.png");
            resizeImageIcon(btn,200,200).paintIcon(this,g,checkButton.x,checkButton.y);
        }
    }
    public void initializeGame(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(this);
        playerA = new Player();
        playerB = new Player();
        State = STATE.MENU;
        mouseListener = new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(State == STATE.MENU){
                    if(x<=playButtonPoint.x+playButton.width&&x>=playButtonPoint.x){
                        if(y<=playButtonPoint.y+playButton.height&&y>=playButtonPoint.y){
                            State = STATE.GAME;
                            repaint();
                        }
                    }
                }else if(State == STATE.GAME){

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(State == STATE.MENU){
                    if(x<=playButtonPoint.x+playButton.width&&x>=playButtonPoint.x){
                        if(y<=playButtonPoint.y+playButton.height&&y>=playButtonPoint.y){

                        }
                    }
                }else if(State == STATE.GAME){
                    if(isFirstStep){
                        if(x<=toEndButton.x+toEndButton.width&&x>=toEndButton.x){
                            if(y<=toEndButton.y+toEndButton.height&&y>=toEndButton.y){
                                toEndButtonPressed();
                            }
                        }
                    }
                    if(isNextButtonToShowUp) {
                        if (x <= checkButton.x + checkButton.width && x >= checkButton.x && (isAWinner || isBWinner)) {//next button
                            if (y <= checkButton.y + checkButton.height && y >= checkButton.y) {
                                isFirstStep=true;
                                if (cleanUpWar) {
                                    cleanUpWar = false;
                                    isCheckButtonToShowUp=false;
                                    isNextButtonToShowUp=false;
                                    warCardsA.clear();
                                    warCardsB.clear();
                                    isChecked = false;
                                    isWar=false;
                                    isNextWar=false;
                                    isBWinner=false;
                                    isAWinner=false;
                                } else {
                                    Deck dckA = playerA.getDeck();
                                    Deck dckB = playerB.getDeck();
                                    Card a1 = dckA.getIndex(0);
                                    Card b1 = dckB.getIndex(0);

                                    if (isAWinner) {
                                        dckA.addCard(b1);

                                        dckB.removeIndex(0);
                                        dckA.swap();
                                        dckA.increaseAmountOfCards();
                                        dckB.decreaseAmountOfCards();
                                        isAWinner = false;
                                    }
                                    if (isBWinner) {
                                        dckB.addCard(a1);

                                        dckA.removeIndex(0);
                                        dckB.swap();
                                        dckA.decreaseAmountOfCards();
                                        dckB.increaseAmountOfCards();
                                        isBWinner = false;
                                    }

                                    isNextButtonToShowUp = false;
                                }
                                repaint();
                            }
                        }
                    }
                    if(isCheckButtonToShowUp){//checkbutton
                        if(x<=checkButton.x+checkButton.width&&x>=checkButton.x){
                            if(y<=checkButton.y+checkButton.height&&y>=checkButton.y){
                                isChecked = true;
                                cleanUpWar=true;
                                isNextButtonToShowUp=true;
                                repaint();
                            }
                        }
                    }
                    if(x<=fightButtonPoint.x+fightButton.width&&x>=fightButtonPoint.x&&!isWar&&!isNextButtonToShowUp){//fight button
                        if(y<=playButtonPoint.y+fightButton.height&&y>=fightButtonPoint.y){
                            isFirstStep=false;
                            Deck dckA = playerA.getDeck();
                            Deck dckB = playerB.getDeck();
                            Card a1,b1;
                            a1 = dckA.getIndex(0);
                            b1 = dckB.getIndex(0);

                            if(a1.getId()>b1.getId()){ //player A wins
                                isAWinner=true;
                                isNextButtonToShowUp=true;
                                repaint();
                            }else if(a1.getId()<b1.getId()){// player B wins
                                isNextButtonToShowUp=true;
                                isBWinner=true;
                                repaint();
                            }else if(a1.getId()==b1.getId()){ // WAR
                                isWar=true;
                                isCheckButtonToShowUp = true;
                                repaint();
                                war(dckA,dckB);
                            }
                            movesDone++;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
        addMouseListener(mouseListener);
    }

    private void toEndButtonPressed() {
        Deck dckA=playerA.getDeck();
        Deck dckB=playerB.getDeck();
        while(dckA.isDeckAlive()&&dckB.isDeckAlive()){
            boolean deckAlive = true;
            dckA = playerA.getDeck();
            dckB = playerB.getDeck();
            Card a1=null,b1=null;
            try {
                a1 = dckA.getIndex(0);
                b1 = dckB.getIndex(0);
            }catch(IndexOutOfBoundsException e){
                if(a1 == null){
                    playerBWins=true;
                    endGame();
                    break;
                }
                if(b1 ==null){
                    playerAWins = true;
                    System.out.println("deckalive->false");
                    endGame();
                    break;
                }
                System.out.println("INDEX BAŁNDS AŁT OF EKSCEPSZYN");
                e.printStackTrace();
            }
                System.out.println(a1+" -> a1"+" b1->"+b1);
                if (a1.getId() > b1.getId()) {// a wins
                    dckA.addCard(b1);

                    dckB.removeIndex(0);
                    dckA.swap();
                    dckA.increaseAmountOfCards();
                    dckB.decreaseAmountOfCards();
                    isAWinner = true;
                    isNextButtonToShowUp = true;
                    repaint();

                } else if (a1.getId() < b1.getId()) {// bw ins
                    dckB.addCard(a1);

                    dckA.removeIndex(0);
                    dckB.swap();
                    dckA.decreaseAmountOfCards();
                    dckB.increaseAmountOfCards();
                    isBWinner = true;
                    isNextButtonToShowUp = true;
                    repaint();
                } else if (a1.getId() == b1.getId()) { // fast war
                    isWar = true;
                    isCheckButtonToShowUp = true;
                    repaint();
                    war(dckA, dckB);
                    if(isBreak){
                        break;
                    }
                }
                if (isCheckButtonToShowUp) {
                    isChecked = true;
                    cleanUpWar = true;
                    isNextButtonToShowUp = true;
                }
                if (isNextButtonToShowUp) {
                    isFirstStep = true;
                    if (cleanUpWar) {
                        cleanUpWar = false;
                        isCheckButtonToShowUp = false;
                        isNextButtonToShowUp = false;
                        warCardsA.clear();
                        warCardsB.clear();
                        isChecked = false;
                        isWar = false;
                        isNextWar = false;
                        isBWinner = false;
                        isAWinner = false;
                    } else {
                        isAWinner = false;
                        isBWinner = false;
                        isNextButtonToShowUp = false;
                    }
                    repaint();
                }
            }
            movesDone++;
    }
    public void endGame(){
        State = STATE.STATS;
        repaint();
    }


    public ImageIcon resizeImageIcon(ImageIcon icon,int newW,int newH){
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(newW,newH,Image.SCALE_DEFAULT);
        icon = new ImageIcon(newimg);
        return icon;
    }
    private int warcounter=-1;
    private ArrayList<Card> warCardsA = new ArrayList<>();
    private ArrayList<Card> warCardsB = new ArrayList<>();
    private boolean isNextWar = false;
    private boolean isBreak = false;
    public void war(Deck dckA,Deck dckB){
        boolean isDeckAlive=true;
        Card a=null,b=null;
        warcounter++;
            try {
                a = dckA.getIndex(warcounter);
                b = dckB.getIndex(warcounter);
                warCardsA.add(warCardsA.size(),a);
                warCardsB.add(warCardsB.size(),b);
            }catch(IndexOutOfBoundsException e){
                if(a==null){
                    playerAWins = true;
                    isBreak = true;
                    endGame();
                }else if(b==null){
                    playerBWins = true;
                    isDeckAlive=false;
                    endGame();
                    isBreak = true;
                }
                e.printStackTrace();
            }
            Card a2=null,a3=null,b2=null,b3=null;
            warcounter++;
            try {
                a2 = dckA.getIndex(warcounter);
                b2 = dckB.getIndex(warcounter);
                warCardsA.add(warCardsA.size(),a2);
                warCardsB.add(warCardsB.size(),b2);
            }catch(IndexOutOfBoundsException e){
                if(a2==null){
                    playerBWins = true;
                    isDeckAlive=false;
                    isBreak=true;
                    endGame();
                }else if(b2==null){
                    playerAWins = true;
                    isDeckAlive=false;
                    isBreak=true;
                    endGame();
                }
                e.printStackTrace();
            }
        if(!isNextWar&&isDeckAlive){
            warcounter++;
                try {
                    a3 = dckA.getIndex(warcounter);
                    b3 = dckB.getIndex(warcounter);
                    warCardsA.add(warCardsA.size(),a3);
                    warCardsB.add(warCardsB.size(),b3);
                }catch(IndexOutOfBoundsException e){
                    if(a3==null){
                        playerAWins = true;
                        isDeckAlive = false;
                        endGame();
                    }else if(b3==null){
                        playerBWins = true;
                        isDeckAlive = false;
                        endGame();
                    }
                    e.printStackTrace();
                }
                if(isDeckAlive) {
                    if (a3.getId() > b3.getId()) {
                        for (int i = 0; i < warCardsB.size(); i++) {//player a wins war
                            dckA.addCard(warCardsB.get(i));
                            dckB.removeIndex(i);
                            dckA.increaseAmountOfCards();
                            dckB.decreaseAmountOfCards();
                        }
                        dckA.swap();
                        warcounter = -1;
                        isAWinner = true;
                    } else if (a3.getId() < b3.getId()) {//player b wins war
                        for (int i = 0; i < warCardsA.size(); i++) {
                            dckB.addCard(warCardsA.get(i));
                            dckA.removeIndex(i);
                            dckA.decreaseAmountOfCards();
                            dckB.increaseAmountOfCards();
                        }
                        dckB.swap();
                        warcounter = -1;
                        isBWinner = true;
                    } else if (a3.getId() == b3.getId()) {
                        isNextWar = true;
                        war(dckA, dckB);
                    }
                }
            }else if(isDeckAlive){
                if(a2.getId()>b2.getId()){//plyer a wins
                    for(int i=0;i<warCardsB.size();i++){
                        dckA.addCard(warCardsB.get(i));
                        dckB.removeIndex(i);
                        dckA.increaseAmountOfCards();
                        dckB.decreaseAmountOfCards();
                    }
                    dckA.swap();
                    warcounter=-1;
                    isAWinner=true;
                }
                else if(a2.getId()<b2.getId()){//plyer b wins
                    for(int i=0;i<warCardsA.size();i++){
                        dckB.addCard(warCardsA.get(i));
                        dckA.removeIndex(i);
                        dckA.decreaseAmountOfCards();
                        dckB.increaseAmountOfCards();
                    }
                    dckB.swap();
                    warcounter=-1;
                    isBWinner=true;
                }else if(a2.getId()==b2.getId()){
                    isNextWar=true;
                    try {
                        war(dckA, dckB);
                    }catch (NullPointerException e){
                        System.out.println("SHOW MUST GO ON");
                    }
                }
            }
    }
}