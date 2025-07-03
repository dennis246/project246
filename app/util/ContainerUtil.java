package app.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/** 
 * @note experimental utility methods 
 * @author Dennis Thomas
 */

public class ContainerUtil {

    public static Integer[] distinct(Integer[] arr) {
        // Integer[] sdp = { 3, 3, 4, 4, 5, 3, 7, 9 };
        Integer[] checked = new Integer[arr.length];
        checked[0] = arr[0];
        int distinctSize = 0;

        for (int i = 1, pi = 1; i < arr.length; i++) {

            if (!contains(checked, arr[i])) {
                checked[pi] = arr[i];
                pi++;
            }
        }

        for (var x : checked) {
            if (x != 0) {
                distinctSize += 1;
            }
        }

        Integer[] cpArr = new Integer[distinctSize];

        for (int i = 0, pi = 0; i < arr.length; i++) {
            if (contains(cpArr, arr[i])) {
                continue;
            } else {

                cpArr[pi] = arr[i];
                if (pi == cpArr.length - 1) {
                    break;
                }
                pi++;

            }
        }

        return cpArr;
    }

    static boolean contains(Integer[] arr, int element) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == element) {
                return true;
            }
        }
        return false;
    }

    public static Integer[] swap(Integer[] e) {
        int temp = e[0];
        if (e[0] > e[1]) {
            e[1] = e[0];
            e[0] = temp;
        }
        return e;
    }

    public static Integer[] slice(Integer[] e, int from, int to) {

        int size = to > 0 && to > from ? to - from : from;
        Integer[] e1 = new Integer[size];
        for (int i = 0, j = from; i <= size - 1; j++, i++) {
            e1[i] = e[j];
            /*
             * if (to != 0 && i == (to - 1)) {
             * break; || j <= e.length - 1
             * }
             */
        }

        return e1;

    }

    public static Integer[] copyTo(Integer[] des, int desFrom, Integer[] src, int srcFrom) {

        Integer[] consDes = new Integer[des.length];
        if (src.length > des.length) {

            // check vacant spaces

            int vcount = vacantCount(des);

            if (vcount > 0) {
                des = distinct(des);
                des = defrag(des, vcount);
            }

            // resize des
            int diff = src.length - des.length;
            consDes = new Integer[des.length + diff];
        } else {
            consDes = des;
        }

        for (int d = 0, pi = desFrom; d < src.length; d++, pi++) {
            consDes[pi] = src[d];
        }
        return consDes;

    }

    public static int vacantCount(Integer[] arr) {
        int vcount = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == null) {
                vcount += 1;
            }
        }

        return vcount;
    }


    public static <E extends Object> E[] defrag(E[] arr, int actsize) {
        E[] anewArr = (E[]) Array.newInstance(arr.getClass().getComponentType(), 0);

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != null) {
                // vcount += 1;
                anewArr[i] = arr[i];
            }
        }

        return anewArr;
    }






    public static Integer[] defrag(Integer[] arr, int actsize) {
        Integer[] anewArr = new Integer[actsize];

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != 0) {
                // vcount += 1;
                anewArr[i] = arr[i];
            }
        }

        return anewArr;
    }

    public static Integer[] fullCopy(Integer[] arr) {
        Integer[] cpArr = new Integer[arr.length];

        for (int i = 0; i <= arr.length - 1; i++) {
            cpArr[i] = arr[i];
        }

        return cpArr;

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

            e = removeItem(e, pe);
            e2[pi] = pe;
            pi++;
            --i;
        }

        return e2;

    }

    public static int[] purgeSort(int[] e, SortMode sortOrder) {
        int[] e2 = new int[e.length];
        for (int i = 0, pi = 0; i < e.length; i++) {

            if (e2[e2.length - 1] != 0) {
                break;
            }

            int pe = 0;
            if (sortOrder.equals(SortMode.Desc)) {
                pe = maxOfArray(e);
            } else {
                pe = minOfArray(e);
            }

            e = removeItem(e, pe);
            e2[pi] = pe;
            pi++;
            --i;
        }

        return e2;

    }

    public static <E extends Object> E[] removeItem(E[] arr, E[] narr, E item) {
        // narr to be init before method call
        // E[] narr = new E[arr.length - 1];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {

                if (arr[i].equals(item)) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }

            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static Integer[] removeItem(Integer[] arr, int item) {

        Integer[] narr = new Integer[arr.length - 1];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (arr[i] == item) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static int[] removeItem(int[] arr, int item) {

        int[] narr = new int[arr.length - 1];
        try {
            int itemFoundInd = 0;
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (arr[i] == item && itemFoundInd == 0) {
                    ++itemFoundInd;
                    continue; 
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static char[] removeItem(char[] arr, char item) {

        char[] narr = new char[arr.length - 1];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (arr[i] == item) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static char[] removeItem(char[] arr, int targetIndex) {

        char[] narr = new char[arr.length - 1];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (i == targetIndex) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static char[][] removeItem(char[][] arr, int targetIndex) {

        char[][] narr = new char[arr.length - 1][];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (i == targetIndex) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static int minOfArray(Integer[] e) {

        int min = 0;
        try {
            min = e[0];
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

    public static int minOfArray(int[] e) {

        int min = 0;
        try {
            min = e[0];
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

    public static int maxOfArray(Integer[] e) {

        int max = 0;
        try {
            max = e[0];
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

        int max = 0;
        try {
            max = e[0];
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

    public static char[][] addToCharArray2D(char[][] target, char[] item) {
        char[][] cArray = (char[][]) target;
        char[][] cArrayInc = new char[cArray.length + 1][cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;
    }

    public static char[] addToCharArray(char[] target, char item) {
        char[] cArray = target;
        char[] cArrayInc = new char[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;
        return cArrayInc;

    }

    public static char[] addAllToCharArray(char[] mainArr, char[] subArr) {

        for (int i = 0; i < subArr.length; i++) {
            mainArr = addToCharArray(mainArr, subArr[i]);
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

    public static int[] addToIntArray(int[] target, Integer item) {
        int[] cArray = (int[]) target;
        int[] cArrayInc = new int[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

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



    
    @SuppressWarnings("unchecked")
    public static <E> E[] addAt(E[] mainArr, int targetiIndex, E item) throws Exception {

        E[] mainArrInc = (E[]) Array.newInstance(mainArr.getClass().getComponentType(), mainArr.length + 1);

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

        return (E[]) mainArrInc;

    }









    public static <E> E[] remove(E[] target, int from) throws Exception {

        if (target == null || from > target.length - 1 || from < 0) {
            throw new Exception("invalid index for method char[] removeFrom(char[] arr, int targetIndex)");
        }

        E[] cArray = (E[]) target;
        var cArrayInc = Arrays.copyOf(target, target.length - 1, target.getClass());
        for (int i = 0; i < cArray.length; i++) {

            if (i == from) {
                continue;
            }

            cArrayInc[i] = cArray[i];
        }

        return (E[]) cArrayInc;

    }

    @SuppressWarnings("unchecked")
    private static Object[] initArray(Object[] target, Class cc) {
        return new Object[0];
    }

    public static <E> E[] addAll(E[] mainArr, E[] subArr) {
        for (int i = 0; i < subArr.length; i++) {
            mainArr = add(mainArr, subArr[i]);
        }

        return mainArr;
    }

    public static <E> int has(E[] arr, E item) {

        int checkMode = 1;
        if (item instanceof String) {
            checkMode = 2;
        }

        for (int i = 0; i < arr.length; i++) {

            if (checkMode == 1) {
                if (arr[i] == item) {
                    return 1;
                }

            } else if (checkMode == 2) {

            }
        }
        return 0;
    }

    public static int matches(char[] firstArr, char[] secondArr) {

        int matchCount = 0;
        for (int i = 0; i < secondArr.length; i++) {
            if (i <= firstArr.length - 1) {
                if (secondArr[i] == firstArr[i]) {
                    ++matchCount;
                }
            }
        }

        if (matchCount == secondArr.length) {
            return 1;
        }

        return 0;
    }

    static int hasChars(char[] mainArr, char[] items) {
        // criteria : 1 = 'and' while 0 = 'or'
        int[] scoreArr = new int[items.length];
        int ri = 0;
        int hasCount = 0;
        for (int i = 0; i < mainArr.length; i++, ri++) {

            for (int j = 0; j < items.length; j++) {

                if (mainArr[i] == items[j]) {
                    hasCount += 1;
                    scoreArr[j] += 1;
                }
            }
        }

        int scoreSum = MathUtil.sumOf(scoreArr);
        if (hasCount > 0 && scoreSum >= scoreArr.length) {
            return 1;
        }

        return 0;
    }

    /*
     * static int hasChars(char mainArr, char[] items) {
     * 
     * int[] scoreArr = new int[items.length];
     * int hasCount = 0;
     * 
     * for (int j = 0; j < items.length; j++) {
     * 
     * if (mainArr == items[j]) {
     * hasCount += 1;
     * scoreArr[j] += 1;
     * }
     * }
     * 
     * // int scoreSum = sumOf(scoreArr);
     * if (hasCount > 0 // && scoreSum >= scoreArr.length
     * ) {
     * return 1;
     * }
     * 
     * return 0;
     * }
     */

    static char[] removeChars(char[] src, char[] items) {

        char[] filteredArr = new char[0];
        try {
            for (int i = 0; i < src.length; i++) {

                int itemsFound = 0;
                for (int j = 0; j < items.length; j++) {
                    if (src[i] == items[j]) {
                        ++itemsFound;
                    }
                }

                if (itemsFound == 0) {
                    filteredArr = addToCharArray(filteredArr, src[i]);
                }

            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return filteredArr;

    }

    public static char[] removeFrom(char[] arr, int targetIndex) throws Exception {

        if (arr == null || targetIndex > arr.length - 1 || targetIndex < 0) {
            throw new Exception("invalid index for method char[] removeFrom(char[] arr, int targetIndex)");
        }

        char[] newArr = new char[0];
        for (int i = 0; i < arr.length; i++) {

            if (i == targetIndex) {
                continue;
            }
            newArr = addToCharArray(newArr, arr[i]);

        }

        return newArr;
    }

    public static int[] addAllToIntArray(int[] mainArr, int[] subArr) {
        for (int i = 0; i < subArr.length; i++) {
            mainArr = addToIntArray(mainArr, subArr[i]);
        }

        return mainArr;
    }

    public static int hasInt(int[] arr, int item) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == item) {
                return 1;
            }
        }
        return 0;
    }

    public static int contains(String[] mainList, String item) {

        for (var part : mainList) {
            var res = matches(part.toCharArray(), item.toCharArray());
            if (res == 1) {
                return 1;
            }
        }

        return 0;

    }

    public static int equateObjectFields(Object obj1, Object obj2) {

        try {
            int eqc = 0;
            int nnfc = 0;
            for (Field field : obj1.getClass().getFields()) {

                field.setAccessible(true);

                if (field.get(obj1) != null) {
                    ++nnfc;
                    if (field.get(obj1).toString() == field.get(obj2).toString()) {
                        ++eqc;
                    }

                }

                field.setAccessible(false);

            }

            if (eqc == nnfc) {
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    private static int validateSortable(Object currentObject) throws Exception {
        /*
         * Class[] interfaces;
         * if(currentObject instanceof Array){
         * } else if(currentObject instanceof List){
         * }
         */

        var interfaces = currentObject.getClass().getInterfaces();
        int sortableImplExists = 0;
        for (var crow : interfaces) {

            if (crow.getName().equals("Sortable")) {
                sortableImplExists = 1;
                break;
            }
        }

        if (sortableImplExists == 0) {
            throw new Exception("Entity doesn't implment Sortable interface");
        }

        return 1;
    }

    private static <E> int validateContainer(E currentObject) throws Exception {
        var superClass = currentObject.getClass().componentType().getSuperclass();
        /*
         * Class superClass;
         * if (currentObject instanceof Array) {
         * 
         * superClass = currentObject.getClass().componentType().getSuperclass();
         * 
         * } else if (currentObject instanceof List) {
         * }
         */

        /*
         * for (var crow : contructors) {
         * 
         * if (crow.getName().equals("Sortable")) {
         * containerConstructprExists = 1;
         * break;
         * }
         * }
         */

       
        /* int containerConstructprExists = 0;
        if (containerConstructprExists == 0) {
            throw new Exception("Entity doesn't container Container Constructor");
        } */

        if(superClass.getSimpleName().equals("Container")){
            return 1;
        }


        return 0;

    }

    public static List<?> sortBy(List<?> objList, String sortByFieldName, SortMode asc) {
        try {

            Field[] fields = objList.get(0).getClass().getDeclaredFields();

            int fieldExists = 0;
            for (var field : fields) {

                if (field.getName().toLowerCase().equals(sortByFieldName.toLowerCase())) {
                    fieldExists = 1;
                    break;
                }

            }

            if (fieldExists == 0) {
                throw new Exception("No such field found for current object list");
            }

            // System.out.println(res);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return objList;
    }

    public static <E extends Container> E[] sort(E[] objArr, SortMode sortMode) throws Exception {
        return purgeSort(objArr, sortMode);
    }

    public static <E extends Container> E[] purgeSort(E[] mainArr, SortMode sortOrder) throws Exception {

        E[] sortedArr = (E[]) Array.newInstance(mainArr.getClass().getComponentType(), mainArr.length);

        for (int i = 0, pi = 0; i < mainArr.length; i++) {

            if (sortedArr[sortedArr.length - 1] != null) {
                break;
            }

            E pe = null;
            if (sortOrder.equals(SortMode.Desc)) {
                pe = maxOfArray(mainArr);
            } else {
                pe = minOfArray(mainArr);
            }

            mainArr = removeItem(mainArr, pe);
            sortedArr[pi] = pe;
            pi++;
            --i;
        }

        return sortedArr;

    }

    private static <E> E[] removeItem(E[] arr, E item) {
        E[] narr = (E[]) Array.newInstance(arr.getClass().getComponentType(), arr.length - 1);
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (arr[i] == item) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    private static <E extends Container> E minOfArray(E[] mainArr) throws Exception {

        E lastMinObj = mainArr[0];
        int min = 0;
        try {
            min = mainArr[0].sequenceNumber;
            for (int j = 0; j < mainArr.length; j++) {

                if (mainArr[j].sequenceNumber < min) {
                    min = mainArr[j].sequenceNumber;
                    lastMinObj = mainArr[j];
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        return lastMinObj;

    }

    private static <E extends Container> E maxOfArray(E[] mainArr) throws Exception {

        E lastMaxObj = mainArr[0];
        int max = 0;
        try {
            max = mainArr[0].sequenceNumber;
            for (int j = 0; j < mainArr.length; j++) {

                if (mainArr[j].sequenceNumber > max) {
                    max = mainArr[j].sequenceNumber;
                    lastMaxObj = mainArr[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return lastMaxObj;
    }

}
