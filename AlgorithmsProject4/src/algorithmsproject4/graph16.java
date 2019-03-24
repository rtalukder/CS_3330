package algorithmsproject4;

import java.util.*;
import java.io.*;

/**
 *
 * @author Raquib Talukder
 */

class graph16 {
    public class vertex {
        public int index;           // each vertex has an integer index number
	public int x;               // the x-position of the vertex
	public int y;               // the y-position of the vertex
		   
	public boolean wasVisited; // used by search
	public int nextNeighbor;   // used by search
	public int parent;         // used by search
		   
	public vertex(int i, int x, int y) {  // constructor
            index = i;
            this.x = x;
            this.y = y;
            wasVisited = false;
            nextNeighbor = 0;
            parent = -1;
        }
		   
        public void reset() {
            wasVisited = false;
            nextNeighbor = 0;
            parent = -1;
        }
    } // end of class vertex

    private int V;                 // number of vertices
    private vertex vertexList[];   // list of vertices
    private float adjMat[][];      // adjacency matrix
   
    public graph16()  {                   // constructor
        this(20);
    }

    public graph16(int n)  {             // constructor
        V = n;

        vertexList = new vertex[V];                                          
        adjMat = new float[V][V];  // adjacency matrix	    

        Random randomGenerator = new Random();
        for(int j = 0; j < V; j++) {      // fill VertexList
	    int x = randomGenerator.nextInt(20*V);
            int y = randomGenerator.nextInt(20*V);
            vertexList[j] = new vertex(j, x, y);
        }
        
        for(int j=0; j<V; j++) {     // set adjacency matrix to distance
            vertex v1 = vertexList[j];
            
            for(int k=j; k<V; k++)  { 
                if (k == j) {
                    adjMat[j][k] = 0;
                }
                else {
                    vertex v2 = vertexList[k];
                    float dx = (v1.x-v2.x);
                    float dy = (v1.y-v2.y);
                    adjMat[j][k] = (float) Math.sqrt(dx*dx + dy*dy); 
                    adjMat[k][j] = adjMat[j][k];
                }
	    }
	}
    }  // end constructor
   
    public void display() {
        if (V == 0) { 
            System.out.println("G is empty"); return; 
        }
        if (V > 20) { 
            System.out.println("G = (V, E), where V = { 0, ..., " + (V-1) + " } and E is a full set of edges too large to display...\n"); 	
            return; 
        }
        System.out.println("G = (V, E), where V = { 0, ..., " + (V-1) + " } and E is a full set of edges with distance:"); 	   
 	System.out.print("     ");
 	for(int j=0; j<V; j++) {
            System.out.format("%8d", j);
        }
 	System.out.println();
        
 	    /*
 	    System.out.print("posi.: ");
 	    for(int j=0; j<V; j++) {
 	    	vertex v1 = vertexList[j];
 	    	System.out.format("(%2d,%2d) ", v1.x, v1.y);
 	    }
 	    System.out.println();
 	   */
 	    
        for (int i = 0; i < V; i++) {
            System.out.format("%5d", i);
            for(int j=0; j<V; j++)  
 			    System.out.format("%8.2f", adjMat[i][j]);
 		    System.out.println();
 	    }
 	    System.out.println();
    }

    public String treeDisplay() {  // return the printing of the set of tree edges 
        if (V == 0) {
            return "G is empty";
        }
        String x = "The tree: ";
	for (int i = 0; i < V; i++) {
            vertex v1 = vertexList[i];
            int j = v1.parent;
            if (j != -1) {
                x = x + "(" + j + ", " + i + ") ";
            }
	}
        return x+"\n";   
    }
 
    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v) {
        for(int j=vertexList[v].nextNeighbor; j<V; j++) 
	        if (vertexList[j].wasVisited==false) {
		        vertexList[v].nextNeighbor = j+1;
		        return j;
	        }
	    vertexList[v].nextNeighbor = V;
	    return -1;
    }  // end getAdjUnvisitedvertex()

    public static void main(String[] args) {
    	int size=0;
    	BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    	try {
            System.out.print("Please enter array size : ");
            size = Integer.parseInt(read.readLine());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        graph16 theGraph = new graph16(size);
        theGraph.display();

        System.out.println("Running Prim's MST");
        System.out.println("Size of G: " + theGraph.V + "\n");
        
        MST test = new MST();
        test.primMST(theGraph.adjMat, theGraph.V);
		      
    }  // end main

}  // end class graph16

