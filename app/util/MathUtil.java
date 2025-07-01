package app.util;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @note experimental utility methods
 * @author Dennis Thomas
 */

public class MathUtil {

    public static List<Integer> numStore = new ArrayList<>();
    public static int regPassCount = 0;

    public enum MatchCriteria {
        First, Last, All, Any
    }

    public static enum Distribution {
        Uneven, Even
    }

    public static enum Flux {
        ONE, TWO, THREE, FOUR
    }

    public static enum TrimDirection {
        Leading, Trailing
    }

    public static void primeSequence(int from, int to) {

        int[] unitDivs = { 2, 3, 5 };
        for (int i = from; i < to; i++) {

            /*
             * if ( (i / 1 == i && i % 2 != 0 && i % 3 != 0 && i % 5 != 0)) {
             * System.out.print(i + "\s");
             * }
             */
            int passCounts = 0;
            if (i / 1 == i && i / 1 != 1) {
                passCounts += 1;
            }

            for (int d = 0; d < unitDivs.length; d++) {

                if (i == unitDivs[d] || i % unitDivs[d] != 0) {
                    passCounts += 1;
                }
            }

            if (passCounts == 1 + unitDivs.length) {
                System.out.print(i + "\s");
            }

        }
    }

    public static boolean checkIfPrime(int num) {
        int[] unitDivs = { 2, 3, 5 };

        int passCounts = 0;
        if (num / 1 == num && num / 1 != 1) {
            passCounts += 1;
        }

        for (int d = 0; d < unitDivs.length; d++) {

            if (num == unitDivs[d] || num % unitDivs[d] != 0) {
                passCounts += 1;
            }
        }

        if (passCounts == 1 + unitDivs.length) {
            // System.out.print(num + "is prime!");
            return true;
        } else {
            return false;
        }

    }

    public static void fibo(int from, int to) {

        int[] pool = { 0, 1 };
        System.out.print(pool[0] + "\s" + pool[1] + "\s");

        for (int i = 0; i < 12; i++) {

            int res = pool[0] + pool[1];
            System.out.print(res + "\s");

            pool[0] = pool[1];
            pool[1] = res;

        }
    }

    public static void primeFactorization(int target) {
        // factorStatment += target + " = ";

        if (checkIfPrime(target)) {
            System.out.print(target + " is prime");
            return;
        }
        unitDivision(target, 2);
        int index = 0;
        for (var item : numStore) {
            index += 1;
            System.out.print(item + (index != numStore.size() ? "x" : ""));
        }
    }

    public static void unitDivision(int target, int div) {
        if (target == 0 || target < div) {
            return;
        }

        int quot = 0;
        if (target % div == 0) {
            quot = target / div;
            numStore.add(div);

            if (quot > 0) {
                unitDivision(quot, div);
            }

        } else {
            // target = quot;
            unitDivision(target, div + 1);
        }

    }

    public static int GCF(int num1, int num2) {
        int gtrNum = 0;
        int lsrNum = 0;

        if (num1 > num2) {
            gtrNum = num1;
            lsrNum = num2;
        } else if (num1 < num2) {
            lsrNum = num1;
            gtrNum = num2;
        }

        if (lsrNum == 0) {
            return gtrNum;
        }

        regReduce(gtrNum, lsrNum);
        // System.out.print("GCF of " + num1 + " and " + num2 + " is " +
        // numStore.get(numStore.size() - 1));
        int res = numStore.get(numStore.size() - 1);
        numStore = new ArrayList<>();
        return res;

    }

    public static void regReduce(int gtrNum, int lsrNum) {

        int res = gtrNum - lsrNum;

        if (res == 0) {
            numStore.add(lsrNum);
            return;
        }

        if (res > lsrNum) {
            numStore.add(lsrNum);
            regReduce(res, lsrNum);
        } else if (res <= lsrNum) {
            regReduce(lsrNum, res);
        }

        // return res;

    }

    private static void findGCF(List<Integer> numList) {

        // if(numList.size() > 1)
        int num1 = numList != null ? numList.get(0) : 0;
        int num2 = numList != null && numList.size() > 1 ? numList.get(1) : 0;
        int res = 0;
        for (int i = 1; i < numList.size(); i++) {

            res = GCF(num1, num2);

            if (i == numList.size() - 1) {
                break;
            }

            num1 = res;
            num2 = numList.get(i + 1);

        }

        System.out.print("GCF of set is " + res);

    }

