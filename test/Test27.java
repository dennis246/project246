import java.util.HashMap;

import app.util.MathUtil;
import app.util.MathUtil.ElementPosition;
import app.util.MathUtil.PadDirection;

public class Test27 {

    int width = 25;
    int height = 25;
    //int gridsize = width * height;
    int[] columnsArr = new int[0];
    int[] inkPos1Arr = new int[0];
    String tipShape = "*";

    public static void main(String[] ag) {
        Test27 mt27 = new Test27();
        mt27.generateInkPoints();
    }

    public void generateInkPoints() {

        try {

            var shapeInfo = new HashMap<String, Object>();
            shapeInfo.put("Sides", 3);
            shapeInfo.put("Angles", new int[] { 60, 60, 60 });
            shapeInfo.put("RenderOrientation", "TopDown"); // read and rend dir LTR so
            shapeInfo.put("BaseRestingAxis", "X");
            shapeFormation(shapeInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shapeFormation(HashMap<String, Object> shapeInfo) throws Exception {

        int[][] grid2DArr = new int[0][];
        int Sides = (int) shapeInfo.get("Sides");
        int[] Angles = (int[]) shapeInfo.get("Angles");
        if (Sides != Angles.length) {
            throw new Exception("invalid shape info");
        }
        var BaseRestingAxis = (String) shapeInfo.get("BaseRestingAxis");
        int[] plotptsArr = new int[0];
        int midpt = width / 2;
        if (Sides == 3) {

            int plotstartInd = 0;
            int touchedBaseAxisInd = 0;
            int vPt1At = -1, vPt2At = -1;
            int htIncAt = 1;
            int angluardeviation = 2;
            int deviationFactor = 2;
            int gridsize = width * height;
            for (int i = 0; i <= gridsize; i++) {

                int cpval = 0;
                if (plotstartInd == 0 && i == midpt && BaseRestingAxis.equals("X")) {
                    cpval = 1;
                    plotstartInd = 1;
                }

                if (plotstartInd == 1 && (i == vPt1At || i == vPt2At)) {
                    cpval = 1;
                }

                // grid size
                if (touchedBaseAxisInd == 1) {
                    if (i % 2 != 0) {
                        cpval = 0;
                    } else {
                        cpval = 1;
                    }
                }

                plotptsArr = MathUtil.addToIntArray(plotptsArr, cpval);
                if (plotptsArr[0] == 1) {
                    touchedBaseAxisInd = 1;
                }

                if (i + 1 < gridsize && (i + 1) % width == 0 || i == gridsize) {

                    htIncAt += width;

                    if (plotstartInd == 1) {
                        if (touchedBaseAxisInd == 0 && i == (width * (height - 1)) - 1) {
                            touchedBaseAxisInd = 1;
                            vPt1At = i;
                            vPt2At = (width * height) - 1;
                        } else {
                            vPt1At = htIncAt + (midpt - 1) - angluardeviation;
                            vPt2At = htIncAt + (midpt - 1) + angluardeviation;
                        }
                    }

                    grid2DArr = MathUtil.addToIntArray2D(grid2DArr, plotptsArr);
                    plotptsArr = new int[0];
                    angluardeviation += deviationFactor;

                    if (touchedBaseAxisInd == 1) {
                        touchedBaseAxisInd = 0;
                        break;
                    }

                }

            }

        }

        var grid2DArrRev = MathUtil.reverse(grid2DArr);
        int[][] canvasArr = MathUtil.canvas(width * 2 + 15, height, 0);
        canvasArr = addToCanvas(canvasArr, new int[][][] { grid2DArr, grid2DArrRev }, 1,
                ElementPosition.Center);
        printInkPoints(canvasArr);

    }

    public static int[][] addToCanvas(int[][] canvasArr, int[][][] dataRows, int mergeVal, ElementPosition epos) {

        int ri = 0;
        try {

            for (int i = 0; i < dataRows.length; i++, ri++) {

                int[][] crowArr = dataRows[i];
                int diff = 0;
                if (epos.equals(ElementPosition.Center)) {

                    if ((diff = Math.abs(crowArr[0].length - canvasArr[0].length)) > 0) {

                        if (i == 0) {
                            crowArr = MathUtil.pad2DArray(crowArr, 8, PadDirection.Top_Down);
                        } else {
                            crowArr = MathUtil.pad2DArray(crowArr, 10, PadDirection.Top_Down);
                        }

                        // printInkPoints(crowArr);
                        crowArr = MathUtil.pad2DArray(crowArr, diff / 2, PadDirection.Left_Right);
                        // printInkPoints(crowArr);

                    }

                }

                for (int j = 0; j < crowArr.length; j++) {

                    int[] ccellArr = crowArr[j];
                    for (int k = 0; k < ccellArr.length; k++) {

                        if (ccellArr[k] == mergeVal) {
                            canvasArr[j][k] = mergeVal;
                        }

                    }

                }

            }

        } catch (Exception e) {
            System.err.println("canvas error @ " + ri);
            e.printStackTrace();
        }

        return canvasArr;

    }

    public void printInkPoints(int[][] grid2DArr) {

        try {
            int ri = 0;
            for (int i = 0; i < grid2DArr.length; i++) {
                ri += i;
                int inkedInd = 0;

                var row = grid2DArr[i];

                for (int j = 0; j < row.length; j++) {

                    var pt = row[j];

                    if (pt == 1) {
                        System.out.print(tipShape);
                        inkPos1Arr = MathUtil.removeFromIntArray(inkPos1Arr, 0);
                        inkedInd = 1;
                    } else {
                        inkedInd = 0;
                    }

                    if (inkedInd == 0) {
                        System.out.print(".");
                        // System.out.print("\s");
                    }

                }

                ri += width;
                System.out.println("\r");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
