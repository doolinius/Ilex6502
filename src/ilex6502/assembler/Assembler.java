/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502.assembler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
/**
/**
 *
 * @author jdoolin
 */
public class Assembler {
    
    private final int COMMENT = 1;
    private final int LABEL = 2;
    private final int INSTRUCTION = 3;
    private final int INVALID_LINE = 4;
    private final int VARIABLE = 5;

    private short lc = 0; // line counter
    private short lineNum = 0;
    private HashMap<String, Symbol> symbolTable = new HashMap<String, Symbol>();
    private Instructions instructions = new Instructions();
    private ArrayList<SourceLine> intermediateSourceLines = new ArrayList<SourceLine>();
    
    private List<String> asmDirectives = Arrays.asList("ORG");

    /*
    public void main(String[] args) throws IOException {
        //OpCodes instructions = new Instructions();
        String filename = "fib.asm";
        try{
            //assemble(filename);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        /*
        try{
            String c = instructions.getOpCode("BNE",AddressMode.RELATIVE);
            int bytes = instructions.instructionSize("JMP","$F000");
            System.out.println("Instruction size: " + bytes);
            printSymTab();
            printIntermediate();
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
         * 
         */
        /*
        try {
            FileOutputStream out = new FileOutputStream("a.out");
            out.write(c.byteValue());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
    }
    */

    public Assembler() {
    }
    
    // Just strip any comments from any source line
    // O(2) => O(1)
    public String stripComments(String str){
        if (str.contains(";")){
            return(str.substring(0,str.indexOf(';')));
        }else{
            return(str);
        }
    }
    
    // uses regex to determine if entire line is a comment
    public boolean isCommentLine(String line){
        if(line.matches("^\\w*;.*")){
            System.out.println("COMMENT?");
        }
        return(line.matches("^\\w*;.*"));
    }
    
    // uses regex to determine if any source line is a variable definition
    public boolean isVariableLine(String line){
        return(line.matches("^[a-zA-Z]\\w+(\\s+)?=(\\s+)?#?(\\d{1,4}|\\$(\\d|[a-fA-F])+|%(0|1){8})\\s*$"));
    }
    
    // uses regex to determine if any source a line is a label
    public boolean isLabelLine(String line){
        return(line.matches("^[a-zA-Z]\\w+:\\s*$"));
    }
    
    // uses regex to determine if a source line is an instruction
    public boolean isInstructionLine(String line){
        return(line.matches("^\\s+[a-zA-Z]{3}.*"));
    }
    
    // uses regex to determine if a source line is an ASM directive
    public boolean isDirective(String line){
        return(asmDirectives.contains(line.trim().split("\\s+")[0]));
    }
    
    // checks to see if a label is a valid identifier
    public boolean isValidLabel(String line){
        // must take into account Accumulator addressing mode
        // and not confuse it with a label
        return(!(line.equalsIgnoreCase("a")) && line.matches("^[a-zA-Z]\\w*\\s*$"));
    }
    
    public String hasValidLabel(String line){
        Pattern p = Pattern.compile("([a-zA-Z]\\w*).*");
        Matcher m = p.matcher(line);
        // must take into account Accumulator addressing mode
        // and not confuse it with a label
        if (m.matches()){
            if (m.group(1).equalsIgnoreCase("a")){// using accumulator mode
                return(null); // operand has no label
            }else{
                //System.out.println("----hasValidLabel matched " + m.group(1));
                return(m.group(1));
            }
        }else{
            return(null);
        }
    }
    
    // Adds a new variable to the symbol table if it doesn't exist
    // changes the type to MTDF (Multiply defined) if it does
    // Requires the identifier (name), numeric value, symbol type and 
    // line number
    public void addVariable(String name, String value, SymType type, int lineNumber){
        Symbol s = symbolTable.get(name);
        if (s == null){
            s = new Symbol(name,value,type,lineNumber);
            symbolTable.put(name,s);
        }else{
            s.setType(SymType.MTDF);
        }
    }
    
    // Adds a new source line label to the symbol table if it doesn't exist
    // changes the type to MTDF (multiply defined) if it does
    public void addLabel(String name, int loc, SymType type,int lineNumber, int size){
        Symbol s = symbolTable.get(name);
        if (s == null){
            System.out.println("Label " + name + " loc is " + loc);
            s = new Symbol(name,loc,type,lineNumber, size);
            symbolTable.put(name,s);
        }else{
            s.setType(SymType.MTDF);
        }
    }
    
