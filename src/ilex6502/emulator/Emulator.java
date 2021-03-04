/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.emulator;

import ilex6502.assembler.AddressMode;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 *
 * @author jdoolin
 */
public class Emulator {
    private static OpCodes codes = new OpCodes();
    private final int STACK_START = 0x0100;
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
    protected int negativeFlag;
    protected int overflowFlag;
    protected int breakFlag;
    protected int decimalFlag;
    protected int interruptFlag;
    protected int carryFlag;
    
    protected short romSize = 0;
    
    public Emulator(){
        initEmulator();
        test();
        initEmulator();
    }
    
    private void test(){
        byte low = 0x10;
        byte high = 0x2E;
        short addr = bytesToAddr(low, high);
        if (addr == 0x102E){
            System.out.println("bytesToAddr PASSED");
            System.out.println("Holds " + addr);
        }
        short testAddr = 0x1066;
        pushAddr(testAddr);
        System.out.println("Low byte: " + stackMemory[0]);
        System.out.println("High byte: " + stackMemory[1]);
        System.out.println("StackPointer: " + stackPointer);
    }
    
    private void initEmulator(){
        accumulator = 0x00;
        xRegister = 0x00;
        yRegister = 0x00;
        stackPointer = (byte)0xFF;
        programCounter = 0x00;
        statusRegister = 0x20;
        zeroFlag = negativeFlag = overflowFlag = breakFlag = decimalFlag = carryFlag = 0;
        interruptFlag = 1;
    }
    
    public void loadBinary(File f){
        try{
            byte[] data = Files.readAllBytes(f.toPath());
            System.arraycopy(data, 0, rom, 0, data.length);
            romSize = (short)data.length;
            //for (int x =0; x<data.length; x++){
            //    System.out.println("Loaded ROM with " + rom[x]);
            //}
        }catch(Exception e){
            System.out.println("Error reading binary data" + e.getMessage());;
        }
    }
    
    public void runBinary(){
        initEmulator();
        while (programCounter < romSize){
            performInstruction();
        }
    }
    
    public static short byteToUnsigned(byte b) {
        short x = (short)(b & 0xFF);
        return (x);
  }
    
