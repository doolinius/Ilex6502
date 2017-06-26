/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.emulator;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 *
 * @author jdoolin
 */
public class Emulator {
    private static OpCodes codes = new OpCodes();
    private static byte[] ram = new byte[27648];// 0x0000 - 0x6C00 (exclusive)
    private static byte[] screenMemory = new byte[1024];// - 0x6C00 - 7000
    private static byte[] stackMemory = new byte[4096]; // - 0x7000 - 0x8000
    private static byte[] rom = new byte[32768]; // - 0x8000 - 0xFFFF
    protected static byte accumulator;
    protected static byte xRegister;
    protected static byte yRegister;
    protected static byte statusRegister;
    protected static byte stackPointer;
    protected static short programCounter;
    protected int zeroFlag;
    protected int signFlag;
    protected int overflowFlag;
    protected int breakFlag;
    protected int decimalFlag;
    protected int interruptFlag;
    protected int carryFlag;
    
    public Emulator(){
        accumulator = 0x00;
        xRegister = 0x00;
        yRegister = 0x00;
        stackPointer = 0x00;
        programCounter = 0x00;
        statusRegister = 0x20;
        zeroFlag = signFlag = overflowFlag = breakFlag = decimalFlag = interruptFlag = carryFlag = 0;
    }
    
    public void loadBinary(File f){
        try{
            byte[] data = Files.readAllBytes(f.toPath());
            System.arraycopy(data, 0, rom, 0, data.length);
            //for (int x =0; x<data.length; x++){
            //    System.out.println("Loaded ROM with " + rom[x]);
            //}
        }catch(Exception e){
            System.out.println("Error reading binary data" + e.getMessage());;
        }
    }
    
    public void runBinary(){
        
    }
    
    public static short byteToUnsigned(byte b) {
        short x = (short)(b & 0xFF);
        return (x);
  }
    
    private void processFlags(short uResult, byte sResult){
        if (uResult > 127) {
            signFlag = 1;
        }else{
            signFlag = 1;
        }
        if (uResult > 255) {
            carryFlag = 1;
        }else{
            carryFlag = 0;
        }
        if (uResult == 0) {
            zeroFlag = 1;
        }else{
            zeroFlag = 1;
        }
        if ((sResult < -128) || (sResult > 127)){
            overflowFlag = 1;
        }else{
            overflowFlag = 0;
        }
    }
    
    private short bytesToAddr(byte low, byte high){
        short addr = (short)((low << 8) + high);
        return(addr);
    }
    
    private byte resolveAbsolute(byte low, byte high){
        return(ram[bytesToAddr(low,high)]);
    }
    
    private byte resolveAbsoluteX(byte low, byte high){
        return(ram[bytesToAddr(low,high) + xRegister]);
    }
    
    private byte resolveAbsoluteY(byte low, byte high){
        return(ram[bytesToAddr(low,high) + yRegister]);
    }
    
    private byte resolveIndirectX(byte addr){
        byte low = ram[addr+xRegister];
        byte high = ram[addr+xRegister+1];
        
        return(ram[bytesToAddr(low,high)]);
    }
    
    private byte resolveIndirectY(byte addr){
        byte low = ram[addr];
        byte high = ram[addr+1];
        
        return(ram[bytesToAddr(low,high)+yRegister]);
    }
    
