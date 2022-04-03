import javax.swing.*;

public class Box extends JTable {

    private int color;

    public int getColor() {
        return color;
    }

    public Box(int x, int y){
        color = 0;
        setBounds(x* Configuration.SIZE,y* Configuration.SIZE, Configuration.SIZE, Configuration.SIZE );
        setBackground(Configuration.COLORS[0]);
    }

    public void setColor(int color){
        this.color = color;
        if(color >= 0 && color < Configuration.COLORS.length)
            setBackground(Configuration.COLORS[color]);
    }
}
