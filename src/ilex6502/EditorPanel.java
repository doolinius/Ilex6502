/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import ilex6502.assembler.Assembler;
import ilex6502.asmeditorview.ASMTextPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jdoolin
 */
public class EditorPanel extends JPanel implements ActionListener{
    private JPanel buttonPanel;
    private JTextPane editorPane;
    private JTextPane console;
    private Assembler assembler;
    private String filename;
    private boolean fileChanged;
    private JFileChooser fc;
    private File sourceFile;
    private File binaryFile;
    
    public EditorPanel(){
        filename = null;
        binaryFile = null;
        assembler = new Assembler();
        setLayout(new BorderLayout());
        
        TitledBorder title = BorderFactory.createTitledBorder("Editor/Console");
        setBorder(title);
        editorPane = new ASMTextPane();
        Font f = new Font(Font.MONOSPACED, Font.PLAIN, 18);
        editorPane.setFont(f);
        editorPane.setPreferredSize(new Dimension(565,255));
        //editorPane.setMaximumSize(new Dimension800,600));
        JScrollPane sp = new JScrollPane(editorPane);
        TextLineNumber tln = new TextLineNumber(editorPane);
        sp.setRowHeaderView( tln );
        
        editorPane.getDocument().addDocumentListener(new EditorListener());
        
        console = new JTextPane();
        console.setEditable(false);
        
        MessageConsole mConsole = new MessageConsole(console);
        mConsole.redirectOut();
        mConsole.redirectErr(Color.RED, null);
        mConsole.setMessageLines(1000);
        
        JScrollPane consoleScrollPane = new JScrollPane(console);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sp,consoleScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        
        add(splitPane,BorderLayout.LINE_START);
        
        setPreferredSize(new Dimension(640,255));
    }

    /**
     * @return the sourceFile
     */
    public File getSourceFile() {
        return sourceFile;
    }

    /**
     * @return the binaryFile
     */
    public File getBinaryFile() {
        return binaryFile;
    }
    
    private class EditorListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            //System.out.println("insert");
            fileChanged = true;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            //System.out.println("remove");
            fileChanged = true;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //System.out.println("Document changed");
            fileChanged = true;
        }
        
    }
    
    public void closeFile(){
        filename = null;
        sourceFile = null;
        editorPane.setText("");
    }
    
    public void openFile(){
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Assembly source files", "s", "asm"));
        int returnVal = fc.showOpenDialog(EditorPanel.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            sourceFile = fc.getSelectedFile();
            filename = getSourceFile().getName();
            try{
                String line = null;
                StringBuilder sb = new StringBuilder();
                FileReader fr = new FileReader(getSourceFile());
                BufferedReader br = new BufferedReader(fr);
                while((line = br.readLine()) != null){
                    sb.append(line+"\n");
                }
                editorPane.setText(sb.toString());
                fileChanged = false;
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void newFile(){
        
    }
    
    public void writeFile(){
        String data = editorPane.getText();
        try{
            FileOutputStream out = new FileOutputStream(getSourceFile());
            out.write(data.getBytes());
        }catch(Exception e){
            System.out.println("Couldn't write to file");
        }
    }
    
    public void saveFile(){
        if (filename == null) {
            fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Assembly source files", "s", "asm"));
            int returnVal = fc.showSaveDialog(EditorPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                sourceFile = fc.getSelectedFile();
                filename = getSourceFile().getName();
                writeFile();
                fileChanged = false;
            }
        } else {
            fileChanged = false;
            writeFile();
        }

    }
    
    public void assembleFile(){
        try{
            binaryFile = assembler.assemble(getSourceFile());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")){
            saveFile();
        } else if (e.getActionCommand().equals("new")){
            if (fileChanged){
                Object[] options = {"Save","Discard Changes","Cancel"};
                int n = JOptionPane.showOptionDialog(this, "File has been changed.  Do you wish to save before closing?","Unsaved Changs", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
                switch(n){
                    case 0:
                        saveFile();
                        closeFile();
                        newFile();
                        break;
                    case 1:
                        closeFile();
                        newFile();
                        break;
                    default:
                        break;
                }
            }else{
                if(!(filename == null)){
                    closeFile();
                }
                newFile();
            }
        } else if (e.getActionCommand().equals("open")){
            if (fileChanged){
                Object[] options = {"Save","Discard Changes","Cancel"};
                int n = JOptionPane.showOptionDialog(this, "File has been changed.  Do you wish to save before closing?","Unsaved Changs", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
                switch(n){
                    case 0:
                        saveFile();
                        closeFile();
                        openFile();
                        break;
                    case 1:
                        closeFile();
                        openFile();
                        break;
                    default:
                        break;
                }
            }else{
                openFile();
            }
        } else if (e.getActionCommand().equals("assemble")){
            assembleFile();
        }
    }
    
}