    // ADd with Carry instruction
    // Addes the operand to the accumulator and stores the result
    // back to the accumulator
    // Modifies carryFlag (1 if carry was needed)
    //          zeroFlag if result was zero
    //          signFlag if sign bit was set
    //          overFlowFlag if addition caused overflow
    private void ADC(byte b){
        // get temporary result and check for carry
        short x = (short)(carryFlag + accumulator + byteToUnsigned(b));
        carryFlag = (x > 255) ? 1 : 0;
        
        accumulator += carryFlag + byteToUnsigned(b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        signFlag = (accumulator & 0x80) >> 7;
        overflowFlag = (accumulator & 0x40) >> 6;
    }
    
    // Arithmetic Shift Left
    // Performs an arithmetic left bitshift to the contents of a memory address
    // Modifles: 
    //          carryFlag (1 if bit 1 is set)
    //          zeroFlag (if shift results in 0)
    //          signFlag (if sign bit is set)
    private void ASL(short addr){
        carryFlag = (ram[addr] & 0x01);// is bit 1 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] << 1);// shift the number left
        zeroFlag = (ram[addr] == 0) ? 0 : 1;
        signFlag = (ram[addr] & 0x80) >> 7;
    }
    
    // Logical Shift Right
    // performs a logical right bitshift
    // Modifies:
    //          carryFlag (1 if bit was was originally set)
    //          zeroFlag (if result was 0)
    //          signFlag always set to 0
    private void LSR(short addr){
        carryFlag = (ram[addr] & 0x01);// is bit 1 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] >> 1);
        zeroFlag = (ram[addr] == 0) ? 0 : 1;
        signFlag = 0;
    }
    
    private void ROL(short addr){
        carryFlag = (ram[addr] & 0x80) >> 7;// is bit 7 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] << 1);
        ram[addr] += carryFlag; // put the carry into bit 0
        zeroFlag = (ram[addr] == 0) ? 0 : 1;
    }
    
    private void ROR(short addr){
        carryFlag = (ram[addr] & 0x01);// is bit 0 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] >> 1);
        ram[addr] = (byte) (ram[addr] & 0x80);
        zeroFlag = (ram[addr] == 0) ? 0 : 1;
    }
    
    private void ORA(byte b){
        accumulator = (byte) (accumulator | b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        signFlag = (accumulator & 0x80) >> 7;
    }
    
    private void AND(byte b){
        accumulator = (byte) (accumulator & b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        signFlag = (accumulator & 0x80) >> 7;
    }
    
    private void BIT(byte b){
        byte result = (byte) (accumulator & b);
        zeroFlag = (result == 0) ? 0 : 1;
        signFlag = (b & 0x80) >> 7;
        overflowFlag = (b & 0x40) >> 6;
    }
    
    private void EOR(byte b){
        accumulator = (byte) (accumulator ^ b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        signFlag = (accumulator & 0x80) >> 7;
    }
    
    public void performInstruction(){
        System.out.println("PC: " + programCounter);
        System.out.println("Pre-decoded Instruction: " + rom[programCounter]);
        short addr;
        switch(byteToUnsigned(rom[programCounter++])){
            case 0x00: // BRK - break
                // implement a break/listener in the emulator
                break;
            case 0x01: // ORA - OR Accumulator, indirect X
                ORA(resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0x05: // ORA OR Accumulator, zero page
                ORA(ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0x06: // ASL Arithmetic Shift Left, zero page
                addr = rom[programCounter];
                ASL(addr);
                programCounter++;
                break;
            case 0x09: // ORA immediate
                ORA(rom[programCounter]);
                programCounter++;
                break;
            case 0x0A: // ASL accumulator
                carryFlag = (accumulator & 0x80) >> 7;// is bit 7 set? if so it goes into carry
                accumulator = (byte)(accumulator << 1);
                zeroFlag = (accumulator == 0) ? 0 : 1;
                signFlag = (accumulator & 0x80) >> 7;
                break;
            case 0x0D: // ORA absolute
                ORA(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x0E: // ASL absolute
                addr = bytesToAddr(rom[programCounter++],rom[programCounter]);
                ASL(addr);
                programCounter++;
                break;
            case 0x10: // BPL - branch on plus
                if (signFlag == 0){
                    programCounter = (short) (programCounter + rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0x11: // ORA - OR Accumulator, indirect Y
                ORA(resolveIndirectY(rom[programCounter]));
                programCounter++;
                break;
            case 0x15: // ORA - OR Accumulator, zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                ORA(ram[addr]);
                programCounter++;
                break;
            case 0x16: // ASL , zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                ASL(addr);
                programCounter++;
                break;
            case 0x18: // CLC - clear carry bit
                carryFlag = 0;
                break;
            case 0x19: // ORA - OR Accumulator, absolute y
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + yRegister);
                ORA(ram[addr]);
                programCounter++;
                break;
            case 0x1D: // ORA - OR Accumulator, absolute x
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                ORA(ram[addr]);
                programCounter++;
                break;
            case 0x1E: // ASL , absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                ASL(addr);
                programCounter++;
                break;
            case 0x21: // AND, indirect X
                AND(resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0x24: // BIT zero page
                BIT(rom[programCounter]);
                programCounter++;
                break;
            case 0x25: // AND zero page
                AND(ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0x26: // ROL, rotate left, zero page
                ROL(rom[programCounter]);
                programCounter++;
                break;
            case 0x29: // AND immediate
                AND(rom[programCounter]);
                programCounter++;
                break;
            case 0x2A: // ROL accumulator
                carryFlag = (accumulator & 0x80) >> 7;// is bit 7 set? if so it goes into carry
                accumulator = (byte) (accumulator << 1);
                accumulator += carryFlag; // put the carry into bit 0
                zeroFlag = (accumulator == 0) ? 0 : 1;
                break;
            case 0x2C: // BIT absolute
                BIT(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x2D: // AND absolute
                AND(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x2E: // ROL absolute
                ROL(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x30:
            case 0x31: // AND, indirect Y
                AND(resolveIndirectY(rom[programCounter]));
                programCounter++;
                break;
            case 0x35: // AND, zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                AND(ram[addr]);
                programCounter++;
                break;
            case 0x36: // ROL, zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                ROL(addr);
                programCounter++;
                break;
            case 0x38: // SEC - set carry bit
                carryFlag = 1;
                break;
            case 0x39: // AND, absolute Y
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + yRegister);
                AND(ram[addr]);
                programCounter++;
                break;
            case 0x3D: // AND absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                AND(ram[addr]);
                programCounter++;
                break;
            case 0x3E: // ROL absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                ROL(addr);
                programCounter++;
                break;
            case 0x41: // EOR, indirect X
                EOR(resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0x45: // EOR, zero page
                EOR(rom[programCounter]);
                programCounter++;
                break;
            case 0x46: // LSR, zero page
                LSR(rom[programCounter]);
                programCounter++;
                break;
            case 0x48: // PHA
            case 0x49: // EOR immediate
                EOR(rom[programCounter]);
                programCounter++;
                break;
            case 0x4A: // LSR accumulator
                carryFlag = (accumulator & 0x01);// is bit 0 set? if so it goes into carry
                accumulator = (byte) (accumulator >> 1);
                zeroFlag = (accumulator == 0) ? 0 : 1;
                signFlag = 0;
                break;
            case 0x4C: // JMP absolute
                programCounter = resolveAbsolute(rom[programCounter++],rom[programCounter]);
                break;
            case 0x4D: // EOR absolute
                EOR(ram[resolveAbsolute(rom[programCounter++],rom[programCounter])]);
                programCounter++;
                break;
            case 0x4E: // LSR absolute
                LSR(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x50: // BVC relative
            case 0x51: // EOR indirect Y
                EOR(resolveIndirectY(rom[programCounter]));
                programCounter++;
                break;
            case 0x55: // EOR, zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                EOR(ram[addr]);
                programCounter++;
                break;
            case 0x56: // LSR, zero-page X
                addr = (short)(rom[programCounter] + xRegister);
                LSR(addr);
                programCounter++;
                break;
            case 0x58: // CLI - clear interrupt flag
                interruptFlag = 0;
                break;
            case 0x59: // EOR absolute Y
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + yRegister);
                EOR(ram[addr]);
                programCounter++;
                break;
            case 0x5D: // EOR absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                EOR(ram[addr]);
                programCounter++;
                break;
            case 0x5E: // LSR absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                LSR(addr);
                programCounter++;
                break;
            case 0x61: // ADC indirect X
                ADC(resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0x65: // ADC zero-page
                ADC(rom[programCounter]);
                programCounter++;
                break;
            case 0x69: // ADC immediate
                ADC(rom[programCounter]);
                programCounter++;
                break;
            case 0x78: // SEI - set interrupt flag
                interruptFlag = 1;
                break;
            case 0x85: // STA zero page
                ram[rom[programCounter]] = accumulator;
                break;
            case 0xA9: // LDA immediate
                System.out.println("PC: " + programCounter);
                System.out.println("LDA immediate");
                accumulator = rom[programCounter];
                programCounter++;
                signFlag = accumulator & 0x80;
                zeroFlag = ~accumulator;
                break;
            case 0xB8: // CLV - clear overflow
                overflowFlag = 0;
                break;
            case 0xD8: // CLD - clear decimal flag
                decimalFlag = 0;
                break;
            case 0xF8: // SED - set decimal flag
                decimalFlag = 1;
                break;
            default:
                System.out.println("invalid instruction");
                System.out.println("PC: " + programCounter);
                System.out.println("Instruction: " + rom[programCounter]);
        }
    }
    
    private static byte readMemory(short address){
        if (address < 0x6C00){
            return(ram[address]);
        }else if(address < 0x7000){
            return(screenMemory[address-0x6C00]);
        }else if(address < 0x8000){
            return(stackMemory[address-0x7000]);
        }else{
            return(rom[address-0x8000]);
        }
    }
    
    private static void stackPush(byte b){
        stackPointer++;
        stackMemory[stackPointer] = b;
    }
    
    private static byte stackPop(){
        byte b = stackMemory[stackPointer];
        return(b);
    }
    
    public static void main(String[] args) {
        //System.out.println("OpCode 0xA9 = " + codes.getInstruction(0xA9).getInstruction());
    }
    
}
