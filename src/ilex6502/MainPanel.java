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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author jdoolin
 */
public class MainPanel extends JPanel implements ActionListener{
    
    private EditorPanel edPanel;
    private EmulatorPanel emuPanel;
    
    private JToolBar toolbar;
    private JButton newButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton assembleButton;
    private JButton runButton;
    private JButton stopButton;
    
    public MainPanel(){
        edPanel = new EditorPanel();
        emuPanel = new EmulatorPanel();
        setLayout(new BorderLayout());
        
        
        JToolBar toolBar = makeToolBar(edPanel);
        
        add(toolBar,BorderLayout.PAGE_START);
        add(edPanel, BorderLayout.LINE_START);
        add(emuPanel, BorderLayout.LINE_END);
        setPreferredSize(new Dimension(1615,850));
    }
    
    private JToolBar makeToolBar(ActionListener listener){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        URL imageURL = Ilex6502.class.getResource("/save.png");
        
        saveButton = new JButton("New");
        saveButton.setToolTipText("Create New ASM File");
        saveButton.setActionCommand("new");
        saveButton.addActionListener(listener);
        imageURL = Ilex6502.class.getResource("/new.png");
        if (imageURL != null) {                      //image found
            saveButton.setIcon(new ImageIcon(imageURL,"Create New ASM File"));
        } else {                                     //no image found
            saveButton.setText("New File");
            System.err.println("Resource not found: " + "/new.png");
        }
        toolBar.add(saveButton);
        
        openButton = new JButton("Open");
        openButton.setToolTipText("Open ASM File");
        openButton.setActionCommand("open");
        openButton.addActionListener(listener);
        imageURL = Ilex6502.class.getResource("/open.png");
        if (imageURL != null) {                      //image found
            openButton.setIcon(new ImageIcon(imageURL,"Open ASM File"));
        } else {                                     //no image found
            openButton.setText("Open ASM File");
            System.err.println("Resource not found: " + "/open.png");
        }
        toolBar.add(openButton);

        saveButton = new JButton("Save");
        saveButton.setToolTipText("Save Source Code");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(listener);
        imageURL = Ilex6502.class.getResource("/save.png");
        if (imageURL != null) {                      //image found
            saveButton.setIcon(new ImageIcon(imageURL,"Save Source Code"));
        } else {                                     //no image found
            saveButton.setText("Save Source Code");
            System.err.println("Resource not found: " + "/save.png");
        }
        
        toolBar.add(saveButton);
        
        assembleButton = new JButton("Assemble");
        assembleButton.setToolTipText("Assemble Source Code");
        assembleButton.setActionCommand("assemble");
        assembleButton.addActionListener(listener);
        imageURL = Ilex6502.class.getResource("/assemble.png");
        if (imageURL != null) {                      //image found
            assembleButton.setIcon(new ImageIcon(imageURL,"Assemble Source Code"));
        } else {                                     //no image found
            assembleButton.setText("Save Source Code");
            System.err.println("Resource not found: " + "/save.png");
        }
        
        toolBar.add(assembleButton);
        
        runButton = new JButton("Run");
        runButton.setToolTipText("Run in Emulator");
        runButton.setActionCommand("run");
        runButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(true);
                emuPanel.getEmu().loadBinary(edPanel.getBinaryFile());
                emuPanel.getEmu().performInstruction();
            }
            
        });
        imageURL = Ilex6502.class.getResource("/run.png");
        if (imageURL != null) {                      //image found
            runButton.setIcon(new ImageIcon(imageURL,"Run in Emulator"));
        } else {                                     //no image found
            runButton.setText("Save Source Code");
            System.err.println("Resource not found: " + "/save.png");
        }
        //runButton.setEnabled(false);
        toolBar.add(runButton);
        
        stopButton = new JButton("Stop");
        stopButton.setToolTipText("Stop running program");
        stopButton.setActionCommand("stop");
        stopButton.addActionListener(listener);
        imageURL = Ilex6502.class.getResource("/stop.png");
        if (imageURL != null) {                      //image found
            stopButton.setIcon(new ImageIcon(imageURL,"Stop running program"));
        } else {                                     //no image found
            stopButton.setText("Save Source Code");
            System.err.println("Resource not found: " + "/run.png");
        }
        stopButton.setEnabled(false);;
        toolBar.add(stopButton);
        
        return(toolBar);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (EmuTheme theme: EmuTheme.values()){
            if (e.getActionCommand().equals(theme.getName())){
                emuPanel.setTheme(theme);
            }
        }
    }

    
}
