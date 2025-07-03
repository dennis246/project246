package app.composite.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import app.util.MathUtil;

public class GridView extends JPanel {

    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    static LinkedHashMap<String, Object> componentsInfo;
    static LinkedHashMap<String, Object> gridInfo;
    static JFrame mainframe;

    static Font primaryHeaderFont = new Font("Calibri", Font.BOLD, 18);
    static Font primaryContentFont = new Font("Calibri", Font.PLAIN, 12);
    static Font secondaryContentFont = new Font("Calibri", Font.PLAIN, 14);
    static Font tertiaryContentFont = new Font("MonoSans", Font.PLAIN, 14);
    static Font tertiarySubContentFont = new Font("MonoSans", Font.PLAIN, 12);

    BufferedImage bufferedImage;

    public static int compose(LinkedHashMap<String, Object> graphInfo) {

        try {
            gridInfo = new LinkedHashMap<>();
            gridInfo.putAll(graphInfo);
            buildView();
            // updateViewLooper();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static void buildView() {

        componentsInfo = new LinkedHashMap<String, Object>();
        mainframe = new JFrame(GraphSheet.class.getSimpleName());
        mainframe.setBackground(Color.black);

        var width = (int) gridInfo.get("Width"); // (int) gridInfo.get("width");
        var height = (int) gridInfo.get("Height");// (int) gridInfo.get("height");
        var unitspacer = (int) gridInfo.get("UnitSpacer");

        mainframe.setBounds(0, 0, width, height);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // gridInfo = new LinkedHashMap<String, Object>(); checked before
        gridInfo.put("width", viewPortWidth);
        gridInfo.put("height", viewPortHeight);
        gridInfo.put("unitspacer", 20);

        var graphSheet = new GraphSheet(gridInfo);
        mainframe.add(graphSheet);
        registerComponent(graphSheet);
        mainframe.setVisible(true);

    }

    public static void updateViewLooper() {
        try {

            for (;;) {
                updateView();
                Thread.sleep(10);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateView() {

        componentsInfo.size();
        if (componentsInfo.get("GridView") != null) {
            var last = (Component) componentsInfo.get("GridView");
            componentsInfo.remove(componentsInfo.get("GridView"));
            mainframe.remove(last);
        }

        var gv1 = new ScanLine(gridInfo);
        mainframe.add(gv1);
        registerComponent(gv1);
        mainframe.validate();

    }

    private static void registerComponent(Component currentComponent) {
        var intotal = componentsInfo.keySet().size();
        ++intotal;
        // currentComponent.setName("GridView_"+intotal);
        currentComponent.setName(GraphSheet.class.getSimpleName());
        componentsInfo.put(currentComponent.getName(), currentComponent);

    }

    public GridView(LinkedHashMap<String, Object> gridInfo) {


    }

}
