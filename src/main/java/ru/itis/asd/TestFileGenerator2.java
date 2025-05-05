package ru.itis.asd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;

public class TestFileGenerator2 {
    public static void main(String[] args) {
        String filename = "generated_data2.txt";
        Random random = new Random();

        try {
            FileWriter writer = new FileWriter(new File(filename));

            int numGraphs = random.nextInt(51) + 50; // Генерируем количество графов в файле
            System.out.println("Создано " + numGraphs + " графов.");

            for (int i = 0; i < numGraphs; i++) {
                int vertices = random.nextInt(120) + 10; // Генерация 10-130 вершин

                // Базовое количество рёбер пропорционально количеству вершин
                int baseEdges = vertices * (vertices - 1) / 4; // Ориентировочно 25% от максимального

                // Добавляем случайную вариацию (±20%)
                int edgesVariation = (int)(baseEdges * 0.2);
                int edges = baseEdges + random.nextInt(2 * edgesVariation) - edgesVariation;

                // Гарантируем минимальное и максимальное количество рёбер
                edges = Math.max(vertices - 1, edges); // Не меньше чем для связности
                edges = Math.min(vertices * (vertices - 1), edges); // Не больше максимального

                writer.write(vertices + " " + edges + "\n");

                // Генерация связного графа
                List<String> allEdges = generateConnectedGraph(vertices, edges, random);

                // Записываем все рёбра
                for (String edge : allEdges) {
                    writer.write(edge + "\n");
                }
            }

            writer.close();
            System.out.println("Файл успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка во время создания файла: " + e.getMessage());
        }
    }

    private static List<String> generateConnectedGraph(int vertices, int edges, Random random) {
        List<Integer> notConnected = new ArrayList<>();
        List<Integer> connected = new ArrayList<>();
        List<String> allEdges = new ArrayList<>();

        // Начинаем с вершины 0
        connected.add(0);
        for (int v = 1; v < vertices; v++) {
            notConnected.add(v);
        }

        // Создаём остовное дерево
        while (!notConnected.isEmpty()) {
            int source = connected.get(random.nextInt(connected.size()));
            int destination = notConnected.remove(random.nextInt(notConnected.size()));
            connected.add(destination);

            int weight = random.nextInt(100);
            allEdges.add(source + " " + destination + " " + weight);
        }

        // Добавляем оставшиеся рёбра
        for (int j = vertices - 1; j < edges; j++) {
            int source, destination;
            do {
                source = random.nextInt(vertices);
                destination = random.nextInt(vertices);
            } while (source == destination || edgeExists(allEdges, source, destination));

            int weight = random.nextInt(100);
            allEdges.add(source + " " + destination + " " + weight);
        }

        Collections.shuffle(allEdges, random);
        return allEdges;
    }

    private static boolean edgeExists(List<String> edges, int source, int destination) {
        for (String edge : edges) {
            String[] parts = edge.split(" ");
            int s = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            if (s == source && d == destination) {
                return true;
            }
        }
        return false;
    }
}