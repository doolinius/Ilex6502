
package ilex6502.asmeditorview;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;


public class ASMViewFactory extends Object implements ViewFactory {

    /**
     * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
     */
    public View create(Element element) {

        return new ASMView(element);
    }

}
