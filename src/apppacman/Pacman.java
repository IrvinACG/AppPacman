package apppacman;
import java.awt.Color;
import java.awt.Graphics2D;
/**
 * * @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Pacman extends Thread implements IPintar, IMovimientos {

    private int tam;
    private final int TAM_OJO;
    private int ojoX;
    private int ojoY;
    private int bocaX;
    private int bocaY;
    private int corX;
    private int corY;
    private int poder;
    private Color color = Color.YELLOW;

    public final int DEFAULT_X = 10;
    public final int DEFAULT_Y = 11;
    private int mx;
    private int my;
    private int laberinto[][];

    public boolean vivo;
    private int direcActual = 0;
    private final double TIEMPO = 0.3;
    private int vidas = 3;

    public Pacman(int corX, int corY, int tam, int laberinto[][]) {
        this.corX = corX;
        this.corY = corY;
        this.mx = (corX / 50);
        this.my = (corY / 50);
        this.tam = tam;
        this.laberinto = laberinto;
        this.TAM_OJO = 5;
        //Default Right
        this.ojoX = 15;
        this.ojoY = 5;
        this.bocaX = 35;
        this.bocaY = 295;
        this.poder = 0;
        //Jugabilidad
        vivo = true;

    }

    public Pacman(int corX, int corY, int tam) {
        this.corX = corX;
        this.corY = corY;
        this.tam = tam;
        this.TAM_OJO = 5;
        //Default Right
        this.ojoX = 15;
        this.ojoY = 5;
        this.bocaX = 35;
        this.bocaY = 295;
        this.poder = 0;
    }

    @Override
    public void run() {
        while (vivo == true) {
            tiempoEspera();
            correrEnLab();
            estadoVidas();
        }
    }

    @Override
    public synchronized void correrEnLab() {
        switch (direcActual) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGTH:
                moveRigth();
                break;
            default:

        }
    }

    private void pintarPacmanUp() {
        this.ojoX = 35;
        this.ojoY = 25;
        this.bocaX = 115;
        this.bocaY = 300;
    }

    private void pintarPacmanDown() {
        this.ojoX = 5;
        this.ojoY = 25;
        this.bocaX = 295;
        this.bocaY = 305;
    }

    private void pintarPacmanLeft() {
        this.ojoX = 15;
        this.ojoY = 5;
        this.bocaX = 215;
        this.bocaY = 295;
    }

    private void pintarPacmanRigth() {
        this.ojoX = 15;
        this.ojoY = 5;
        this.bocaX = 35;
        this.bocaY = 295;
    }

    public void actualizarVarsMov() {
        if (laberinto[my][mx] != 5) 
            Sonido.sonar(Sonido.PACMAN);
        else 
            Sonido.sonar(Sonido.PACMAN_2);        
        switch (laberinto[my][mx]) {
                case 1: //Bola Normal
                    laberinto[my][mx] = 2;
                    Lienzo.score += 50;
                    Lienzo.bolasActu++;
                    break;
                case 3:
                    if (direcActual == UP)
                        my = 13;                    
                    if (direcActual == DOWN) 
                        my = 0;                    
                    if (direcActual == LEFT) 
                        mx = 20;                    
                    if (direcActual == RIGTH) 
                        mx = 0;                    
                    break;
                case 5: //Bola Grande
                    laberinto[my][mx] = 2;
                    Lienzo.score += 100;
                    Lienzo.bolasActu++;
                    agregarPoder();
                    break;
        }        
    }

    public void moveUp() {
        try {
            my--;
            if (laberinto[my][mx] > 0 && laberinto[my][mx] < 6) {
                corY = my * Laberinto.TAM;
                actualizarVarsMov();
                pintarPacmanUp();
                estadoPoder();
            } else {
                my++;
                direcActual = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ++my;
        }

    }

    public void moveDown() {
        try {
            my++;
            if (laberinto[my][mx] > 0 && laberinto[my][mx] < 6) {
                corY = my * Laberinto.TAM;
                actualizarVarsMov();
                pintarPacmanDown();
                estadoPoder();
            } else {
                my--;
                direcActual = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            --my;
        }

    }

    public void moveRigth() {
        try {
            mx++;
            if (laberinto[my][mx] > 0 && laberinto[my][mx] < 6) {
                corX = mx * Laberinto.TAM;
                actualizarVarsMov();
                pintarPacmanRigth();
                estadoPoder();
            } else {//Pared
                mx--;
                direcActual = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            --mx;
        }

    }

    @Override
    public void moveLeft() {
        try {
            mx--;
            if (laberinto[my][mx] > 0 && laberinto[my][mx] < 6) {
                corX = mx * Laberinto.TAM;
                actualizarVarsMov();
                pintarPacmanLeft();
                estadoPoder();
            } else {//Pared
                mx++;
                direcActual = 0;

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ++mx;
        }

    }

    @Override
    public synchronized void pintar(Graphics2D g) {
        g.setColor(color);
        g.fillArc(corX, corY, tam, tam, bocaX, bocaY);
        g.setColor(Color.BLACK);
        g.fillOval(corX + ojoX, corY + ojoY, TAM_OJO, TAM_OJO);
    }

    private synchronized void estadoVidas() {
        if (vidas <= 0) {
            vivo = false;
        }
    }

    private synchronized void agregarPoder() {
        poder = 10;
        Lienzo.fantasmas[0].setColor(Color.BLUE);
        Lienzo.fantasmas[1].setColor(Color.BLUE);
        Lienzo.fantasmas[2].setColor(Color.BLUE);
        Lienzo.fantasmas[3].setColor(Color.BLUE);
    }

    private synchronized void estadoPoder() {
        if (poder != 0) {
            poder -= 1;
        } else {
            Lienzo.fantasmas[0].setColor(Fantasma.COLORES[0]);
            Lienzo.fantasmas[1].setColor(Fantasma.COLORES[1]);
            Lienzo.fantasmas[2].setColor(Fantasma.COLORES[2]);
            Lienzo.fantasmas[3].setColor(Fantasma.COLORES[3]);
        }
    }

    private void tiempoEspera() {
        int tiempo = (int) (1000 * this.TIEMPO);
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {

        }
    }

    public int getMx() {
        return mx;
    }

    public void setMx(int mx) {
        this.mx = mx;
    }

    public int getMy() {
        return my;
    }

    public void setMy(int my) {
        this.my = my;
    }

    public int getCorX() {
        return corX;
    }

    public void setCorX(int corX) {
        this.corX = corX;
    }

    public int getCorY() {
        return corY;
    }

    public void setCorY(int corY) {
        this.corY = corY;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getPoder() {
        return poder;
    }

    public Color getColor() {
        return color;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public synchronized void setDirecActual(int direccion) {
        this.direcActual = direccion;
    }

}
