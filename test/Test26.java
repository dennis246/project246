import app.util.MathUtil;
import app.util.StringUtil;
import app.util.StringUtil.Direction;

public class Test26 {

    int width = 64 ;
    int height = 48 ;
    int gridsize = width * height;
    int[] columnsArr = new int[0];
    int[] inkPos1Arr = new int[0];
    int[][] grid2DArr = new int[0][];
    String tipShape = "*";

    public static void main(String[] ag) {

        Test26 mt26 = new Test26();
        mt26.generateInkPoints();
    }

    public void generateInkPoints() {

        try {

            int padding = 6;
            {
                //int[] rowArr = { 0, 0, 1, 0, 0 };
                var x1 = StringUtil.pad(new char[]{'0','0','1','0','0'}, '0', padding, Direction.Leading);
                var x2 = StringUtil.pad(x1, '0', padding, Direction.Trailing);
                var rowArr = MathUtil.convertToIntArray(x2);
                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, rowArr);
            }

            {
                //int[] rowArr = { 0, 1, 0, 1, 0 };
                var x1 = StringUtil.pad(new char[]{'0','1','1','1','0'}, '0', padding, Direction.Leading);
                var x2 = StringUtil.pad(x1, '0', padding, Direction.Trailing);
                var rowArr = MathUtil.convertToIntArray(x2);
                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, rowArr);
            }

           {
                //int[] rowArr = { 0, 1, 0, 1, 0 };
                var x1 = StringUtil.pad(new char[]{'1','1','1','1','1'}, '0', padding, Direction.Leading);
                var x2 = StringUtil.pad(x1, '0', padding, Direction.Trailing);
                var rowArr = MathUtil.convertToIntArray(x2);
                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, rowArr);
            }

            {
                //int[] rowArr = { 0, 1, 0, 1, 0 };
                var x1 = StringUtil.pad(new char[]{'0','1','1','1','0'}, '0', padding, Direction.Leading);
                var x2 = StringUtil.pad(x1, '0', padding, Direction.Trailing);
                var rowArr = MathUtil.convertToIntArray(x2);
                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, rowArr);
            }


            {
                //int[] rowArr = { 0, 0, 1, 0, 0 };
                var x1 = StringUtil.pad(new char[]{'0','0','1','0','0'}, '0', padding, Direction.Leading);
                var x2 = StringUtil.pad(x1, '0', padding, Direction.Trailing);
                var rowArr = MathUtil.convertToIntArray(x2);
                grid2DArr = MathUtil.addToIntArray2D(grid2DArr, rowArr);
            }

            printInkPoints();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printInkPoints() {

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
                        //System.out.print("\s");
                    }

                }

                ri += width;
                System.out.println("\r");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printInkPoints_() {

        try {
            int ri = 0;
            for (int i = 1; i < height; i++) {
                ri += i;
                int inkedInd = 0;

                int canInkInd = 0;
                if (columnsArr[i] == 1) {
                    canInkInd = 1;
                }

                for (int j = 1; j < width; j++) {

                    if (inkPos1Arr.length > 0 && j == inkPos1Arr[0] && canInkInd == 1) {
                        System.out.print("//");
                        inkPos1Arr = MathUtil.removeFromIntArray(inkPos1Arr, 0);
                        inkedInd = 1;
                    } else {
                        inkedInd = 0;
                    }

                    if (inkedInd == 0) {
                        System.out.print("..");
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
