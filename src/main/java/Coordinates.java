public class Coordinates {

    public final  int X;
    public final int Y;

    public Coordinates(int x, int y){
        this.X = x;
        this.Y = y;
    }

    public Coordinates plus(int sx, int sy){
        return new Coordinates(X + sx, Y + sy);
    }
}
