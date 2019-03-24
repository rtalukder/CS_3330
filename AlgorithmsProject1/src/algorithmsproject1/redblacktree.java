package algorithmsproject1;


public class redblacktree {

	  private treeNode Root;
	  
	  public treeNode getRoot() { 
              return Root; 
          }
	  // Constructs a new empty red-black tree.
	  public redblacktree() { 
              Root = Root.nullnode;
          }
	  public void clear() { 
              Root = Root.nullnode; 
          }
	  
	  public boolean isEmpty() {
              return Root.isEmpty(); 
          }	  

	  public int size() { 
              return Root.size(); 
          }
	    
	  public treeNode find(int key) {
		  if (Root.isEmpty()) return Root;
		  return Root.find(key);
	  }

	  public void checkRedBlack() {
		  if (Root.isRed) 
			  System.out.println( "The root is red");
		  Root.bheight();
	  }
	  
	  public void insert(int id) { // call recursive insert
	       treeNode nnd = new treeNode(id);    // make new node

	       if (Root.isEmpty()) Root = nnd;
	       else if (id < Root.iData) {
	    	   if (Root.left.isEmpty())
	    		   Root.left = nnd;
	    	   else if (insert(nnd, Root.left, Root)) {
	  	    	   // red-red violation exists at Root.left and its children
			       if (Root.right.isRed) { // red uncle case:
			    	   Root.right.isRed = Root.left.isRed = false;
			       } else { // black uncle case:			    	   
			    	   Root.isRed = true;
			           if (nnd.iData < Root.left.iData) {
			               Root = Root.rotateToRight();
			           } else {
			               Root = Root.doubleRotateToRight();
			           } 			 
			       } // else
	    	   }
	       } else if (Root.right.isEmpty()) {
	    		   Root.right = nnd;
	       } else if (insert(nnd, Root.right, Root)) {
  	    	   // red-red violation exists at Root.right and its children
		       if (Root.left.isRed) { // red uncle case:
		    	   Root.right.isRed = Root.left.isRed = false;
		       } else { // black uncle case:		    	   
		    	   Root.isRed = true;
		           if (nnd.iData >= Root.right.iData) {
		               Root = Root.rotateToLeft();
		           } else {
		               Root = Root.doubleRotateToLeft();
		           } 
		       } // else
	       }
	       Root.isRed = false;
	    }  // end insert()
	  
	  /* *************************************************** *
	   *  PRIVATE METHODS                                    *
	   * *************************************************** */
	  
	  /* Inserts a node into tree and returns a boolean */
	  private boolean insert(treeNode nnd, treeNode t, treeNode par) {
		  // return true iff t is red and t has a red child

		  if (nnd.iData < t.iData) {
		      if (t.left.isEmpty()) {
		         t.left = nnd;  //attach new node as leaf
		      } else if (insert(nnd, t.left, t)) { 
		    	 // red-red violation exists at t.left and its children
		    	 if (t.right.isRed) { // red uncle case:
		    		 t.right.isRed = t.left.isRed = false;
		    		 t.isRed = true;
		    	 } else { // black uncle case:
		    		 treeNode nt;
		    		 
		             if (nnd.iData < t.left.iData) {
		                nt = t.rotateToRight();
		             } else {
		                nt = t.doubleRotateToRight();
		             } 
		    		 t.isRed = true;
		    		 nt.isRed = false;
		    		 if (nt.iData < par.iData) par.left = nt;
		    		 else par.right = nt;
		         } //if
		      }
		      
		      return (t.isRed && t.left.isRed);
		 } else { // branch right
		      if (t.right.isEmpty()) {
		           t.right = nnd;   // attach new node as leaf
			  } else if (insert(nnd, t.right, t)) { 
			       // red-red violation exists at t.right and its children
			       if (t.left.isRed) { // red uncle case:
			    		 t.right.isRed = t.left.isRed = false;
			    		 t.isRed = true;
			       } else { // black uncle case:
			    		 treeNode nt;
			    		 
			             if (nnd.iData >= t.right.iData) {
			                nt = t.rotateToLeft();
			             } else {
			                nt = t.doubleRotateToLeft();
			             } 
			    		 t.isRed = true;
			    		 nt.isRed = false;
			    		 if (nt.iData < par.iData) par.left = nt;
			    		 else par.right = nt;
			       } // else
			  } 
		      
			  return (t.isRed && t.right.isRed);
		 } // else
	  }  // end insert

    public void remove(int id) {
        
    }
}
