package com.example.indexer;

import java.util.Set;

public interface WordSplitter {
    Set<String> split(String text);
}