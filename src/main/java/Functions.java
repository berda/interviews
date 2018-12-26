import java.util.*;

import static java.lang.System.out;

public class Functions {


    //--------------------------------------------------------------------------------------------

    //Given N integers, count the number of pairs of integers whose difference is K.
    public static int arrayDiffPairs(ArrayList<Integer> array, int k) {
        int result = 0;
        for (int i = 0; i < array.size(); i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (Math.abs(array.get(i) - array.get(j)) == k)
                    result = result + 1;
            }
        }
        return result;
    }

    //--------------------------------------------------------------------------------------------
    public static boolean isPollindrom(String str) {
        char[] array = str.toCharArray();

        for (int i = 0, j = str.length() - 1; i < j; i++, j--) {
            if (array[i] != array[j])
                return false;
        }
        return true;
    }

    public static int countPollindorm(String str) {
        int result = 0;

        for (int i = 0; i <= str.length() - 1; i++) {
            for (int j = i + 2; j <= str.length(); j++) {
                boolean isPol = isPollindrom(str.substring(i, j));
                if (isPol) {
                    result = result + 1;
                    out.println("Polindrom string: " + str.substring(i, j));
                }
            }
        }
        return result;
    }
//-------------------------------------------------------------------------------------------
    /*
    Write a function:

    class Solution { public int solution(int[] A); }

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

Given A = [1, 2, 3], the function should return 4.

Given A = [−1, −3], the function should return 1.

Write an efficient algorithm for the following assumptions:

        N is an integer within the range [1..100,000];
        each element of array A is an integer within the range [−1,000,000..1,000,000].


     */

    public static int solution(int[] A) {
        for (int i = 1; i < 1000000; i++) {
            for (int j = 0; j < A.length; j++) {
                if (i == A[j] || A[j] < 1)
                    break;
                if (j == A.length - 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int smartSolution(int[] A) {
        Set<Integer> set = new HashSet<>();
        for (Integer t : A) {
            set.add(t);
        }

        for (int i = 1; i < 1000000; i++) {
            if (!set.contains(i))
                return i;
        }
        return -1;
    }
//------------------------------------------------------------------------------------

    /*


A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends in the binary representation of N.

For example, number 9 has binary representation 1001 and contains a binary gap of length 2. The number 529 has binary representation 1000010001 and contains two binary gaps: one of length 4 and one of length 3. The number 20 has binary representation 10100 and contains one binary gap of length 1. The number 15 has binary representation 1111 and has no binary gaps. The number 32 has binary representation 100000 and has no binary gaps.

Write a function:

    class Solution { public int solution(int N); }

that, given a positive integer N, returns the length of its longest binary gap. The function should return 0 if N doesn't contain a binary gap.

For example, given N = 1041 the function should return 5, because N has binary representation 10000010001 and so its longest binary gap is of length 5. Given N = 32 the function should return 0, because N has binary representation '100000' and thus no binary gaps.

Write an efficient algorithm for the following assumptions:

        N is an integer within the range [1..2,147,483,647].

     */

    public static int binaryGap(String str) {
        int result = 0;

        String[] strs = str.split("1");

        for (int i = 0; i < strs.length; i++) {
            if (result < strs[i].length()) {
                result = strs[i].length();
            }
        }
        return result;
    }

//------------------------------------------------------------------------------------
    /*
    Letters A,B,C
    remove all AA, BB, CC
    [ABBAACACC]->[ACA]
    [ABCBBCBA]->[ABCCBA]->[ABBA]->[AA]->[]

    */
    public static String stringSplit(String S) {

        S = S.replace("AA", ":");
        S = S.replace("BB", ":");
        S = S.replace("CC", ":");

        if (!S.contains(":"))
            return S;
        String[] strs = S.split(":");

        StringBuilder builder = new StringBuilder();
        for(String s : strs) {
            builder.append(s);
        }
        return stringSplit(builder.toString());
    }


    //------------------------------calcluate gap size zeros between 1000...0001
    public static int gapSize(char[] chars) {//10001 return 3
        if (chars[0] != '1' || chars[chars.length - 1] != '1') {
            return 0;
        }

        int value = Integer.parseInt(String.valueOf(chars).substring(1, chars.length - 2));
        if (value == 0)
            return chars.length - 2;
        else
            return 0;
    }

    //--------------------------------------------------------------------------------------------
    /*

Write function which receive the number N, N represent the size of array which returns and contains different numbers which sum is 0. Function returns one of possible arrays
Example:

4: [-1,2,3,-4]
5: [0,2,5,-8,1]


     */
    public static int[] randomArray(int N) {
        int[] result = new int[N];
        int sum = 0;
        Set<Integer> set = new HashSet<>();
        Random rn = new Random();
        int max = rn.nextInt();
        while(set.size()<N-1) {
            int m = rn.nextInt() % max;
            set.add(m);
            sum+=m;
        }
        if (sum==0) {
            set.add(0);
        }
        else{
            set.add(sum*-1);
        }
        return set.stream().mapToInt(Integer::intValue).toArray();
    }
//--------------------------------------------------------------------------------------------
}