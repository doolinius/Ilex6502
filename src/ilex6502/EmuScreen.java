/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jdoolin
 */
public class EmuScreen extends Canvas{
    //private JTextArea screen;
    //private Canvas screen;
    private final int MARGIN_SIZE = 30;
    private String contents = "";
    private byte[] charMem;
    public EmuScreen(byte[] mem){
        charMem = mem;
        //screen = new JTextArea(40,25);
        //screen = new Canvas();
        //screen.setWrapStyleWord(false);
        //screen.setLineWrap(true);
        //screen.setMargin( new Insets(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE) );// 
        Dimension screenSize = new Dimension(980,760);
        setSize(screenSize);
        
        addKeyListener(null);
        
        //setLayout(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Emulator Screen");
        
        //setBorder(border);
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        //add(screen,BorderLayout.CENTER);
    }
    
    private String getLine(int lineNum){
        String line = "";
        for(int i=lineNum*40; i<40; ++i){
            if (charMem[i] != 0){
                line += Character.toString(charMem[i]);
            }else{
                break;
            }
        }
        return(line);
    }
    
    private void printText(Graphics g){
        // set color to red
        g.setColor(getForeground());

        // set Font
        g.setFont(getFont());

        // draw a string
        /*
        g.drawString("abcdefghijklmnopqrstuvwxyz12345678901234", 10, 30);
        int y=60;
        for (int i=0; i<24; ++i){
            g.drawString((i+2) + "bcdefghijklmnopqrstuvwxyz12345678901234", 10, y+(30*i));
        }
        */
        int y=30;
        int x=10;
        for (int i=0; i<25; ++i){
            g.drawString(getLine(i), 10, y+(30*i));
        }
    }
    
    public void paint(Graphics g){
        printText(g);
    }

    /**
     * @return the screen
     */
    //public Canvas getScreen() {
    //    return screen;
    //}

    /**
     * @param screen the screen to set
     */
    //public void setScreen(Canvas screen) {
    //    this.screen = screen;
    //}
    
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
