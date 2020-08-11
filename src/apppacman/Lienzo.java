package apppacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 * ** @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Lienzo extends JPanel implements KeyListener {

    private Graphics2D g2;
    private int tamLabYpx;
    private int tamFullScreenX;
    private int tamFullScreenY;
    private final int tamLetra = 30;

    static final double VEL = 0.3;
    public static int score = 0;
    private int win;
    public static int bolasActu = 0;
    private int nivel = 1;
    Pacman vidasPac[];
    Pacman miPacman;
    public static Fantasma fantasmas[];
    Laberinto laberinto;
    public static int arrLab[][];

//MATRIZ
    public Lienzo() {
        this.setBackground(Color.black);
        Sonido.sonar(Sonido.INICIO);
        laberinto = new Laberinto(nivel);
        arrLab = laberinto.getLab();
        miPacman = new Pacman(500, 550, Laberinto.TAM, arrLab);
        vidasPac = new Pacman[miPacman.getVidas()];
        fantasmas = new Fantasma[4];
        fantasmas[0] = new Fantasma(this,500, 300, 50, 0, arrLab, 4);
        fantasmas[1] = new Fantasma(this,550, 350, 50, 1, arrLab, 6);
        fantasmas[2] = new Fantasma(this,450, 350, 50, 2, arrLab, 8);
        fantasmas[3] = new Fantasma(this,500, 400, 50, 3, arrLab, 10);
        fantasmas[0].start();
        fantasmas[1].start();
        fantasmas[2].start();
        fantasmas[3].start();
        miPacman.start();
        cargarPunTot();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        laberinto.pintar(g2);
        verScore(g2);
        verNivel(g2);
        fantasmas[0].pintar(g2);
        fantasmas[1].pintar(g2);
        fantasmas[2].pintar(g2);
        fantasmas[3].pintar(g2);
        miPacman.pintar(g2);
        comerFantasmaPacman();
        verVidas(g2);
        verGanarPerder(g2);

    }

//FUNCIONES
    public void cargarPunTot() {
        for (int i = 0; i < 14; i++) 
            for (int j = 0; j < 21; j++) 
                if (arrLab[i][j] == 1 || arrLab[i][j] == 5) 
                    win++;
    }

    public void verScore(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.setFont(new Font("COMIC SANS", Font.BOLD, tamLetra));
        g2.drawString("SCORE: " + score, 200, tamLabYpx - tamLetra - 5);

    }

    public void verGanarPerder(Graphics2D g2) {
        if (bolasActu >= win) {
            if (Laberinto.MAX_LEVEL > nivel) {
                siguienteNivel();
            } else {
                g2.setColor(Color.GREEN);
                g2.setFont(new Font("COMIC SANS", Font.BOLD, tamLetra));
                g2.drawString("GANADOR", 450, tamLabYpx - tamLetra - 5);
                terminarJuego();
                Sonido.sonar(Sonido.VICTORIA);
            }
        }
        if (miPacman.getVidas() == 0) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("COMIC SANS", Font.BOLD, tamLetra));
            g2.drawString("PERDIO", 450, tamLabYpx - tamLetra - 5);
        }

    }

    public void verNivel(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("COMIC SANS", Font.BOLD, tamLetra));
        g2.drawString("NIVEL: " + (nivel), 10, tamLabYpx - tamLetra - 5);
    }

    public void verVidas(Graphics2D g2) {
        int corX = 850;
        int corY = tamLabYpx - 62;
        g2.setColor(Color.ORANGE);
        g2.setFont(new Font("COMIC SANS", Font.BOLD, tamLetra));
        g2.drawString("VIDAS: ", 730, tamLabYpx - tamLetra - 5);
        if (miPacman.getVidas() > 0) {
            for (int i = 0; i < miPacman.getVidas(); i++) {
                vidasPac[i] = new Pacman(corX, corY, 30);
                vidasPac[i].pintar(g2);
                corX += 30;
            }
        }

    }

    public void comerFantasmaPacman() {
        for (int i = 0; i < fantasmas.length; i++) {
            if (fantasmas[i].corX == miPacman.getCorX() && fantasmas[i].corY == miPacman.getCorY()) {
                //Si Pacman tiene poder, Comer fantasma y regresa a posicion default
                if (miPacman.getPoder() > 0) {
                    fantasmas[i].setMy(fantasmas[i].DEFAULT_Y);
                    fantasmas[i].setMx(fantasmas[i].DEFAULT_X);
                    fantasmas[i].corX = fantasmas[i].DEFAULT_X * Laberinto.TAM;
                    fantasmas[i].corY = fantasmas[i].DEFAULT_Y * Laberinto.TAM;
                    score+=250;
                } else {    //Pacman no tiene poder, pacman regresa a posicion default
                    miPacman.setVidas(miPacman.getVidas() - 1);
                    //Posicion ACtual
                    miPacman.setMy(miPacman.DEFAULT_Y);
                    miPacman.setMx(miPacman.DEFAULT_X);
                    miPacman.setCorY(miPacman.DEFAULT_Y * Laberinto.TAM);
                    miPacman.setCorX(miPacman.DEFAULT_X * Laberinto.TAM);
                    miPacman.setDirecActual(0);
                }
            }
        }
        //PerdioJuego no se mueve pacman, no hay vidas
        if (miPacman.getVidas() <= 0) {
            terminarJuego();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                miPacman.setDirecActual(miPacman.UP);
                break;
            case KeyEvent.VK_DOWN:
                miPacman.setDirecActual(miPacman.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                miPacman.setDirecActual(miPacman.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                miPacman.setDirecActual(miPacman.RIGTH);
                break;
            case KeyEvent.VK_R:
                terminarJuego();
                reiniciarJuego();
                break;
            default:
                this.repaint();
        }
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setMedidasScreen(int tamLabYpx, int tamFullScreenX, int tamFullScreenY) {
        this.tamLabYpx = tamLabYpx;
        this.tamFullScreenX = tamFullScreenX;
        this.tamFullScreenY = tamFullScreenY;
    }

    public void terminarJuego() {
        try {
            miPacman.setVivo(false);
            fantasmas[0].setDetener(false);
            fantasmas[1].setDetener(false);
            fantasmas[2].setDetener(false);
            fantasmas[3].setDetener(false);
        } catch (Exception e) {
        }
    }

    public void reiniciarJuego() {
        nivel = 1;
        Sonido.sonar(Sonido.INICIO);
        laberinto = new Laberinto(nivel);
        arrLab = laberinto.getLab();
        //Posicion Default Pacman
        miPacman = new Pacman(500, 550, Laberinto.TAM, arrLab);
        //Valores por defecto pacman
        fantasmas[0] = new Fantasma(this,500, 300, 50, 0, arrLab, 4);
        fantasmas[1] = new Fantasma(this,550, 350, 50, 1, arrLab, 6);
        fantasmas[2] = new Fantasma(this,450, 350, 50, 2, arrLab, 8);
        fantasmas[3] = new Fantasma(this,500, 400, 50, 3, arrLab, 10);
        //Iniciamos Pacman
        fantasmas[0].start();
        fantasmas[1].start();
        fantasmas[2].start();
        fantasmas[3].start();
        miPacman.start();
        //Default marcador
        score = 0;
        win = 0;
        bolasActu = 0;
        cargarPunTot();
    }

    public void siguienteNivel() {
        Sonido.sonar(Sonido.VICTORIA);
        terminarJuego();
        nivel += 1;
        Sonido.sonar(Sonido.VICTORIA);
        laberinto = new Laberinto(nivel);
        arrLab = laberinto.getLab();
        //Posicion Default Pacman
        miPacman = new Pacman(500, 550, Laberinto.TAM, arrLab);
        //Valores por defecto pacman
        fantasmas[0] = new Fantasma(this,500, 300, 50, 0, arrLab, 4);
        fantasmas[1] = new Fantasma(this,550, 350, 50, 1, arrLab, 6);
        fantasmas[2] = new Fantasma(this,450, 350, 50, 2, arrLab, 8);
        fantasmas[3] = new Fantasma(this,500, 400, 50, 3, arrLab, 10);
        //Iniciamos Pacman
        fantasmas[0].start();
        fantasmas[1].start();
        fantasmas[2].start();
        fantasmas[3].start();
        miPacman.start();
        //Default marcador
        win = 0;
        bolasActu = 0;
        cargarPunTot();
    }
}
