/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import ilex6502.emulator.Emulator;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author jdoolin
 */
public class MemoryWindow extends JFrame{
    private int currentPage;
    private JScrollPane scrollPane;
    private JTable memoryContents;
    private JTextField currentPageNum;
    private JButton previousPage, nextPage, closeButton;
    private Emulator emu;
    
    public MemoryWindow(String title, Emulator e){
        super(title);
        this.emu = e;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo (null);
        
        ButtonListener l = new ButtonListener();
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        currentPage = 0;
        currentPageNum = new JTextField("" + currentPage, 3);
        currentPageNum.addActionListener(l);
        previousPage = new JButton("<");
        previousPage.addActionListener(l);
        previousPage.setEnabled(false);
        nextPage = new JButton(">");
        nextPage.addActionListener(l);
        navPanel.add(previousPage);
        navPanel.add(new JLabel("Page "));
        navPanel.add(currentPageNum);
        navPanel.add(nextPage);
        
        memoryContents = new JTable(new MyTableModel());
        memoryContents.getColumnModel().getColumn(0).setHeaderValue("Memory Location");
        memoryContents.getColumnModel().getColumn(1).setHeaderValue("Contents");
        memoryContents.getTableHeader().resizeAndRepaint();
        setMemTable();
        scrollPane = new JScrollPane(memoryContents);
        scrollPane.setPreferredSize(new Dimension(180, 400));
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(l);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(navPanel);
        panel.add(scrollPane);
        panel.add(closeButton);
        
        add(panel);
        pack();
    }
    
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = { "Memory Location", "Contents"};

        private Object[][] data = new String[256][2];

        public int getColumnCount() {
          return columnNames.length;
        }

        public int getRowCount() {
          return data.length;
        }

        public String getColumnName(int col) {
          return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
          return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/ editor for
         * each cell. If we didn't implement this method, then the last column
         * would contain text ("true"/"false"), rather than a check box.
         */
        public Class getColumnClass(int c) {
          return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's data can
         * change.
         */
        public void setValueAt(Object value, int row, int col) {

          data[row][col] = value;
          fireTableCellUpdated(row, col);
        }
    }
    
    public void setMemTable(){
        TableModel m = memoryContents.getModel();
        short p = (byte)currentPage;
        short startAddr = (short)(p << 8);
        for (short i=startAddr; i<startAddr+256; i++){
            String addr = String.format("0x%04x", i);
            String value = String.format("0x%02x", emu.readMemory(i));
            m.setValueAt(addr, i-startAddr, 0);
            m.setValueAt(value, i-startAddr, 1);
        }
    }
    
    public class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == previousPage){
                currentPage = Math.max(0, currentPage-1);
                currentPageNum.setText("" + currentPage);
                if (currentPage == 0){
                    previousPage.setEnabled(false);
                }
                nextPage.setEnabled(true);
                // setMemoryContents Data
                setMemTable();
                System.out.println("New Page: " + currentPage);
            }else if (source == nextPage){
                currentPage = Math.min(255, currentPage+1);
                currentPageNum.setText("" + currentPage);
                if (currentPage == 255){
                    nextPage.setEnabled(false);
                }
                previousPage.setEnabled(true);
                // setMemory Contents Data
                setMemTable();
                System.out.println("New Page: " + currentPage);
            }else if (source == closeButton){
                setVisible(false);
            }else{
                int oldPage = currentPage;
                String pageNum = currentPageNum.getText();
                int newPage = Integer.parseInt(pageNum);
                if (newPage >= 256 || newPage < 0){
                    currentPageNum.setText(""+oldPage);
                }else{
                    currentPage = newPage;
                    setMemTable();
                }
            }
        }
        
    }
}
