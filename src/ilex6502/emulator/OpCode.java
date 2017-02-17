/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.emulator;

import ilex6502.assembler.AddressMode;

/**
 *
 * @author jdoolin
 */
public class OpCode {
    private String instruction;
    private AddressMode mode;
    
    public OpCode(String i, AddressMode m){
        instruction = i;
        mode = m;
    }

    /**
     * @return the instruction
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * @param instruction the instruction to set
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * @return the mode
     */
    public AddressMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(AddressMode mode) {
        this.mode = mode;
    }
}
