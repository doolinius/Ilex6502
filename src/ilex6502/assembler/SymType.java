/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */
public enum SymType {
    LABEL,      // code label
    VARIABLE,   // label for memory location
    OPERAND,    // label of memory location or code
    UNDEFINED,  // not sure why I have this
    MTDF // multiply defined
}