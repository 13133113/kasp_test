package com.example.indexer;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileIndex {
    private final Map<String, Set<Path>> index = new ConcurrentHashMap<>();

    public void addFile(Path file, Set<String> words) {
        for (String word : words) {
            index.computeIfAbsent(word, k -> ConcurrentHashMap.newKeySet()).add(file);
        }
    }

    public void removeFile(Path file) {
        for (Set<Path> files : index.values()) {
            files.remove(file);
        }
    }

    public Set<Path> getFilesForWord(String word) {
        return index.getOrDefault(word, Collections.emptySet());
    }
}