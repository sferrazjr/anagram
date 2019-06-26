package com.sf9000.anagram.model;

import java.util.HashMap;
import java.util.Map;

public class BinaryEquivalency {

    static final Map<Character, Integer> BINARY_EQUIVALENCY = new HashMap<>();

    static {
        BinaryEquivalency.BINARY_EQUIVALENCY.put('a', 1);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('b', 2);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('c', 4);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('d', 8);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('e', 16);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('f', 32);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('g', 64);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('h', 128);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('i', 256);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('j', 512);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('k', 1024);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('l', 2048);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('m', 4096);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('n', 8192);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('o', 16384);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('p', 32768);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('q', 65536);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('r', 131072);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('s', 262144);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('t', 524288);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('u', 1048576);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('v', 2097152);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('w', 4194304);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('x', 8388608);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('y', 16777216);
        BinaryEquivalency.BINARY_EQUIVALENCY.put('z', 33554432);
    }

}
