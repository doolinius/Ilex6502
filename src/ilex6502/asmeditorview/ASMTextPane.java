/*
    ASMTextPane - A customized text pane for editing 6502 Assembly
    allows for syntax highlighting and indentation as well as
    logging
*/
package ilex6502.asmeditorview;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class ASMTextPane extends JTextPane {

    //private static final long serialVersionUID = 6270183148379328084L;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ASMTextPane() {
        
        // Set editor kit
        this.setEditorKitForContentType("text/asm", new ASMEditorKit());
        this.setContentType("text/asm");
        
        addKeyListener(new IndentKeyListener());
    }
    
	private class IndentKeyListener implements KeyListener {

		private boolean enterFlag;
		private final Character NEW_LINE = '\n';

		public void keyPressed(KeyEvent event) {
			enterFlag = false;
			if ((event.getKeyCode() == KeyEvent.VK_ENTER)
					&& (event.getModifiers() == 0)) {
				if (getSelectionStart() == getSelectionEnd()) {
					enterFlag = true;
					event.consume();
				}
			}
		}

		public void keyReleased(KeyEvent event) {
			if ((event.getKeyCode() == KeyEvent.VK_ENTER)
					&& (event.getModifiers() == 0)) {
				if (enterFlag) {
					event.consume();

					int start, end;
					String text = getText();

					int caretPosition = getCaretPosition();
					try {
						if (text.charAt(caretPosition) == NEW_LINE) {
							caretPosition--;
						}
					} catch (IndexOutOfBoundsException e) {
					}

					start = text.lastIndexOf(NEW_LINE, caretPosition) + 1;
					end = start;
					try {
						if (text.charAt(start) != NEW_LINE) {
							while ((end < text.length())
									&& (Character
											.isWhitespace(text.charAt(end)))
									&& (text.charAt(end) != NEW_LINE)) {
								end++;
							}
							if (end > start) {
								getDocument()
										.insertString(
												getCaretPosition(),
												NEW_LINE
														+ text.substring(start,
																end), null);
							} else {
								getDocument().insertString(getCaretPosition(),
										NEW_LINE.toString(), null);
							}
						} else {
							getDocument().insertString(getCaretPosition(),
									NEW_LINE.toString(), null);
						}
					} catch (IndexOutOfBoundsException e) {
						try {
							getDocument().insertString(getCaretPosition(),
									NEW_LINE.toString(), null);
						} catch (BadLocationException e1) {
							logger.log(Level.WARNING, e1.toString());
						}
					} catch (BadLocationException e) {
						logger.log(Level.WARNING, e.toString());
					}
				}
			}
		}

		public void keyTyped(KeyEvent e) {
		}
	}
    
}
