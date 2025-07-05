import app.util.MathUtil;
import app.util.SortMode;
import app.util.StringUtil;
import app.util.StringUtil.MatchCriteria;

public class Test24 {

    public static void main(String[] args) {

        try {

             // int[] ax = new int[] { 2, 8, 9, 2, 7, 9, 1 };
            // var axd = MathUtil.distinct(ax);

            int[] rnumArr = new int[0];
            for (int i = 0; i < 20; i++) {
                var rmx = StringUtil.generateRandomSequenceWith(24,
                        new char[] { '1', '0' });
                String rmxS = new String(rmx);
                int decNum = MathUtil.binarySeqToDecimal(rmxS);
                System.out.println(rmxS + ":" + decNum);
                rnumArr = MathUtil.addToIntArray(rnumArr, decNum);
            }

            var rnumArrDist = MathUtil.distinct(rnumArr);


            var chars = "space".toCharArray();
            int bm = MathUtil.factorial(chars.length);
            int maxix = MathUtil.pow(2, chars.length);
            int pcounter = 0;
            String[] aggArr = new String[0];
            while (pcounter != maxix) {

                char[] sf = StringUtil.shuffle(chars);
                String sfw = new String(sf);

                int cutAt = Double.valueOf(Math.random() * chars.length + 1).intValue();
                sfw = sfw.substring(0, cutAt);

                if (StringUtil.hasItemAt(aggArr, sfw, MatchCriteria.First) == -1) {
                    aggArr = StringUtil.add(aggArr, sfw);
                    ++pcounter;
                }
            }

            System.out.println("total size: "+aggArr.length);
            aggArr = StringUtil.reorder(aggArr, null, SortMode.Asc);
            for (var item : aggArr) {
                System.out.println(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
