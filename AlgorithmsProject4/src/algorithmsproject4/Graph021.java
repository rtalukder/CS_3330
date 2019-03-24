package algorithmsproject4;

import java.util.Arrays;

/**
 *
 * @author Raquib Talukder
 */
public class Graph021 implements Graph {

    private int DEFAULT_CAPACITY = 1;
    private String[] vtxLabels = new String[DEFAULT_CAPACITY];
    private boolean[][] adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    private int capacity = DEFAULT_CAPACITY;
    private int vertices = 0;

    public Graph021() {
    }

    public Graph021(int Capacity) {
        if (Capacity < 1)
        {
            throw new IllegalArgumentException("Capacity is less than 1");
        }

        vtxLabels = new String[Capacity];
        adjMatrix = new boolean[Capacity][Capacity];
        capacity = Capacity;
    }

    /**
     * Returns the number of vertices in the graph. ie. the "order" of the graph.
     * @return the number of vertices in the graph
     */
    @Override
    public int numberOfVertices() {
      int j = 0;
      for (int i=0; i < vertices; i++) {
          if (vtxLabels[i]!=null)
              j++;
      }
      return j;
    }
    
    /**
     * Returns the number of edges in the graph. ie. the "size" of the graph.
     * @return the number of edges in the graph
     */
    @Override
    public int numberOfEdges() {
      int edges = 0;
      for (int i=0; i < vertices; i++) {
          for (int j=0; j < i; j++) { // lower triangle, without diagonal
              if (vtxLabels[i]!=null && vtxLabels[j]!=null && adjMatrix[i][j]) {
                  edges++;
              }
          }
      }
      return edges;
    }
    
    /**
     * Returns an array of Strings containing the labels of the vertices
     * @return a String array containing the vertex labels
     */
    @Override
    public String[] getVertexLabels() {
      String[] vtxNames = new String[numberOfVertices()];
      int j = 0;
      for (int i=0; i < vertices; i++) {
          if (vtxLabels[i]!=null)
              vtxNames[j++] = vtxLabels[i];
      }
      return vtxNames;
    }

    /**
     * Adds a new vertex to the graph
     * @param newVertex the new vertex to be added
     * @throws RuntimeException if vertex already exists
     */
    @Override
    public void addVertex(String newVertex) {
       if (vertexExists(newVertex))
            throw new IllegalArgumentException("Vertex " + newVertex + " already exists");
       vtxLabels[vertices++] = new String(newVertex);
    }
    
    /**
     * Determines if a vertex with the given label exists.
     * @param vertex the label of a vertex.
     * @return true of the vertex exist, false if it does not exist.
     */
    @Override
    public boolean vertexExists(String vertex) {
       for (int i = 0; i < vertices; i++) {
         if (vtxLabels[i]!=null && vtxLabels[i].equals(vertex))
           return true;
       }
       return false;
    }

    private int vertexIndex(String vertex) {
       for (int i = 0; i < vertices; i++) {
         if (vtxLabels[i]!=null && vtxLabels[i].equals(vertex))
           return i;
       }
       return -1;
    }

    /**
     * Determines if an edge with the given end vertices exists.
     * @param vertex1 one end vertex
     * @param vertex2 the other end vertex
     * @return true if the edge exists, false otherwise.
     * @throws RuntimeException if either end vertex does not exist.
     */
    @Override
    public boolean edgeExists(String vertex1, String vertex2) {
      int iv1 = vertexIndex(vertex1);
      int iv2 = vertexIndex(vertex2);

      if (iv1 < 0)
          throw new IllegalArgumentException("Vertex " + vertex1 + " does not exist");

      if (iv2 < 0)
          throw new IllegalArgumentException("Vertex " + vertex2 + " does not exist");

      if (adjMatrix[iv1][iv2] || adjMatrix[iv2][iv1])
          return true;

      return false;
    }

    /**
     * Deletes a vertex and all incident edges.
     * @param vertex The vertex to be deleted
     * @throws RuntimeException if the vertex does not exist
     */
    @Override
    public void deleteVertex(String vertex) {
      int iv = vertexIndex(vertex);

      if (iv < 0)
          throw new IllegalArgumentException("Vertex " + vertex + " does not exist");

      for (int j=0; j < vertices; j++)
      {
        adjMatrix[iv][j] = false;
        adjMatrix[j][iv] = false;
      }
      vtxLabels[iv] = null;
    }

