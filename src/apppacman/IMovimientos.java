package apppacman;

/**
 *
 * @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public interface IMovimientos {
    public static final int UP=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    public static final int RIGTH=4;
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRigth();
    public void correrEnLab();
    
}
