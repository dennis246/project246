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

public class GraphSheet extends JPanel {

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

    public GraphSheet(LinkedHashMap<String, Object> gridInfo) {

        try {

            var width = (int) gridInfo.get("Width"); // (int) gridInfo.get("width");
            var height = (int) gridInfo.get("Height");// (int) gridInfo.get("height");
            var unitspacer = (int) gridInfo.get("UnitSpacer");

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.setColor(Color.white);
            g.setFont(tertiaryContentFont);

            Graphics g1 = bufferedImage.getGraphics();
            g1.setColor(Color.GREEN);
            g1.setFont(primaryHeaderFont);

            g.drawString("INFO", 10, height / 2);

            var defaultMinVal_X = Double.valueOf(width * 0.1).intValue();
            var defaultMinVal_Y = Double.valueOf(height * 0.1).intValue();

            var defaultMaxVal_X = Double.valueOf(width / 2 * 0.8).intValue();
            var defaultMaxVal_Y = Double.valueOf(height / 2 * 0.8).intValue();

            var defaultSubDivisions_X = Double.valueOf(defaultMaxVal_X * 0.05).intValue();
            var defaultSubDivisions_Y = Double.valueOf(defaultMaxVal_Y * 0.05).intValue();

            /*
             * 1st quad
             * int defaultXAxisAt = Double.valueOf(width * 0.1).intValue();
             * int defaultYAxisAt = Double.valueOf(height * 0.8).intValue();
             */

            int defaultXAxisAt = Double.valueOf(height / 2).intValue();
            int defaultYAxisAt = Double.valueOf(width / 2).intValue();
            // X axis
            g.drawLine(0, defaultXAxisAt, width, defaultXAxisAt);
            g.drawLine(0, defaultXAxisAt - 1, width, defaultXAxisAt - 1);

            // Y axis
            g.drawLine(defaultYAxisAt, 0, defaultYAxisAt, height);
            g.drawLine(defaultYAxisAt + 1, 0, defaultYAxisAt + 1, height);

            var centerX = defaultYAxisAt;
            var centerY = defaultXAxisAt;

            var Co_ordinateStops_XArr = (int[]) gridInfo.get("Co_ordinateStops_XArr");
            var Co_ordinateStops_YArr = (int[]) gridInfo.get("Co_ordinateStops_YArr");

            var maxValRound_X = (int) (gridInfo.get("maxValRound_X") != null ? gridInfo.get("maxValRound_X")
                    : defaultMaxVal_X);
            var minValRound_X = (int) (gridInfo.get("minValRound_X") != null ? gridInfo.get("minValRound_X")
                    : defaultMinVal_X);

            var subDivisions_X = (int) (gridInfo.get("subDivisions_Y") != null ? gridInfo.get("subDivisions_Y")
                    : defaultSubDivisions_X);
            var stopsCount_X = maxValRound_X / subDivisions_X;

            var maxValRound_Y = (int) (gridInfo.get("maxValRound_Y") != null ? gridInfo.get("maxValRound_Y")
                    : defaultMaxVal_Y);
            var minValRound_Y = (int) (gridInfo.get("minValRound_Y") != null ? gridInfo.get("minValRound_Y")
                    : defaultMinVal_Y);

            var subDivisions_Y = (int) (gridInfo.get("subDivisions_Y") != null ? gridInfo.get("subDivisions_Y")
                    : defaultSubDivisions_Y);
            var stopsCount_Y = maxValRound_Y / subDivisions_X;

            int sampleLen = Co_ordinateStops_XArr.length;

            int[] rndrXAxisPtsArr = new int[0];

            {
                // XAxisPoints
                int XAxisPoint_X = centerX + unitspacer;
                int XAxisPoint_Y = centerY + unitspacer;
                subDivisions_X = subDivisions_X / 4; // for 4quad dis
                var pointX = minValRound_X;

                for (int i = 0; i < sampleLen; i++) {
                    // X-axis points
                    g.drawString(String.valueOf(pointX), XAxisPoint_X, XAxisPoint_Y);
                    rndrXAxisPtsArr = MathUtil.addToIntArray(rndrXAxisPtsArr, XAxisPoint_X);
                    XAxisPoint_X += subDivisions_X;
                    pointX += subDivisions_X;
                }

            }

            int[] rndrYAxisPtsArr = new int[0];

            {
                // YAxisPoints
                int YAxisPoint_X = centerX - unitspacer;
                int YAxisPoint_Y = centerY - unitspacer;
                subDivisions_Y = subDivisions_Y / 4; // for 4quad dis
                var pointY = minValRound_Y;

                for (int i = 0; i < sampleLen; i++) {
                    // X-axis points
                    g.drawString(String.valueOf(pointY), YAxisPoint_X, YAxisPoint_Y);
                    rndrYAxisPtsArr = MathUtil.addToIntArray(rndrYAxisPtsArr, YAxisPoint_Y);
                    YAxisPoint_Y -= subDivisions_Y;
                    pointY += subDivisions_Y;
                }

            }

            {
                // data line coordinates
                // var pointX = minValRound_X;
                for (int i = 0; i < sampleLen; i++) {

                    // X-axis points
                    g.drawString(
                            String.valueOf(Co_ordinateStops_XArr[i]) + "," + String.valueOf(Co_ordinateStops_YArr[i]),
                            rndrXAxisPtsArr[i], rndrYAxisPtsArr[i]);

                }

            }

            g.dispose();
            g1.dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GraphSheet0(LinkedHashMap<String, Object> gridInfo) {

        try {

            var width = viewPortWidth; // (int) gridInfo.get("width");
            var height = viewPortHeight;// (int) gridInfo.get("height");
            var unitspacer = (int) gridInfo.get("unitspacer");

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.setColor(Color.white);
            g.setFont(tertiaryContentFont);

            g.drawString("INFO", 10, height / 2);

            var defaultMinVal_X = Double.valueOf(width * 0.1).intValue();
            var defaultMinVal_Y = Double.valueOf(height * 0.1).intValue();

            var defaultMaxVal_X = Double.valueOf(width / 2 * 0.8).intValue();
            var defaultMaxVal_Y = Double.valueOf(height / 2 * 0.8).intValue();

            var defaultSubDivisions_X = Double.valueOf(defaultMaxVal_X * 0.05).intValue();
            var defaultSubDivisions_Y = Double.valueOf(defaultMaxVal_Y * 0.05).intValue();

            int YcordAt = Double.valueOf(width * 0.1).intValue();
            int XcordAt = Double.valueOf(height * 0.8).intValue();

            // Y axis
            g.drawLine(YcordAt, 0, YcordAt, height);
            g.drawLine(YcordAt + 1, 0, YcordAt + 1, height);

            // X axis
            g.drawLine(0, XcordAt, width, XcordAt);
            g.drawLine(0, XcordAt - 1, width, XcordAt - 1);

            var Co_ordinateStops_XArr = (int[]) gridInfo.get("Co_ordinateStops_XArr");
            var Co_ordinateStops_YArr = (int[]) gridInfo.get("Co_ordinateStops_YArr");

            var maxValRound_X = (int) (gridInfo.get("maxValRound_X") != null ? gridInfo.get("maxValRound_X")
                    : defaultMaxVal_X);
            var minValRound_X = (int) (gridInfo.get("minValRound_X") != null ? gridInfo.get("minValRound_X")
                    : defaultMinVal_X);

            var subDivisions_X = (int) (gridInfo.get("subDivisions_Y") != null ? gridInfo.get("subDivisions_Y")
                    : defaultSubDivisions_X);
            var stopsCount_X = maxValRound_X / subDivisions_X;

            var maxValRound_Y = (int) (gridInfo.get("maxValRound_Y") != null ? gridInfo.get("maxValRound_Y")
                    : defaultMaxVal_Y);
            var minValRound_Y = (int) (gridInfo.get("minValRound_Y") != null ? gridInfo.get("minValRound_Y")
                    : defaultMinVal_Y);

            var subDivisions_Y = (int) (gridInfo.get("subDivisions_Y") != null ? gridInfo.get("subDivisions_Y")
                    : defaultSubDivisions_Y);
            var stopsCount_Y = maxValRound_Y / subDivisions_X;

            g.drawString(String.valueOf(0), Double.valueOf(width * 0.05).intValue(),
                    Double.valueOf(height * 0.85).intValue());

            // int[] XCordStops = new int[stopsCount_X+1];
            {

                int ccordstop_x = Double.valueOf(width * 0.125).intValue();
                int ccordstop_y = Double.valueOf(height * 0.85).intValue();
                for (int i = 0,
                        cv = minValRound_X; i < Co_ordinateStops_XArr.length; i++, cv += subDivisions_X, ccordstop_x += stopsCount_X
                                + unitspacer
                /* * unitspacer / 2 */) {
                    g.drawString(String.valueOf(cv), ccordstop_x, ccordstop_y);
                }

            }

            // int[] YCordStops = new int[stopsCount_Y];
            {

                int ccordstop_x = Double.valueOf(width * 0.05).intValue();
                int ccordstop_y = Double.valueOf(height * 0.77).intValue();
                for (int i = 0,
                        cv = minValRound_Y; i < Co_ordinateStops_YArr.length; i++, cv += subDivisions_Y, ccordstop_y -= stopsCount_Y
                                + unitspacer
                /* * unitspacer / 4 */) {
                    g.drawString(String.valueOf(cv), ccordstop_x, ccordstop_y);
                }

            }

            Graphics g1 = bufferedImage.getGraphics();
            Graphics g2 = bufferedImage.getGraphics();
            g2.setFont(tertiarySubContentFont);

            // data values
            {

                g1.setColor(Color.GREEN);
                g1.setFont(tertiaryContentFont);

                if (Co_ordinateStops_XArr.length != Co_ordinateStops_YArr.length) {
                    throw new Exception("Invalid coordinate points");
                }

                // (0,0)@

                int ccstop_x1 = Double.valueOf(width * 0.1).intValue();
                int ccstop_y1 = Double.valueOf(height * 0.1).intValue();

                int centerX = ccstop_x1;
                int centerY = ccstop_y1;

                /*
                 * int ccstop_x2 = Double.valueOf(width * 0.1).intValue();
                 * int ccstop_y2 = Double.valueOf(height * 0.1).intValue();
                 */

                int ccstop_x2 = minValRound_X;
                int ccstop_y2 = minValRound_Y;

                int invWidth = width - centerX;
                int invHeight = height - centerY; // correction for ui display only
                g.drawString(String.valueOf(invWidth) + "," + String.valueOf(invHeight), invWidth,
                        invHeight);

                for (int i = 1; i < Co_ordinateStops_XArr.length; i++) {

                    ccstop_x2 = Co_ordinateStops_XArr[i] + centerX;
                    ccstop_y2 = Co_ordinateStops_YArr[i] + centerY;

                    /*
                     * g.drawString(String.valueOf(ccstop_x2) + "," + String.valueOf(ccstop_y2),
                     * ccstop_x2,
                     * invHeight - ccstop_y2);
                     * g1.drawLine(ccstop_x1, invHeight - ccstop_y1, ccstop_x2, invHeight -
                     * ccstop_y2);
                     */
                    g.drawString(String.valueOf(ccstop_x2) + "," + String.valueOf(ccstop_y2), ccstop_x2,
                            invHeight - ccstop_y2);
                    g1.drawLine(ccstop_x1, invHeight - ccstop_y1, ccstop_x2, invHeight - ccstop_y2);

                    ccstop_x1 = ccstop_x2;
                    ccstop_y1 = ccstop_y2;

                }

            }

            g.dispose();
            g1.dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

}