    private void processFlags(short uResult, byte sResult){
        if (uResult > 127) {
            negativeFlag = 1;
        }else{
            negativeFlag = 1;
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
    
    private byte readRam(AddressMode m){
        return(0);
    }
    
    // Reads and returns the byte stored in a memory address
    public static byte readMemory(short address){
        return(ram[address]);
        /*
        if (address < 0x6C00){
            return(ram[address]);
        }else if(address < 0x7000){
            return(screenMemory[address-0x6C00]);
        }else if(address < 0x8000){
            return(stackMemory[address-0x7000]);
        }else{
            return(rom[address-0x8000]);
        }
        */
    }
    
    // Returns the memory address based on Address Mode
    // This will happen after reading an instruction byte
    // Reads and may modify programCounter
    private short getMemoryAddress(AddressMode m){
        switch(m){
            case ABSOLUTE:
                return(getAbsoluteAddr());
            case ABSOLUTE_X:
                return(getAbsoluteXAddr());
            case ABSOLUTE_Y:
                return(getAbsoluteYAddr());
            case IMMEDIATE:
                return(getImmediateAddr());
            case ZERO_PAGE:
                return(getZeroPageAddr());
            case ZERO_PAGE_X:
                return(getZeroPageXAddr());
            case ZERO_PAGE_Y:
                return(getZeroPageYAddr());
            case INDIRECT:
                return(getIndirectAddr());
            case INDIRECT_X:
                return(getIndirectXAddr());
            case INDIRECT_Y:
                return(getIndirectYAddr());
            case RELATIVE:
                // the only mode that uses signed byte offsets
                return(rom[programCounter]);
        }
        return(0);
    }
    
    private short bytesToAddr(byte low, byte high){
        short addr = (short)((low << 8) + high);
        return(addr);
    }
    
    private short getImmediateAddr(){
        return(byteToUnsigned(rom[programCounter]));
    }
    
    private short getZeroPageAddr(){
        return(byteToUnsigned(rom[programCounter]));
    }
    
    private short getZeroPageXAddr(){
        short addr = (short)(byteToUnsigned(rom[programCounter]) + xRegister);
        return(addr);
    }
    
    private short getZeroPageYAddr(){
        short addr = (short)(byteToUnsigned(rom[programCounter]) + yRegister);
        return(addr);
    }
    
    private short getAbsoluteAddr(){
        System.out.println("Next two bytes in Absolute Mode: " + printHex(rom[programCounter]) + "-" + printHex(rom[programCounter+1]));
        return(bytesToAddr(rom[programCounter++], rom[programCounter]));
    }
    
    private short getAbsoluteXAddr(){
        return((short)(bytesToAddr(rom[programCounter++], rom[programCounter]) + xRegister));
    }
    
    private short getAbsoluteYAddr(){
        return((short)(bytesToAddr(rom[programCounter++], rom[programCounter]) + yRegister));
    }
    
    private short getIndirectAddr(){
        byte low = ram[byteToUnsigned(rom[programCounter])];
        byte high = ram[byteToUnsigned(rom[programCounter])+1];
        return((short)(bytesToAddr(low, high)));
    }
    
    private short getIndirectXAddr(){
        byte low = ram[byteToUnsigned(rom[programCounter])+xRegister];
        byte high = ram[byteToUnsigned(rom[programCounter])+xRegister+1];
        return(bytesToAddr(low, high));
    }
    
    private short getIndirectYAddr(){
        byte low = ram[byteToUnsigned(rom[programCounter])];
        byte high = ram[byteToUnsigned(rom[programCounter])+1];
        return((short)(bytesToAddr(low, high) + yRegister));
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
    
    private byte resolveImmediate(){
        byte val = (byte)(byteToUnsigned(rom[programCounter]));
        return(val);
    }
    
    // ADd with Carry instruction
    // Addes the operand to the accumulator and stores the result
    // back to the accumulator
    // Modifies carryFlag (1 if carry was needed)
    //          zeroFlag if result was zero
    //          negativeFlag if sign bit was set
    //          overFlowFlag if addition caused overflow
    private void ADC(byte b){
        // get temporary result and check for carry
        /*
        short x = (short)(carryFlag + accumulator + byteToUnsigned(b));
        carryFlag = (x > 255) ? 1 : 0;
        
        accumulator += carryFlag + byteToUnsigned(b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        negativeFlag = (accumulator & 0x80) >> 7;
        overflowFlag = (accumulator & 0x40) >> 6;
        */
        //_memTemp = _mem[++_PC.Contents];
        short x = (byte)(accumulator + b + getCarryFlag()); //_SR[_BIT0_SR_CARRY];
        if (getDecimalFlag() == 1)
        {
          if (((accumulator ^ b ^ x) & 0x10) == 0x10)
          {
            x += 0x06;
          }
          if ((x & 0xf0) > 0x90)
          {
            x += 0x60;
          }
        }
        overflowFlag = ((accumulator ^ x) & (b ^ x) & 0x80) == 0x80 ? 1 : 0;
        carryFlag = (x & 0x100) == 0x100 ? 1 : 0;
        setZeroFlag(xRegister);
        negativeFlag = (accumulator & 0x80) >> 7;
        accumulator = (byte)(x & 0xff);
    }
    
    // SuBtract with Carry instruction
    // Subtracts the operand from the accumulator together with the NOT of the 
    // carryFlag, and stores the result back to the accumulator
    // If overflow occurs, the carry bit is clear
    // Modifies 
    //          carryFlag (1 if carry was needed)
    //          zeroFlag if result was zero
    //          negativeFlag if sign bit was set
    //          overFlowFlag if addition caused overflow
    private void SBC(byte b){
        // get temporary result and check for carry
        // THIS IS STILL ADC
        // TODO: MAKE THIS THE REAL SBC INSTRUCTION
        // In fact, make sure ADC is good too.  Check this out:
        // https://stackoverflow.com/questions/29193303/6502-emulation-proper-way-to-implement-adc-and-sbc
        /*
        short x = (short)(carryFlag + accumulator + byteToUnsigned(b));
        carryFlag = (x > 255) ? 1 : 0;
        
        accumulator += carryFlag + byteToUnsigned(b);
        zeroFlag = (accumulator == 0) ? 0 : 1;
        negativeFlag = (accumulator & 0x80) >> 7;
        overflowFlag = (accumulator & 0x40) >> 6;
        */
        ADC((byte)~b);
    }
    
    // Arithmetic Shift Left
    // Performs an arithmetic left bitshift to the contents of a memory address
    // Modifles: 
    //          carryFlag (1 if bit 1 is set)
    //          zeroFlag (if shift results in 0)
    //          negativeFlag (if sign bit is set)
    private void ASL(short addr){
        carryFlag = (ram[addr] & 0b1000000);// is bit 1 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] << 1);// shift the number left
        setZeroFlag(ram[addr]);
        negativeFlag = (ram[addr] & 0x80) >> 7;
    }
    
    // Logical Shift Right
    // performs a logical right bitshift
    // Modifies:
    //          carryFlag (1 if bit was was originally set)
    //          zeroFlag (if result was 0)
    //          negativeFlag always set to 0
    private void LSR(short addr){
        carryFlag = (ram[addr] & 0x01);// is bit 1 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] >> 1);
        setZeroFlag(ram[addr]);
        negativeFlag = 0;
    }
    
    // Rotate Left
    // Move each of the bits in either A or M one place to the left. 
    // Bit 0 is filled with the current value of the carry flag whilst the old 
    // bit 7 becomes the new carry flag value.
    // Modifies:
    //          carryFlag (set to old bit 7)
    //          zeroFlag (if result was 0)
    //          negativeFlag (set equal to bit 7 of result)
    private void ROL(short addr){
        carryFlag = (ram[addr] & 0x80) >> 7;// is bit 7 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] << 1);
        ram[addr] += getCarryFlag(); // put the carry into bit 0
        setZeroFlag(ram[addr]);
        negativeFlag = (ram[addr] & 0x80) >> 7;
    }
    
    // Rotate Right
    // Move each of the bits in either A or M one place to the right. 
    // Bit 7 is filled with the current value of the carry flag whilst the old 
    // bit 0 becomes the new carry flag value.
    // Modifies:
    //          carryFlag (set to old bit 0)
    //          zeroFlag (set if accumulator is 0)
    //          negativeFlag (set if bit 7 or result is set)
    private void ROR(short addr){
        //byte oldCarry = (byte)carryFlag; // old carry flag goes into bit 7 
        byte oldCarry = (byte)((getCarryFlag() == 1) ? 0x80: 0x00); // old carry flag goes into bit 7 
        carryFlag = (ram[addr] & 0x01);// is bit 0 set? if so it goes into carry
        ram[addr] = (byte) (ram[addr] >> 1);
        ram[addr] = (byte) (ram[addr] & oldCarry); // 0x80); WRONG
        setZeroFlag(ram[addr]);
        negativeFlag = (ram[addr] & 0x80) >> 7;
    }
    
    // Logical Inclusive OR
    // An inclusive OR is performed, bit by bit, on the accumulator contents 
    // using the contents of a byte of memory.
    // Modifies:
    //          zeroFlag (set if Accumulator = 0)
    //          negativeFlag (set if bit 7 is set)
    private void ORA(byte b){
        accumulator = (byte) (accumulator | b);
        setZeroFlag(accumulator);
        negativeFlag = (accumulator & 0x80) >> 7;
    }
    
    // Logical Inclusive AND
    // A logical AND is performed, bit by bit, on the accumulator contents 
    // using the contents of a byte of memory.
    // Modifies:
    //          zeroFlag (set if Accumulator = 0)
    //          negativeFlag (set if bit 7 is set)
    private void AND(byte b){
        accumulator = (byte) (accumulator & b);
        setZeroFlag(accumulator);
        negativeFlag = (accumulator & 0x80) >> 7;
    }
    
    // This instructions is used to test if one or more bits are set in a 
    // target memory location. The mask pattern in A is ANDed with the value 
    // in memory to set or clear the zero flag, but the result is not kept. 
    // Bits 7 and 6 of the value from memory are copied into the N and V flags.
    // Modifies:
    //          zeroFlag (set if result of AND is zero)
    //          overflowFlag (set to bit 6 of memory location)
    //          negativeFlag (set to bit 7 of memory location)
    private void BIT(byte b){
        byte result = (byte) (accumulator & b);
        setZeroFlag(result);
        negativeFlag = (b & 0x80) >> 7;
        overflowFlag = (b & 0x40) >> 6;
    }
    
    // Logical Exclusive OR
    // An exclusive OR is performed, bit by bit, on the accumulator contents 
    // using the contents of a byte of memory.
    // Modifies:
    //          zeroFlag (set if Accumulator = 0)
    //          negativeFlag (set if bit 7 is set)
    private void EOR(byte b){
        accumulator = (byte) (accumulator ^ b);
        setZeroFlag(accumulator);
        negativeFlag = (accumulator & 0x80) >> 7;
    }
    
    public void compare(byte a, byte b){
        /*
        carryFlag = 0;
        zeroFlag = 0;
        if (a == b){
            carryFlag = 1;
            zeroFlag = 1;
        }else if (a > b){
            carryFlag = 1;
        }
        */
        byte t = (byte)(a - b);
        setNegativeFlag(t);
        setZeroFlag(t);
        carryFlag = (a >= b) ? 1:0;
    }
    
    public void setZeroFlag(byte test){
        System.out.println("Setting Zero Flag");
        zeroFlag = (test == 0) ? 1 : 0;
        System.out.println("To " + getZeroFlag());
    }
    
    public void setNegativeFlag(byte test){
        negativeFlag = (test & 0x80) >> 7;
    }
    
    public void performInstruction(){
        System.out.println("PC: " + programCounter);
        System.out.println("Pre-decoded Instruction: " + String.format("0x%02X",byteToUnsigned(rom[programCounter])));
        short addr;
        switch(byteToUnsigned(rom[programCounter++])){
            case 0x00: // BRK - break
                // implement a break/listener in the emulator
                programCounter = (short)(romSize + 1);
                break;
            case 0x01: // ORA - OR Accumulator, indirect X
                addr = getMemoryAddress(AddressMode.INDIRECT_X);
                ORA(ram[addr]);
                programCounter++;
                break;
            case 0x05: // ORA OR Accumulator, zero page
                addr = getMemoryAddress(AddressMode.ZERO_PAGE);
                ORA(ram[addr]);
                programCounter++;
                break;
            case 0x06: // ASL Arithmetic Shift Left, zero page
                addr = getMemoryAddress(AddressMode.ZERO_PAGE);
                ASL(addr);
                programCounter++;
                break;
            case 0x08: // PHP - Push Processor Status - push status flags onto stack
                stackPush(statusFlagsToByte());
                programCounter++;
                break;
            case 0x09: // ORA immediate
                byte val = resolveImmediate(); // (byte)(byteToUnsigned(rom[programCounter]));
                ORA(val);
                programCounter++;
                break;
            case 0x0A: // ASL accumulator
                carryFlag = (accumulator & 0x80) >> 7;// is bit 7 set? if so it goes into carry
                accumulator = (byte)(accumulator << 1);
                setZeroFlag(accumulator);
                setNegativeFlag(accumulator);
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
                if (getNegativeFlag() == 0){
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
            case 0x20: // JSR - jump to subroutine
                addr = getAbsoluteAddr(); // gets the address to JuMP to (where the function is)
                System.out.println("JSR to " + addr);
                pushAddr((short)(programCounter-0)); // push current code location onto stack
                programCounter = addr; // change to new code location
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
            case 0x28: // PLP - Pull Processor status - pull from stack and set processor flags
                byteToStatusFlags(stackPop());
                programCounter++;
                break;
            case 0x2A: // ROL accumulator
                carryFlag = (accumulator & 0x80) >> 7;// is bit 7 set? if so it goes into carry
                accumulator = (byte) (accumulator << 1);
                accumulator += getCarryFlag(); // put the carry into bit 0
                setZeroFlag(accumulator);
                setNegativeFlag(accumulator);
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
            case 0x30: // BMI - branch if negative set
                if (getNegativeFlag() != 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
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
            case 0x48: // PHA - push copy of accumulator onto the stack
                stackPush(accumulator);
                programCounter++;
                break;
            case 0x49: // EOR immediate
                EOR(rom[programCounter]);
                programCounter++;
                break;
            case 0x4A: // LSR accumulator
                carryFlag = (accumulator & 0x01);// is bit 0 set? if so it goes into carry
                accumulator = (byte) (accumulator >> 1);
                setZeroFlag(accumulator);
                negativeFlag = 0;
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
            case 0x50: // BVC - branch if Overflow Clear
                if (getOverflowFlag() == 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
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
            case 0x60: // RTS return from subroutine
                byte high = stackPop(); // remove previous memory address from stack
                byte low = stackPop(); // (it's two bytes, by the way)
                addr = bytesToAddr(low, high);
                programCounter = addr; // Change the address back to the old one
                programCounter++;
                break;
            case 0x61: // ADC indirect X
                ADC(resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0x65: // ADC zero-page
                ADC(ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0x66: // ROR zero-page
                ROR(rom[programCounter]);
                programCounter++;
                break;
            case 0x68: // PLA pull accumulator - pull 8 bit value from stack into accumulator
                accumulator = stackPop();
                setZeroFlag(accumulator);
                setNegativeFlag(accumulator);
                break;
            case 0x69: // ADC immediate
                ADC(rom[programCounter]);
                programCounter++;
                break;
            case 0x6A: // ROR accumulator
                byte oldCarry = (byte)((getCarryFlag() == 1) ? 0x80: 0x00); // old carry flag goes into bit 7 
                carryFlag = (accumulator & 0x01);// is bit 0 set? if so it goes into carry
                accumulator = (byte) (accumulator >> 1);
                accumulator = (byte) (accumulator ^ oldCarry); // 0x80);
                setZeroFlag(accumulator);
                setNegativeFlag(accumulator);
                break;
            case 0x6D:// ADC, absolute
                ADC(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x6E: // ROR absolute
                ROR(resolveAbsolute(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0x70: // BVS - branch if Overflow Set
                if (getOverflowFlag() != 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0x71: // ADC indirect Y
                ADC(resolveIndirectY(rom[programCounter]));
                programCounter++;
                break;
            case 0x75:
                addr = (short)(rom[programCounter] + xRegister);
                ADC(ram[addr]);
                break;
            case 0x76: // ROR zero page X
                addr = (short)(rom[programCounter] + xRegister);
                ROR(addr);
                programCounter++;
                break; 
            case 0x78: // SEI - set interrupt flag
                interruptFlag = 1;
                break;
            case 0x79: // ADC - absolute, Y
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + yRegister);
                ADC(ram[addr]);
                programCounter++;
                break;
            case 0x7D: // ADC - absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                ADC(ram[addr]);
                programCounter++;
                break;
            case 0x7E: // ROR absolute
                ROR(resolveAbsoluteX(rom[programCounter++],rom[programCounter])); // double check
                programCounter++;
                break;
            case 0x81: // STA - indirect X
                addr = getIndirectXAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0x84: // STY zero page
                addr = getZeroPageAddr();
                ram[addr] = yRegister;
                programCounter++;
                break;
            case 0x85: // STA zero page
                //addr = byteToUnsigned(rom[programCounter]);
                addr = getZeroPageAddr();
                System.out.println("Storing " + accumulator + " in address " + addr);
                ram[addr] = accumulator;
                System.out.println("Confirming: " + ram[addr] + " now in RAM");
                programCounter++;
                break;
            case 0x86: // STX zero page
                //addr = byteToUnsigned(rom[programCounter]);
                addr = getZeroPageAddr();
                ram[addr] = xRegister;
                programCounter++;
                break;
            case 0x88: // DEY - decrement Y register, implied
                yRegister--;
                setZeroFlag(yRegister);
                setNegativeFlag(yRegister);
                //programCounter++;
                break;
            case 0x8A: // TXA - copy of X register to accumulator and set flags
                accumulator = xRegister;
                //programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0x8C: // STY absolute
                addr = getAbsoluteAddr();
                ram[addr] = yRegister;
                programCounter++;
                break;
            case 0x8D: // STA absolute
                addr = getAbsoluteAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0x8E: // STX absolute
                addr = getAbsoluteAddr();
                ram[addr] = xRegister;
                programCounter++;
                break;
            case 0x90: // BCC - branch if carry clear
                if (getCarryFlag() == 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0x91: // STA - indirect Y
                addr = getIndirectYAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0x94: // STY - zero page X
                addr = getZeroPageXAddr();
                ram[addr] = yRegister;
                programCounter++;
                break;
            case 0x95: // STA - zero page X
                addr = getZeroPageXAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0x96: // STX - zero page Y
                addr = getZeroPageYAddr();
                ram[addr] = xRegister;
                programCounter++;
                break;
            case 0x98: // TYA - copy of Y register to accumulator and set flags
                accumulator = yRegister;
                //programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0x99: // STA - absolute Y
                addr = getAbsoluteYAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0x9A: // TXS - copy of X register to stackPointer and set flags
                stackPointer = xRegister;
                programCounter++;
                break;
            case 0x9D: // STA - absolute X
                addr = getAbsoluteXAddr();
                ram[addr] = accumulator;
                programCounter++;
                break;
            case 0xA0: // LDY - immediate
                yRegister = rom[programCounter];
                programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xA1: // LDA - load accumulator, indirect X
                accumulator = resolveIndirectX(rom[programCounter]);
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xA2: // LDX - immediate
                System.out.println("PC " + programCounter);
                System.out.println("LDX immediate");
                xRegister = rom[programCounter];
                programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xA4: // LDY - zero page
                yRegister = ram[rom[programCounter]];
                programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xA5: // LDA - load accumulator, zero page
                addr = getZeroPageAddr();
                accumulator = ram[addr];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xA6: // LDX - load X register, zero page
                addr = byteToUnsigned(rom[programCounter]);
                xRegister = ram[addr];
                programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xA8: // TAY - copy accumulator to y register and set flags
                yRegister = accumulator;
                //programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xA9: // LDA immediate
                System.out.println("PC: " + programCounter);
                System.out.println("LDA immediate");
                accumulator = rom[programCounter];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xAA: // TAX - copy accumulator to X register and set flags
                xRegister = accumulator;
                //programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xAC:// LDY - absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                yRegister = ram[addr];
                programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xAD:// LDA - absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                accumulator = ram[addr];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xAE:// LDX - absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                xRegister = ram[addr];
                programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xB0: // BCS - branch if carry set
                if (getCarryFlag() != 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0xB1:// LDA - indirect Y
                accumulator = resolveIndirectY(rom[programCounter]);
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xB4: // LDY - zero page, X
                addr = (short)(rom[programCounter] + xRegister);
                yRegister = ram[addr];
                programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xB5: // LDA - zero page, X
                addr = (short)(rom[programCounter] + xRegister);
                accumulator = ram[addr];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xB6: // LDX - zero page, Y
                addr = (short)(rom[programCounter] + yRegister);
                xRegister = ram[addr];
                programCounter++;
                negativeFlag = xRegister & 0x80;
                setZeroFlag(xRegister);
                break;
            case 0xBA: // TSX - copy stack pointer to X register and set flags
                xRegister = stackPointer;
                programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
            case 0xB8: // CLV - clear overflow
                overflowFlag = 0;
                break;
            case 0xB9: // LDA - absolute Y
                addr = resolveAbsoluteY(rom[programCounter++], rom[programCounter]);
                accumulator = ram[addr];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xBC:// LDY - absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                yRegister = ram[addr];
                programCounter++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xBD: // LDA - absolute X
                addr = resolveAbsoluteX(rom[programCounter++], rom[programCounter]);
                accumulator = ram[addr];
                programCounter++;
                setNegativeFlag(accumulator);
                setZeroFlag(accumulator);
                break;
            case 0xBE: // LDX - absolute Y
                addr = resolveAbsoluteY(rom[programCounter++], rom[programCounter]);
                xRegister = ram[addr];
                programCounter++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xC0: // CPY - compare Y register, immediate mode
                compare(yRegister, rom[programCounter]);
                programCounter++;
                break;
            case 0xC1: // CMP - compare Accumulator, indirect X
                compare(accumulator, resolveIndirectX(rom[programCounter]));
                programCounter++;
                break;
            case 0xC4: // CPY - compare Y register, zero page
                compare(yRegister, ram[rom[programCounter]]);
                //programCounter++;
                programCounter++;
                break;
            case 0xC5: // CMP - compare Accumulator, zero page
                compare(accumulator, ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0xC6: // DEC - zero page
                ram[rom[programCounter]]--;
                programCounter++;
                break;
            case 0xC9: // CMP - compare Accumulator, immediate
                compare(accumulator, rom[programCounter]);
                programCounter++;
                break;
            case 0xC8: // INY - increment y Register, implied
                yRegister++;
                setNegativeFlag(yRegister);
                setZeroFlag(yRegister);
                break;
            case 0xCA: // DEX - decrement X register, implied
                xRegister--;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                //programCounter++;
                break;
            case 0xCC: // CPY - compare Y register, absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                programCounter++;
                compare(yRegister, ram[addr]);
                break;
            case 0xCE: // DEC - decrement, absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                programCounter++;
                ram[addr]--;
                break;
            case 0xCD: // CPY - compare Accumulator, absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                programCounter++;
                compare(accumulator, ram[addr]);
                break;
            case 0xD0: // BNE - branch if Not Equal (zeroFlag is clear)
                if (getZeroFlag() == 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0xD1: // CMP - compare Accumulator, indirect Y
                compare(accumulator, resolveIndirectY(rom[programCounter]));
                programCounter++;
                break;
            case 0xD5: // CMP - compare Accumulator, zero page X
                addr = (short)(rom[programCounter] + xRegister);
                compare(accumulator, ram[addr]);
                programCounter++;
                break;
            case 0xD6: // DEC - zero page, X
                addr = (short)(rom[programCounter] + xRegister);
                ram[addr]--;
                programCounter++;
                break;
            case 0xD8: // CLD - clear decimal flag
                decimalFlag = 0;
                break;
            case 0xD9: // CMP - compare Accumulator, absolute Y
                compare(accumulator, resolveAbsoluteY(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0xDD: // CMP - compare Accumulator, absolute X
                compare(accumulator, resolveAbsoluteX(rom[programCounter++],rom[programCounter]));
                programCounter++;
                break;
            case 0xDE: // DEC - decrement, absolute X
                ram[bytesToAddr(rom[programCounter++], rom[programCounter]) + xRegister]--;
                programCounter++;
                break;
            case 0xE0: // CPX - compare X register, immediate mode
                compare(xRegister, rom[programCounter]);
                programCounter++;
                break;
            case 0xE4: // CPX - compare X register, zero page
                compare(xRegister, ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0xE6: // INC - increment memory address, zero page
                ram[rom[programCounter]]++;
                setNegativeFlag(ram[rom[programCounter]]);
                setZeroFlag(ram[rom[programCounter]]);
                programCounter++;
                break;
            case 0xEC: // CPX - compare X register, absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                programCounter++;
                compare(xRegister, ram[addr]);
                break;
            case 0xE8: // INX - increment X register, implied mode
                xRegister++;
                setNegativeFlag(xRegister);
                setZeroFlag(xRegister);
                break;
            case 0xEA: // NOP - no changes other than incrementing program counter
                programCounter++;
                break;
            case 0xEE: // INC - increment memory address, Absolute
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]));
                ram[addr]++;
                setNegativeFlag(ram[addr]);
                setZeroFlag(ram[addr]);
                programCounter++;
                break;
            case 0xF0: // BEQ - branch if equal (zeroFlag set)
                if (getZeroFlag() != 0){
                    programCounter = (short)(programCounter + rom[programCounter]);//byteToUnsigned(rom[programCounter]);
                }else{
                    programCounter++;
                }
                break;
            case 0xF6: // INC - increment memory address, zero page, X
                addr = (short)(rom[programCounter] + xRegister);
                ram[addr]++;
                setNegativeFlag(ram[addr]);
                setZeroFlag(ram[addr]);
                programCounter++;
                break;
            case 0xF8: // SED - set decimal flag
                decimalFlag = 1;
                break;
            case 0xFE: // INC - increment memory address, Absolute X
                addr = (short)(bytesToAddr(rom[programCounter++],rom[programCounter]) + xRegister);
                ram[addr]++;
                setNegativeFlag(ram[addr]);
                setZeroFlag(ram[addr]);
                programCounter++;
                break;
            default:
                System.out.println("invalid instruction");
                System.out.println("PC: " + programCounter);
                System.out.println("Instruction: " + rom[programCounter]);
        }
    }
    
    
    protected byte statusFlagsToByte(){
        byte statusFlags = (byte)0b00110000;
        if (getNegativeFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b10000000);
        }
        if (getOverflowFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b01000000);
        }
        if (getDecimalFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b00001000);
        }
        if (getInterruptFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b00000100);
        }
        if (getZeroFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b00000010);
        }
        if (getCarryFlag() == 1){
            statusFlags = (byte)(statusFlags & 0b00000001);
        }
        return(statusFlags);
    }
    
    private void byteToStatusFlags(byte b){
        negativeFlag = b >> 7;
        overflowFlag = (b & 0b01000000) >> 7;
        decimalFlag = (b & 0b00001000) >> 7;
        interruptFlag = (b & 0b00000100) >> 7;
        zeroFlag = (b & 0b00000010) >> 7;
        carryFlag = (b & 0b00000001) >> 7;
    }
    
    // 1101 0111 1000 0010
    private void pushAddr(short addr){
        byte low = (byte)((addr & 0xFF00) >> 8);
        byte high = (byte)(addr & 0x00FF);
        stackPush(low);
        stackPush(high);
    }
    
    // pushes a byte onto the 6502 stack
    // the stack grows down from 0x01FF to 0x0100
    // The 6502 keeps the stackpointer at the next available element
    // so it has to decrement the pointer AFTER pushing and BEFORE pulling
    private void stackPush(byte b){
        System.out.println("Pushing " + b + " onto the stack");
        //stackMemory[stackPointer] = b;
        ram[STACK_START+stackPointer] = b;
        stackPointer--;
    }
    
    private byte stackPop(){
        stackPointer++;
        byte b = ram[STACK_START+stackPointer];
        //byte b = stackMemory[stackPointer];
        return(b);
    }
    
    private static String printHex(byte num){
        return(String.format("0x%02X",num));
    }
    
    private static String printHex(short num){
        return(String.format("0x%04X",num));
    }
    
    public static void main(String[] args) {
        //System.out.println("OpCode 0xA9 = " + codes.getInstruction(0xA9).getInstruction());
    }

    /**
     * @return the zeroFlag
     */
    public int getZeroFlag() {
        return zeroFlag;
    }

    /**
     * @return the negativeFlag
     */
    public int getNegativeFlag() {
        return negativeFlag;
    }

    /**
     * @return the overflowFlag
     */
    public int getOverflowFlag() {
        return overflowFlag;
    }

    /**
     * @return the breakFlag
     */
    public int getBreakFlag() {
        return breakFlag;
    }

    /**
     * @return the decimalFlag
     */
    public int getDecimalFlag() {
        return decimalFlag;
    }

    /**
     * @return the interruptFlag
     */
    public int getInterruptFlag() {
        return interruptFlag;
    }

    /**
     * @return the carryFlag
     */
    public int getCarryFlag() {
        return carryFlag;
    }
    
}
