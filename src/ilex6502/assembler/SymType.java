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
    UNDEFINED,  // a symbol that has not yet been resolved in Pass 1
    MTDF // multiply defined
}