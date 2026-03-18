package com.example.indexer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class IndexerCLI {
    public static void main(String[] args) {
        Indexer indexer = new IndexerImpl(new SimpleWordSplitter());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            try {
                switch (command) {
                    case "add":
                        Path addPath = Paths.get(argument);
                        indexer.addPath(addPath);
                        System.out.println("Добавлено: " + addPath);
                        break;
                    case "remove":
                        Path removePath = Paths.get(argument);
                        indexer.removePath(removePath);
                        System.out.println("Удалено: " + removePath);
                        break;
                    case "search":
                        Set<Path> result = indexer.search(argument);
                        if (result.isEmpty()) {
                            System.out.println("Не найдено.");
                        } else {
                            System.out.println("Найдено в:");
                            result.forEach(p -> System.out.println("  " + p));
                        }
                        break;
                    case "exit":
                        indexer.close();
                        System.out.println("Выход.");
                        return;
                    default:
                        System.out.println("Неизвестная команда. Доступные: add, remove, search, exit");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }
}