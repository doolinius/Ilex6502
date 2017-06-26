/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

import java.lang.Exception;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jdoolin
 */
public class Instructions {
    private static HashMap<String, Instruction> codes = new HashMap<String, Instruction>();
    private List<String> validJumpOps = Arrays.asList("JMP","JSR");
    private List<String> validBranchOps = Arrays.asList("BEQ","BNE","BCC","BCS","BPL","BVC","BMI","BVS");
    
    public Instructions() {
        addOp("BRK", "00", AddressMode.IMMEDIATE);
        addOp("ORA", "01", AddressMode.INDIRECT_X);
        addOp("JAM", "02", AddressMode.IMPLIED);
        addOp("SLO", "03", AddressMode.INDIRECT_X);
        addOp("NOP", "04", AddressMode.ZERO_PAGE);
        addOp("ORA", "05", AddressMode.ZERO_PAGE);
        addOp("ASL", "06", AddressMode.ZERO_PAGE);
        addOp("SLO", "07", AddressMode.ZERO_PAGE);
        addOp("PHP", "08", AddressMode.IMPLIED,1);
        addOp("ORA", "09", AddressMode.IMMEDIATE);
        addOp("ASL", "0A", AddressMode.ACCUMULATOR);
        addOp("ANC", "0B", AddressMode.IMMEDIATE);
        addOp("NOP", "0C", AddressMode.ABSOLUTE);
        addOp("ORA", "0D", AddressMode.ABSOLUTE);
        addOp("ASL", "0E", AddressMode.ABSOLUTE);
        addOp("SLO", "0F", AddressMode.ABSOLUTE);
        addOp("BPL", "10", AddressMode.RELATIVE,2);
        addOp("ORA", "11", AddressMode.INDIRECT_Y);
        addOp("JAM", "12", AddressMode.IMPLIED);
        addOp("SLO", "13", AddressMode.INDIRECT_Y);
        addOp("NOP", "14", AddressMode.ZERO_PAGE_X);
        addOp("ORA", "15", AddressMode.ZERO_PAGE_X);
        addOp("ASL", "16", AddressMode.ZERO_PAGE_X);
        addOp("SLO", "17", AddressMode.ZERO_PAGE_X);
        addOp("CLC", "18", AddressMode.IMPLIED,1);
        addOp("ORA", "19", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "1A", AddressMode.IMPLIED);
        addOp("SLO", "1B", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "1C", AddressMode.ABSOLUTE_X);
        addOp("ORA", "1D", AddressMode.ABSOLUTE_X);
        addOp("ASL", "1E", AddressMode.ABSOLUTE_X);
        addOp("SLO", "1F", AddressMode.ABSOLUTE_X);
        addOp("JSR", "20", AddressMode.ABSOLUTE,3);
        addOp("AND", "21", AddressMode.INDIRECT_X);
        addOp("JAM", "22", AddressMode.IMPLIED);
        addOp("RLA", "23", AddressMode.INDIRECT_X);
        addOp("BIT", "24", AddressMode.ZERO_PAGE);
        addOp("AND", "25", AddressMode.ZERO_PAGE);
        addOp("ROL", "26", AddressMode.ZERO_PAGE);
        addOp("RLA", "27", AddressMode.ZERO_PAGE);
        addOp("PLP", "28", AddressMode.IMPLIED,1);
        addOp("AND", "29", AddressMode.IMMEDIATE);
        addOp("ROL", "2A", AddressMode.ACCUMULATOR);
        addOp("ANC", "2B", AddressMode.IMMEDIATE);
        addOp("BIT", "2C", AddressMode.ABSOLUTE);
        addOp("AND", "2D", AddressMode.ABSOLUTE);
        addOp("ROL", "2E", AddressMode.ABSOLUTE);
        addOp("RLA", "2F", AddressMode.ABSOLUTE);
        addOp("BMI", "30", AddressMode.RELATIVE);
        addOp("AND", "31", AddressMode.INDIRECT_Y);
        addOp("JAM", "32", AddressMode.IMPLIED);
        addOp("RLA", "33", AddressMode.INDIRECT_Y);
        addOp("NOP", "34", AddressMode.ZERO_PAGE_X);
        addOp("AND", "35", AddressMode.ZERO_PAGE_X);
        addOp("ROL", "36", AddressMode.ZERO_PAGE_X);
        addOp("RLA", "37", AddressMode.ZERO_PAGE_X);
        addOp("SEC", "38", AddressMode.IMPLIED,1);
        addOp("AND", "39", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "3A", AddressMode.IMPLIED);
        addOp("RLA", "3B", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "3C", AddressMode.ABSOLUTE_X);
        addOp("AND", "3D", AddressMode.ABSOLUTE_X);
        addOp("ROL", "3E", AddressMode.ABSOLUTE_X);
        addOp("RLA", "3F", AddressMode.ABSOLUTE_X);
        addOp("RTI", "40", AddressMode.IMPLIED,1);
        addOp("EOR", "41", AddressMode.INDIRECT_X);
        addOp("JAM", "42", AddressMode.IMPLIED);
        addOp("SRE", "43", AddressMode.INDIRECT_X);
        addOp("NOP", "44", AddressMode.ZERO_PAGE);
        addOp("EOR", "45", AddressMode.ZERO_PAGE);
        addOp("LSR", "46", AddressMode.ABSOLUTE_X);
        addOp("SRE", "47", AddressMode.ZERO_PAGE);
        addOp("PHA", "48", AddressMode.IMPLIED,1);
        addOp("EOR", "49", AddressMode.IMMEDIATE);
        addOp("LSR", "4A", AddressMode.ACCUMULATOR);
        addOp("ASR", "4B", AddressMode.IMMEDIATE,2);
        addOp("JMP", "4C", AddressMode.ABSOLUTE);
        addOp("EOR", "4D", AddressMode.ABSOLUTE);
        addOp("LSR", "4E", AddressMode.ABSOLUTE);
        addOp("SRE", "4F", AddressMode.ABSOLUTE);
        addOp("BVC", "50", AddressMode.RELATIVE,2);
        addOp("EOR", "51", AddressMode.INDIRECT_Y);
        addOp("JAM", "52", AddressMode.IMPLIED);
        addOp("SRE", "53", AddressMode.INDIRECT_Y);
        addOp("NOP", "54", AddressMode.ZERO_PAGE_X);
        addOp("EOR", "55", AddressMode.ZERO_PAGE_X);
        addOp("LSR", "56", AddressMode.ZERO_PAGE_X);
        addOp("SRE", "57", AddressMode.ZERO_PAGE_X);
        addOp("CLI", "58", AddressMode.IMPLIED,1);
        addOp("EOR", "59", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "5A", AddressMode.IMPLIED);
        addOp("SRE", "5B", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "5C", AddressMode.ABSOLUTE_X);
        addOp("EOR", "5D", AddressMode.ABSOLUTE_X);
        addOp("SRE", "5F", AddressMode.ABSOLUTE_X);
        addOp("RTS", "60", AddressMode.IMPLIED,1);
        addOp("ADC", "61", AddressMode.INDIRECT_X);
        addOp("JAM", "62", AddressMode.IMPLIED);
        addOp("RRA", "63", AddressMode.INDIRECT_X);
        addOp("NOP", "64", AddressMode.ZERO_PAGE);
        addOp("ADC", "65", AddressMode.ZERO_PAGE);
        addOp("ROR", "66", AddressMode.ZERO_PAGE);
        addOp("RRA", "67", AddressMode.ZERO_PAGE);
        addOp("PLA", "68", AddressMode.IMPLIED,1);
        addOp("ADC", "69", AddressMode.IMMEDIATE);
        addOp("ROR", "6A", AddressMode.ACCUMULATOR);
        addOp("ARR", "6B", AddressMode.IMMEDIATE,2);
        addOp("JMP", "6C", AddressMode.INDIRECT);
        addOp("ADC", "6D", AddressMode.ABSOLUTE);
        addOp("ROR", "6E", AddressMode.ABSOLUTE);
        addOp("RRA", "6F", AddressMode.ABSOLUTE);
        addOp("BVS", "70", AddressMode.RELATIVE,2);
        addOp("ADC", "71", AddressMode.INDIRECT_Y);
        addOp("JAM", "72", AddressMode.IMPLIED);
        addOp("RRA", "73", AddressMode.INDIRECT_Y);
        addOp("NOP", "74", AddressMode.ZERO_PAGE_X);
        addOp("ADC", "75", AddressMode.ZERO_PAGE_X);
        addOp("ROR", "76", AddressMode.ZERO_PAGE_X);
        addOp("RRA", "77", AddressMode.ZERO_PAGE_X);
        addOp("SEI", "78", AddressMode.IMPLIED,1);
        addOp("ADC", "79", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "7A", AddressMode.IMPLIED);
        addOp("RRA", "7B", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "7C", AddressMode.ABSOLUTE_X);
        addOp("ADC", "7D", AddressMode.ABSOLUTE_X);
        addOp("ROR", "7E", AddressMode.ABSOLUTE_X);
        addOp("RRA", "7F", AddressMode.ABSOLUTE_X);
        addOp("NOP", "80", AddressMode.IMMEDIATE);
        addOp("STA", "81", AddressMode.INDIRECT_X);
        addOp("NOP", "82", AddressMode.IMMEDIATE);
        addOp("SAX", "83", AddressMode.INDIRECT_X);
        addOp("STY", "84", AddressMode.ZERO_PAGE);
        addOp("STA", "85", AddressMode.ZERO_PAGE);
        addOp("STX", "86", AddressMode.ZERO_PAGE);
        addOp("SAX", "87", AddressMode.ZERO_PAGE);
        addOp("DEY", "88", AddressMode.IMPLIED,1);
        addOp("NOP", "89", AddressMode.IMMEDIATE);
        addOp("TXA", "8A", AddressMode.IMPLIED,1);
        addOp("ANE", "8B", AddressMode.IMMEDIATE,2);
        addOp("STY", "8C", AddressMode.ABSOLUTE);
        addOp("STA", "8D", AddressMode.ABSOLUTE);
        addOp("STX", "8E", AddressMode.ABSOLUTE);
        addOp("SAX", "8F", AddressMode.ABSOLUTE);
        addOp("BCC", "90", AddressMode.RELATIVE,2);
        addOp("STA", "91", AddressMode.INDIRECT_Y);
        addOp("JAM", "92", AddressMode.IMPLIED);
        addOp("SHA", "93", AddressMode.ABSOLUTE_X);
        addOp("STY", "94", AddressMode.ZERO_PAGE_X);
        addOp("STA", "95", AddressMode.ZERO_PAGE_X);
        addOp("SAX", "97", AddressMode.ZERO_PAGE_Y);
        addOp("STX", "96", AddressMode.ZERO_PAGE_Y);
        addOp("TYA", "98", AddressMode.IMPLIED,1);
        addOp("STA", "99", AddressMode.ABSOLUTE_Y);
        addOp("TXS", "9A", AddressMode.IMPLIED,1);
        addOp("SHS", "9B", AddressMode.ABSOLUTE_Y,3);
        addOp("SHY", "9C", AddressMode.ABSOLUTE_Y,3);
        addOp("STA", "9D", AddressMode.ABSOLUTE_X);
        addOp("SHX", "9E", AddressMode.ABSOLUTE_X,3);
        addOp("SHA", "9F", AddressMode.ABSOLUTE_Y);
        addOp("LDY", "A0", AddressMode.IMMEDIATE);
        addOp("LDA", "A1", AddressMode.INDIRECT_X);
        addOp("LDX", "A2", AddressMode.IMMEDIATE);
        addOp("LAX", "A3", AddressMode.INDIRECT_X);
        addOp("LDY", "A4", AddressMode.ZERO_PAGE);
        addOp("LDA", "A5", AddressMode.ZERO_PAGE);
        addOp("LDX", "A6", AddressMode.ZERO_PAGE);
        addOp("LAX", "A7", AddressMode.ZERO_PAGE);
        addOp("TAY", "A8", AddressMode.IMPLIED,1);
        addOp("LDA", "A9", AddressMode.IMMEDIATE);
        addOp("TAX", "AA", AddressMode.IMPLIED,1);
        addOp("LXA", "AB", AddressMode.IMMEDIATE,2);
        addOp("LDY", "AC", AddressMode.ABSOLUTE);
        addOp("LDA", "AD", AddressMode.ABSOLUTE);
        addOp("LDX", "AE", AddressMode.ABSOLUTE);
        addOp("LAX", "AF", AddressMode.ABSOLUTE);
        addOp("BCS", "B0", AddressMode.RELATIVE,2);
        addOp("LDA", "B1", AddressMode.INDIRECT_Y);
        addOp("JAM", "B2", AddressMode.IMPLIED);
        addOp("LAX", "B3", AddressMode.INDIRECT_Y);
        addOp("LDY", "B4", AddressMode.ZERO_PAGE_X);
        addOp("LDA", "B5", AddressMode.ZERO_PAGE_X);
        addOp("LDX", "B6", AddressMode.ZERO_PAGE_Y);
        addOp("LAX", "B7", AddressMode.ZERO_PAGE_Y);
        addOp("CLV", "B8", AddressMode.IMPLIED,1);
        addOp("LDA", "B9", AddressMode.ABSOLUTE_Y);
        addOp("TSX", "BA", AddressMode.IMPLIED,1);
        addOp("LAE", "BB", AddressMode.ABSOLUTE_Y,3);
        addOp("LDY", "BC", AddressMode.ABSOLUTE_X);
        addOp("LDA", "BD", AddressMode.ABSOLUTE_X);
        addOp("LDX", "BE", AddressMode.ABSOLUTE_Y);
        addOp("LAX", "BF", AddressMode.ABSOLUTE_Y);
        addOp("CPY", "C0", AddressMode.IMMEDIATE);
        addOp("CMP", "C1", AddressMode.INDIRECT_X);
        addOp("NOP", "C2", AddressMode.IMMEDIATE);
        addOp("DCP", "C3", AddressMode.INDIRECT_X);
        addOp("CPY", "C4", AddressMode.ZERO_PAGE);
        addOp("CMP", "C5", AddressMode.ZERO_PAGE);
        addOp("DEC", "C6", AddressMode.ZERO_PAGE);
        addOp("DCP", "C7", AddressMode.ZERO_PAGE);
        addOp("INY", "C8", AddressMode.IMPLIED,1);
        addOp("CMP", "C9", AddressMode.IMMEDIATE);
        addOp("DEX", "CA", AddressMode.IMPLIED,1);
        addOp("SBX", "CB", AddressMode.IMMEDIATE,2);
        addOp("CPY", "CC", AddressMode.ABSOLUTE);
        addOp("CMP", "CD", AddressMode.ABSOLUTE);
        addOp("DEC", "CE", AddressMode.ABSOLUTE);
        addOp("DCP", "CF", AddressMode.ABSOLUTE);
        addOp("BNE", "D0", AddressMode.RELATIVE,2);
        addOp("CMP", "D1", AddressMode.INDIRECT_Y);
        addOp("JAM", "D2", AddressMode.IMPLIED);
        addOp("DCP", "D3", AddressMode.INDIRECT_Y);
        addOp("NOP", "D4", AddressMode.ZERO_PAGE_X);
        addOp("CMP", "D5", AddressMode.ZERO_PAGE_X);
        addOp("DEC", "D6", AddressMode.ZERO_PAGE_X);
        addOp("DCP", "D7", AddressMode.ZERO_PAGE_X);
        addOp("CLD", "D8", AddressMode.IMPLIED,1);
        addOp("CMP", "D9", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "DA", AddressMode.IMPLIED);
        addOp("DCP", "DB", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "DC", AddressMode.ABSOLUTE_X);
        addOp("CMP", "DD", AddressMode.ABSOLUTE_X);
        addOp("DEC", "DE", AddressMode.ABSOLUTE_X);
        addOp("DCP", "DF", AddressMode.ABSOLUTE_X);
        addOp("CPX", "E0", AddressMode.IMMEDIATE);
        addOp("SBC", "E1", AddressMode.INDIRECT_X);
        addOp("NOP", "E2", AddressMode.IMMEDIATE);
        addOp("ISB", "E3", AddressMode.INDIRECT_X);
        addOp("CPX", "E4", AddressMode.ZERO_PAGE);
        addOp("SBC", "E5", AddressMode.ZERO_PAGE);
        addOp("INC", "E6", AddressMode.ZERO_PAGE);
        addOp("ISB", "E7", AddressMode.ZERO_PAGE);
        addOp("INX", "E8", AddressMode.IMPLIED,1);
        addOp("SBC", "E9", AddressMode.IMMEDIATE);
        addOp("NOP", "EA", AddressMode.IMPLIED);
        addOp("SBC", "EB", AddressMode.IMMEDIATE);
        addOp("SBC", "ED", AddressMode.ABSOLUTE);
        addOp("CPX", "EC", AddressMode.ABSOLUTE);
        addOp("INC", "EE", AddressMode.ABSOLUTE);
        addOp("ISB", "EF", AddressMode.ABSOLUTE);
        addOp("BEQ", "F0", AddressMode.RELATIVE,2);
        addOp("SBC", "F1", AddressMode.INDIRECT_Y);
        addOp("JAM", "F2", AddressMode.IMPLIED);
        addOp("ISB", "F3", AddressMode.INDIRECT_Y);
        addOp("NOP", "F4", AddressMode.ZERO_PAGE_X);
        addOp("SBC", "F5", AddressMode.ZERO_PAGE_X);
        addOp("INC", "F6", AddressMode.ZERO_PAGE_X);
        addOp("ISB", "F7", AddressMode.ZERO_PAGE_X);
        addOp("SED", "F8", AddressMode.IMPLIED,1);
        addOp("SBC", "F9", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "FA", AddressMode.IMPLIED);
        addOp("ISB", "FB", AddressMode.ABSOLUTE_Y);
        addOp("NOP", "FC", AddressMode.ABSOLUTE_X);
        addOp("SBC", "FD", AddressMode.ABSOLUTE_X);
        addOp("INC", "FE", AddressMode.ABSOLUTE_X);
        addOp("ISB", "FF", AddressMode.ABSOLUTE_X);
    }
    