    /**
     * Adds a new edge
     * @param vertex1 one end vertex of the edge
     * @param vertex2 the other end vertex of the edge
     * @throws RuntimeException if the edge already exists or if either end
     * vertex does not exist.
     */
    @Override
    public void addEdge(String vertex1, String vertex2) {
      int iv1 = vertexIndex(vertex1);
      int iv2 = vertexIndex(vertex2);

      if (edgeExists(vertex1, vertex2))
        throw new IllegalArgumentException("Edge " + vertex1 + " : " + vertex2 + " already exists");

      adjMatrix[iv1][iv2] = true;
      adjMatrix[iv2][iv1] = true;
    }

    /**
     * Deletes an edge
     * @param vertex1 one end vertex of the edge
     * @param vertex2 the other end vertex of the edge
     * @throws RuntimeException if the edge does not exist or if either end
     * vertex does not exist.
     */
    @Override
    public void deleteEdge(String vertex1, String vertex2) {
      int iv1 = vertexIndex(vertex1);
      int iv2 = vertexIndex(vertex2);

      if (!edgeExists(vertex1, vertex2))
        throw new IllegalArgumentException("Edge " + vertex1 + " : " + vertex2 + " does not exist");

      adjMatrix[iv1][iv2] = false;
      adjMatrix[iv2][iv1] = false;
    }

    /**
     * Returns the labels of all neighbors of a vertex
     * @param vertex the vertex of interest
     * @return a String array holding the labels the neighbors.
     * @throws RuntimeException if the vertex does not exist
     */
    @Override
    public String[] getNeighbors(String vertex) {
      int iv = vertexIndex(vertex);
      int count = degree(vertex);
      String[] adjLabels = new String[count];

      count = 0;
      for (int k=0; k < vertices; k++)
      {
        if (adjMatrix[iv][k])
          adjLabels[count++] = vtxLabels[k];
      }

      Arrays.sort(adjLabels);
      return adjLabels;
    }

    public Integer[] getAdjacents(int iv) {
      int count = idegree(iv);
      Integer[] adjIndices = new Integer[count];

      count = 0;
      for (int k=0; k < vertices; k++)
      {
        if (adjMatrix[iv][k])
          adjIndices[count++] = k;
      }

      // sort indices based on corresponding names
      for(int j = 0; j < adjIndices.length - 1; j++)
      {
        for(int i = 0; i < adjIndices.length - 1; i++)
        {
          if(vtxLabels[adjIndices[i]].compareTo(vtxLabels[adjIndices[i+1]]) > 0) {
            int temp = adjIndices[i];
            adjIndices[i] = adjIndices[i + 1];
            adjIndices[i + 1] = temp;
          }
        }
      }

      return adjIndices;
    }

    private int idegree(int iv) {
      int count = 0;

      for (int j=0; j < vertices; j++)
      {
        if (adjMatrix[iv][j])
          count++;
      }

      return count;
    }

    /**
     * Returns the degree of a vertex
     * @param vertex the vertex of interest
     * @return the degree of the given vertex
     * @throws RuntimeException if the vertex does not exist.
     */
    @Override
    public int degree(String vertex) {
      int iv = vertexIndex(vertex);

      if (iv < 0)
          throw new IllegalArgumentException("Vertex " + vertex + " does not exist");

      return idegree(iv);
    }
    
    /**
     * Returns the maximum degree of the graph
     * @return the maximum degree
     */
    @Override
    public int maxDegree() {
      int count = 0;
      int value = 0;

      for (int i=0; i < vertices; i++)
      {
        count = 0;
        for (int j=0; j < vertices; j++)
        {
          if (adjMatrix[i][j])
          {
            count++;
          }
        }
        if (count > value)
          value = count;
      }
      return value;
    }

    /**
     * Returns the minimum degree of the graph.
     * @return the minimum degree
     */
    @Override
    public int minDegree() {
      int count = 0;
      int value = 0;

      for (int i=0; i < vertices; i++)
      {
        count = 0;
        for (int j=0; j < vertices; j++)
        {
          if (adjMatrix[i][j])
          {
            count++;
          }
        }
        if (count < value)
          value = count;
      }
      return value;
    }