    // Checks a source line and returns the line type
    // Returns INVALID_LINE if it is not a valid ASM source line
    public int lineType(String line){
        if (isCommentLine(line)){
            return(COMMENT);
        }else if (isLabelLine(line)){
            return(LABEL);
        }else if (isInstructionLine(line)){
            return(INSTRUCTION);
        }else if (isVariableLine(line)){
            return(VARIABLE);
        }else{
            return(INVALID_LINE);
        }
    }
    
    // Pass 1 of 2 for the assembly of the code
    public void assembleP1(File file) throws Exception{
        try{ 
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            // Loop through ASM file
            while ((line = br.readLine()) != null) {
                lineNum += 1;
                //System.out.println("Line #" + lineNum + ": " + line);
                // remove any comments
                line = stripComments(line);
                // Check to see if it's an empty line
                if (line.trim().length() > 0){
                    // if it's not an empty line, see what type of line it is
                    switch(lineType(line)){
                        case VARIABLE:
                            // If it's a variable we can just save the value in the symtab
                            System.out.println("Pass 1: Line " + lineNum + ": Variable");;
                            String symbol = line.split("=")[0].trim();
                            String val = line.split("=")[1].trim();
                            // O(1)
                            addVariable(symbol,val,SymType.VARIABLE,lineNum);
                            break;
                        case LABEL:
                            // If it's a code Label (for subroutines or branches)
                            // In pass 1 it's complicated.  Check out p1ProcessLabel
                            System.out.println("Pass 1: Line " + lineNum + ": Label");
                            p1ProcessLabel(line);
                            break;
                        case INSTRUCTION:
                            if (isDirective(line)){
                                System.out.println("Pass 1 Line " + lineNum + ": Directive");
                                processDirective(line);
                            }else{
                                System.out.println("Pass 1: Line " + lineNum + ": Instruction");
                                int size = p1ProcessInstruction(line);
                                lc += size;
                            }
                            break;
                        case INVALID_LINE:
                            throw new Exception("Pass1 : Invalid line");
                            //System.err.println("Pass 1: Invalid line");
                            //break;
                    }
                // if it's an empty line or was just a comment
                }else{
                    System.out.println("Pass 1: Line " + lineNum + " is a comment or empty");
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());;
        }
        //boolean noErrorsDetected = true;
        for (String key: symbolTable.keySet()){
            Symbol s = symbolTable.get(key);
            if(s.getType() == SymType.UNDEFINED){
                //noErrorsDetected = false;
                //System.err.println("ERROR: Undefined symbol: " + s.getName());
                throw new Exception("ERROR: Undefined symbol: " + s.getName());
            }else if (s.getType() == SymType.MTDF){
                //noErrorsDetected = false;
                //System.err.println("ERROR: Multiply defined label: " + s.getName());
                throw new Exception("ERROR: Multiply defined label: " + s.getName());
            }
        }
        //return(noErrorsDetected);
    }
    
    // Converts a string of hexadecimal values into the corresponding
    // byte array
    public byte[] hexStringToByteArray(String s) {
    int len = s.length(); // Java uses 16 bit Unicode for Strings, so each character is 2 bytes
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}
    
    // Pass 2 of 2 for the assembly of the code
    // O(n)
    public byte[] assembleP2() throws Exception{
        StringBuilder outStr = new StringBuilder();
        for (SourceLine line: intermediateSourceLines){
            System.out.println("Pass2, assembling instruction " + line.getOperator() + " " + line.getOperand() + " " +  line.getMode().toString());
            outStr.append(p2ProcessInstruction(line));
        }
        //System.out.println(outStr.toString());
        byte[] output = hexStringToByteArray(outStr.toString());
        
        return(output);
    }
    
    // Prints the current symbol table
    public void printSymTab(){
        for (String key: symbolTable.keySet()){
            System.out.println("Symbol: " + key);
            System.out.println("    l=> " + symbolTable.get(key).getLocation());
            System.out.println("    v=> "+ symbolTable.get(key).getValue());
        }
    }
    
    // Prints all intermediate source lines
    public void printIntermediate(){
        System.out.println("INTERMEDIATE DATA");
        for (SourceLine line: intermediateSourceLines){
            System.out.println(line.toString());
        }
        System.out.println("END INTERMEDIATE DATA");
    }
    
    // Updates the value of a label if it was previously undefined
    // This is used in Pass 2 to resolve uses of a label before
    // the label is defined
    public void updateLabel(String label,int loc){
        Symbol s = symbolTable.get(label);
        if (s.getType() == SymType.UNDEFINED){
            s.setLocation(loc);
            s.setType(SymType.LABEL);
        }else{
            s.setType(SymType.MTDF);
        }
    }
    
    public void processDirective(String line) throws Exception{
        String pseudoOp = line.trim().split("\\s+")[0];
        if (pseudoOp.equals("ORG")){
            processOrg(line);
        }else{
            throw new Exception("Unsupported directive");
        }
    }
    
    public void processOrg(String line) throws Exception{
        String operand = line.trim().split("\\s+")[1];
        int n = Integer.parseInt(num2hex(operand,3),16);// use 3 to treat as 2 byte operand
        if (lc <= n){
            if (operand.matches(AddressMode.ABSOLUTE.pattern())){
                lc = (short)n;
            }else{
                throw new Exception("Invalid Operand for ORG directive");
            }
        }else{
            throw new Exception("ORG directive cannot set LC to lower value");
        }
    }
    
    public void p1ProcessLabel(String line){
        // Trim off the colon and only save the label itself
        line = line.replace(":","").trim();
        //System.out.println("Label found: " + line);
        if (isValidLabel(line)){
            // if the label doesn't exist in the symbol table, add it
            if(!symbolTable.containsKey(line)){
                addLabel(line,lc,SymType.LABEL,lineNum, 3);
                //System.out.println("added label");
            //otherwise, update the symbol table if possible
            }else{
                updateLabel(line,lc);
            }
        }else{
            System.err.println("INVALID LABEL??? " + line);
        }
    }
    
    // returns hex string of instruction
    public String p2ProcessInstruction(SourceLine line) throws Exception{
        // get opcode
        // get operand
        String operand = line.getOperand();
        String oc = instructions.getOpCode(line.getOperator(), line.getMode());
        if (isValidLabel(line.getOperand())){
            operand = symbolTable.get(line.getOperand()).getValue();
            System.out.println("P2 Process: got instruction operand " + operand);
            if (line.getMode() == AddressMode.RELATIVE){
                int opInt = Integer.parseInt(operand,16);
                int diff = opInt - line.getLc();
                operand = String.format("%02x",Integer.toHexString(diff & 0x000000FF));
            }else if(line.getMode() == AddressMode.ABSOLUTE){
                int memLocation = symbolTable.get(line.getOperand()).getLocation();
                operand = "$" + String.format("%04x",memLocation);
                System.out.println("Operand NOW: " + operand);
                operand = num2hex(operand, line.getMode().size());
            }else{
                operand = num2hex(operand,line.getMode().size());
            }
        }else{
            if (!operand.equals(""))
                operand = num2hex(operand,line.getMode().size());
        }
        return(oc + operand);
    }
    
    // Converts numeric data to hexidecimal string data
    public String num2hex(String operand, int size) {
        String hex;
        String num = operand.charAt(0) == '#' ? operand.substring(1) :  operand;
        String format = (size == 3) ? "%04x" : "%02x";
        switch (num.charAt(0)) {
            case '$':
                //System.out.println("base 16 y'all");
                hex = num.substring(1);
                break;
            case '%':
                //System.out.println("binary, bitches");
                String op = num.substring(1);
                int decimal = Integer.parseInt(op, 2);
                hex = String.format(format,decimal);
                break;
            default:
                //System.out.println("boring decimal");
                hex = String.format(format,Integer.parseInt(num));
                //System.out.println("gots me a hex: " + hex);
                break;
        }
        //System.out.println("Num2Hex: " + operand + " => " + hex);
        return(hex);
    }
    
    // Adds a new source line to the Intermediate file 
    public void addSourceLine(String operator, String operand, AddressMode mode, int lc){
        //System.out.println("adding source line for " + operator + "," + operand + "," + mode.toString());
        if (mode == AddressMode.ACCUMULATOR){
            operand = "";
        }
        intermediateSourceLines.add(new SourceLine(operator,operand,mode,lc));
    }
    
    public int p1ProcessInstruction(String line) throws Exception {
        line = line.trim();
        String instruction[] = line.split("\\s");
        String operator = instruction[0];

        Instruction op;
        //try {
            op = instructions.getOp(operator);
            String operand;

            if (instruction.length > 1) {
                operand = instruction[1];
            } else {
                operand = "";
            }
            String label = hasValidLabel(operand);
            if (label != null) {// if there is a label in the instruction operand
                //System.out.println("Instruction " + operator + " has a label " + label);
                Symbol s = symbolTable.get(label);
                if (s == null) {// if the label is NOT in the symbol table
                    //System.out.println("..... aaaaand it's NOT in the symbol table!!");
                    if (instructions.isJumpOrBranch(operator)){// only Jump or Branch can have future label
                        AddressMode m = instructions.isValidJump(operator,operand);
                        if (m != null){// i.e., if it's a valid Jump
                            addLabel(operand,lc,SymType.UNDEFINED,lineNum,instructions.getOp(operator).getSize());
                            addSourceLine(operator,operand,m,lc);
                            return(instructions.getOp(operator).getSize());
                        }else if (instructions.isValidBranch(operator, operand)){
                            addLabel(operand,lc,SymType.UNDEFINED,lineNum,instructions.getOp(operator).getSize());
                            addSourceLine(operator,operand,AddressMode.RELATIVE,lc);
                            return(instructions.getOp(operator).getSize());
                        }else{
                            throw new Exception("Line  " + lineNum + ": " + "invalid addressing mode for instruction " + operator);
                        }
                    }else{// if it's an invalid FUTURE label
                        throw new Exception("Line " + lineNum + ": " + "undefined symbol " + operand);
                    }
                } else { // if the label is FOUND in the symbol table
                    //System.out.println("label exists in symTab: " + s.getValue());
                    String newOperand = operand.replace(label, s.getValue());
                    addSourceLine(operator, s.getValue(), instructions.getMode(operator, newOperand),lc);
                    return (instructions.instructionSize(operator, newOperand));
                }
            } else { // if the operand doesn't contain a label
                //System.out.println("Instruction " + operator + " has NO label");
                Pattern p = Pattern.compile("(#?(\\d{1,5}|\\$(\\d|[a-fA-F]){2,4}|%(0|1){8,16}))");
                Matcher m = p.matcher(operand);
                if (m.matches()) {
                    addSourceLine(operator, m.group(1), instructions.getMode(operator, operand),lc);
                } else {
                    addSourceLine(operator, operand, instructions.getMode(operator, operand),lc);
                }
                return (instructions.instructionSize(operator, operand));
            }
       // } catch (Exception e) {
       //     System.err.println("Line " + lineNum + ":" + e.getMessage());
       //     return(0);
       // }

    }
    
    private void reset(){
        symbolTable.clear();
        intermediateSourceLines.clear();
        lineNum = 0;
    }
    
    // Takes a .asm file parameter and runs both passes of the assembler
    public File assemble(File file) throws Exception{
        byte[] binaryData;
        reset();
        System.out.println("Beginning assembly of file " + file.getName());
        System.out.println("Starting Pass 1");
        try{
            assembleP1(file);
                //}){// if pass 1 succeeds
            System.out.println("Starting Pass 2");
            //printSymTab();
            binaryData = assembleP2(); // run pass 2
            String basename = file.getName().substring(0, file.getName().lastIndexOf('.'));
            String path = file.getParent();
            File binFile = new File(path+"/"+basename+".bin");
            FileOutputStream o = new FileOutputStream(binFile);
            //FileOutputStream o = new FileOutputStream(path + "/" + basename + ".bin");
            BufferedOutputStream out = new BufferedOutputStream(o);
            out.write(binaryData);
            out.close();
            System.out.println("SUCCESSFULY ASSEMBLED " + basename + ".bin");
            return(binFile);
        }catch(Exception e){
            System.err.println("Assembly FAILED: " + e.getMessage());
            return(null);
        }
    }

        
    /**
     * @return the lc
     */
    public short getLc() {
        return lc;
    }

    /**
     * @param lc the lc to set
     */
    public void setLc(short lc) {
        this.lc = lc;
    }

    /**
     * @return the lineNum
     */
    public short getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum the lineNum to set
     */
    public void setLineNum(short lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return the symbolTable
     */
    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    /**
     * @param symbolTable the symbolTable to set
     */
    public void setSymbolTable(HashMap<String, Symbol> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