    public boolean isJumpOrBranch(String operator){
        operator = operator.toUpperCase();
        return(validJumpOps.contains(operator) || validBranchOps.contains(operator));
    }
    
    public AddressMode isValidJump(String operator, String operand){
        if((operator.trim().equalsIgnoreCase("JMP") &&
                (operand.trim().matches("^[a-zA-Z]\\w+$")))){
            return(AddressMode.ABSOLUTE);
        }else if(operator.trim().equalsIgnoreCase("JMP") &&
                operand.trim().matches("^\\([a-zA-Z]\\w+\\)$")){
            return(AddressMode.INDIRECT);
        }else if(operator.trim().equalsIgnoreCase("JSR") &&
                operand.trim().matches("^[a-zA-Z]\\w+$")){
            return(AddressMode.ABSOLUTE);
        }else{
            return(null);
        }
    }
    
    public boolean isValidBranch(String operator, String operand){
        return(validBranchOps.contains(operator) &&
                operand.trim().matches("^[a-zA-Z]\\w+&"));
    }
    
    public AddressMode getModeByOperand(String operand) throws Exception{
        AddressMode m = null;
        for (AddressMode mode : AddressMode.values()) {
            if (operand.matches(mode.pattern())) {
                m = mode;
                break;
            }
        }
        if(m == null){
            throw new Exception("Unknown addressing mode");
        }else{
            return(m);
        }
    }
    
