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

public class ScanLine extends JPanel {

    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    static LinkedHashMap<String, Object> componentsInfo;
    static LinkedHashMap<String, Object> gridInfo;
    static JFrame mainframe;

    static Font primaryHeaderFont = new Font("Calibri", Font.BOLD, 18);
    static Font primaryContentFont = new Font("Calibri", Font.PLAIN, 12);
    static Font secondaryContentFont = new Font("Calibri", Font.PLAIN, 14);
    static Font tertiaryContentFont = new Font("MonoSans", Font.PLAIN, 14);

    BufferedImage bufferedImage;

    public static int compose() {

        try {
            buildView();
            updateViewLooper();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static void buildView() {

        componentsInfo = new LinkedHashMap<String, Object>();
        mainframe = new JFrame("Scanline");
        mainframe.setBackground(Color.black);
        mainframe.setBounds(100, 200, 600, 500);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gridInfo = new LinkedHashMap<String, Object>();
        gridInfo.put("width", viewPortWidth);
        gridInfo.put("height", viewPortHeight);
        gridInfo.put("unitspacer", 20);
        gridInfo.put("scanlineDir", "LTR");

        gridInfo.put("x1", 0);
        gridInfo.put("y1", 0);
        gridInfo.put("x2", 0);
        gridInfo.put("y2", viewPortHeight);
        // int x1 = 0, y1 = 0, x2 = 0, y2 = height;

        var scanLine = new ScanLine(gridInfo);
        mainframe.add(scanLine);
        registerComponent(scanLine);
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
        currentComponent.setName(ScanLine.class.getSimpleName());
        componentsInfo.put(currentComponent.getName(), currentComponent);

    }

    public ScanLine(LinkedHashMap<String, Object> gridInfo) {

        try {

            var width = viewPortWidth; // (int) gridInfo.get("width");
            var height = viewPortHeight;// (int) gridInfo.get("height");
            var unitspacer = (int) gridInfo.get("unitspacer");

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.setColor(Color.GREEN);
            g.setFont(tertiaryContentFont);

            var x1 = (int) gridInfo.get("x1");
            var y1 = (int) gridInfo.get("y1");
            var x2 = (int) gridInfo.get("x2");
            var y2 = (int) gridInfo.get("y2");
            var scanlineDir = (String) gridInfo.get("scanlineDir");

            if (x1 >= width) {
                x1 = width - unitspacer;
                x2 = width - unitspacer;
                scanlineDir = "RTL";
            } else if (x1 <= 0) {
                scanlineDir = "LTR";
            }

            gridInfo.put("scanlineDir", scanlineDir);

            // int x1 = 0, y1 = 0, x2 = 0, y2 = height;
            if (scanlineDir.equals("LTR")) {
                x1 += unitspacer;
                x2 += unitspacer;
            } else {
                x1 -= unitspacer;
                x2 -= unitspacer;
            }

            gridInfo.put("x1", x1);
            gridInfo.put("y1", y1);
            gridInfo.put("x2", x2);
            gridInfo.put("y2", y2);
            // System.out.println(x1+"-"+y1+"-"+x2+"-"+y2);

            g.drawLine(x1, y1, x2, y2);
            g.dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public static void getComponentView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComponentView'");
    }

}
