package com.sf9000.anagram;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class BinaryTest {

    @Test
    public void myTest() {

        Map<Character, Integer> equivalentSet = new HashMap<>();
        equivalentSet.put('a',1);
        equivalentSet.put('b',2);
        equivalentSet.put('c',4);
        equivalentSet.put('d',8);
        equivalentSet.put('e',16);
        equivalentSet.put('f',32);
        equivalentSet.put('g',64);
        equivalentSet.put('h',128);
        equivalentSet.put('i',256);
        equivalentSet.put('j',512);
        equivalentSet.put('k',1024);
        equivalentSet.put('l',2048);
        equivalentSet.put('m',4096);
        equivalentSet.put('n',8192);
        equivalentSet.put('o',16384);
        equivalentSet.put('p',32768);
        equivalentSet.put('q',65536);
        equivalentSet.put('r',131072);
        equivalentSet.put('s',262144);
        equivalentSet.put('t',524288);
        equivalentSet.put('u',1048576);
        equivalentSet.put('v',2097152);
        equivalentSet.put('w',4194304);
        equivalentSet.put('x',8388608);
        equivalentSet.put('y',16777216);
        equivalentSet.put('z',33554432);



        String myWord = "best";

        char[] myWordCharArray = myWord.toCharArray();

        int mySum = 0;

        for (char c : myWordCharArray) {
            Integer binaryEquivalent = equivalentSet.get(c);
            mySum = mySum | binaryEquivalent;
        }

        System.out.println(Integer.toBinaryString(mySum));

        String myPhrase = "bestfriend";

        char[] myPhraseCharArray = myPhrase.toCharArray();

        int myPhraseSum = 0;

        for (char c : myPhraseCharArray) {
            Integer binaryEquivalent = equivalentSet.get(c);
            myPhraseSum = myPhraseSum | binaryEquivalent;
        }

        System.out.println(Integer.toBinaryString(myPhraseSum));



    }


    @Test
    public void testWithArray(){

        String myPhrase = "bestsecret";

        List<String> dictionary = new ArrayList<>();
        dictionary.add("beets");
        dictionary.add("beset");
        dictionary.add("crest");
        dictionary.add("crete");
        dictionary.add("better");
        dictionary.add("cess");
        dictionary.add("betters");
        dictionary.add("sec");

        int[] letters = new int[1<<8];

        for(char c : myPhrase.toCharArray()){
            letters[c]++;
        }

        boolean isFoundAllChars = false;

        for (int a = 0; a < dictionary.size() && !isFoundAllChars; a++) {

            String dictionaryWord = dictionary.get(a);

            for(char c : dictionaryWord.toCharArray()){
                letters[c]--;
            }

            int sumLetters = 0;
            for(int i : letters){
                if(i<0) {
                    System.out.println("menor que zero");
                } else {

                }
                sumLetters = sumLetters + i;


            }

            if (sumLetters==0){
                isFoundAllChars = true;
                System.out.println("achou");
                System.out.print(dictionary.get(a));
            } else {
                System.out.println(a + ":" + sumLetters);
                System.out.println(dictionary.get(a));
            }

        }

    }


}