    public AddressMode getMode(String operator, String operand) throws Exception{
        AddressMode m = getModeByOperand(operand);
        Instruction oc = codes.get(operator);
        if (m == AddressMode.ABSOLUTE && validBranchOps.contains(operator)){
                m = AddressMode.RELATIVE;
            }
        //System.out.println("Getting mode for + " + oc.getName() + " .... found " + m.name());
        if (! oc.isValidMode(m)) {// if m is an invalid addressing mode for operator
            throw new Exception("Invalid addressing mode for " + operator);
        } else {
            return (m);
        }
    }
    
    public int instructionSize(String op, String arg){
        try{
            Instruction c = getOp(op);
            if (c.getSize() == 0){
                return(getMode(op,arg).size());
            }else{
                //System.out.println("Found mode: " + c.getOnlyMode().name());
                return(c.getSize());
            }
        }catch(Exception e){
            System.err.println("Invalid operation " + op);
        }
        return(-1);
    }

    private static void addOp(String name, String code, AddressMode addressMode) {
        Instruction c = codes.get(name);
        //byte b = (byte) code;
        if (c == null){ // instruction is not yet in the codes map
            //System.out.println("adding new opcode " + name);
            Instruction newCode = new Instruction(name, code, addressMode);
            codes.put(name, newCode);
            //System.out.println("# of codes: " + codes.size());
        } else {
            c.addCode(code, addressMode);
        }
    }
    
    private static void addOp(String name, String code, AddressMode addressMode, int size) {
        Instruction c = codes.get(name);
        //byte b = (byte) code;
        if (c == null){
            //System.out.println("adding new opcode " + name);
            Instruction newCode = new Instruction(name, code, addressMode, size);
            codes.put(name, newCode);
            //System.out.println("# of codes: " + codes.size());
        } else {
            c.addCode(code, addressMode);
        }
    }

    public Instruction getOp(String name) throws Exception {
        Instruction c = codes.get(name);
        if (c == null){
            throw new Exception("Invalid mnemonic/operator (" + name + ")");
        }
        return (c);
    }
    
    public String getOpCode(String name, AddressMode am) throws Exception{
        Instruction c = codes.get(name);
        if (c == null){
            throw new Exception("Invalid mnemonic/operator (" + name + ")");
        }else{
            String b = c.getOpCodes()[am.id()];
            //if (b.byteValue() == "8B){
            if (b == null){
                throw new Exception("Invalid addressing mode for " + name);
            }else{
                return(b);
            }
        }
    }
}
