package algorithmsproject4;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Raquib Talukder
 */

public class MST extends Graph021 {
    private int V = 10;
    int maxInt = Integer.MAX_VALUE;
    
    public void primMST(float[][] myGraph, int vertices){
        V = vertices;
        // If graph is empty
        if (V == 0){
            System.out.println("G is empty");
            return;
        }
        // Store MST
        int parent[] = new int[V];
        // Keys to pick minimum values from graph
        float key[] = new float [V];
        // Unexplored/Explored for minKey function
        Boolean mstSet[] = new Boolean[V];
        
        // Initialize all keys to infinite
        for (int i = 0; i < V; i++){
            key[i] = maxInt;
            mstSet[i] = false;
        }
        // Distance from first node will always be 0
        // root of MST is -1
        key[0] = 0;     
        parent[0] = -1; 
 
        for (int j = 0; j < V-1; j++){
            // Pick minimum key vertex
            int n = (int) minKey(key, mstSet);
 
            // visited node
            mstSet[n] = true;
 
            // Update key value and parent index of the adj. vertices of the picked vertex
            for (int k = 0; k < V; k++){
                // Update the key only if graph[u][v] is smaller than key[v]
                if (myGraph[n][k]!=0 && mstSet[k] == false &&
                    myGraph[n][k] <  key[k]){
                    parent[k]  = n;
                    key[k] = (int) myGraph[n][k];
                }
            }
        }
        printMST(parent, V, myGraph);
        treeMake(parent, myGraph);
    }
    
    public float minKey(float key[], Boolean mstSet[]){
        // set all values to infinity
        int min = maxInt;
        int minIndex = -1;
        
        for (int i = 0; i < V; i++){
            if (mstSet[i] == false && key[i] < min){
                min = (int) key[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    public void printMST(int[] parent, int n, float[][] myGraph){
        System.out.println("Edge      Weight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " -- "+ i + "    " + myGraph[i][parent[i]]);
        }
    }

    public void treeMake(int[] parent, float[][] myGraph){
        // create graph to insert verticies of MST
        Graph021 test = new Graph021(V);
        
        for (int i = 0; i < V; i++){
            String vertex = String.valueOf(i);
            test.addVertex(vertex);
        }
        //System.out.println(Arrays.toString(test.getVertexLabels()));
        
        for (int j = 1; j < V; j++){
            String toVertex = String.valueOf(j);
            String vertex = String.valueOf(parent[j]);
            
            //System.out.println(vertex + "   " + toVertex);
            
            test.addEdge(vertex, toVertex);
        }        
        
        // run dfs on finished graph on randomly picked vertex
        Random random = new Random();
        int randomVertex = random.nextInt(V);
        String stringRandom = String.valueOf(randomVertex);
        
        System.out.println("\nStarting DFS of MST tree beginning from vertex: " + String.valueOf(randomVertex) + "\n");
        
        System.out.println("Array of vertices traversed by DFS algorithm: " + 
                            Arrays.toString(test.dfsOrder(stringRandom)));
        
        String [] myArr = test.dfsOrder(stringRandom);
        
        System.out.println("Total distance traveled: " + totWeight(myArr, myGraph, parent));
    }
    
    public float totWeight (String [] myArr, float[][] myGraph, int[] parent){
        int [] newArr = new int[myArr.length];
        float totWeight = 0;
        
        for(int i = 0; i<myArr.length; i++){
            newArr[i] = Integer.parseInt(myArr[i]);
        }
        
        for(int i = 1; i<newArr.length; i++){
            totWeight += myGraph[i][parent[i]];
        }
        
        return totWeight;
    }
}
