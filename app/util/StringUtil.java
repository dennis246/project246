package app.util;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import app.util.MapUtil.FilterCriteria;

/**
 * @note experimental utility methods
 * @author Dennis Thomas
 */

public class StringUtil {
    // public static void main(String[] args) {

    /*
     * String content =
     * "A program consist of set of instructions which are either variables or functions.\nBy default, external variables and functions have the property that all references to the same concept."
     * ;
     * String searchTerm = "consist of set of instructions";
     * 
     * System.out.println("seq" + (findSequence(content, searchTerm, null) ? "" :
     * "\snot") + "\sfound"
     * + "for statement \n\n" + content);
     * 
     * String replaceTerm = "can be seen as a sequence of steps";
     * String replacedContent = replaceSequence(content, searchTerm, replaceTerm);
     * System.out.println("replaced with: \n\n" + replacedContent);
     * 
     * replaceTerm = "";
     * replacedContent = replaceSequence(content,
     * "By default, external variables and functions have the property that all references to the same concept."
     * , replaceTerm);
     * System.out.println("replaced with: \n\n" + replacedContent);
     */

    /*
     * String replaceTerm = "20, 200, 23440, 21, 45, 506";
     * String replacedContent = replace("functional programming", "al program",
     * "_of_");
     * System.out.println("replaced with: \n\n" + replacedContent);
     * 
     * }
     */
    public enum SeekPosExclusionCriteria {
        excludeFirst, excludeCurrent, excludeLast, excludeNone
    }

    public enum CharacterType {
        Numeric, Alphabetic, AlphaNumeric, Symbols, ASCII
    }

    public enum Orientation {
        Top, Right, Bottom, Left
    }

    enum TrimDirection {
        Leading, Trailing
    }

    enum Direction {
        Leading, Trailing
    }

    private static LinkedHashMap<String, Object> utilInfo = new LinkedHashMap<>();

    public static void clearConsoleLine(int len) {
        for (int i = 0; i != len; ++i) {
            System.out.print("\b");
        }
    }

    public static String clearLine(String line, int len) {
        char[] lineArr = line.toCharArray();

        for (int i = 0, clf = lineArr.length - 1; i < len; i++, clf--) {
            lineArr[clf] = '\0';
        }
        return new String(lineArr);
    }

