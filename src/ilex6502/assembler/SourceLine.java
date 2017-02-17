/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

/**
 *
 * @author jdoolin
 */
public class SourceLine {
    private String operator;
    private String operand;
    private AddressMode mode;
    private int lc;
    
    public SourceLine(String operator, String operand, AddressMode mode, int lc){
        this.operator = operator;
        this.operand = operand;
        this.mode = mode;
        this.lc = lc;
    }
    
    public String toString(){
        String operandStr = (operand.equals("")? "<none>":operand);
        return("Operator: " + operator + ", Operand: " + operandStr + ", Mode: " + mode.toString() + ", LC: " + lc);
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the operand
     */
    public String getOperand() {
        return operand;
    }

    /**
     * @param operand the operand to set
     */
    public void setOperand(String operand) {
        this.operand = operand;
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

    /**
     * @return the lc
     */
    public int getLc() {
        return lc;
    }

    /**
     * @param lc the lc to set
     */
    public void setLc(int lc) {
        this.lc = lc;
    }
    
}
