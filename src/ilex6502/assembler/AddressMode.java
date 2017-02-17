/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */
public enum AddressMode {
    IMMEDIATE(0,"^\\s*#(\\d{1,3}|\\$(\\d|[a-fA-F])+|%(0|1){8})\\s*$",2),
    ABSOLUTE(1,"^\\s*(\\d{4}|\\$(\\d|[a-fA-F]){4}|%(0|1){16})\\s*$",3),
    ZERO_PAGE(2,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8})\\s*$",2),
    ZERO_PAGE_X(3,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(x|X)\\s*$",2),
    ZERO_PAGE_Y(4,"^\\s*(\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(y|Y)\\s*$",2),
    ABSOLUTE_X(5,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16}),(x|X)\\s*$",3),
    ABSOLUTE_Y(6,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16}),(y|Y)\\s*$",3),
    INDIRECT_X(7,"^\\s*\\((\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8}),(x|X)\\)\\s*$",2),
    INDIRECT_Y(8,"^\\s*\\((\\d{1,3}|\\$(\\d|[a-fA-F]){2}|%(0|1){8})\\),(y|Y)\\s*$",2),
    ACCUMULATOR(9,"^\\s*(a|A)\\s*$",1),
    IMPLIED(10,"^\\s*$",1),
    RELATIVE(11,"^\\s*(\\d{1,5}|\\$(\\d|[a-fA-F]){4}|%(0|1){16})\\s*$",2),
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
