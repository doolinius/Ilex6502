/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;

/**
 *
 * @author jdoolin
 */
public class Ilex6502{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("Ilex 6502");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        
        f.setPreferredSize(new Dimension(1615,850));
        
        MainPanel mainPanel = new MainPanel();
        //mainPanel.setLayout(new BorderLayout());
        
        JMenuBar menuBar = makeMenuBar(mainPanel);
        f.setJMenuBar(menuBar);
        f.getContentPane().add(mainPanel);
        
        
        f.pack();
        f.setVisible(true);
    }
    
    
    private static JMenuBar makeMenuBar(ActionListener listener){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New ASM File");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem openRecent = new JMenuItem("Open Recent");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        
        JMenu emuMenu = new JMenu("Emulator");
        JMenu themesMenu = new JMenu("Themes");
        ButtonGroup group = new ButtonGroup();
        for (EmuTheme theme: EmuTheme.values()){
            //System.out.println("name: " + theme.getName());
            JRadioButtonMenuItem themeItem = new JRadioButtonMenuItem(theme.getName());
            themeItem.setActionCommand(theme.getName());
            themeItem.addActionListener(listener);
            if (theme == EmuTheme.ATARI_CLASSIC){
                themeItem.setSelected(true);
            }
            group.add(themeItem);
            themesMenu.add(themeItem);
        }
        emuMenu.add(themesMenu);
        
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(emuMenu);
        menuBar.add(helpMenu);
        return(menuBar);
    }
    
}
