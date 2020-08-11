package apppacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ** @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Ventana extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private final int tamLabYpx = 725;
    private final int tamFullScreenX = 1055;
    private final int tamFullScreenY = 755;

    public Ventana() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("Ayuda");
        menuBar.add(menu);
        menuItem = new JMenuItem("Reinicar Juego (Tecla R)");
        menuItem.setEnabled(false);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        this.setTitle("Irvin CG PACMAN");
        this.setSize(tamFullScreenX, tamFullScreenY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        Lienzo miLienzo = new Lienzo();
        this.addKeyListener(miLienzo);
        this.add(miLienzo);
        miLienzo.setMedidasScreen(tamLabYpx, tamFullScreenX, tamFullScreenY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
