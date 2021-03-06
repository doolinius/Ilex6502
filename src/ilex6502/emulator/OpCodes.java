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
public class OpCodes {
    
    OpCode[] ops = new OpCode[256];
    
    public OpCodes(){
        addOp("BRK", 0x00, AddressMode.IMPLIED);
        addOp("ORA", 0x01, AddressMode.INDIRECT_X);
        addOp("JAM", 0x02, AddressMode.IMPLIED);
        addOp("SLO", 0x03, AddressMode.INDIRECT_X);
        addOp("NOP", 0x04, AddressMode.ZERO_PAGE);
        addOp("ORA", 0x05, AddressMode.ZERO_PAGE);
        addOp("ASL", 0x06, AddressMode.ZERO_PAGE);
        addOp("SLO", 0x07, AddressMode.ZERO_PAGE);
        addOp("PHP", 0x08, AddressMode.IMPLIED);
        addOp("ORA", 0x09, AddressMode.IMMEDIATE);
        addOp("ASL", 0x0A, AddressMode.ACCUMULATOR);
        addOp("ANC", 0x0B, AddressMode.IMMEDIATE);
        addOp("NOP", 0x0C, AddressMode.ABSOLUTE);
        addOp("ORA", 0x0D, AddressMode.ABSOLUTE);
        addOp("ASL", 0x0E, AddressMode.ABSOLUTE);
        addOp("SLO", 0x0F, AddressMode.ABSOLUTE);
        addOp("BPL", 0x10, AddressMode.RELATIVE);
        addOp("ORA", 0x11, AddressMode.INDIRECT_Y);
        addOp("JAM", 0x12, AddressMode.IMPLIED);
        addOp("SLO", 0x13, AddressMode.INDIRECT_Y);
        addOp("NOP", 0x14, AddressMode.ZERO_PAGE_X);
        addOp("ORA", 0x15, AddressMode.ZERO_PAGE_X);
        addOp("ASL", 0x16, AddressMode.ZERO_PAGE_X);
        addOp("SLO", 0x17, AddressMode.ZERO_PAGE_X);
        addOp("CLC", 0x18, AddressMode.IMPLIED);
        addOp("ORA", 0x19, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x1A, AddressMode.IMPLIED);
        addOp("SLO", 0x1B, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x1C, AddressMode.ABSOLUTE_X);
        addOp("ORA", 0x1D, AddressMode.ABSOLUTE_X);
        addOp("ASL", 0x1E, AddressMode.ABSOLUTE_X);
        addOp("SLO", 0x1F, AddressMode.ABSOLUTE_X);
        addOp("JSR", 0x20, AddressMode.ABSOLUTE);
        addOp("AND", 0x21, AddressMode.INDIRECT_X);
        addOp("JAM", 0x22, AddressMode.IMPLIED);
        addOp("RLA", 0x23, AddressMode.INDIRECT_X);
        addOp("BIT", 0x24, AddressMode.ZERO_PAGE);
        addOp("AND", 0x25, AddressMode.ZERO_PAGE);
        addOp("ROL", 0x26, AddressMode.ZERO_PAGE);
        addOp("RLA", 0x27, AddressMode.ZERO_PAGE);
        addOp("PLP", 0x28, AddressMode.IMPLIED);
        addOp("AND", 0x29, AddressMode.IMMEDIATE);
        addOp("ROL", 0x2A, AddressMode.ACCUMULATOR);
        addOp("ANC", 0x2B, AddressMode.IMMEDIATE);
        addOp("BIT", 0x2C, AddressMode.ABSOLUTE);
        addOp("AND", 0x2D, AddressMode.ABSOLUTE);
        addOp("ROL", 0x2E, AddressMode.ABSOLUTE);
        addOp("RLA", 0x2F, AddressMode.ABSOLUTE);
        addOp("BMI", 0x30, AddressMode.RELATIVE);
        addOp("AND", 0x31, AddressMode.INDIRECT_Y);
        addOp("JAM", 0x32, AddressMode.IMPLIED);
        addOp("RLA", 0x33, AddressMode.INDIRECT_Y);
        addOp("NOP", 0x34, AddressMode.ZERO_PAGE_X);
        addOp("AND", 0x35, AddressMode.ZERO_PAGE_X);
        addOp("ROL", 0x36, AddressMode.ZERO_PAGE_X);
        addOp("RLA", 0x37, AddressMode.ZERO_PAGE_X);
        addOp("SEC", 0x38, AddressMode.IMPLIED);
        addOp("AND", 0x39, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x3A, AddressMode.IMPLIED);
        addOp("RLA", 0x3B, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x3C, AddressMode.ABSOLUTE_X);
        addOp("AND", 0x3D, AddressMode.ABSOLUTE_X);
        addOp("ROL", 0x3E, AddressMode.ABSOLUTE_X);
        addOp("RLA", 0x3F, AddressMode.ABSOLUTE_X);
        addOp("RTI", 0x40, AddressMode.IMPLIED);
        addOp("EOR", 0x41, AddressMode.INDIRECT_X);
        addOp("JAM", 0x42, AddressMode.IMPLIED);
        addOp("SRE", 0x43, AddressMode.INDIRECT_X);
        addOp("NOP", 0x44, AddressMode.ZERO_PAGE);
        addOp("EOR", 0x45, AddressMode.ZERO_PAGE);
        addOp("LSR", 0x46, AddressMode.ABSOLUTE_X);
        addOp("SRE", 0x47, AddressMode.ZERO_PAGE);
        addOp("PHA", 0x48, AddressMode.IMPLIED);
        addOp("EOR", 0x49, AddressMode.IMMEDIATE);
        addOp("LSR", 0x4A, AddressMode.ACCUMULATOR);
        addOp("ASR", 0x4B, AddressMode.IMMEDIATE);
        addOp("JMP", 0x4C, AddressMode.ABSOLUTE);
        addOp("EOR", 0x4D, AddressMode.ABSOLUTE);
        addOp("LSR", 0x4E, AddressMode.ABSOLUTE);
        addOp("SRE", 0x4F, AddressMode.ABSOLUTE);
        addOp("BVC", 0x50, AddressMode.RELATIVE);
        addOp("EOR", 0x51, AddressMode.INDIRECT_Y);
        addOp("JAM", 0x52, AddressMode.IMPLIED);
        addOp("SRE", 0x53, AddressMode.INDIRECT_Y);
        addOp("NOP", 0x54, AddressMode.ZERO_PAGE_X);
        addOp("EOR", 0x55, AddressMode.ZERO_PAGE_X);
        addOp("LSR", 0x56, AddressMode.ZERO_PAGE_X);
        addOp("SRE", 0x57, AddressMode.ZERO_PAGE_X);
        addOp("CLI", 0x58, AddressMode.IMPLIED);
        addOp("EOR", 0x59, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x5A, AddressMode.IMPLIED);
        addOp("SRE", 0x5B, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x5C, AddressMode.ABSOLUTE_X);
        addOp("EOR", 0x5D, AddressMode.ABSOLUTE_X);
        addOp("SRE", 0x5F, AddressMode.ABSOLUTE_X);
        addOp("RTS", 0x60, AddressMode.IMPLIED);
        addOp("ADC", 0x61, AddressMode.INDIRECT_X);
        addOp("JAM", 0x62, AddressMode.IMPLIED);
        addOp("RRA", 0x63, AddressMode.INDIRECT_X);
        addOp("NOP", 0x64, AddressMode.ZERO_PAGE);
        addOp("ADC", 0x65, AddressMode.ZERO_PAGE);
        addOp("ROR", 0x66, AddressMode.ZERO_PAGE);
        addOp("RRA", 0x67, AddressMode.ZERO_PAGE);
        addOp("PLA", 0x68, AddressMode.IMPLIED);
        addOp("ADC", 0x69, AddressMode.IMMEDIATE);
        addOp("ROR", 0x6A, AddressMode.ACCUMULATOR);
        addOp("ARR", 0x6B, AddressMode.IMMEDIATE);
        addOp("JMP", 0x6C, AddressMode.INDIRECT);
        addOp("ADC", 0x6D, AddressMode.ABSOLUTE);
        addOp("ROR", 0x6E, AddressMode.ABSOLUTE);
        addOp("RRA", 0x6F, AddressMode.ABSOLUTE);
        addOp("BVS", 0x70, AddressMode.RELATIVE);
        addOp("ADC", 0x71, AddressMode.INDIRECT_Y);
        addOp("JAM", 0x72, AddressMode.IMPLIED);
        addOp("RRA", 0x73, AddressMode.INDIRECT_Y);
        addOp("NOP", 0x74, AddressMode.ZERO_PAGE_X);
        addOp("ADC", 0x75, AddressMode.ZERO_PAGE_X);
        addOp("ROR", 0x76, AddressMode.ZERO_PAGE_X);
        addOp("RRA", 0x77, AddressMode.ZERO_PAGE_X);
        addOp("SEI", 0x78, AddressMode.IMPLIED);
        addOp("ADC", 0x79, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x7A, AddressMode.IMPLIED);
        addOp("RRA", 0x7B, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0x7C, AddressMode.ABSOLUTE_X);
        addOp("ADC", 0x7D, AddressMode.ABSOLUTE_X);
        addOp("ROR", 0x7E, AddressMode.ABSOLUTE_X);
        addOp("RRA", 0x7F, AddressMode.ABSOLUTE_X);
        addOp("NOP", 0x80, AddressMode.IMMEDIATE);
        addOp("STA", 0x81, AddressMode.INDIRECT_X);
        addOp("NOP", 0x82, AddressMode.IMMEDIATE);
        addOp("SAX", 0x83, AddressMode.INDIRECT_X);
        addOp("STY", 0x84, AddressMode.ZERO_PAGE);
        addOp("STA", 0x85, AddressMode.ZERO_PAGE);
        addOp("STX", 0x86, AddressMode.ZERO_PAGE);
        addOp("SAX", 0x87, AddressMode.ZERO_PAGE);
        addOp("DEY", 0x88, AddressMode.IMPLIED);
        addOp("NOP", 0x89, AddressMode.IMMEDIATE);
        addOp("TXA", 0x8A, AddressMode.IMPLIED);
        addOp("ANE", 0x8B, AddressMode.IMMEDIATE);
        addOp("STY", 0x8C, AddressMode.ABSOLUTE);
        addOp("STA", 0x8D, AddressMode.ABSOLUTE);
        addOp("STX", 0x8E, AddressMode.ABSOLUTE);
        addOp("SAX", 0x8F, AddressMode.ABSOLUTE);
        addOp("BCC", 0x90, AddressMode.RELATIVE);
        addOp("STA", 0x91, AddressMode.INDIRECT_Y);
        addOp("JAM", 0x92, AddressMode.IMPLIED);
        addOp("SHA", 0x93, AddressMode.ABSOLUTE_X);
        addOp("STY", 0x94, AddressMode.ZERO_PAGE_X);
        addOp("STA", 0x95, AddressMode.ZERO_PAGE_X);
        addOp("SAX", 0x97, AddressMode.ZERO_PAGE_Y);
        addOp("STX", 0x96, AddressMode.ZERO_PAGE_Y);
        addOp("TYA", 0x98, AddressMode.IMPLIED);
        addOp("STA", 0x99, AddressMode.ABSOLUTE_Y);
        addOp("TXS", 0x9A, AddressMode.IMPLIED);
        addOp("SHS", 0x9B, AddressMode.ABSOLUTE_Y);
        addOp("SHY", 0x9C, AddressMode.ABSOLUTE_Y);
        addOp("STA", 0x9D, AddressMode.ABSOLUTE_X);
        addOp("SHX", 0x9E, AddressMode.ABSOLUTE_X);
        addOp("SHA", 0x9F, AddressMode.ABSOLUTE_Y);
        addOp("LDY", 0xA0, AddressMode.IMMEDIATE);
        addOp("LDA", 0xA1, AddressMode.INDIRECT_X);
        addOp("LDX", 0xA2, AddressMode.IMMEDIATE);
        addOp("LAX", 0xA3, AddressMode.INDIRECT_X);
        addOp("LDY", 0xA4, AddressMode.ZERO_PAGE);
        addOp("LDA", 0xA5, AddressMode.ZERO_PAGE);
        addOp("LDX", 0xA6, AddressMode.ZERO_PAGE);
        addOp("LAX", 0xA7, AddressMode.ZERO_PAGE);
        addOp("TAY", 0xA8, AddressMode.IMPLIED);
        addOp("LDA", 0xA9, AddressMode.IMMEDIATE);
        addOp("TAX", 0xAA, AddressMode.IMPLIED);
        addOp("LXA", 0xAB, AddressMode.IMMEDIATE);
        addOp("LDY", 0xAC, AddressMode.ABSOLUTE);
        addOp("LDA", 0xAD, AddressMode.ABSOLUTE);
        addOp("LDX", 0xAE, AddressMode.ABSOLUTE);
        addOp("LAX", 0xAF, AddressMode.ABSOLUTE);
        addOp("BCS", 0xB0, AddressMode.RELATIVE);
        addOp("LDA", 0xB1, AddressMode.INDIRECT_Y);
        addOp("JAM", 0xB2, AddressMode.IMPLIED);
        addOp("LAX", 0xB3, AddressMode.INDIRECT_Y);
        addOp("LDY", 0xB4, AddressMode.ZERO_PAGE_X);
        addOp("LDA", 0xB5, AddressMode.ZERO_PAGE_X);
        addOp("LDX", 0xB6, AddressMode.ZERO_PAGE_Y);
        addOp("LAX", 0xB7, AddressMode.ZERO_PAGE_Y);
        addOp("CLV", 0xB8, AddressMode.IMPLIED);
        addOp("LDA", 0xB9, AddressMode.ABSOLUTE_Y);
        addOp("TSX", 0xBA, AddressMode.IMPLIED);
        addOp("LAE", 0xBB, AddressMode.ABSOLUTE_Y);
        addOp("LDY", 0xBC, AddressMode.ABSOLUTE_X);
        addOp("LDA", 0xBD, AddressMode.ABSOLUTE_X);
        addOp("LDX", 0xBE, AddressMode.ABSOLUTE_Y);
        addOp("LAX", 0xBF, AddressMode.ABSOLUTE_Y);
        addOp("CPY", 0xC0, AddressMode.IMMEDIATE);
        addOp("CMP", 0xC1, AddressMode.INDIRECT_X);
        addOp("NOP", 0xC2, AddressMode.IMMEDIATE);
        addOp("DCP", 0xC3, AddressMode.INDIRECT_X);
        addOp("CPY", 0xC4, AddressMode.ZERO_PAGE);
        addOp("CMP", 0xC5, AddressMode.ZERO_PAGE);
        addOp("DEC", 0xC6, AddressMode.ZERO_PAGE);
        addOp("DCP", 0xC7, AddressMode.ZERO_PAGE);
        addOp("INY", 0xC8, AddressMode.IMPLIED);
        addOp("CMP", 0xC9, AddressMode.IMMEDIATE);
        addOp("DEX", 0xCA, AddressMode.IMPLIED);
        addOp("SBX", 0xCB, AddressMode.IMMEDIATE);
        addOp("CPY", 0xCC, AddressMode.ABSOLUTE);
        addOp("CMP", 0xCD, AddressMode.ABSOLUTE);
        addOp("DEC", 0xCE, AddressMode.ABSOLUTE);
        addOp("DCP", 0xCF, AddressMode.ABSOLUTE);
        addOp("BNE", 0xD0, AddressMode.RELATIVE);
        addOp("CMP", 0xD1, AddressMode.INDIRECT_Y);
        addOp("JAM", 0xD2, AddressMode.IMPLIED);
        addOp("DCP", 0xD3, AddressMode.INDIRECT_Y);
        addOp("NOP", 0xD4, AddressMode.ZERO_PAGE_X);
        addOp("CMP", 0xD5, AddressMode.ZERO_PAGE_X);
        addOp("DEC", 0xD6, AddressMode.ZERO_PAGE_X);
        addOp("DCP", 0xD7, AddressMode.ZERO_PAGE_X);
        addOp("CLD", 0xD8, AddressMode.IMPLIED);
        addOp("CMP", 0xD9, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0xDA, AddressMode.IMPLIED);
        addOp("DCP", 0xDB, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0xDC, AddressMode.ABSOLUTE_X);
        addOp("CMP", 0xDD, AddressMode.ABSOLUTE_X);
        addOp("DEC", 0xDE, AddressMode.ABSOLUTE_X);
        addOp("DCP", 0xDF, AddressMode.ABSOLUTE_X);
        addOp("CPX", 0xE0, AddressMode.IMMEDIATE);
        addOp("SBC", 0xE1, AddressMode.INDIRECT_X);
        addOp("NOP", 0xE2, AddressMode.IMMEDIATE);
        addOp("ISB", 0xE3, AddressMode.INDIRECT_X);
        addOp("CPX", 0xE4, AddressMode.ZERO_PAGE);
        addOp("SBC", 0xE5, AddressMode.ZERO_PAGE);
        addOp("INC", 0xE6, AddressMode.ZERO_PAGE);
        addOp("ISB", 0xE7, AddressMode.ZERO_PAGE);
        addOp("INX", 0xE8, AddressMode.IMPLIED);
        addOp("SBC", 0xE9, AddressMode.IMMEDIATE);
        addOp("NOP", 0xEA, AddressMode.IMPLIED);
        addOp("SBC", 0xEB, AddressMode.IMMEDIATE);
        addOp("SBC", 0xED, AddressMode.ABSOLUTE);
        addOp("CPX", 0xEC, AddressMode.ABSOLUTE);
        addOp("INC", 0xEE, AddressMode.ABSOLUTE);
        addOp("ISB", 0xEF, AddressMode.ABSOLUTE);
        addOp("BEQ", 0xF0, AddressMode.RELATIVE);
        addOp("SBC", 0xF1, AddressMode.INDIRECT_Y);
        addOp("JAM", 0xF2, AddressMode.IMPLIED);
        addOp("ISB", 0xF3, AddressMode.INDIRECT_Y);
        addOp("NOP", 0xF4, AddressMode.ZERO_PAGE_X);
        addOp("SBC", 0xF5, AddressMode.ZERO_PAGE_X);
        addOp("INC", 0xF6, AddressMode.ZERO_PAGE_X);
        addOp("ISB", 0xF7, AddressMode.ZERO_PAGE_X);
        addOp("SED", 0xF8, AddressMode.IMPLIED);
        addOp("SBC", 0xF9, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0xFA, AddressMode.IMPLIED);
        addOp("ISB", 0xFB, AddressMode.ABSOLUTE_Y);
        addOp("NOP", 0xFC, AddressMode.ABSOLUTE_X);
        addOp("SBC", 0xFD, AddressMode.ABSOLUTE_X);
        addOp("INC", 0xFE, AddressMode.ABSOLUTE_X);
        addOp("ISB", 0xFF, AddressMode.ABSOLUTE_X);
    }
    
    private void addOp(String instruction, int opCode, AddressMode am){
        OpCode oc = new OpCode(instruction,am);
        ops[opCode] = oc;
    }
    
    public OpCode getInstruction(int oc){
        return(ops[oc]);
    }
    
}
