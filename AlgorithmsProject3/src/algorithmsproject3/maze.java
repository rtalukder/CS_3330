package algorithmsproject3;
/**
 *
 * @author Raquib Talukder
 */
import java.util.*;

public class maze {
    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    private static Random randomGenerator;  // for random numbers
    
    public static int Size;
    // The variable CountUnions will be used in the while loop later on
    public static int CountUnions;
    
    public static class Point {  // a Point is a position in the maze
        public int x, y;
        public boolean visited;   // for DFS
        public Point parent;      // for DFS
        public int rank = 0;
        public int index;

        // Constructor
        public Point(int x, int y) {
            this.x = x;
	    this.y = y;
        }
        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
    public static class Edge { 
	   Point point;
	   int direction;
	   boolean used;     // for maze creation
	   boolean deleted;  // for maze creation
	
	   // Constructor
	   public Edge(Point p, int d) {
               this.point = p;
               this.direction = d;
               this.used = false;
               this.deleted = false;
           }
    }
    public static Point find(Point a){
        // Assign a to a new point y, and then copy it
        Point y = a;
        a.copy(y);
        // Keep searching until we find the highest parent node
        while (y.parent != null) {
            y = y.parent;
        }
        // Assign y to the new point root
        Point root = y;
        a.copy(y);
        // This part does path compression.
        while (y.parent != null) {
            Point w = y.parent;
            y.parent = root;
            y = w;
        }
        return root;
    }
    public static void union(Point a, Point b){
        // Call find on both points to determine their highest ranks.
        Point u = find(a);
        Point v = find(b);
        // Check here to see which rank is higher.
        if (u.rank <= v.rank) {
            // Assign u's parent to be v
            u.parent = v;
            // If they're equal ranks, then we also must increase v's rank by 1
            if (u.rank == v.rank) {
                v.rank += 1;
            }
        }
        // Otherwise, assign v's parent to be u
        else {
            v.parent = u;
        }
    }
    public static Point[][] board;
    public static Edge[][] graph;
    public static int N;   // number of points in the graph
    
    public static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < Size; ++i) {
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) System.out.print("----");
                System.out.println();
                if (i == 0) System.out.print("Start");
                else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                if (i == Size-1 && j == Size-1)
		    System.out.print("    End");
                else System.out.print("   |");
            }
            System.out.println();
        }
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
            System.out.println();
    }
    
    // This function prints out the final maze.
    public static void createMaze() {
        System.out.println("\nFinished Maze:");
        System.out.println();
        System.out.print("    -");
        // This for loop prints out the top wall of the maze.
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
        
        for (int i = 0; i < Size; ++i) {

            for (int j = 0; j < Size; ++j) {
                if ((i == Size-1) && (j == Size-1)) {
                    System.out.print("    End");
                    break;
                }
                if ((i == 0) && (j == 0)) {
                    System.out.print("Start");
                }
                if (graph[i*Size+j][0].deleted == false) {
                    System.out.print("   |");
                }
                else {
                    System.out.print("    ");
                }
            }
            System.out.println();
            System.out.print("    |");

            for (int k = 0; k < Size; ++k) {
                if (graph[(i*Size)+k][1].deleted == false) {
                    System.out.print("----");
                }
                else {
                    System.out.print("   |");
                }
            }
            System.out.println();
            if (i < Size-1) {
                System.out.print("    |");
            }
        }
    }
    
    public static void main(String[] args) {
         
    	// Read in the Size of a maze
	    Scanner scan = new Scanner(System.in);         
	    try {	     
	        System.out.println("What's the size of your maze? ");
	        Size = scan.nextInt();
	    }
	    catch(Exception ex){
	        ex.printStackTrace();
	    }
	    scan.close();

         
	    // Create one dummy edge for all boundary edges.
	    Edge dummy = new Edge(new Point(0, 0), 0);
	    dummy.used = true;
            // The index of a dummy's point must be 0.
            dummy.point.index = 0;
            dummy.point.visited = true;
	     
	    // Create board and graph.
	    board = new Point[Size][Size];
	    N = Size*Size;  // number of points
	    graph = new Edge[N][4];
            int r = 0;
	     
	    for (int i = 0; i < Size; ++i) 
		  for (int j = 0; j < Size; ++j) {
		    Point p = new Point(i, j);
                    p.index = r++;
		    int pindex = i*Size+j;   // Point(i, j)'s index is i*Size + j
                    
                    board[i][j] = p;
		    
		    graph[pindex][right] = (j < Size-1)? new Edge(p, right): dummy;
		    graph[pindex][down] = (i < Size-1)? new Edge(p, down) : dummy;        
		    graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;         
		    graph[pindex][up] = (i > 0)? graph[pindex-Size][down] : dummy;
                    
		}
	    
	    displayInitBoard();
         
	    // Hint: To randomly pick an edge in the maze, you may 
	    // randomly pick a point first, then randomly pick
	    // an edge associated with the point.
	    randomGenerator = new Random();
	    int i = randomGenerator.nextInt(N);
            while (CountUnions < (Size)*((Size-1)*2)) {
                randomGenerator = new Random();
                int randPoint = randomGenerator.nextInt(N);
                int randDirection = randomGenerator.nextInt(2);
                
                Edge myEdge = graph[randPoint][randDirection];
                Point current = myEdge.point;
                Point adjacent = null;

                if (myEdge.used == false) {
                    // The first case branches when the direction is 0, or right
                    if (randDirection == 0) {
                        adjacent = graph[randPoint+1][0].point;
                        
                        if (adjacent.index == 0) {
                            adjacent = graph[randPoint+1][1].point;

                            if (adjacent.index == 0) {
                                adjacent = board[Size-1][Size-1];
                            }
                        }
                    }
                    // Otherwise we are looking at the cell beneath the current
                    // cell.
                    else {
                        adjacent = graph[randPoint+Size][1].point;
                        if (adjacent.index == 0) {
                            adjacent = graph[randPoint+Size][0].point;

                            if (adjacent.index == 0) {
                                adjacent = board[Size-1][Size-1];
                            }
                        }
                    }
                    Point u = find(current);
                    Point v = find(adjacent);
                    
                    if (u != v) {
                        union(u, v);
                        myEdge.deleted = true;
                        myEdge.used = true;
                        CountUnions++;
                    }
                    else {
                        myEdge.used = true;
                        CountUnions++;
                    }
                }
            }
            createMaze();
        }
    }
