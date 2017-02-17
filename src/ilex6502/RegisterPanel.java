/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jdoolin
 */
public class RegisterPanel extends JPanel{
    private JLabel accLabel;
    private JLabel accumulator;
    private JLabel xLabel;
    private JLabel xRegister;
    private JLabel yLabel;
    private JLabel yRegister;
    private JLabel pcLabel;
    private JLabel programCounter;
    private JLabel spLabel;
    private JLabel stackPointer;
    private JLabel flagsLabel;
    private JLabel flags;
    
    public RegisterPanel(){
        setLayout(new GridLayout(0,6));
        accLabel = new JLabel("Accumulator");
        accumulator = new JLabel("0x00");
        
        xLabel = new JLabel("X Register");
        xRegister = new JLabel("0x00");
        
        yLabel = new JLabel("Y Register");
        yRegister = new JLabel("0x00");
        
        flagsLabel = new JLabel("Status Flags");
        flags = new JLabel("0x00");
        
        pcLabel = new JLabel("Program Counter");
        programCounter = new JLabel("0x0000");
        
        spLabel = new JLabel("Stack Pointer");
        stackPointer = new JLabel("0x0000");
        
        add(accLabel);
        add(accumulator);
        add(xLabel);
        add(xRegister);
        add(yLabel);
        add(yRegister);
        add(flagsLabel);
        add(flags);
        add(spLabel);
        add(stackPointer);
        add(new JLabel("View Mode"));
        add(new JLabel("Drop down here"));
        add(pcLabel);
        add(programCounter);
        add(new JButton("View RAM"));
        
        TitledBorder title = BorderFactory.createTitledBorder("Registers");
        
        setBorder(title);
        setPreferredSize(new Dimension(975,60));
    }

    /**
     * @param accumulator the accumulator to set
     */
    public void setAccumulator(byte accumulator) {
        this.accumulator.setText(Integer.toString(accumulator, 16));
    }

    /**
     * @param xRegister the xRegister to set
     */
    public void setxRegister(byte xRegister) {
        this.xRegister.setText(Integer.toString(xRegister, 16));
    }

    /**
     * @param yRegister the yRegister to set
     */
    public void setyRegister(byte yRegister) {
        this.yRegister.setText(Integer.toString(yRegister, 16));
    }

    /**
     * @param programCounter the programCounter to set
     */
    public void setProgramCounter(short programCounter) {
        this.programCounter.setText(Integer.toString(programCounter, 16));
    }

    /**
     * @param stackPointer the stackPointer to set
     */
    public void setStackPointer(byte stackPointer) {
        this.stackPointer.setText(Integer.toString(stackPointer, 16));
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(byte flags) {
        this.flags.setText(Integer.toString(flags, 16));
    }
}