    public static int LCM(List<Integer> numList) {

        // if(numList.size() > 1)
        int num1 = numList != null ? numList.get(0) : 0;
        int num2 = numList != null && numList.size() > 1 ? numList.get(1) : 0;
        int resGCF = 0;
        int resLCM = 0;
        for (int i = 1; i < numList.size(); i++) {

            resGCF = GCF(num1, num2);

            resLCM = num1 * num2 / resGCF;

            if (i == numList.size() - 1) {
                break;
            }

            num1 = resLCM;
            num2 = numList.get(i + 1);

        }

        // System.out.print("LCM of set is " + resLCM);
        return resLCM;

    }

    private static int arrayToInteger(Integer[] numArr) {
        int num = 0;

        for (int i = 0, pf = numArr.length - 1; i < numArr.length; i++, pf--) {
            num += numArr[i] * pow(pf, 10);
        }

        return num;
    }

    private static BigInteger arrayToBigInteger(Integer[] numArr) {
        BigInteger num = BigInteger.ZERO;

        for (int i = 0, pf = numArr.length - 1; i < numArr.length; i++, pf--) {
            // num += numArr[i] * pow(pf,10);
            BigInteger p1 = new BigInteger(String.valueOf(numArr[i]));
            BigInteger p2 = powBig(pf, 10);
            BigInteger current = p1.multiply(p2);
            num = num.add(current);
        }

        return num;
    }

    public static BigInteger powBig(int factor, int base) {

        if (factor == 0) {
            return BigInteger.ONE;
        }

        BigInteger baseBig = new BigInteger(String.valueOf(base));
        BigInteger res = baseBig;
        for (int i = 1; i < factor; i++) {
            // res *= base;
            res = res.multiply(baseBig);
        }

        return res;
    }

   public static int pow(int expfactor, int base) {

        if (expfactor == 0) {
            return 1;
        }

        int res = base;
        for (int i = 1; i < expfactor; i++) {
            res *= base;
        }

        return res;
    }

    public static int subtractionOf(int[] numArr) {
        int sum = 0;
        for (int i = 0; i < numArr.length; i++) {
            sum -= numArr[i];
        }

        return sum;
    }

    public static int sumOf(Integer[] numArr) {
        int sum = 0;
        for (int i = 0; i < numArr.length; i++) {
            sum += numArr[i];
        }

        return sum;
    }

    public static int sumOf(int[] numArr) {
        int sum = 0;
        for (int i = 0; i < numArr.length; i++) {
            sum += numArr[i];
        }

        return sum;
    }

    public static int productOf(Integer[] numArr) {
        int product = 1;
        for (int i = 0; i < numArr.length; i++) {
            if (numArr[i] == 0) {
                continue;
            }
            product *= numArr[i];
        }

        return product;
    }

    public static int productOf(int[] numArr) {
        int product = 1;
        for (int i = 0; i < numArr.length; i++) {
            if (numArr[i] == 0) {
                continue;
            }
            product *= numArr[i];
        }

        return product;
    }

    public static int trimZeroes(String currentSeq) {

        char[] numSeqArrP1 = numAccumalator(currentSeq.toCharArray());
        String numStr = new String(numSeqArrP1);
        int finalRes = Integer.parseInt(numStr);
        return finalRes;

    }

    public static int trimZeroes(String currentSeq, TrimDirection currentTrimDir) {

        char[] numSeqArrP1 = new char[0];
        if (currentTrimDir.equals(TrimDirection.Trailing)) {
            numSeqArrP1 = nonZeroNumAccumalator(currentSeq.toCharArray());
        } else {
            numSeqArrP1 = numAccumalator(currentSeq.toCharArray());
        }

        String numStr = new String(numSeqArrP1);
        int finalRes = Integer.parseInt(numStr);
        return finalRes;

    }

