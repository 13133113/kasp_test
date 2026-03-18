package com.example.indexer;

import java.nio.file.Path;
import java.io.IOException;
import java.util.Set;

public interface Indexer {
    void addPath(Path path) throws IOException;
    void removePath(Path path);
    Set<Path> search(String word);
    void close();
}