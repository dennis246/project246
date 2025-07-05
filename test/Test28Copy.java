import java.util.HashMap;
import java.util.LinkedHashMap;

import app.util.MathUtil;

public class Test28Copy {

    int height = 25;
    int width = height * 2 + height / 2;
    int pxShiftFactor = 1; // screenbased

    public static void main(String[] ag) {
        Test28 mt28 = new Test28();
        mt28.generateInkPoints();
    }

    public void generateInkPoints() {

        try {

            // {
            // var objectInfo = new HashMap<String, Object>();
            // objectInfo.put("Type", "Line");
            // objectInfo.put("PlotEndPoints", new int[][] { { 540, 1 }, { 1200, 2 } });
            // var grid2DArr = objectFormation(objectInfo);

            // var cprintInfo = new LinkedHashMap<String, Object>();
            // cprintInfo.put("Width", width);
            // cprintInfo.put("TipShape", "*");
            // cprintInfo.put("Grid2DArr", grid2DArr);

            // MathUtil.printInkPoints(cprintInfo);
            // }

            {
                var objectInfo = new HashMap<String, Object>();
                objectInfo.put("Type", "Line");
                objectInfo.put("PlotEndPoints", new int[][] { { 540, 1 }, { 615, 2 } });
                var grid2DArr = objectFormation(objectInfo);

                var cprintInfo = new LinkedHashMap<String, Object>();
                cprintInfo.put("Width", width);
                cprintInfo.put("TipShape", "*");
                cprintInfo.put("Grid2DArr", grid2DArr);

                MathUtil.printInkPoints(cprintInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[][] objectFormation(HashMap<String, Object> objectInfo) {

        int[][] grid2DArr = new int[0][];
        int[][] PlotEndPoints = (int[][]) objectInfo.get("PlotEndPoints");
        int gridsize = width * height;
        int[] plotptsArr = new int[0];
        int plotstartInd = 0;
        // int cplotval = -1;
        int eop = 0;

        // plot all pts end to end
        int xCdt = PlotEndPoints[0][0];
        int yCdt = PlotEndPoints[0][1];

        String progIndY = Math.abs(PlotEndPoints[0][0] -
                PlotEndPoints[PlotEndPoints.length - 1][0]) > 0 ? "linear"
                        : "na";

        String progIndX = Math.abs(PlotEndPoints[0][1] - PlotEndPoints[PlotEndPoints.length - 1][1]) > 0 ? "linear"
                : "na";

        // int caxcdts = PlotEndPoints[0][0] * PlotEndPoints[0][1];
        int caxcdts = -1;
        int[][] PlotPoints = new int[0][];
        PlotPoints = MathUtil.addToIntArray2D(PlotPoints, PlotEndPoints[0]);

        for (int i = 0; i <= gridsize; i++) {

            int cpval = 0;
            caxcdts = xCdt * yCdt;

            if (i == caxcdts) {
                plotstartInd = 1;
                cpval = 1;

                if (progIndX == "linear") {
                    xCdt += width + pxShiftFactor;
                }

                if (progIndY == "linear") {
                    yCdt += pxShiftFactor;
                }

                PlotPoints = MathUtil.addToIntArray2D(PlotPoints, new int[] { xCdt, yCdt });
            }

            if (plotstartInd == 1 && caxcdts >= PlotEndPoints[PlotEndPoints.length - 1][0]) {
                cpval = 0;
            }

            plotptsArr = MathUtil.addToIntArray(plotptsArr, cpval);

            if (i + 1 < gridsize && (i + 1) % width == 0 || i == gridsize) {

                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, plotptsArr);
                plotptsArr = new int[0];

                if (eop == 1) {
                    break;
                }

            }

        }

        return grid2DArr;
    }
}
