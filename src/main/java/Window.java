import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame{

    private final Box[][] BOXES;
    private ActivityFigure figure;

    public int getBoxColor(int x, int y){
        if(x<0 || x >= Configuration.WIDTH) return -1;
        if(y<0 || y >= Configuration.HEIGHT) return -1;
        return BOXES[x][y].getColor();
    }

    public Window(){
        BOXES = new Box[Configuration.WIDTH][Configuration.HEIGHT];
        initForm();
        initBoxes();
        addKeyListener(new KeyAdapter());
        TimeAdapter timeAdapter = new TimeAdapter();
        Timer timer = new Timer(500,timeAdapter);
        timer.start();
        repaint();
    }

    public void addFigure(){
        figure = new ActivityFigure(this);
        if(checkGameOver()){
            dispose();
        }
        showFigure();
    }

    private boolean checkGameOver(){
        return !figure.canMoveFigure(figure.getFigure(),0,0);
    }

    class KeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> moveFigure(+1, 0);
                case KeyEvent.VK_LEFT -> moveFigure(-1, 0);
                case KeyEvent.VK_UP -> turnFigure();
                case KeyEvent.VK_DOWN -> moveFigure(0, 1);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
    private  void initForm(){

        setSize(Configuration.WIDTH* Configuration.SIZE + 15,
                Configuration.HEIGHT * Configuration.SIZE + 38);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private  void initBoxes(){
        for (int x = 0; x < Configuration.WIDTH; x++) {
            for (int y = 0; y < Configuration.HEIGHT; y++) {
                BOXES[x][y] = new Box(x,y);
                add(BOXES[x][y]);
            }
        }
    }

    private void moveFigure(int sx,int sy){
        hideFigure();
        figure.moveFigure(sx,sy);
        showFigure();
    }
    private void turnFigure(){
        hideFigure();
        figure.turnFigure();
        showFigure();
    }

    private void showFigure(){
        showFigure(figure.numberColor);
    }

    private void hideFigure(){
        showFigure(0);
    }

    private void showFigure(int color){
        for (Coordinates dot: figure.getFigure().dots){
            setBoxColor(figure.getCoordination().X + dot.X,figure.getCoordination().Y+dot.Y,color);
        }
    }

    private void setBoxColor(int x,int y, int color){
        if(x<0 || x >= Configuration.WIDTH) return;
        if(y<0 || y >= Configuration.HEIGHT) return;
        BOXES[x][y].setColor(color);
    }

    private void stripLines(){
        for (int y = Configuration.HEIGHT - 1; y >= 0; y --)
            if (isFullLine(y)){
                dropLine(y);
            }
    }

    private void dropLine(int y){
        for (int my = y-1; my >= 0; my--) {
            for (int x = 0; x < Configuration.WIDTH; x++) {
                setBoxColor(x,my +1,getBoxColor(x, my));
            }
        }
        for (int x = 0; x < Configuration.WIDTH; x++){
            setBoxColor(x,0,0);
        }
    }

    private boolean isFullLine(int y){
        for (int x = 0; x < Configuration.WIDTH; x++)
            if (getBoxColor(x,y) == 0)
                return false;
        return true;

    }
    
    class TimeAdapter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            moveFigure(0,1);
            if(figure.isLanded()){
                showFigure(figure.numberColor);
                stripLines();
                addFigure();
            }
        }
    }
}
