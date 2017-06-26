/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
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
    private final int MARGIN_SIZE = 30;
    public EmuScreen(){
        screen = new JTextArea(40,25);
        screen.setWrapStyleWord(false);
        screen.setLineWrap(true);
        screen.setMargin( new Insets(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE) );// 
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
