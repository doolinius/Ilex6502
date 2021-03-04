/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jdoolin
 */
public class RegisterPanel extends JPanel{
    private GUIEmulator emu;
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
    private JLabel carryFlagLabel;
    private JLabel carryFlag;
    private JLabel negativeFlagLabel;
    private JLabel negativeFlag;
    private JLabel zeroFlagLabel;
    private JLabel zeroFlag;
    private JLabel decimalFlagLabel;
    private JLabel decimalFlag;
    private JLabel interruptFlagLabel;
    private JLabel interruptFlag;
    private JLabel overflowFlagLabel;
    private JLabel overflowFlag;
    // Format Radio Buttons
    private JRadioButton binary;
    private JRadioButton hex;
    private JRadioButton decimal;
    private final int BINARY = 0;
    private final int HEX = 1;
    private final int DECIMAL = 2;
    private int regFormat = HEX;
    private JButton viewRamButton;
    
    public RegisterPanel(GUIEmulator e){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel regPanel = new JPanel();
        regPanel.setLayout(new GridLayout(0,8));
        emu = e;
        //setLayout(new GridBagLayout());
        
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
        
        carryFlagLabel = new JLabel("Carry:");
        carryFlag = new JLabel("0");
        negativeFlagLabel = new JLabel("Negative:");
        negativeFlag = new JLabel("0");
        zeroFlagLabel = new JLabel("Zero:");
        zeroFlag = new JLabel("0");
        decimalFlagLabel = new JLabel("Decimal:");
        decimalFlag = new JLabel("0");
        interruptFlagLabel = new JLabel("Interrupt:");
        interruptFlag = new JLabel("0");
        overflowFlagLabel = new JLabel("Overflow");
        overflowFlag = new JLabel("0");
        
        
        regPanel.add(accLabel);
        regPanel.add(accumulator);
        regPanel.add(xLabel);
        regPanel.add(xRegister);
        regPanel.add(yLabel);
        regPanel.add(yRegister);
        regPanel.add(Box.createRigidArea(new Dimension(1,1)));
        regPanel.add(Box.createRigidArea(new Dimension(1,1)));
        regPanel.add(flagsLabel);
        regPanel.add(flags);
        regPanel.add(spLabel);
        regPanel.add(stackPointer);
        regPanel.add(pcLabel);
        regPanel.add(programCounter);
        regPanel.add(Box.createRigidArea(new Dimension(1,1)));
        regPanel.add(Box.createRigidArea(new Dimension(1,1)));
        regPanel.add(negativeFlagLabel);
        regPanel.add(negativeFlag);
        regPanel.add(overflowFlagLabel);
        regPanel.add(overflowFlag);
        regPanel.add(decimalFlagLabel);
        regPanel.add(decimalFlag);
        regPanel.add(interruptFlagLabel);
        regPanel.add(interruptFlag);
        regPanel.add(zeroFlagLabel);
        regPanel.add(zeroFlag);
        regPanel.add(carryFlagLabel);
        regPanel.add(carryFlag);
        add(regPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("View Mode"));
        
        
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        ButtonListener l = new ButtonListener();
        binary = new JRadioButton("Binary");
        binary.addActionListener(l);
        hex = new JRadioButton("Hex", true);
        hex.addActionListener(l);
        decimal = new JRadioButton("Decimal");
        decimal.addActionListener(l);
        ButtonGroup formatGroup = new ButtonGroup();
        formatGroup.add(binary);
        formatGroup.add(hex);
        formatGroup.add(decimal);
        
        buttonPanel.add(decimal);
        buttonPanel.add(binary);
        buttonPanel.add(hex);
        buttonPanel.add(Box.createRigidArea(new Dimension(50,1)));
        viewRamButton = new JButton("View RAM");
        viewRamButton.addActionListener(new ViewRamListener());
        buttonPanel.add(viewRamButton);
        add(buttonPanel);
        
        TitledBorder title = BorderFactory.createTitledBorder("Registers");
        
        setBorder(title);
        setPreferredSize(new Dimension(975,60));
    }
    
    private class ViewRamListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            emu.memoryWindow.setMemTable();
            emu.memoryWindow.setVisible(true);
        }
        
    }
    
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == binary){
                regFormat = BINARY;
            }else if (source == hex){
                regFormat = HEX;
            }else{
                regFormat = DECIMAL;
            }
            emu.updateRegisterPanel();
        }
        
    }
    
    private String registerString(byte reg){
        switch(regFormat){
            case BINARY:
                // Because this is an Integer method, it will convert to 32 bit numbers
                // When bit 7 is set.  
                String bString = String.format("%8s", Integer.toBinaryString(reg)).replace(' ', '0');
                // When that happens, check the length of the resulting string
                // if it's > 8, just get the last 8 characters by starting with
                // index 24
                if (bString.length() > 8){
                    bString = bString.substring(24);
                }
                //System.out.println("Returning " + bString);
                return("0b" + bString);
            case HEX:
                return(String.format("0x%02x", reg));
            case DECIMAL:
                return("" + reg);
            default:
                return(String.format("0x%02x", reg));
        }
        
        //String text = String.format("%8s", Integer.toBinaryString(reg)).replace(' ', '0');
        //text +=  ", " + String.format("%02x",reg);
        //text +=  ", " + reg;
        //return(text);
    }

    /**
     * @param accumulator the accumulator to set
     */
    public void setAccumulator(byte accumulator) {
        //this.accumulator.setText(Integer.toBinaryString(accumulator));
        //this.accumulator.setText(String.format("%8s", Integer.toBinaryString(accumulator)).replace(' ', '0'));
        this.accumulator.setText(registerString(accumulator));
    }

    /**
     * @param xRegister the xRegister to set
     */
    public void setxRegister(byte xRegister) {
        //this.xRegister.setText(Integer.toString(xRegister, 16));
        this.xRegister.setText(registerString(xRegister));
    }

    /**
     * @param yRegister the yRegister to set
     */
    public void setyRegister(byte yRegister) {
        this.yRegister.setText(registerString(yRegister));
    }

    /**
     * @param programCounter the programCounter to set
     */
    public void setProgramCounter(short programCounter) {
        this.programCounter.setText(Integer.toString(programCounter, 16) + "/" + programCounter);
    }

    /**
     * @param stackPointer the stackPointer to set
     */
    public void setStackPointer(byte stackPointer) {
        this.stackPointer.setText(registerString(stackPointer));
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(byte flags) {
        this.flags.setText(registerString(flags));
        this.carryFlag.setText("" + emu.getCarryFlag());
        this.zeroFlag.setText("" + emu.getZeroFlag());
        this.negativeFlag.setText("" + emu.getNegativeFlag());
        this.overflowFlag.setText("" + emu.getOverflowFlag());
        this.interruptFlag.setText("" + emu.getInterruptFlag());
        this.decimalFlag.setText("" + emu.getDecimalFlag());
    }
}
