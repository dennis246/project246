package app.composite.controller;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import app.composite.component.GraphSheet;
import app.util.MathUtil;

public class GraphViewController {

    static int viewPortHeight = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
    static int viewPortWidth = (int) new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();

    static LinkedHashMap<String, Object> componentsInfo;
    static LinkedHashMap<String, Object> gridInfo;
    static JFrame mainframe;

    public static void main(String[] args) {

        int initInd = 0;
        // ScanLine.compose();
       /*  int[] Co_ordinateStops_XArr = MathUtil.randomNumberSequence(200, 4000, 10);
        int[] Co_ordinateStops_YArr = MathUtil.randomNumberSequence(200, 4000, 5);

        Co_ordinateStops_XArr = MathUtil.purgeSort(Co_ordinateStops_XArr, SortMode.Asc);
        Co_ordinateStops_YArr = MathUtil.purgeSort(Co_ordinateStops_YArr, SortMode.Asc);
 */

        int[] Co_ordinateStops_XArr = {200, 340, 560, 665, 789, 812, 990, 1021, 1386, 1442};
        int[] Co_ordinateStops_YArr = {132, 572, 876, 1087, 1196, 2454, 3409};


        // var xx1 = MathUtil.meanOfArray(Co_ordinateStops_XArr);
        // var xx2 = MathUtil.meanIntervalOfArray(Co_ordinateStops_XArr);

        var graphInfo = new LinkedHashMap<String, Object>();

        graphInfo.put("Width", viewPortWidth);
        graphInfo.put("Height", viewPortHeight);
        graphInfo.put("UnitSpacer", 50);

        graphInfo.put("Co_ordinateStops_XArr", Co_ordinateStops_XArr);
        graphInfo.put("Co_ordinateStops_YArr", Co_ordinateStops_YArr);

        var maxValRound_X = MathUtil.maxOfArray(Co_ordinateStops_XArr);
        var minValRound_X = MathUtil.minOfArray(Co_ordinateStops_XArr);
        var subDivisions_X = MathUtil.meanIntervalOfArray(Co_ordinateStops_XArr);
        // Co_ordinateStops_XArr.length > 25 ? 25 : Co_ordinateStops_XArr.length; //+
        // Double.valueOf(maxValRound_X * 0.01).intValue();

        var maxValRound_Y = MathUtil.maxOfArray(Co_ordinateStops_YArr);
        var minValRound_Y = MathUtil.minOfArray(Co_ordinateStops_YArr);
        var subDivisions_Y = MathUtil.meanIntervalOfArray(Co_ordinateStops_YArr);
        // Co_ordinateStops_YArr.length > 25 ? 25 : Co_ordinateStops_YArr.length; //+
        // Double.valueOf(maxValRound_Y * 0.01).intValue();

        graphInfo.put("maxValRound_X", maxValRound_X);
        graphInfo.put("minValRound_X", minValRound_X);
        graphInfo.put("subDivisions_X", subDivisions_X);

        graphInfo.put("maxValRound_Y", maxValRound_Y);
        graphInfo.put("minValRound_Y", minValRound_Y);
        graphInfo.put("subDivisions_Y", subDivisions_Y);

        GraphSheet.compose(graphInfo);
        System.out.println("end");

    }

}
