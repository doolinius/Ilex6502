
package ilex6502.asmeditorview;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;


/**
 * @author kees
 * @date 12-jan-2006
 *
 */
public class ASMEditorKit extends StyledEditorKit {

    //private static final long serialVersionUID = 2969169649596107757L;
    private ViewFactory asmViewFactory;

    public ASMEditorKit() {
        asmViewFactory = new ASMViewFactory();
    }
    
    @Override
    public ViewFactory getViewFactory() {
        return asmViewFactory;
    }

    @Override
    public String getContentType() {
        return "text/asm";
    }

    
}
