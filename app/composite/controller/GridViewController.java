package app.composite.controller;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import app.composite.component.GridView;
import app.util.MathUtil;

public class GridViewController {

    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    static LinkedHashMap<String, Object> componentsInfo;
    static LinkedHashMap<String, Object> gridInfo;
    static JFrame mainframe;

    public static void main(String[] args) {

        gridInfo = new LinkedHashMap<String, Object>();
        gridInfo.put("Width", viewPortWidth);
        gridInfo.put("Height", viewPortHeight);
        gridInfo.put("UnitSpacer", 50);

        GridView.compose(gridInfo);
        System.out.println("end");

    }

}
