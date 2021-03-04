/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import ilex6502.emulator.Emulator;
import javax.swing.JTextArea;

/**
 *
 * @author jdoolin
 */
public class GUIEmulator extends Emulator{
    private EmuScreen screenPanel;
    private RegisterPanel registerPanel;
    protected MemoryWindow memoryWindow;

    public GUIEmulator() {
        super();
        screenPanel = new EmuScreen();
        registerPanel = new RegisterPanel(this);
        memoryWindow = new MemoryWindow("Memory Contents", this);
        updateRegisterPanel();
    }

    @Override
    public void runBinary() {
        super.runBinary();
        
    }

    @Override
    public void performInstruction() {
        super.performInstruction();
        //updateScreen();
        updateRegisterPanel();
        memoryWindow.setMemTable();
    }

    public void updateScreen(){
        
    }
    
    public void updateRegisterPanel(){
        registerPanel.setAccumulator(accumulator);
        registerPanel.setxRegister(xRegister);
        registerPanel.setyRegister(yRegister);
        //byte flags = (byte)(byteToUnsigned(statusFlagsToByte()));
        //registerPanel.setFlags(flags);
        registerPanel.setFlags(statusFlagsToByte());
        registerPanel.setStackPointer(stackPointer);
        registerPanel.setProgramCounter(programCounter);
    }
    
    /**
     * @return the screenPanel
     */
    public EmuScreen getScreenPanel() {
        return screenPanel;
    }

    /**
     * @param screenPanel the screenPanel to set
     */
    public void setScreenPanel(EmuScreen screenPanel) {
        this.screenPanel = screenPanel;
    }

    /**
     * @return the registerPanel
     */
    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    /**
     * @param registerPanel the registerPanel to set
     */
    public void setRegisterPanel(RegisterPanel registerPanel) {
        this.registerPanel = registerPanel;
    }
    
}
