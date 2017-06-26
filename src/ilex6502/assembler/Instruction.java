/*
 * The Instructor class represents a single assembly instruction
 * It has three parts:
 * -name - the instruction mnemonic
 * -opCodes - an array of possible opcode (hex) strings.  
 *      since there are 14 possible address modes, the array is of size 14.
 * -size - the size of the instruction in bytes
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */
public class Instruction {
    
    // get the number of possible addressing modes
    private final int NUM_MODES = AddressMode.values().length;
    
    private String name;
    //private byte[] opCodes = new byte[13];
    private String[] opCodes = new String[NUM_MODES];
    private int size;
    
    /*
    public Instruction(String n, byte code, AddressMode addressMode){
        name = n;
        for (int i=0; i<13; i++){
            opCodes[i] = (byte)0x8B;// 0x8B is an unstable opcode on the 6502
            // so i'm using it as my "doesn't exist" value
        }
        opCodes[addressMode.id()] = code;
        size = 0;
        
    }
     * 
     */
    public Instruction(String n, String code, AddressMode addressMode){
        name = n;
        // initialize array to all null values
        for (int i=0; i<13; i++){
            opCodes[i] = null;
        }
        opCodes[addressMode.id()] = code;
        size = 0;
        
    }
    
    public Instruction(String n, String code, AddressMode addressMode, int size){
        name = n;
        // initialize array to all null values
        for (int i=0; i<13; i++){
            opCodes[i] = null;
        }
        opCodes[addressMode.id()] = code;
        this.size = size;
        
    }
    
    public boolean isValidMode(AddressMode mode){
        return(opCodes[mode.id()] != null);
    }
    
    // Adds a new opCode to the array
    // Because the same mnemonic can have multiple opCodes for different
    // Addressing modes
    public void addCode(String code, AddressMode addressMode){
        //System.out.println("Mode: " + addressMode.name() + " " + addressMode.ordinal());
        getOpCodes()[addressMode.id()] = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the opCodes
     */
    public String[] getOpCodes() {
        return opCodes;
    }

    /**
     * @param opCodes the opCodes to set
     */
    public void setOpCodes(String[] opCodes) {
        this.opCodes = opCodes;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
}
