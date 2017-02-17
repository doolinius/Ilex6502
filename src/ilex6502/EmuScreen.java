/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jdoolin
 */
public class EmuScreen extends JPanel{
    private JTextArea screen;
    
    public EmuScreen(){
        screen = new JTextArea();
        screen.setWrapStyleWord(false);
        screen.setLineWrap(true);
        Dimension screenSize = new Dimension(975,615);
        screen.setSize(screenSize);
        
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
    public JTextArea getScreen() {
        return screen;
    }

    /**
     * @param screen the screen to set
     */
    public void setScreen(JTextArea screen) {
        this.screen = screen;
    }
    
}
