package com.example.indexer;

import java.util.HashSet;
import java.util.Set;

public class SimpleWordSplitter implements WordSplitter {
    @Override
    public Set<String> split(String text) {
        String[] words = text.toLowerCase().split("[^\\p{L}\\p{N}]+");
        Set<String> result = new HashSet<>();
        for (String w : words) {
            if (!w.isEmpty()) {
                result.add(w);
            }
        }
        return result;
    }
}