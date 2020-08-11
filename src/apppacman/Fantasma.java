package apppacman;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Fantasma extends Thread implements IPintar, IMovimientos {
    public static Color COLORES[] = {Color.MAGENTA,Color.ORANGE,Color.GREEN,Color.RED};
    private Color color;
    private int tam;
    private Lienzo lienzo;
    //Var posicionamiento de Fantasma
    public int corX;
    public int corY;
    private int mx;
    private int my;
    public final int DEFAULT_X = 10;
    public final int DEFAULT_Y = 7;    
    private int laberinto[][];
    //Var Funcionamiento de Fantasma
    private boolean corre = true;
    private double tiempo;
    private boolean cambio = false;
    private int direcActual = 0;
    private int pasos;
    private int totPasos;

    public Fantasma(Lienzo lienzo,int corX, int corY, int tam, int color, int laberinto[][], double tiempo) {
        this.lienzo=lienzo;
        this.corX = corX;
        this.corY = corY;
        this.tam = tam;
        this.color = COLORES[color];
        this.laberinto = laberinto;
        this.tiempo = tiempo;
        this.mx = (corX / 50);
        this.my = (corY / 50);
        this.pasos = 0;
    }

    public void run() {
        cambiarDireccion();
        tiempoEspera();
        tiempo = 0.3;
        totPasos();
        while (corre == true) {
            if (cambio == true && pasos != totPasos) {
                correrEnLab();
                tiempoEspera();
                pasos++;                
            } else {
                cambiarDireccion();
                pasos = 0;
                totPasos();
            }
            lienzo.repaint();
        }
    }

    private synchronized void cambiarDireccion() {
        direcActual = getPosRandom();
        cambio = true;
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
        }
    }

    @Override
    public void moveUp() {
        my--;
        if (laberinto[my][mx] > 0 && laberinto[my][mx] <= 6 && laberinto[my][mx] != 3) {
            corY = 50 * my;
        } else {//Pared
            my++;
            cambio = false;
        }
    }

    @Override
    public void moveDown() {
        my++;
        if (laberinto[my][mx] > 0 && laberinto[my][mx] <= 6 && laberinto[my][mx] != 3) {
            corY = 50 * my;
        } else {//Pared
            my--;
            cambio = false;
        }
    }

    @Override
    public void moveLeft() {
        mx--;
        if (laberinto[my][mx] > 0 && laberinto[my][mx] <= 6 && laberinto[my][mx] != 3) {
            corX = 50 * mx;
        } else {//Pared
            mx++;
            cambio = false;
        }
    }

    @Override
    public void moveRigth() {
        mx++;
        if (laberinto[my][mx] > 0 && laberinto[my][mx] <= 6 && laberinto[my][mx] != 3) {
            corX = 50 * mx;
        } else {//Pared
            mx--;
            cambio = false;
        }
    }

    private void tiempoEspera() {
        int tiempo = (int) (1000 * this.tiempo);
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {

        }
    }
    
    public void totPasos() {
        this.totPasos = (int) (Math.random() * 7) + 1;
    }

    private int getPosRandom() {
        int aux = 1;
        aux = (int) (Math.random() * 4) + 1;
        return aux;
    }

    public void setDetener(boolean corre) {
        this.corre = corre;
    }

    @Override
    public synchronized void pintar(Graphics2D g) {
        //g.fillPolygon(pointsX, pointsY, 6);   //Para generar curvas de fantasa
        g.setColor(color);
        g.fillOval(corX + 5, corY, 40, 43);
        //Ojos
        g.setColor(Color.WHITE);
        g.fillOval(corX + 13, corY + 8, 10, 10);
        g.fillOval(corX + 27, corY + 8, 10, 10);
        g.setColor(Color.BLACK);
        g.fillOval(corX + 15, corY + 10, 5, 5);
        g.fillOval(corX + 29, corY + 10, 5, 5);
        g.setColor(color);
        //Picos fantasma
        int x1[] = {corX + 5, corX + 15, corX + 10};
        int y1[] = {corY + 25, corY + 40, corY + 48};
        int x2[] = {corX + 15, corX + 25, corX + 20};
        int y2[] = {corY + 40, corY + 40, corY + 48};
        int x3[] = {corX + 25, corX + 35, corX + 30};
        int y3[] = {corY + 40, corY + 40, corY + 48};
        int x4[] = {corX + 35, corX + 45, corX + 40};
        int y4[] = {corY + 40, corY + 25, corY + 48};
        g.fillPolygon(x1, y1, 3);
        g.fillPolygon(x2, y2, 3);
        g.fillPolygon(x3, y3, 3);
        g.fillPolygon(x4, y4, 3);

    }

    public void setMx(int x) {
        this.mx = x;
    }

    public void setMy(int y) {
        this.my = y;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public synchronized void setValoresDefault(int corX, int corY, double tiempo, boolean corre) {
        this.corX = corX;
        this.corY = corY;
        this.tiempo = tiempo;
        this.corre = corre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    

    @Override
    public String toString() {
        return "Fantasma my: " + my + " mx: " + mx;
    }

}
