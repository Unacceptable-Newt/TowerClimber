package org.example.util;

/**
 * @param first  First item in the pair
 * @param second Second item in the pair
 * @author Yucheng Zhu
 * Contains a pair of different objects.
 * Pair is immutable. To change its value, create a new `Pair` object.
 */
public record Pair<S, T>(S first, T second) {}
