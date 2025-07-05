import app.util.MathUtil;

class MapTest25 {
    public static void main(String[] ag) {

        int width = 64/2;
        int height = 48/2;
        int gridsize = width * height;
        int[] inkPosArr = MathUtil.generateNumArrByRange(100, 0, 1, 12);
        int ri = 0;

        for (int i = 1; i < height; i++) {
            ri += i;

            for (int j = 1; j < width; j++) {

                int cp = i + j;
                if (MathUtil.hasInt(inkPosArr, cp) == 1) {
                    System.out.print("//");
                    continue;
                }

                System.out.print("..");
                ri += width;

            }

            System.out.println("\r");

        }

    }

}