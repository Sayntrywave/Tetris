public class ActivityFigure {

    private Figure figure;
    private Coordinates coordinates;
    private boolean landed;
    private int ticks;
    private final Window window;
    public int numberColor;

    public Figure getFigure() {
        return figure;
    }

    public Coordinates getCoordination() {
        return coordinates;
    }

    public boolean isLanded(){
        return landed;
    }

    public ActivityFigure(Window window){
        this.window = window;
        figure = Figure.getRandom();
        numberColor = 1 + (int)(Math.random()*5);
        coordinates = new Coordinates(Configuration.WIDTH/2 -2,0);
        landed = false;
        ticks = 1;
    }

    public boolean canMoveFigure(Figure figure, int sx,int sy){
        if (coordinates.X + sx + figure.top.X < 0) return false;
        if(coordinates.X + sx + figure.bot.X>= Configuration.WIDTH) return false;
        if(coordinates.Y + sy + figure.bot.Y>= Configuration.HEIGHT) return false;
        for (Coordinates dot : figure.dots) {
            if(window.getBoxColor(coordinates.X + dot.X + sx,coordinates.Y + dot.Y+ sy ) != 0)
                return false;
        }
        return true;
    }

    public void moveFigure(int sx, int sy){
        if(canMoveFigure(figure,sx,sy)){
            coordinates = coordinates.plus(sx,sy);
        }
        else
        if (sy == 1){
            if (ticks > 0)
                ticks--;
            else
                landed = true;
        }
    }

    public void turnFigure(){
        Figure rotatedFigure = figure.turnRight();
        if(canMoveFigure(rotatedFigure,0,0)) {
            figure = rotatedFigure;
        }
        else if(canMoveFigure(rotatedFigure,1,0)) {
            figure = rotatedFigure;
            moveFigure(1,0);
        } else if(canMoveFigure(rotatedFigure,-1,0)) {
            figure = rotatedFigure;
            moveFigure(-1,0);
        }else if (canMoveFigure(rotatedFigure,0,-1)) {
            figure = rotatedFigure;
            moveFigure(0,-1);
        }
    }
}