    private static char[] nonZeroNumAccumalator(char[] seqArr) {

        char[] finalSeq = new char[0];
        int nzNumSeqStartInd = 0;
        for (int i = 0; i < seqArr.length; i++) {

            int cp = (int) seqArr[i];
            if (cp >= 48 && cp <= 57) {

                if (seqArr[i] != '0') {
                    ++nzNumSeqStartInd;
                } else {
                    // if (nzNumSeqStartInd == 0) {
                    continue;
                    // }
                }

                finalSeq = StringUtil.addToCharArray(finalSeq, seqArr[i]);
            } else {

                if (i > 0 && nzNumSeqStartInd > 0) {
                    break;
                }

                continue;

            }

        }

        return finalSeq;
    }

    private static char[] numAccumalator(char[] seqArr) {

        char[] finalSeq = new char[0];
        int nzNumSeqStartInd = 0;
        for (int i = 0; i < seqArr.length; i++) {

            int cp = (int) seqArr[i];
            if (cp >= 48 && cp <= 57) {

                if (seqArr[i] != '0') {
                    ++nzNumSeqStartInd;
                } else {
                    if (nzNumSeqStartInd == 0) {
                        continue;
                    }
                }

                finalSeq = StringUtil.addToCharArray(finalSeq, seqArr[i]);
            } else {

                if (i > 0 && nzNumSeqStartInd > 0) {
                    break;
                }

                continue;

            }

        }

        return finalSeq;
    }

    public static Integer[] purgeSort(Integer[] e, SortMode sortOrder) {
        Integer[] e2 = new Integer[e.length];
        for (int i = 0, pi = 0; i < e.length; i++) {

            if (e2[e2.length - 1] != null) {
                break;
            }

            Integer pe = null;
            if (sortOrder.equals(SortMode.Desc)) {
                pe = maxOfArray(e);
            } else {
                pe = minOfArray(e);
            }

            e = ContainerUtil.removeItem(e, pe);
            e2[pi] = pe;
            pi++;
            --i;
        }

        return e2;

    }

    public static int[] purgeSort(int[] mainArr, SortMode sortOrder) {
        int[] sortedArr = new int[mainArr.length];
        for (int i = 0, pi = 0; i < mainArr.length; i++) {

            if (sortedArr[sortedArr.length - 1] != 0) {
                break;
            }

            int pe = 0;
            if (sortOrder.equals(SortMode.Desc)) {
                pe = maxOfArray(mainArr);
            } else {
                pe = minOfArray(mainArr);
            }

            mainArr = ContainerUtil.removeItem(mainArr, pe);
            sortedArr[pi] = pe;
            pi++;
            --i;
        }

        return sortedArr;

    }

