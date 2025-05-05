package ru.itis.asd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Считывание и обработка первого файла с графами
            Scanner sc = new Scanner(new File("generated_data1.txt"));
            while (sc.hasNextLine()) {
                if (!sc.hasNextInt()) {
                    break;
                }
                // Инициализация графа
                Graph graph = new Graph(sc.nextInt(),sc.nextInt());
                // Инициализация рёбер
                for (int i = 0; i < graph.getEdges(); i++) {
                    graph.addEdge(new Integer[]{sc.nextInt(),sc.nextInt(), sc.nextInt()});
                }
                algOfLevit(0,graph);
            }
            System.out.println("--------------------------------");
            //Считывание и обработка более удобных графов
            Scanner sc2 = new Scanner(new File("generated_data2.txt"));
            while (sc2.hasNextLine()) {
                if (!sc2.hasNextInt()) {
                    break;
                }
                Graph graph = new Graph(sc2.nextInt(),sc2.nextInt());
                for (int i = 0; i < graph.getEdges(); i++) {
                    graph.addEdge(new Integer[]{sc2.nextInt(),sc2.nextInt(), sc2.nextInt()});
                }
                algOfLevit(0,graph);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void algOfLevit(int start, Graph graph) {

        enum Status {
            M1,M2,M0;
        }

        //Проверка на то, что принятая за начало вершина существует
        if (start>graph.getVertices()+1) {
            throw new NoSuchElementException();
        }

        // Инициализация массива кратчайших расстояний
        int[] distance = new int[graph.getVertices()];
        Arrays.fill(distance,Integer.MAX_VALUE);
        distance[start] = 0;

        // Инициализация массива предков
        int[] parents = new int[graph.getVertices()];
        Arrays.fill(parents,0);

        // Инициализация массива статусов каждой вершины
        Status[] statuses = new Status[graph.getVertices()];
        Arrays.fill(statuses,Status.M2);
        statuses[start] = Status.M1;

        // Инициализация всех 3х структур
        List<Integer> m0 = new ArrayList();
        Queue<Integer> m1 = new LinkedList();
        List<Integer> m2 = new ArrayList();

        m1.add(start);
        for (int i = 0; i < graph.getVertices(); i++) {
            if (i!=start) {
                m2.add(i);
            }
        }
        int iterations = 0;
        long starting = System.nanoTime();
        while(!m1.isEmpty()) {
            Integer cur = m1.poll();
            // Обработка всех исходящих рёбер вершины cur
            for (int i = 0; i < graph.getEdgesOfvertices().get(cur).size(); i++) {
                // Релаксация рёбер
                int first = distance[cur]+graph.getEdgesOfvertices().get(cur).get(i).dist;
                Integer curInEnds = graph.getEdgesOfvertices().get(cur).get(i).end;
                int second = distance[curInEnds];
                if (first<second) {
                    distance[curInEnds] = first;
                    parents[curInEnds] = cur;
                    // перекладывание вершины в соответствующее множество после релаксации
                    if (statuses[curInEnds] == Status.M0) {
                        m1.add(curInEnds);
                        m0.remove(curInEnds);
                        statuses[curInEnds] = Status.M0;
                    }
                    if (statuses[curInEnds] == Status.M2) {
                        m1.add(curInEnds);
                        m2.remove(curInEnds);
                        statuses[curInEnds] = Status.M1;
                    }
                }
                iterations++;
            }
            m0.add(cur);
        }
        long ending = System.nanoTime();
        System.out.printf(iterations + " " + graph.getVertices() + " %.7f%n",((double)(ending-starting)/1000000000));
    }
}
