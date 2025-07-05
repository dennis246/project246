import java.math.BigInteger;

import app.util.MathUtil;
import app.util.StringUtil;

class Test25 {
    public static void main(String[] ag) {

        try {

            int width = 64 / 2;
            int height = 48 / 2;
            int gridsize = width * height;
            // int[] inkPos1Arr = MathUtil.generateNumArrByRange(60, 10, 1, 6);
            // inkPos1Arr = MathUtil.addAllToIntArray(inkPos1Arr,
            // MathUtil.generateNumArrByRange(60, 11, 1, 6));

            //int[][] grid2DArr = new int[0][];

            var rcolumnInkPos = StringUtil.generateRandomSequenceWith(24,
                    new char[] { '1', '0' });

            var columnsArr = MathUtil.convertToIntArray(rcolumnInkPos);

            int[] inkPos1Arr = MathUtil.generateNumArrByRange(width, 3, 1, 2);
            // var tempArr = inkPos1Arr;
            inkPos1Arr = MathUtil.shuffle(inkPos1Arr);

            // for (int i = 0; i < columnsArr.length; i++) {

            //     if (columnsArr[i] == 1) {
            //         grid2DArr = MathUtil.addToIntArray2D(grid2DArr, inkPos1Arr);
            //     } else {
            //         grid2DArr = MathUtil.addToIntArray2D(grid2DArr, new int[0]);
            //     }

            // }

            // int[] inkPos2Arr = MathUtil.generateNumArrByRange(10, 200, -1, 60);
            int ri = 0;

            for (int i = 1; i < height; i++) {
                ri += i;
                int inkedInd = 0;

                int canInkInd = 0;
                if(columnsArr[i] == 1){
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

}