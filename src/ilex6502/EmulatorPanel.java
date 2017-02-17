/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import ilex6502.emulator.Emulator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

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
        Dimension screenSize = new Dimension(975,615);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        
        setTheme(EmuTheme.ATARI_CLASSIC);
        //registers = new RegisterPanel();
        
        JPanel foo = emu.getScreenPanel();
        if (foo == null){
            System.out.println("FUCK");
        }
        
        add(emu.getScreenPanel());
        add(emu.getRegisterPanel());
        add(Box.createVerticalGlue());
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        
    }
    
    public void setTheme(EmuTheme theme){
        getScreen().setFont(theme.getSystemFont());
        getScreen().setBackground(theme.getBgColor());
        getScreen().setForeground(theme.getFgColor());
    }

    
    /**
     * @return the screen
     */
    public JTextArea getScreen() {
        return getEmu().getScreenPanel().getScreen();
    }

    /**
     * @return the emu
     */
    public GUIEmulator getEmu() {
        return emu;
    }

    
    
}