    public static int maxOfArray(Integer[] e) {

        int max = e[0];
        try {

            for (int j = 0; j < e.length; j++) {

                if (e[j] > max) {
                    max = e[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return max;
    }

    public static int maxOfArray(int[] e) {

        int max = e[0];
        try {

            for (int j = 0; j < e.length; j++) {

                if (e[j] > max) {
                    max = e[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return max;
    }

    public static int minOfArray(Integer[] e) {

        int min = e[0];
        try {
            for (int j = 0; j < e.length; j++) {

                if (e[j] < min) {
                    min = e[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return min;
    }

    public static int minOfArray(int[] arr) {

        int min = arr[0];
        try {
            for (int j = 0; j < arr.length; j++) {

                if (arr[j] < min) {
                    min = arr[j];
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        return min;
    }

    public static int[] addToIntArray(int[] target, int item) {
        int[] cArray = (int[]) target;
        int[] cArrayInc = new int[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static int[] addAllToIntArray(int[] mainArr, int[] subArr) {

        for (int i = 0; i < subArr.length; i++) {
            mainArr = addToIntArray(mainArr, subArr[i]);
        }

        return mainArr;

    }

    public static Integer[] addToIntegerArray(Integer[] target, Integer item) {
        Integer[] cArray = (Integer[]) target;
        Integer[] cArrayInc = new Integer[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static int[] convertToIntArray(String content) {
        char[] contentArr = content.toCharArray();

        int[] intArr = new int[0];
        for (int i = 0; i < contentArr.length; i++) {
            int cv = (int) contentArr[i];
            intArr = addToIntArray(intArr, cv);
        }

        return intArr;

    }

    public static int hasInt(int[] arr, int item) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == item) {
                return 1;
            }
        }
        return 0;
    }

    public static int hasIntAt(int[] currentArr, int target, MatchCriteria optCriteria) throws Exception {

        MatchCriteria matchCriteria = optCriteria == null ? MatchCriteria.First
                : optCriteria;

        if (!matchCriteria.name().equals("First") && !matchCriteria.name().equals("Last")) {
            throw new Exception("Invalid criteria");
        }

        int foundAt = -1;
        for (int i = 0; i < currentArr.length; i++) {

            if (currentArr[i] == target) {
                foundAt = i;
                if (matchCriteria.name().equals("First")) {
                    return foundAt;
                }
            }

        }

        return foundAt;

    }

    /*
     * public static int[] subdivide_uneven(int num, int partsCount) throws
     * Exception {
     * int[] partsArr = new int[0];
     * 
     * if (partsCount > 2) {
     * int currentNum = num / 2;
     * int currentDivisions = partsCount / 2;
     * int remainingParts = partsCount - currentDivisions;
     * int remainingNum = num - currentNum;
     * if (remainingParts > 0) {
     * var resArr = subdivide_uneven(remainingNum, remainingParts);
     * partsArr = addAllToIntArray(partsArr, resArr);
     * }
     * } else {
     * int cnum = num / 2;
     * partsArr = addToIntArray(partsArr, cnum);
     * int remaining = num - cnum;
     * partsArr = addToIntArray(partsArr, remaining);
     * }
     * 
     * return partsArr;
     * }
     */

    public static int[] subdivide_uneven0(int num, int partsCount) throws Exception {

        /*
         * if (distribution == null) {
         * distribution = distribution.Even;
         * }
         */

        int[] partsArr = new int[0];

        if (partsCount < 2) {
            partsArr[0] = num;
        } else {
            // int itr = partsCount / 2 + partsCount / 4;
            int part = 0;
            int quo = num / partsCount;

            if (quo < partsCount) {
                // System.err.println("will have duplicates");
                throw new Exception("cannot make subdivisions with num of parts");
            }
            // int factor = num / 2;
            int append = partsCount + 2;
            // int max = num / partsCount + partsCount / 2; /* num / partsCount + num /
            // (partsCount * 9); */ // //
            // int min = num / partsCount - num / partsCount / 2; // max / 2 + max / 4; //
            // max - variance - variance/2; //

            int max = num / partsCount + 2; /* num / partsCount + num / (partsCount * 9); */ // //
            int min = num / partsCount - 2; // max / 2 + max / 4; // max - variance - variance/2; //

            int diff = max - min;
            int ri = 0;

            if (diff < partsCount) {
                System.err.println("will have duplicates with even val");
            }

            int findPartInd = 1;
            for (int i = 0; i < partsCount - 1; i++) {
                while (findPartInd == 1) {
                    part = Double.valueOf(Math.random() * max).intValue();

                    if (hasInt(partsArr, part) == 1 || part < min) {
                        findPartInd = 1;
                    } else {
                        findPartInd = 0;
                    }

                    ++ri;
                }

                partsArr = addToIntArray(partsArr, part);
                part = 0;
                findPartInd = 1;

            }

            int sumofUP = sumOf(partsArr);
            int remaining = num - sumofUP;
            partsArr = addToIntArray(partsArr, remaining);

        }

        return partsArr;
    }

    public static int[] subdivide_uneven(int num, int partsCount, Flux fluxDef) throws Exception {
        // unique uneven distribution

        int flux = 2;
        if (fluxDef != null) {
            if (fluxDef.equals(Flux.ONE)) {
                flux = 1;
            } else if (fluxDef.equals(Flux.TWO)) {
                flux = 2;
            } else if (fluxDef.equals(Flux.THREE)) {
                flux = 3;
            } else if (fluxDef.equals(Flux.FOUR)) {
                flux = 4;
            }
        } else {

            flux = partsCount / 2;
            if (flux > 6) {
                throw new Exception("cannot make subdivisions with num of parts : HF");
            }
        }

        int[] partsArr = new int[0];

        if (partsCount < 2) {
            partsArr[0] = num;
        } else {

            int part = 0;
            int quo = num / partsCount;

            if (quo < partsCount) {
                throw new Exception("cannot make subdivisions with num of parts");
            }

            int max = num / partsCount + flux;
            int min = num / partsCount - flux;

            int diff = max - min;
            int ri = 0;

            if (diff < partsCount) {
                throw new Exception("cannot make subdivisions with num of parts");
            }

            partsArr = addToIntArray(partsArr, max);
            // --max;
            int findPartInd = 1;
            for (int i = 1; i < partsCount - 1; i++) {
                while (findPartInd == 1) {
                    part = Double.valueOf(Math.random() * max).intValue();

                    if (hasInt(partsArr, part) == 1 || part < min) {
                        findPartInd = 1;
                    } else {
                        findPartInd = 0;
                    }

                    ++ri;
                }

                // part = max;
                partsArr = addToIntArray(partsArr, part);
                part = 0;
                findPartInd = 1;
                max = max - 1;

            }

            int sumofUP = sumOf(partsArr);
            int remaining = num - sumofUP;

            int exValIndex = -1;
            if ((exValIndex = hasIntAt(partsArr, remaining, null)) != -1) {
                remaining = remaining - 1;
                partsArr[exValIndex] = partsArr[exValIndex] + 1;
            }

            int item1 = partsArr[0];
            partsArr = removeFromIntArray(partsArr, 0);
            int item1newpos = 0;
            while (item1newpos == 0) {
                item1newpos = Double.valueOf(Math.random() * partsArr.length).intValue();
            }

            partsArr = addIntAt(partsArr, item1newpos, item1);
            partsArr = addToIntArray(partsArr, remaining);

        }

        return partsArr;
    }

    public static int[] removeFromIntArray(int[] mainArr, int ti) {

        int[] procArr = new int[0];
        for (int i = 0; i < mainArr.length; i++) {

            if (i == ti) {
                continue;
            } else {
                procArr = addToIntArray(procArr, mainArr[i]);
            }
        }

        return procArr;
    }

    @SuppressWarnings("unchecked")
    public static int[] addIntAt(int[] mainArr, int targetiIndex, int item) throws Exception {

        int[] mainArrInc = (int[]) Array.newInstance(mainArr.getClass().getComponentType(), mainArr.length + 1);

        if (targetiIndex < 0) {
            throw new Exception("Invalid index for method addAt");
        }

        if (targetiIndex > mainArr.length - 1) {
            targetiIndex = mainArrInc.length - 1;
        }

        int itemAddedInd = 0;
        for (int i = 0, pi = 0; i < mainArr.length; i++, pi++) {
            if (i == targetiIndex) {
                mainArrInc[pi] = item;
                ++pi;
                ++itemAddedInd;
            }
            mainArrInc[pi] = mainArr[i];
        }

        if (itemAddedInd == 0) {
            mainArrInc[mainArrInc.length - 1] = item;
        }

        return (int[]) mainArrInc;

    }

    public static int[] randomNumberSequence(int maxDig) {

        int[] seqArr = new int[0];
        int findPartInd = 1;
        int part = 0;
        for (int i = 0; i < maxDig; i++) {

            while (findPartInd == 1) {
                part = Double.valueOf(Math.random() * maxDig).intValue();

                if (hasInt(seqArr, part) == 1) {
                    findPartInd = 1;
                } else {
                    findPartInd = 0;
                }

            }

            seqArr = addToIntArray(seqArr, part);
            findPartInd = 1;
        }

        return seqArr;

    }

    public static int[] randomNumberSequence(int minDig, int maxDig, int totalNumbers) {

        int[] seqArr = new int[0];
        int findPartInd = 1;
        int part = 0;
        for (int i = 0; i < totalNumbers; i++) {

            while (findPartInd == 1) {
                part = Double.valueOf(Math.random() * maxDig).intValue();

                if (hasInt(seqArr, part) == 1 || part < minDig) {
                    findPartInd = 1;
                } else {
                    findPartInd = 0;
                }

            }

            seqArr = addToIntArray(seqArr, part);
            findPartInd = 1;
        }

        return seqArr;

    }

    public static int meanOfArray(int[] mainArr) {
        int sum = sumOf(mainArr);
        int res = sum / mainArr.length;
        return res;
    }

    public static int meanIntervalOfArray(int[] mainArr) {

        int inAggArr[] = new int[0];
        for (int i = 0; i < mainArr.length; i++) {

            if (i + 1 < mainArr.length - 1) {
                int csum = mainArr[i + 1] - mainArr[i];
                inAggArr = addToIntArray(inAggArr, csum);
            }
        }

        int intAggSum = sumOf(inAggArr);
        int res = intAggSum / mainArr.length;
        return res;

    }

    public static int binarySeqToDecimal(String bseq) throws Exception {

        char[] bsArr = bseq.toCharArray();
        if (StringUtil.isBinarySequence(bsArr) == 0) {
            throw new Exception("Not a binary sequence");
        }

        var bsArrOrd = MapUtil.reverse(bsArr);
        int dsum = 0;
        int prevun = 1;
        for (int i = 0; i < bsArrOrd.length; i++) {

            char cc = bsArrOrd[i];
            prevun = i > 0 ? 2 * prevun : prevun;
            if (cc == '1') {
                dsum += prevun;
            }

        }

        return dsum;

    }

    public static String invertBinarySeq(String bseq) throws Exception {

        char[] bsArr = bseq.toCharArray();
        if (StringUtil.isBinarySequence(bsArr) == 0) {
            throw new Exception("Not a binary sequence");
        }

        for (int i = 0, bu = 1; i < bsArr.length; i++) {

            if (bsArr[i] == '1') {
                bsArr[i] = '0';
            } else if (bsArr[i] == '0') {
                bsArr[i] = '1';
            }

        }
        return new String(bsArr);
    }

    public static int[] digits_decimal() {
        int[] digArr = new int[10];
        for (int i = 0; i < 10; i++) {
            digArr[i] = i;
        }

        return digArr;

    }

    public static int[] convertToIntArray(char[] numCharSeq) {

        int[] numSeq = new int[numCharSeq.length];

        try {

            for (int j = 0; j < numCharSeq.length; j++) {
                int numInt = Integer.valueOf(new String(new char[] { numCharSeq[j] }));
                numSeq[j] = numInt;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return numSeq;

    }

    public static int sumOf(char[] numCharArr) {
        return sumOf(convertToIntArray(numCharArr));
    }

    public static String intToBinarySequence(int num) {
        String binarySeq = "";

        int[] buArr = new int[0];
        int n = 1;
        buArr = addToIntArray(buArr, n);

        while (n < num) {
            n = 2 * n;
            buArr = addToIntArray(buArr, n);
        }

        char[] bcharSeq = new char[0];
        int numReduce = num;
        for (int i = buArr.length - 1; i >= 0; i--) {

            if (buArr[i] <= numReduce) {
                bcharSeq = StringUtil.addToCharArray(bcharSeq, '1');
                numReduce -= buArr[i];
            } else {
                bcharSeq = StringUtil.addToCharArray(bcharSeq, '0');
            }

        }

        /*
         * if (bcharSeq.length < 4) {
         * bcharSeq = StringUtil.pad(bcharSeq, '0', 4 - bcharSeq.length,
         * StringUtil.Direction.Leading);
         * }
         */

        // if (num % 2 == 0 && num % 3 != 0 && num % 5 != 0 && num % 7 != 0 &&
        // bcharSeq.length % 4 != 0) {
        if (bcharSeq.length > 4 && bcharSeq.length % 4 != 0 && bcharSeq[0] == '0') {
            bcharSeq = StringUtil.trimZeroes(bcharSeq, StringUtil.Direction.Leading);
        } else {
            bcharSeq = StringUtil.pad(bcharSeq, '0', 4 - bcharSeq.length, StringUtil.Direction.Leading);
        }

        return new String(bcharSeq);
    }

    public static int factorial(int size) {

        int result = 1;

        for (int i = size; i > 0; i--) {
            result *= i;
        }

        return result;

    }

     public static int[] generateNumArrByRange(final int size, final int from, final int dir) {

        int[] finalArr = new int[0];
        int inc = from;

        int last = dir > 0 ? size : 0;
        do {

            finalArr = addToIntArray(finalArr, inc);
            if (dir > 0) {
                ++inc;
            } else {
                --inc;
            }

        } while (inc != last);

        return finalArr;
    }

    /*
     * public static void main(String a[]) {
     * // prime(1, 100);
     * // System.out.print("\n\n");
     * // fibo(1, 100);
     * // primeFactorization(18);
     * 
     * // findGCF(List.of(1,4, 0));
     * findLCM(List.of(10,18,25,21));
     * 
     * }
     */

}