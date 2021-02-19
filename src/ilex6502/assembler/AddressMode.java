/*
 * AddressMode is an enum describing addressing modes for the MOS 6502
 * It consists of an integer id, a regular expression for matching 
 * modes during parsing and the number of bytes an instruction of that mode
 * requires.
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */
public enum AddressMode {
    // Immediate mode instructions have only data as their arguments
    // No addresses are required.  They often modify the Accumulator
    // E.g.: LDA #12
    IMMEDIATE(0,"^\\s*#(\\d{1,3}|\\$(\\d|[a-fA-F])+|%(0|1){8})\\s*$",2),
    // Absolute mode requires a two byte (16-bit) memory address as its argument
    ABSOLUTE(1,"^\\s*(\\d{4}|\\$(\\d|[a-fA-F]){4}|%(0|1){16})\\s*$",3),
    /*
    Zero-Page is an addressing mode that is only capable of addressing 
    the first 256 bytes of the CPU's memory map. You can think of it as 
    absolute addressing for the first 256 bytes. The instruction LDA $35 
    will put the value stored in memory location $35 into A. The advantage 
    of zero-page are two - the instruction takes one less byte to specify, 
    and it executes in less CPU cycles. Most programs are written to store 
    the most frequently used variables in the first 256 memory locations so 
    they can take advantage of zero page addressing.
    */
    ZERO_PAGE(2,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8})\\s*$",2),
    /*
    Zero Page Indexed modes act like Zero Page, but add the contents of one of
    the two Index registers (X or Y)
    */
    ZERO_PAGE_X(3,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(x|X)\\s*$",2),
    ZERO_PAGE_Y(4,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(y|Y)\\s*$",2),
    /*
    The ABSOLUTE addressing modes will add the contents of the X or Y register
    to the memory location specified as the argument.
    Example: STA $1000,Y ; absolute indexed addressing, adds the contents of the
        Y register to $1000 before the contents of the Accumulator is stored 
        there
    */
    ABSOLUTE_X(5,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16}),(x|X)\\s*$",3),
    ABSOLUTE_Y(6,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16}),(y|Y)\\s*$",3),
    /*
    Indirect X and Indirect Y behave differently
    
    */
    INDIRECT_X(7,"^\\s*\\((\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(x|X)\\)\\s*$",2),
    INDIRECT_Y(8,"^\\s*\\((\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8})\\),(y|Y)\\s*$",2),
    // This mode addresses the Accumulator register specifically
    ACCUMULATOR(9,"^\\s*(a|A)\\s*$",1),
    // These instructions have no target.  The instruction is unique
    // and any modified registers are implied by the instruction itself 
    IMPLIED(10,"^\\s*$",1),
    /*
    Used only for Branch operations, RELATIVE mode will take a branch that
    is a number of bytes equal to the offset argument of the instruction.
    It is a signed argument, so the branch can go forward or backward in code
    */
    RELATIVE(11,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16})\\s*$",2),
    /*
    Only the JMP instruction uses INDIRECT mode. It will move the Program
    Counter to the 16-bit address stored in the address argument
    */
    INDIRECT(12,"^\\s*\\((\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8})\\)\\s*$",3),
    NONE(-1,null,0);
    
    private final int id;
    private final String pattern;
    private final int size;
    
    AddressMode(int id,String pattern, int size){
        this.id = id;
        this.pattern = pattern;
        this.size = size;
    }
    
    public int id(){return(id);}
    public String pattern(){return(pattern);}
    public int size(){return(size);}
    
}
