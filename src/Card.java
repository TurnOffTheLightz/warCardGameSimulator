import java.awt.*;

public class Card {
    int id;
    String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    boolean isFront = true;
    public Card(int id){//card front
        this.isFront=true;
        this.id=id;
    }
    public Card(){//card back
        this.isFront=false;
    }

    public String toString() {
        return path;
    }
}
