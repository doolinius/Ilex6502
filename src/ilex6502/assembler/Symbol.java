/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */

public class Symbol {
    
    private String name;
    private int location = -1;// LC if this is a label
    private String value; // value for variables
    private SymType type;
    private int lineNumber;
    
    public Symbol(){
        
    }
    
    public Symbol(String name,String value, SymType type,int lineNumber){
        this.name = name;
        this.value = value;
        this.type = type;
        this.lineNumber = lineNumber;
    }

    public Symbol(String name,int location, SymType type, int lineNUmber, int size){
        this.name = name;
        this.location = location;
        //this.value = Integer.toHexString(location & 0xffff);
        String format = (size == 2) ? "%02x" : "%04x";
        this.value = "$" + String.format(format, location);
        //System.out.println("New Symbol, " + name + ", value " + this.value + ", loc " + this.location);
        this.type = type;
        this.lineNumber = lineNumber;
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
     * @return the location
     */
    public int getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(int location) {
        this.location = (short)location;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public SymType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(SymType type) {
        this.type = type;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
}
