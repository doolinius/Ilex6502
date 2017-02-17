/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilex6502;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;

/**
 *
 * @author jdoolin
 */
public enum EmuTheme {
    //AMSTRAD(4,"AmstradCPC",24f,new Color(255,251,0), new Color(1,18,118)),//not working
    AMSTRADPC512("Amstrad PC512","AmstradPC512",24f,Color.WHITE,Color.BLACK),
    APPLE2("Apple ][","Apple2",24f,Color.GREEN,Color.BLACK),
    ATARI_CLASSIC("Atari 400/800","AtariClassic",24f,new Color(125,182,222),new Color(0,93,142)),//not working
    BBC_MICRO("BBC Micro","BBCMicro",28f,Color.WHITE,Color.BLACK),
    C64("Commodore 64","C64",24f,new Color(139,202,209),new Color(80,72,178)),
    IBM_PC("IBM PC","IBM_PC",24f,Color.WHITE,Color.BLACK),
    MSX("MSX","MSX",28f,Color.WHITE,Color.BLUE),
    TANDY100("Tandy 1000","Tandy1000",24f,Color.WHITE,Color.BLUE),
    TRS80("TRS-80","TRS-80",28f,new Color(245,245,245), Color.BLACK),
    //ZX_SPECTRUM("ZX Spectrum","ZXSpectrum2",24f,Color.BLACK,Color.GRAY),
    ;
    private final String name;
    private Font systemFont;
    private final Color fgColor;
    private final Color bgColor;
    private final float fontSize;
    
    EmuTheme(String name, String fontName, float fsize, Color fg, Color bg){
        systemFont = null;
        try{
            systemFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/" +fontName+".ttf")).deriveFont(fsize);
        }catch(Exception e){
            System.out.println("SHIT: " + e.getMessage());
        }
        this.name = name;
        fgColor = fg;
        bgColor = bg;
        fontSize = fsize;
    }

    /**
     * @return the systemFont
     */
    public Font getSystemFont() {
        return systemFont;
    }


    /**
     * @return the fgColor
     */
    public Color getFgColor() {
        return fgColor;
    }

   

    /**
     * @return the bgColor
     */
    public Color getBgColor() {
        return bgColor;
    }

    

    /**
     * @return the fontSize
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
