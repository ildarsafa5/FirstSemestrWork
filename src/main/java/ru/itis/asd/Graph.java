package ru.itis.asd;

import java.util.ArrayList;

public class Graph {
    private int vertices;
    private int edges;

    private ArrayList<ArrayList<Edge>> edgesOfvertices = new ArrayList();

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

    public ArrayList<ArrayList<Edge>> getEdgesOfvertices() {
        return edgesOfvertices;
    }

    public void setEdgesOfvertices(ArrayList<ArrayList<Edge>> edgesOfvertices) {
        this.edgesOfvertices = edgesOfvertices;
    }

    public Graph(int vertices, int edges) {
        this.edges = edges;
        this.vertices = vertices;
        for (int i = 0; i < vertices; i++) {
            edgesOfvertices.add(new ArrayList<>());
        }
    }

    class Edge {
        int start;
        int dist;
        int end;

        public Edge(int start,int dist, int end) {
            this.dist = dist;
            this.end = end;
            this.start = start;
        }
    }

    public void addEdge(Integer[] array) {
        if (array.length!=3) {
            throw new RuntimeException();
        }
        edgesOfvertices.get(array[0]).add(new Edge(array[0],array[2],array[1]));
    }
}
