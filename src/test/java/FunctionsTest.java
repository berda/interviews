import org.junit.Assert;

import java.util.ArrayList;

class FunctionsTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void arrayDiffPairs() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(8);
        arrayList.add(5);
        arrayList.add(7);
        arrayList.add(4);

        int result = Functions.arrayDiffPairs(arrayList, 1);
        Assert.assertEquals(2, result);
    }

    @org.junit.jupiter.api.Test
    void isPollindrom() {
        boolean result = Functions.isPollindrom("abba");
        Assert.assertEquals(true, result);

        result = Functions.isPollindrom("aba");
        Assert.assertEquals(true, result);

        result = Functions.isPollindrom("abab");
        Assert.assertEquals(false, result);

        result = Functions.isPollindrom("abbba");
        Assert.assertEquals(true, result);
    }

    @org.junit.jupiter.api.Test
    void countPollindrom() {
        int result = Functions.countPollindorm("baababa");
        Assert.assertEquals(6, result);
    }

    @org.junit.jupiter.api.Test
    void solution() {
        int[] intArray = new int[6];

        intArray[0] = 1;
        intArray[1] = 3;
        intArray[2] = 6;
        intArray[3] = 4;
        intArray[4] = 1;
        intArray[5] = 2;

        int result = Functions.solution(intArray);
        Assert.assertEquals(5, result);

        result = Functions.smartSolution(intArray);
        Assert.assertEquals(5, result);
    }

    @org.junit.jupiter.api.Test
    void gapSize() {
        int result = Functions.gapSize("10001".toCharArray());
        Assert.assertEquals(3, result);

        result = Functions.gapSize("100010".toCharArray());
        Assert.assertEquals(0, result);
    }

    @org.junit.jupiter.api.Test
    void binaryGap() {
        int result = Functions.binaryGap("10001");
        Assert.assertEquals(3, result);

        result = Functions.binaryGap("1010010010");
        Assert.assertEquals(2, result);

        result = Functions.binaryGap("101001000000010");
        Assert.assertEquals(7, result);
    }

    @org.junit.jupiter.api.Test
    void randomArray() {
        int[] result = Functions.randomArray(5);
        Assert.assertEquals(0, sum(result));

        result = Functions.randomArray(100);
        Assert.assertEquals(0, sum(result));
    }

    int sum(int[] array){
        int result = 0;
        for(int i=0;i<array.length;i++){
            result= result+array[i];
        }
        return result;
    }

    @org.junit.jupiter.api.Test
    void stringSplit() {
        String result = Functions.stringSplit("ABBAACACC");
        Assert.assertEquals("ACA", result);

        result = Functions.stringSplit("ABCBBCBA");
        Assert.assertEquals("", result);
    }
}