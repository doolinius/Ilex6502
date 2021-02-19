/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jdoolin
 */
public class EmuScreen extends JPanel{
    //private JTextArea screen;
    private Canvas screen;
    private final int MARGIN_SIZE = 30;
    private String contents = "";
    public EmuScreen(){
        //screen = new JTextArea(40,25);
        screen = new Canvas();
        //screen.setWrapStyleWord(false);
        //screen.setLineWrap(true);
        //screen.setMargin( new Insets(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE) );// 
        Dimension screenSize = new Dimension(975,615);
        screen.setSize(screenSize);
        
        screen.addKeyListener(null);
        
        setLayout(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Emulator Screen");
        
        setBorder(border);
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        add(screen,BorderLayout.CENTER);
    }

    /**
     * @return the screen
     */
    public Canvas getScreen() {
        return screen;
    }

    /**
     * @param screen the screen to set
     */
    public void setScreen(Canvas screen) {
        this.screen = screen;
    }
    
    private class ScreenKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
