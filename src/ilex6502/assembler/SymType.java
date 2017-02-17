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
    LABEL,
    VARIABLE,
    OPERAND,
    UNDEFINED,
    MTDF // multiply defined
}