package ru.itis.asd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;

public class TestFileGenerator1 {
    public static void main(String[] args) {
        String filename = "generated_data1.txt";
        Random random = new Random();

        try {
            FileWriter writer = new FileWriter(new File(filename));

            int numGraphs = random.nextInt(51) + 50; // Генерируем количество графов в файле
            System.out.println("Создано " + numGraphs + " графов.");

            for (int i = 0; i < numGraphs; i++) {
                int vertices = random.nextInt(120) + 10; // Количество вершин от 10 до 130
                // Минимальное количество рёбер для связности - (vertices-1)
                // Максимальное - vertices*(vertices-1) для орграфа
                int maxPossibleEdges = vertices * (vertices - 1);
                int edges = random.nextInt(maxPossibleEdges - (vertices - 1)) + (vertices - 1);

                writer.write(vertices + " " + edges + "\n");

                // Сначала создаём остовное дерево (гарантирует связность)
                List<Integer> notConnected = new ArrayList<>();
                List<Integer> connected = new ArrayList<>();

                // Начинаем с вершины 0
                connected.add(0);
                for (int v = 1; v < vertices; v++) {
                    notConnected.add(v);
                }

                // Список всех рёбер
                List<String> allEdges = new ArrayList<>();

                // Добавляем рёбра остовного дерева
                while (!notConnected.isEmpty()) {
                    int source = connected.get(random.nextInt(connected.size()));
                    int destination = notConnected.remove(random.nextInt(notConnected.size()));
                    connected.add(destination);

                    int weight = random.nextInt(100);
                    allEdges.add(source + " " + destination + " " + weight);
                }

                // Теперь добавляем оставшиеся рёбра
                for (int j = vertices - 1; j < edges; j++) {
                    int source, destination;
                    do {
                        source = random.nextInt(vertices);
                        destination = random.nextInt(vertices);
                    } while (source == destination || edgeExists(allEdges, source, destination));

                    int weight = random.nextInt(100);
                    allEdges.add(source + " " + destination + " " + weight);
                }

                // Перемешиваем рёбра для случайного порядка
                Collections.shuffle(allEdges, random);

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

    // Проверяет, существует ли уже такое ребро в списке
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