    /**
     * Returns the average degree of the graph
     * @return the average degree
     */
    @Override
    public double averageDegree() {
      int count = 0;
      int total = 0;

      for (int i=0; i < vertices; i++)
      {
        count = 0;
        for (int j=0; j < vertices; j++)
        {
          if (adjMatrix[i][j])
          {
            count++;
            total++;
          }
        }
      }
      return (double) total/numberOfVertices();
    }

    /**
     * Determines if the graph is connected.
     * @return true if the graph is connected, false otherwise.
     */
    @Override
    public boolean isConnected() {
      return (bfsOrder(vtxLabels[0]).length == numberOfVertices());
    }

    /**
     * Performs a Breadth First Search traversal starting at a given vertex.
     * Constructs and returns a BFS spanning tree of the connected component 
     * containing the vertex. Adjacent vertices are visited in the
     * lexicographical order of their labels.
     * @param vertex the vertex at which the BFS traversal is started
     * @return a BFS spanning tree of the connected component containing 
     * the given vertex.
     */
    @Override
    public Graph bfsTree(String vertex) {
      Graph g1 = new Graph021(numberOfVertices());
      Integer[] bfsQ = new Integer[vertices];
      int q1 = 0, q2 = 0;
      boolean[] bfsCheck = new boolean[vertices];
      String[] bfsLabels = new String[numberOfVertices()];
      Integer[] adj = new Integer[1];
      int iw = 0, iv = vertexIndex(vertex);

      if (iv < 0)
          throw new IllegalArgumentException("Vertex " + vertex + " does not exist");

      bfsQ[q2++] = iv;
      g1.addVertex(vtxLabels[iv]);
      bfsCheck[iv] = true;

      while (q1 < q2)
      {
        iv = bfsQ[q1++];
        adj = getAdjacents(iv);
        for (int k=0; k<adj.length; k++)
        {
          iw = adj[k];
          if (!bfsCheck[iw])
          {
            bfsQ[q2++] = iw;
            g1.addVertex(vtxLabels[iw]);
            g1.addEdge(vtxLabels[iv],vtxLabels[iw]);
            bfsCheck[iw] = true;
          }
        }
      }

      return g1;
    }
    
    /**
     * Performs a Depth First Search traversal starting at a given vertex.
     * Constructs and returns a DFS spanning tree of the connected component 
     * containing the vertex. Adjacent vertices are visited in the
     * lexicographical order of their labels.
     * @param vertex the vertex at which the DFS traversal is started
     * @return a DFS spanning tree of the connected component containing 
     * the given vertex.
     */
    @Override
    public Graph dfsTree(String vertex) {
      Graph g1 = new Graph021(numberOfVertices());
      boolean[] dfsCheck = new boolean[vertices];
      int iv = vertexIndex(vertex);

      if (iv < 0)
          throw new IllegalArgumentException("Vertex " + vertex + " does not exist");

      return dfs(iv, -1, g1, dfsCheck);
    }
 
    /**
     * Performs a Breadth First Search traversal starting at a given vertex.
     * The label of each vertex visited is added to an array of Strings in the
     * order visited.
     * @param vertex the vertex at which the BFS traversal is started
     * @return an array of Strings containing the labels of the vertices visited
     * in the order that they were visited.
     */
    @Override
    public String[] bfsOrder(String vertex) {
      return bfsTree(vertex).getVertexLabels();
    }

    
    
    /**
     * Performs a Depth First Search traversal starting at a given vertex.
     * The label of each vertex visited is added to an array of Strings in the
     * order visited.
     * @param vertex the vertex at which the DFS traversal is started
     * @return an array of Strings containing the labels of the vertices visited
     * in the order that they were visited.
     */
    @Override
    public String[] dfsOrder(String vertex) {
      return dfsTree(vertex).getVertexLabels();
    }
    
    private Graph dfs(int iv, int iw, Graph g1, boolean[] check) {
      Integer[] adj = getAdjacents(iv);

      if (!check[iv])
      {
        check[iv] = true;
        g1.addVertex(vtxLabels[iv]);
        if (iw >= 0)
          g1.addEdge(vtxLabels[iv],vtxLabels[iw]);
        for (int k=0; k<adj.length; k++)
        {
            dfs(adj[k], iv, g1, check);
        }
      }
      return g1;
    }
    
}

