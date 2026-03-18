package com.example.indexer;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class IndexerImpl implements Indexer {
    private final FileIndex index = new FileIndex();
    private final WordSplitter splitter;
    private final Map<Path, IndexUpdater> watchers = new ConcurrentHashMap<>();

    public IndexerImpl(WordSplitter splitter) {
        this.splitter = splitter;
    }

    @Override
    public void addPath(Path path) throws IOException {
        if (Files.isDirectory(path)) {

            IndexUpdater updater = new IndexUpdater(path, index, splitter);
            watchers.put(path, updater);

            Files.walk(path)
                    .filter(Files::isRegularFile)
                    .forEach(this::indexFile);
        } else {
            indexFile(path);
        }
    }

    private void indexFile(Path file) {
        try {
            String content = Files.readString(file);
            Set<String> words = splitter.split(content);
            index.addFile(file, words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePath(Path path) {
        IndexUpdater updater = watchers.remove(path);
        if (updater != null) {
            updater.close();
        }

    }

    @Override
    public Set<Path> search(String word) {
        return index.getFilesForWord(word);
    }

    @Override
    public void close() {
        watchers.values().forEach(IndexUpdater::close);
        watchers.clear();
    }
}