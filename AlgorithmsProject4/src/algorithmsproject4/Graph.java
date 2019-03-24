package algorithmsproject4;

/**
 *
 * @author Raquib Talukder
 */

public interface Graph {

    /**
     * Returns the number of vertices in the graph. ie. the "order" of the graph.
     * @return the number of vertices in the graph
     */
    public int numberOfVertices();

    
    /**
     * Returns the number of edges in the graph. ie. the "size" of the graph.
     * @return the number of edges in the graph
     */
    public int numberOfEdges();

    
    /**
     * Returns an array of Strings containing the labels of the vertices
     * @return a String array containing the vertex labels
     */
    public String[] getVertexLabels();

    
    /**
     * Adds a new vertex to the graph
     * @param newVertex the new vertex to be added
     * @throws RuntimeException if vertex already exists
     */
    public void addVertex(String newVertex);

    
    /**
     * Determines if a vertex with the given label exists.
     * @param vertex the label of a vertex.
     * @return true of the vertex exist, false if it does not exist.
     */
    public boolean vertexExists(String vertex);

    
    /**
     * Determines if an edge with the given end vertices exists.
     * @param vertex1 one end vertex
     * @param vertex2 the other end vertex
     * @return true if the edge exists, false otherwise.
     * @throws RuntimeException if either end vertex does not exist.
     */
    public boolean edgeExists(String vertex1, String vertex2);

    
    /**
     * Deletes a vertex and all incident edges.
     * @param vertex The vertex to be deleted
     * @throws RuntimeException if the vertex does not exist
     */
    public void deleteVertex(String vertex);

    
    /**
     * Adds a new edge
     * @param vertex1 one end vertex of the edge
     * @param vertex2 the other end vertex of the edge
     * @throws RuntimeException if the edge already exists or if either end
     * vertex does not exist.
     */
    public void addEdge(String vertex1, String vertex2);

    
    /**
     * Deletes an edge
     * @param vertex1 one end vertex of the edge
     * @param vertex2 the other end vertex of the edge
     * @throws RuntimeException if the edge does not exist or if either end
     * vertex does not exist.
     */
    public void deleteEdge(String vertex1, String vertex2);

    
    /**
     * Returns the labels of all neighbors of a vertex
     * @param vertex the vertex of interest
     * @return a String array holding the labels the neighbors.
     * @throws RuntimeException if the vertex does not exist
     */
    public String[] getNeighbors(String vertex);

    
    /**
     * Returns the degree of a vertex
     * @param vertex the vertex of interest
     * @return the degree of the given vertex
     * @throws RuntimeException if the vertex does not exist.
     */
    public int degree(String vertex);

    
    /**
     * Returns the maximum degree of the graph
     * @return the maximum degree
     */
    public int maxDegree();

    
    /**
     * Returns the minimum degree of the graph.
     * @return the minimum degree
     */
    public int minDegree();

    
    /**
     * Returns the average degree of the graph
     * @return the average degree
     */
    public double averageDegree();

    
    /**
     * Determines if the graph is connected.
     * @return true if the graph is connected, false otherwise.
     */
    public boolean isConnected();
    
    
    /**
     * Performs a Breadth First Search traversal starting at a given vertex.
     * Constructs and returns a BFS spanning tree of the connected component 
     * containing the vertex. Adjacent vertices are visited in the
     * lexicographical order of their labels.
     * @param vertex the vertex at which the BFS traversal is started
     * @return a BFS spanning tree of the connected component containing 
     * the given vertex.
     */
    public Graph bfsTree(String vertex);    
    
    
    /**
     * Performs a Depth First Search traversal starting at a given vertex.
     * Constructs and returns a DFS spanning tree of the connected component 
     * containing the vertex. Adjacent vertices are visited in the
     * lexicographical order of their labels.
     * @param vertex the vertex at which the DFS traversal is started
     * @return a DFS spanning tree of the connected component containing 
     * the given vertex.
     */
    public Graph dfsTree(String vertex); 
    
    
    /**
     * Performs a Breadth First Search traversal starting at a given vertex.
     * The label of each vertex visited is added to an array of Strings in the
     * order visited.
     * @param vertex the vertex at which the BFS traversal is started
     * @return an array of Strings containing the labels of the vertices visited
     * in the order that they were visited.
     */
    public String[] bfsOrder(String vertex);
    
    
    /**
     * Performs a Depth First Search traversal starting at a given vertex.
     * The label of each vertex visited is added to an array of Strings in the
     * order visited.
     * @param vertex the vertex at which the DFS traversal is started
     * @return an array of Strings containing the labels of the vertices visited
     * in the order that they were visited.
     */
    public String[] dfsOrder(String vertex);    
}