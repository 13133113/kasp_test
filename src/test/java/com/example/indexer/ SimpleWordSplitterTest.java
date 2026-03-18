package com.example.indexer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class SimpleWordSplitterTest {
    @Test
    void testSplit() {
        SimpleWordSplitter splitter = new SimpleWordSplitter();
        Set<String> words = splitter.split("Hello world, hello!");
        assertEquals(Set.of("hello", "world"), words);
    }
}