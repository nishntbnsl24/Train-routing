/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

import java.util.Scanner;
import sun.security.provider.certpath.Vertex;

/**
 *
 * @author zscaler
 */
public class Train {

    /**
     * @param args the command line arguments
     */
    static int vertex;
    static boolean stauts = false;
    static String cities[];
    static int distance[][];

    public static void main(String[] args) {
        boolean status = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no of vertex");
        vertex = sc.nextInt();
        cities = new String[vertex];
        for (int i = 0; i < vertex; i++) {
            System.out.println("Enter the name Of City");
            cities[i] = sc.next();
        }
        distance = new int[vertex][vertex];
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                if (i == j) {
                    distance[i][j] = 0;
                } else {
                    distance[i][j] = 100000;
                }
            }
        }
        do {
            System.out.print("\nEnter the source      ");
            String source = sc.next();
            int indexsource = 0, indexdest = 0;
            for (int i = 0; i < vertex; i++) {
                if (cities[i].equals(source)) {
                    indexsource = i;
                }
            }
            System.out.print("Enter the destination ");
            String destination = sc.next();
            for (int i = 0; i < vertex; i++) {
                if (cities[i].equals(destination)) {
                    indexdest = i;
                }
            }
            System.out.print("Enter the distance ");
            int cost = sc.nextInt();
            distance[indexsource][indexdest] = cost;
            distance[indexdest][indexsource] = cost;
            System.out.print("\nDo you want to continue(Y/N)???? ");
            char ch = sc.next().charAt(0);
            if (ch != 'y' && ch != 'Y') {
                status = false;
            }
        } while (status);
       /* for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                System.out.print(distance[i][j] + " ");
            }
            System.out.println();
        }*/
        System.out.print("\nEnter Your source      ");
        String yoursource = sc.next();
        System.out.print("\nEnter Your Destination      ");
        String yourdest = sc.next();
        int yoursourcei = -1, yourdesti = -1;
        for (int i = 0; i < vertex; i++) {
            if (cities[i].equals(yoursource)) {
                yoursourcei = i;
                for (int j = 0; j < vertex; j++) {
                    if (cities[j].equals(yourdest)) {
                        yourdesti = j;
                        dijkstra(distance, yoursourcei, yourdesti);
                        break;
                    } 
                }

            } if(yourdesti==-1 || yoursourcei==-1)
            {
                System.out.println("No Such Cityies Found Found");

            }

        }

        //System.out.println("The Minimum cost to reach station "+ Geeks.N+
        //								" is "+Geeks.minCost(distance));    
    }
    private static final int NO_PARENT = -1;

    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented 
    // using adjacency matrix
    // representation
    private static void dijkstra(int[][] adjacencyMatrix,
            int startVertex, int dest) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to 
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as 
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
                vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not 
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all 
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is 
            // always equal to startNode in 
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                    vertexIndex < nVertices;
                    vertexIndex++) {
                if (!added[vertexIndex]
                        && shortestDistances[vertexIndex]
                        < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                    vertexIndex < nVertices;
                    vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance)
                        < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance
                            + edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents, dest);
    }

    // A utility function to print 
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
            int[] distances,
            int[] parents, int dest) {
        int nVertices = distances.length;
        System.out.print("Vertex\t Distance\tPath");

        for (int vertexIndex = 0;
                vertexIndex < nVertices;
                vertexIndex++) {
            if (vertexIndex != startVertex && vertexIndex == dest) {
                System.out.print("\n" + cities[startVertex] + " -> ");
                System.out.print(cities[vertexIndex] + " \t\t ");
                if (distances[vertexIndex] != 100000) {
                    System.out.print(distances[vertexIndex] + "\t\t");
                    printPath(vertexIndex, parents);
                } else {
                    System.out.print("No Path" + "\t\t");
                }
            }
        }
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex,
            int[] parents) {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(cities[currentVertex] + " ");
    }

}
