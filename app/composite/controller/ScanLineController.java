package app.composite.controller;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import app.composite.component.GraphSheet;
import app.composite.component.ScanLine; 
import app.util.MathUtil;

public class ScanLineController {
    
    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    static LinkedHashMap<String, Object> componentsInfo;
    static LinkedHashMap<String, Object> gridInfo;
    static JFrame mainframe;

    public static void main(String[] args) {

        int initInd = 0;
        ScanLine.compose();

    }

}
