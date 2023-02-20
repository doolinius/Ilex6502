/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

/**
 *
 * @author jdoolin
 */
public class EmulatorPanel extends JPanel{
    //private JTextArea screen;
    //private RegisterPanel registers;
    private GUIEmulator emu;
    
    public EmulatorPanel(){
        emu = new GUIEmulator();
        Dimension screenSize = new Dimension(990,690);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        
        setTheme(EmuTheme.ATARI_CLASSIC);
        //registers = new RegisterPanel();
        
        Canvas foo = emu.getScreen();
        if (foo == null){
            System.out.println("WTF");
        }
        
        add(emu.getScreen());
        add(emu.getRegisterPanel());
        add(Box.createVerticalGlue());
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        
    }
    
    class EmuCaret extends DefaultCaret {

        public EmuCaret() {
            setBlinkRate(0); // half a second
        }

        protected synchronized void damage(Rectangle r) {
            if (r == null) {
                return;
            }
            
            // give values to x,y,width,height (inherited from java.awt.Rectangle)
            x = r.x;
            y = r.y + (r.height * 4 / 5 - 3);
            width = 12;
            height = 24;
            repaint(); // calls getComponent().repaint(x, y, width, height)
            
        }
       
        public void paint(Graphics g) {
            JTextComponent comp = getComponent();
            if (comp == null) {
                return;
            }
            
            int dot = getDot();
            Rectangle r = null;
            try {
                r = comp.modelToView(dot);
            } catch (BadLocationException e) {
                return;
            }
            if (r == null) {
                return;
            }

            //int dist = r.height * 4 / 5 - 3; // will be distance from r.y to top

            if ((x != r.x) || (y != r.y)) {
      // paint() has been called directly, without a previous call to
                // damage(), so do some cleanup. (This happens, for example, when
                // the
                // text component is resized.)
                repaint(); // erase previous location of caret
                x = r.x; // set new values for x,y,width,height
                y = r.y;// + dist;
                width = 12;
                height = 24;
            }

            if (isVisible()) {
                g.setColor(comp.getCaretColor());
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);
            }
        }
    }
    
    public void setTheme(EmuTheme theme){
        getScreen().setFont(theme.getSystemFont());
        getScreen().setBackground(theme.getBgColor());
        getScreen().setForeground(theme.getFgColor());
        EmuCaret c = new EmuCaret();
        //getScreen().setCaret(c);
        //getScreen().setCaretColor(theme.getFgColor());
    }

    
    /**
     * @return the screen
     */
    public Canvas getScreen() {
        return getEmu().getScreen();//.getScreen();
    }

    /**
     * @return the emu
     */
    public GUIEmulator getEmu() {
        return emu;
    }

    
    
}