    public static char[] pad(char[] mainArr, char padChar, int times, Direction dir) {
        // pos -1(prefix) 0(?) 1(postfix)
        try {

            for (int i = 0; i < times; i++) {

                if (dir.equals(Direction.Leading)) {
                    mainArr = addAt(mainArr, 0, padChar);
                } else if (dir.equals(Direction.Trailing)) {
                    mainArr = addAt(mainArr, mainArr.length - 1, padChar);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainArr;

    }

    private static char[] removeFrom(char[] mainArr, int ti) {

        var finalArr = (char[]) Array.newInstance(mainArr.getClass().getComponentType(), 0);
        for (int i = 0; i < mainArr.length; i++) {

            if (i == ti) {
                continue;
            } else {
                finalArr = addToCharArray(finalArr, mainArr[i]);
            }
        }

        return (char[]) finalArr;
    }

    public static char[] trimZeroes(char[] mainArr, StringUtil.Direction dir) {
        try {

            if (dir.equals(Direction.Leading)) {

                for (int i = 0; i < mainArr.length; i++) {

                    if (mainArr[i] == '0') {
                        mainArr = removeFrom(mainArr, i);
                        --i;
                    }

                    if (i + 1 < mainArr.length - 1 && mainArr[i + 1] != '0') {
                        break;
                    }

                }
            } else if (dir.equals(Direction.Leading)) {

                for (int i = mainArr.length; i >= 0; i--) {

                    if (mainArr[i] == '0') {
                        mainArr = removeFrom(mainArr, i);
                    }

                    if (i - 1 > 0 && mainArr[i - 1] != '0') {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainArr;
    }

    @SuppressWarnings("unchecked")
    public static char[] addAt(char[] mainArr, int targetiIndex, char item) throws Exception {

        char[] mainArrInc = (char[]) Array.newInstance(mainArr.getClass().getComponentType(), mainArr.length + 1);

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

        return (char[]) mainArrInc;

    }

    private static String replace(String content, String searchTerm, String replaceTerm) {
        String finalContent = null;

        char[] contentArr = content.toCharArray();
        char[] searchTermArr = searchTerm.toCharArray();
        char[] identifiedSeqArr = new char[searchTermArr.length];

        // p1 + m + p2
        int p1EndsAt = contentArr.length;
        int p2StartsFrom = contentArr.length;

        int termIdIndex = 0;
        for (int i = 0; i < contentArr.length; i++) {

            if (identifiedSeqArr.length == 0) {
                termIdIndex = 0;
            }

            for (int j = termIdIndex; j < searchTermArr.length; j++) {
                if (contentArr[i] == searchTermArr[j]) {

                    // index pos
                    if (identifiedSeqArr[0] == 0) {
                        p1EndsAt = i + 1;
                    }

                    identifiedSeqArr[j] = searchTermArr[j];
                    termIdIndex += j;
                    i++;

                    if (identifiedSeqArr[identifiedSeqArr.length - 1] != 0
                            && identifiedSeqArr.length == searchTermArr.length) {
                        // return true;

                        p2StartsFrom = p1EndsAt + searchTermArr.length - 1;

                        // p1 + m + p2
                        char[] p1 = new char[p1EndsAt];
                        System.arraycopy(contentArr, 0, p1, 0, p1.length - 1);
                        char[] p2 = new char[contentArr.length - p2StartsFrom];
                        System.arraycopy(contentArr, p2StartsFrom, p2, 0, p2.length);

                        char[] m = replaceTerm.toCharArray();

                        char[] fcArr = new char[p1.length + m.length + p2.length];

                        System.arraycopy(p1, 0, fcArr, 0, p1.length);
                        System.arraycopy(m, 0, fcArr, p1.length - 1, m.length);
                        System.arraycopy(p2, 0, fcArr, p1.length + m.length, p2.length);

                        finalContent = new String(fcArr);
                        return finalContent;

                    }

                } else {
                    // reset
                    termIdIndex = 0;
                    identifiedSeqArr = new char[searchTermArr.length];
                    break;
                }

            }
        }

        return finalContent;

    }

    private static boolean find(String content, String searchTerm, String criteria) {

        char[] contentArr = content.toLowerCase().toCharArray();
        char[] searchTermArr = searchTerm.toLowerCase().toCharArray();
        char[] identifiedSeqArr = new char[searchTermArr.length];
        int termIdIndex = 0;
        for (int i = 0; i < contentArr.length; i++) {

            if (identifiedSeqArr.length == 0) {
                termIdIndex = 0;
            }

            for (int j = termIdIndex; j < searchTermArr.length; j++) {
                if (contentArr[i] == searchTermArr[j]) {
                    identifiedSeqArr[j] = searchTermArr[j];
                    termIdIndex += j;
                    i++;

                    if (identifiedSeqArr[identifiedSeqArr.length - 1] != 0
                            && identifiedSeqArr.length == searchTermArr.length) {
                        return true;
                    }

                } else {
                    // reset
                    termIdIndex = 0;
                    identifiedSeqArr = new char[searchTermArr.length];
                    break;
                }

            }
        }

        return false;

    }

    private static void matchCase() {
        int start = 0, end = 127;
        for (int i = start; i < end; i++) {
            var current = (char) i;
            System.err.print(current);

            int factor = 0;
            // for nums
            if (i > 32 && i < 65) {
                factor = 16;
            }
            // for alphabets
            if (i > 65 && i < 65 + 32) {
                factor = 32;
            }

            char convCase = (char) (current / 1 + factor);
            System.err.print(convCase);

            if (i != end) {
                System.err.print("\s");
            }

        }

        List<String> aList = new ArrayList<>();
        // DList<String> aList2 = new DList<>();
        // aList2.add("");
    }

    public static String randomIDGenerator(int idLength, boolean includeChars, boolean includeNumbers,
            boolean includeSymbols)
            throws Exception {

        String generated = null;
        int totalSize = 0;
        // int startFrom = includeSymbols ? 33 : 48;
        int startFrom = 33;

        int seekLimit = startFrom + 1;
        if (includeSymbols) {
            int cap = 15;
            totalSize += cap;
            seekLimit += cap;
        }

        if (includeNumbers) {

            if (!includeSymbols) {
                startFrom = 48;
            }

            int cap = 10;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        if (includeChars) {

            if (!includeSymbols && !includeNumbers) {
                startFrom = 65;
            }

            int cap = 52;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        char[] chars = new char[totalSize];

        for (int id = startFrom, pi = 0; id < 123; id++) {
            if ((includeSymbols && id >= 33 && id <= 47) ||
                    (includeNumbers && id >= 48 && id <= 57) ||
                    (includeChars && ((id >= 65 && id <= 90) || (id >= 97 && id <= 122)))) {

                chars[pi] = (char) id;
                pi++;
            }

            if (chars[chars.length - 1] != '\0') {
                break;
            }
        }

        // System.out.print(chars);

        char[] genArr = new char[idLength];
        for (int i = 0; i < idLength; i++) {
            int cindex = Double.valueOf(Math.random() * chars.length).intValue();
            // System.out.print(cindex + "\s");

            if (cindex < seekLimit) {
                genArr[i] = chars[cindex];
            } else {
                i--;
                throw new Exception("invalid fn result");
            }

        }

        generated = new String(genArr);

        return generated;
    }

    public static String randomIDGeneratorOR(int idLength, boolean includeChars, boolean includeNumbers,
            boolean includeSymbols)
            throws Exception {
        String generated = null;
        int totalSize = 0;
        int startFrom = includeSymbols ? 33 : 48;

        int seekLimit = startFrom + 1;
        if (includeSymbols) {
            int cap = 15;
            totalSize += cap;
            seekLimit += cap;
        }

        if (includeNumbers) {
            int cap = 10;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        if (includeChars) {
            int cap = 52;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        char[] genArr = new char[idLength];
        int outOfRangeResCount = 0;

        for (int i = 0; i < idLength; i++) {

            int id = Double.valueOf(Math.random() * seekLimit).intValue();

            // validate

            if ((includeSymbols && id >= 33 && id <= 47) ||
                    (includeNumbers && id >= 48 && id <= 57) ||
                    (includeChars && ((id >= 65 && id <= 90) || (id >= 97 && id <= 122)))) {

                genArr[i] = (char) id;
            } else {
                i--;
                outOfRangeResCount += 1;
                // throw new Exception("invalid fn result");
            }
        }

        if (outOfRangeResCount > 0) {
            System.out.println("out of range count " + outOfRangeResCount);
        }

        generated = new String(genArr);
        return generated;
    }

    public static String errorAt(char[] blockArr, int stopAt) {

        char[] ecArr = new char[0];
        for (int i = 0; i < blockArr.length; i++) {

            if (i == stopAt) {
                break;
            } else {
                ecArr = ContainerUtil.addToCharArray(ecArr, blockArr[i]);
            }
            // System.out.println(blockArr[i]);
        }

        return new String(ecArr);
    }

    static int hasChar(char[] arr, char item) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == item) {
                return 1;
            }
        }
        return 0;
    }

    static int hasChars(char[] mainArr, char[] items) {

        int hasCount = 0;
        for (int i = 0; i < mainArr.length - 1; i++) {

            for (int j = 0; j < items.length - 1; j++) {

                if (mainArr[i] == items[j]) {
                    hasCount += 1;
                }
            }
        }

        if (hasCount > 0) {
            return 1;
        }

        return 0;
    }

    public static int checkExactSequence(char[] fragment, char[][] sequences) {

        /*
         * char[][] sequences = { { '"', ':', '"' }, { '"', ',', '"' }, {
         * '"', '}', '}' }, { '"', '}', ']' },
         * { '"', ']', '}' }, { '"', ']', ']' }, {',','"'} };
         */

        int exactMatch = 0;
        int matchCount = 0;

        for (int g = 0; g < sequences.length; g++) {

            matchCount = 0;
            exactMatch = 0;
            char[] cseq = sequences[g];

            int sp = 0;
            for (int h = 0; h < cseq.length; h++) {
                if (fragment[sp] == cseq[h]) {
                    ++matchCount;
                    ++sp;
                } else {
                    break;
                }
            }

            if (matchCount == fragment.length) {
                exactMatch = 1;

                /*
                 * utilInfo.put("clseq_exactMatch", 1);
                 * utilInfo.put("clseq_matchScore", matchCount);
                 */

                return 1;
            }

        }

        return 0;

    }

    public static String indentBy(int factor, char indentchar) {
        String indented = "";
        for (int i = 0; i < factor; i++) {
            indented += indentchar;
        }

        return indented;
    }

    public static String indentAndNewLineBy(int factor, char indentchar) {
        String indented = "";
        for (int i = 0; i < factor; i++) {
            indented += indentchar;
        }

        indented += "\r\n";

        return indented;
    }

    static int occurs(char[] arr, char item) {

        int counter = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == item) {
                ++counter;
                ;
            }
        }
        return counter;
    }

    public static String camelCase(String columnName) {

        return columnName.substring(0, 1).toLowerCase() + columnName.substring(1);

    }

    public static String firstCharUpperCase(String columnName) {

        return columnName.substring(0, 1).toUpperCase() + columnName.substring(1).toLowerCase();

    }

    @Deprecated
    public static String nameCase(String columnName) {
        // needs dictionary
        return columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

    }

    public static Date parseDate(String cval) {
        var dateMap = MapUtil.convertToDateMap(cval);

        var ldtIns = LocalDateTime
                .of(Integer.parseInt(dateMap.get("year")),
                        Integer.parseInt(dateMap.get("month")),
                        Integer.parseInt(dateMap.get("day")),
                        Integer.parseInt(dateMap.get("hours")),
                        Integer.parseInt(dateMap.get("minutes")),
                        Integer.parseInt(dateMap.get("seconds")))
                .toInstant(ZoneOffset.UTC);
        var cdate = Date.from(ldtIns);
        return cdate;

    }

    public static char[] addToCharArray(char[] target, char item) {
        char[] cArray = (char[]) target;
        char[] cArrayInc = new char[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    private static char[] addAllToCharArray(char[] mainArr, char[] subArr) {

        for (int i = 0; i < subArr.length; i++) {
            mainArr = addToCharArray(mainArr, subArr[i]);
        }

        return mainArr;

    }

    @SuppressWarnings("unchecked")
    public static <E> E[] add(E[] mainArr, E item) {

        E[] mainArrInc = (E[]) Array.newInstance(mainArr.getClass().getComponentType(), mainArr.length + 1);

        for (int i = 0; i < mainArr.length; i++) {
            mainArrInc[i] = mainArr[i];
        }

        mainArrInc[mainArrInc.length - 1] = item;
        return (E[]) mainArrInc;

    }

    public static <E> E[] add(E[] target, E[] cArrayInc, E item) {

        // var cArrayInc = Arrays.copyOf(target, target.length + 1, target.getClass());
        for (int i = 0; i < target.length; i++) {
            cArrayInc[i] = target[i];
        }

        cArrayInc[cArrayInc.length - 1] = item;
        return (E[]) cArrayInc;

    }

    public static String[] split(String content, String splitter) {
        String[] contentParts = new String[0];
        char[] contentArr = content.toCharArray();

        if (splitter.length() == 1) {

            char splitChar = splitter.charAt(0);
            char[] cseq = new char[0];
            for (int i = 0; i < contentArr.length; i++) {

                if (contentArr[i] != splitChar) {
                    cseq = addToCharArray(cseq, contentArr[i]);
                }

                if (cseq.length > 0 && (contentArr[i] == splitChar || i == contentArr.length - 1)) {
                    contentParts = add(contentParts, new String[contentParts.length + 1], new String(cseq));
                    cseq = new char[0];
                }

            }

        } else if (splitter.length() > 1) {

            int jpos = 0;
            char[] splitterArr = splitter.toCharArray();
            char[] cseq = new char[0];
            char[] splitMatchSeq = new char[0];
            for (int i = 0; i < contentArr.length; i++) {

                int ic = i;
                for (int j = 0; j < splitterArr.length; j++) {

                    if (contentArr[ic] == splitterArr[j] && contentArr.length - i - 1 > splitterArr.length - 1) {
                        splitMatchSeq = addToCharArray(splitMatchSeq, contentArr[ic]);
                        ic++;
                    } else {
                        splitMatchSeq = new char[0];
                        break;
                    }

                }

                if (equate(splitMatchSeq, splitterArr) == 1 || i == contentArr.length - 1) {
                    if (cseq.length > 0) {
                        contentParts = add(contentParts, new String[contentParts.length + 1], new String(cseq));
                    }
                    cseq = new char[0];
                } else {
                    cseq = addToCharArray(cseq, contentArr[i]);
                }

                if (splitMatchSeq.length > 0) {
                    i += splitMatchSeq.length - 1;
                    splitMatchSeq = new char[0];
                }

            }

        }

        return contentParts;

    }

    private static int equate(char[] firstArr, char[] secondArr) {

        if (firstArr == null || secondArr == null || firstArr.length != secondArr.length) {
            return 0;
        }

        int hasCount = 0;
        for (int i = 0; i < firstArr.length; i++) {

            if (firstArr[i] == secondArr[i]) {
                hasCount += 1;
            } else {
                return 0;
            }

        }

        if (hasCount == firstArr.length) {
            return 1;
        }

        return 0;
    }

    public static int checkGroupSeq(char[] contentArr, int from, char[][] groupArr) {

        char[] potSeq = new char[0];

        for (int j = 0; j < groupArr.length; j++) {
            int ic = from;
            char[] grpStrArr = groupArr[j];
            int matchCount = 0;

            for (int k = 0; k < grpStrArr.length; k++) {

                if (contentArr[ic] == grpStrArr[k] && contentArr.length - from - 1 > groupArr.length - 1) {
                    potSeq = addToCharArray(potSeq, contentArr[ic]);
                    ic++;
                    ++matchCount;
                } else {
                    potSeq = new char[0];
                    break;
                }
            }

            if (matchCount == groupArr[j].length) {

                utilInfo = new LinkedHashMap<>();
                utilInfo.put("matchedSequence", new String(potSeq));
                utilInfo.put("mainSeekPos", ic);
                utilInfo.put("runningIndex", ic - from);

                return 1;
            }

        }

        return 0;
    }

    private static char[][] addToCharArray2D(char[][] target, char[] item) {
        char[][] cArray = (char[][]) target;
        char[][] cArrayInc = new char[cArray.length + 1][cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;
    }

    public static char[][] stringArrayToCharArray2D(String[] mainArr) {

        if (mainArr == null) {
            return null;
        }

        char[][] charArr2D = new char[0][];
        // char[] cword = new char[0];
        for (int i = 0; i < mainArr.length; i++) {
            char[] cword = mainArr[i].toCharArray();
            charArr2D = addToCharArray2D(charArr2D, cword);
        }

        return charArr2D;
    }

    public static String slice(String content, String[] openingSeq, String[] closingSeq,
            SeekPosExclusionCriteria openingBoundCriteria, SeekPosExclusionCriteria closingBoundCriteria,
            String[] filterSeq) {
        String slicedContent = "";
        char[] contentArr = content.toCharArray();

        char[][] openingSeqArr = stringArrayToCharArray2D(openingSeq);
        char[][] closingSeqArr = stringArrayToCharArray2D(closingSeq);
        char[][] filterSeqArr = stringArrayToCharArray2D(filterSeq);
        char[] cseq = new char[0];
        char[] potSeq = new char[0];
        char[] cword = new char[0];
        int opBoundFoundOcc = 0;
        int boundInd = -1;

        for (int i = 0; i < contentArr.length; i++) {

            if (checkGroupSeq(contentArr, i, openingSeqArr) == 1) {

                i += Integer.valueOf(utilInfo.get("runningIndex").toString()) - 1;
                String matchedSequence = (String) utilInfo.get("matchedSequence");

                ++opBoundFoundOcc;
                boundInd = 1;
                cword = new char[0];
                utilInfo = null;
                if (openingBoundCriteria.equals(SeekPosExclusionCriteria.excludeFirst)) {

                } else {
                    cword = addAllToCharArray(cword, matchedSequence.toCharArray());
                }

                continue;

            }

            if (checkGroupSeq(contentArr, i, closingSeqArr) == 1) {
                // i += Integer.valueOf(utilInfo.get("runningIndex").toString()) - 1;
                utilInfo = null;
                boundInd = -1;
                if (closingBoundCriteria.equals(SeekPosExclusionCriteria.excludeLast)) {
                    // cword = new char[0];
                    utilInfo = null;
                    break;
                }
            }

            if (checkGroupSeq(contentArr, i, filterSeqArr) == 1) {
                i += Integer.valueOf(utilInfo.get("runningIndex").toString()) - 1;
                utilInfo = null;
            } else {
                cword = addToCharArray(cword, contentArr[i]);
            }

            if (opBoundFoundOcc > 0 && boundInd == -1) {
                break;
            }

        }

        slicedContent = new String(cword);
        return slicedContent;
    }

    public static char[] reverse(char[] currentArr) {
        char[] reverseArr = new char[0];

        for (int i = currentArr.length - 1; i >= 0; i--) {
            reverseArr = addToCharArray(reverseArr, currentArr[i]);
        }

        return reverseArr;
    }

    public static int extractCountable(String sequence) {
        char[] numSeqArrP1 = numAccumalator(sequence.toCharArray());

        String numSeqStr = new String(numSeqArrP1);
        int finalNum = Integer.parseInt(numSeqStr);
        return finalNum;
        // return new String(numSeqArrP1);
    }

    public static String extractCountable(String sequence, Direction currentDirection) {

        char[] seqArr = sequence.toCharArray();
        char[] numSeqArrP1 = numAccumalator(seqArr);

        if (currentDirection.equals(Direction.Trailing)) {
            numSeqArrP1 = reverse(numAccumalator(reverse(numSeqArrP1)));
        }

        return new String(numSeqArrP1);
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

                finalSeq = addToCharArray(finalSeq, seqArr[i]);
            } else {

                if (i > 0 && nzNumSeqStartInd > 0) {
                    break;
                }

                continue;

            }

        }

        return finalSeq;
    }

    private static String[] charArrayToStringArray(char[] charArr) {

        String[] strArr = new String[0];
        for (int i = 0; i < charArr.length; i++) {
            strArr = add(strArr, new String(new char[] { charArr[i] }));
        }

        return strArr;

    }

    public static int isAlphaNumeric(char x) {

        // if (x > 32 && x < 177) {
        if ((x >= 48 && x <= 57) || (x >= 65 && x >= 90)) {
            return 1;
        } else {
            return 0;
        }

    }

    public static char[] alphabet() {
        char[] finalArr = new char[0];
        for (int x = 0; x < 123; x++) {

            if (((x >= 65 && x <= 90) || (x >= 97 && x <= 122))) {
                // System.out.print(((char) x) + "-" + x + "\s");
                finalArr = addToCharArray(finalArr, (char) x);
            }
        }

        return finalArr;
    }

    public static char toLowerCase(char target) {

        var alphabet = alphabet();
        var lcChar = target;
        for (int i = 0; i < alphabet.length; i++) {

            if (alphabet[i] == target) {

                if (i < 26) {
                    lcChar = alphabet[i + 26];
                }

                break;

            }

        }

        return lcChar;

    }

    public static char toUpperCase(char target) {

        var alphabet = alphabet();
        var lcChar = target;
        for (int i = 0; i < alphabet.length; i++) {

            if (alphabet[i] == target) {

                if (i > 25) {
                    lcChar = alphabet[i - 26];
                }

                break;

            }

        }

        return lcChar;

    }

    public static char[] nonAlphaNumericChars() {

        char[] finalArr = new char[0];
        for (int x = 0; x < 256; x++) {
            if ((x >= 48 && x <= 57) ||
                    (((x >= 65 && x <= 90) || (x >= 97 && x <= 122)))) {

            } else {
                finalArr = addToCharArray(finalArr, (char) x);
            }

        }

        return finalArr;

    }

    public static String[] nonAlphaNumericChars_StringArray() {

        return charArrayToStringArray(nonAlphaNumericChars());

    }

    public static String removeNonAlphaNumericChars(char[] contentArr) {

        var charArr2D = stringArrayToCharArray2D(StringUtil.nonAlphaNumericChars_StringArray());
        String res = new String(MapUtil.removeSequences(contentArr, charArr2D));
        return res;

    }

    public static String removeNonAlphaNumericChars(String content) {

        var charArr2D = stringArrayToCharArray2D(StringUtil.nonAlphaNumericChars_StringArray());
        String res = new String(MapUtil.removeSequences(content.toCharArray(), charArr2D));
        return res;

    }

    public static String removeInvalidLangVarSequences(String content) {

        var filtRes = MapUtil.filter(StringUtil.nonAlphaNumericChars_StringArray(), new String[] { "_" },
                FilterCriteria.Exclude, 0);
        //var charArr2D = stringArrayToCharArray2D(StringUtil.nonAlphaNumericChars_StringArray());
        var charArr2D = stringArrayToCharArray2D(filtRes);
        String res = new String(MapUtil.removeSequences(content.toCharArray(), charArr2D));
        return res;

    }

    public static int isBinarySequence(char[] charArr) {

        for (int i = 0; i < charArr.length; i++) {

            int cp = charArr[i];

            if (cp != 48 && cp != 49) {
                return 0;
            }

        }

        return 1;
    }

}
