package com.example.indexer;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IndexUpdater implements AutoCloseable {
    private final WatchService watchService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean running = true;

    public IndexUpdater(Path dir, FileIndex index, WordSplitter splitter) throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        dir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        executor.submit(() -> {
            while (running) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changed = (Path) event.context();
                    Path fullPath = dir.resolve(changed);

                }
                key.reset();
            }
        });
    }

    @Override
    public void close() {
        running = false;
        executor.shutdownNow();
        try {
            watchService.close();
        } catch (IOException ignored) {}
    }
}