/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	
	public IAVLNode root; // the root of the tree
	
	  /**
	   * public AVLTree
	   * constructor of an empty tree
	   * O(1)
	   */
	  public AVLTree() {
		}
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *O(1)
   */
	
  public boolean empty() {
    if(this.root == null) {
    	return true;
    }
    return false;
  }
  /**
   * private static nodeHegight(IAVLNode node)
   * returns the height(rank) of a node in the tree
   * O(1)
   */

	private static int nodeHeight(IAVLNode node)
  {
  	if (node==null)
  		return -1;
    return node.getHeight();
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  if(this.root == null) {
		  return null;
	  }
	return searchRec(k, this.root);  // calls recursive search method
  }
  /**
   * private String searchRec(int k, IAVLNode node)
   * recursive method that returns the Value of the node with key k
   * O(logn)
   */
  private String searchRec(int k, IAVLNode node) {
	  if(node.getKey() == k) {
		  return node.getValue();
	  }
	  if(node.getKey()> k) {
		  if(node.getLeft() == null) {
			  return null;
		  }
		  else {
			  return searchRec(k, node.getLeft());
		  }
	  }
	  else {
		  if(node.getRight() == null) {
			  return null;
		  }
		  else {
			  return searchRec(k, node.getRight());
		  }
	  }
  }
  /**
   * private void rotateRight(IAVLNode x)
   * makes right rotation of node X
   * O(1)
   * pre - x has a father
   */

  private void rotateRight(IAVLNode x) {
	  if(x==null) {
		  return;
	  }
	  if (x.getParent()==null) {
		  System.out.println("tried to rotate x to the right, though x has no father");
		  return;
	  }
	  if (x.getParent().getLeft()!=x) {
		  System.out.println("tried to rotate x to the right, though x is not a left child");
	  }
	  IAVLNode y=x.getParent();
	  x.setParent(y.getParent());
	  if (y.getParent()!=null) {
		  boolean y_is_right=y.getParent().getRight()==y;
		  if (y_is_right) {
			  x.getParent().setRight(x);
		  }
		  else {
			  x.getParent().setLeft(x);
		  }
	  }
	  else {
		  this.root=x;
	  }
	  
	  y.setParent(x);
	  y.setLeft(x.getRight());
	  if (x.getRight()!=null)
		  x.getRight().setParent(y);
	  x.setRight(y);
  }
  
  /**
   * private void rotateLeft(IAVLNode x)
   * makes left rotation of node X
   * O(1)
   * pre - x has a father
   */
  private void rotateLeft(IAVLNode x) {
	  if(x==null) {
		  return;
	  }
	  if (x.getParent()==null) {
		  System.out.println("tried to rotate x to the left, though x has no father");
		  return;
	  }
	  if (x.getParent().getRight()!=x) {
		  System.out.println("tried to rotate x to the left, though x is not a right child");
	  }
	  IAVLNode y=x.getParent();
	  x.setParent(y.getParent());
	  if (y.getParent()!=null) {
		  boolean y_is_left=y.getParent().getLeft()==y;
		  if (y_is_left) {
			  x.getParent().setLeft(x);
		  }
		  else {
			  x.getParent().setRight(x);
		  }  
	  }
	  else {
		  this.root=x;
	  }
	  y.setParent(x);
	  y.setRight(x.getLeft());
	  if (x.getLeft()!=null)
		  x.getLeft().setParent(y);
	  x.setLeft(y);
  }
  /**
   * public void legal_Promote(IAVLNode node)
   * promotes all the ranks in the nodes search route
   * O(logn)
   */
  public void legal_Promote(IAVLNode node) {
		do {
			node.setHeight(node.getHeight()+1);
			node=node.getParent();
		} while (node!=null);
	}

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * O(logn)
   */
  	
  public int insert(int k, String i) {
	   IAVLNode z = new AVLNode(k,i,0);//the inserted node
	   if(empty()) {
		   this.root = z;
		   return 0;
	   }
	   
	   if(search(k)!=null) {
		   System.out.println("yo");
		   return -1;
	   }
	   IAVLNode y = treePosition(k,this.root);
	   z.setParent(y);
	   if(z.getKey()>y.getKey()) 
	   {
		   y.setRight(z);
	   }
	   else 
	   {
		   y.setLeft(z);
	   }
	   if(y.getHeight() == 1) //case B, 1, the parent is an unary node
	   {
		   return 0;
	   }
	   else // case A or B 2,3, a rebalance is neccesary 
	   {
		   return rebalanceInsert(z);   
	   }
	   	   
	  
  }
/**
 * private int rebalanceInsert(IAVLNode node)
 * rebalances the AVL and returns number of rebalancing operations, where promote, demote & rotate count as 1 operation
 * o(logn)
 */
	   
   private int rebalanceInsert(IAVLNode node) {
	   
	   IAVLNode z; //parent
	   IAVLNode y; // other child
	   boolean isRight; //is node a right child
	   int count = 0;
	   int diff; // rank difference between z and child 
	   int yHeight;//rank of other child
	   if(node == root) {
		   return 0;
	   }
	   while(node.getParent()!= null)// while we havent reached the root 
	   {
		   z = node.getParent();
		   if(node == z.getRight()) // if our node is a right child
		   {
			   y=z.getLeft();
			   isRight = true;
		   }
		   else {
			   y=z.getRight();
			   isRight = false;
		   }
		   if(y==null) // if our nodes 'brother' does not exist 
		   {
			   yHeight =-1;
		   }
		   else {
			   yHeight = y.getHeight();
		   }
		   diff = z.getHeight() - yHeight;
		   if(diff == 1) // Case A 
		   {
			   z.setHeight(z.getHeight()+1);
			   count++;
			   node = z;
			   if (node.getParent()!=null) {
				   if (valid(node.getParent().getHeight(),nodeHeight(node.getParent().getLeft()),nodeHeight(node.getParent().getRight()))) 
				   {
					   break;
				   }
			   }
			   
		   }
		   else //case 2
		   {
			   if(isRight) {
				   if((node.getHeight() - nodeHeight(node.getLeft())== 2) && (node.getHeight() - nodeHeight(node.getRight()) ==1)) {
					   rotateLeft(node);
					   z.setHeight(z.getHeight()-1);
					   count=count+2;
					   break;
				   }
				   else // case 3 
				   {
					   rotateRight(node.getLeft());
					   rotateLeft(node.getParent());
					   node.setHeight(node.getHeight()-1);
					   z.setHeight(z.getHeight()-1);
					   node.getParent().setHeight(node.getParent().getHeight()+1);
					   count = count +5;
					   break;
				   }
			   }
			   else // case 2 for other side 
			   {
				   if((node.getHeight() - nodeHeight(node.getRight())== 2) && (node.getHeight() - nodeHeight(node.getLeft())) ==1) {
					   rotateRight(node);
					   z.setHeight(z.getHeight()-1);
					   count=count+2;
					   break;
				   }
				   else // case 3 for other side 
				   {
					   rotateLeft(node.getRight());
					   rotateRight(node.getParent());
					   node.setHeight(node.getHeight()-1);
					   z.setHeight(z.getHeight()-1);
					   node.getParent().setHeight(node.getParent().getHeight()+1);
					   count = count +5;
					   break;
				   }
			   }
			   
		   }
	   }
	   
	   return count;
   }
   /**
    * private IAVLNode treePosition(int k, IAVLNode x)
    * returns the node needs to be deleted/splitted and the parent of the node that we insert
    * O(logn)
    */
   private IAVLNode treePosition(int k, IAVLNode x) {
	   IAVLNode y = x;
	   while(x!=null) {
		   y=x;
		   if(x.getKey()==k) {//in case of delete or split return this node
			   return x;
		   }
		   else if(x.getKey() > k) {//checks if to go right or left down the tree
			   x=x.getLeft();
		   }
		   else {
			   x=x.getRight();
		   }
	   }
	   return y;
   }
   /**
	   * public int delete(int k)
	   *
	   * deletes an item with key k from the binary tree, if it is there;
	   * the tree must remain valid (keep its invariants).
	   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	   * returns -1 if an item with key k was not found in the tree.
	   */
	   
	   
	   
	   public int delete(int k)
	   {
		   
		   if(search(k)==null) {
			   return -1;
		   }
		   AVLNode y = (AVLNode)treePosition(k,this.root);
		   if (y.getParent()==null&&y.getHeight()==0) {
			   root=null;
			   return 0;
		   }
		   //AVLNode y = (AVLNode)treePosition(k,this.root);
		   
		   if (y.getHeight()>1||y.getParent()==null) // if leaf, unary node or root 
		   {
			 //swap with succesor or predeccssor acording to the tendency of the tree
			   if (nodeHeight(y.getRight())>nodeHeight(y.getLeft())) 
			   {
				   
				   //y=y.swap((AVLNode)y.successor());
				   y=y.swap((AVLNode)y.successor());
			   }
			   else {
				   y=y.swap((AVLNode)y.predeccessor());
			   }
			   
			   
		   }
		   boolean y_is_right=(y==y.getParent().getRight());
		   if (y.getHeight()==1) // if y is unary node, 'skip' over it and then rebalance 
		   {   
			   
			   
			   if (y_is_right) {
				   if (y.getRight()!=null) {
					   y.getParent().setRight(y.getRight());
					   y.getRight().setParent(y.getParent());
				   }
				   else {
					   y.getParent().setRight(y.getLeft());
					   y.getLeft().setParent(y.getParent());
				   }
				   
			   }
			   
			   else {
				   if (y.getRight()!=null) {
					   y.getParent().setLeft(y.getRight());
					   y.getRight().setParent(y.getParent());
				   }
				   else {
					   y.getParent().setLeft(y.getLeft());
					   y.getLeft().setParent(y.getParent());
				   }
				   
			   }
			   return rebalanceDelete(y.getParent());
			   
		   }
		   else // is leaf 
		   {
			   if (y_is_right) {
				   y.getParent().setRight(null);
				   // case 1 - solvable without problem
				   if (y.getParent().getLeft()!=null &&y.getParent().getLeft().getHeight()==0) 
				   {
						   return 0;
				   }	   
				   else
				   {
					   return rebalanceDelete(y.getParent());
				   }
			   }
			   else // is unary 
			   {
				   y.getParent().setLeft(null);
				   // case 1, solvable without problem
				   if (y.getParent().getRight()!=null &&y.getParent().getRight().getHeight()==0) 
				   {
						   return 0;
				   }	   
				   else
				   {
					   return rebalanceDelete(y.getParent());
				   }
				   
			   }
		   }  
	   }
	   
	   /**
	    * 
	    * @param root
	    * @param right
	    * @param left
	    * @return True if the relations between the ranks of the root,
	    * right and left nodes are valid for AVL, False otherwise
	    * O(1)
	    */
	   public static boolean valid(int root, int right, int left) {
		   int right_diff=root-right;
		   int left_diff=root-left;
		   if ((right_diff==1&&left_diff==1)||(right_diff==1&&left_diff==2)||(right_diff==2&&left_diff==1))
		   {
			   return true;
		   }
		   return false;
	   }
	   /**
	    * Rebalances the tree to keep it a valid AVL Tree after delete operation
	    * returns number of rebalancing operations.
	    * goes from the deleted node up to the root 
	    * o(logn)
	    * 
	    */
	   private int rebalanceDelete(IAVLNode x) {
		   if (x==null) // the last node before was the root. 
		   {
			   return 0;
		   }
		   
		   int right_son_height=nodeHeight(x.getRight());
		   int left_son_height=nodeHeight(x.getLeft());
		   if (valid(x.getHeight(),right_son_height,left_son_height)) // if valid AVL relations 
		   {
			   return 0;
		   }
		   int right_gs_height;
		   int left_gs_height;
		   if (right_son_height-left_son_height==0)//case 1, 2,2 node 
		   {
			   x.setHeight(x.getHeight()-1);
			   return rebalanceDelete(x.getParent())+1;
		   }
		   else {
			   if (right_son_height-left_son_height==2) // 3,1 node 
			   {
				   right_gs_height=nodeHeight(x.getRight().getRight());
				   left_gs_height=nodeHeight(x.getRight().getLeft());
				   if (right_gs_height-left_gs_height==0) //case 2, 1,1 node  
					   {
					   rotateLeft(x.getRight());
					   x.setHeight(x.getHeight()-1);
					   x.getParent().setHeight(x.getParent().getHeight()+1);
					   
					   return 3;
				   }
				   else {
					   
					   if (right_gs_height-left_gs_height==1)//case 3, 2,1 sub node 
					   {   
						   rotateLeft(x.getRight());
						   x.setHeight(x.getHeight()-2);
						   return 3+rebalanceDelete(x.getParent().getParent());
						   
						   
					   }
					   else // case 4, 1,2 sub node 
					   {
						   
						   
						   rotateRight(x.getRight().getLeft());
						   rotateLeft(x.getRight());
						   
						   x.setHeight(x.getHeight()-2);
						   x.getParent().setHeight(x.getParent().getHeight()+1);
						   x.getParent().getRight().setHeight(x.getParent().getRight().getHeight()-1);
						   
						   
						   
						   return 6+rebalanceDelete(x.getParent().getParent());
					   }
					   
				   }
			   }
			   else // same thing for other side, symetric.
			   {
				   right_gs_height=nodeHeight(x.getLeft().getRight());
				   left_gs_height=nodeHeight(x.getLeft().getLeft());
				   if (right_gs_height-left_gs_height==0) //case 2, 1,1 sub node 
					   {
					   rotateRight(x.getLeft());
					   x.setHeight(x.getHeight()-1);
					   x.getParent().setHeight(x.getParent().getHeight()+1);
					   
					   return 3;
				   }
				   else {
					   if (left_gs_height-right_gs_height==1)//case 3, 2,1 sub node 
					   {
						   rotateRight(x.getLeft());
						   x.setHeight(x.getHeight()-2);
						   return 3+rebalanceDelete(x.getParent().getParent());
						   
						   
					   }
					   else {
						   
						   rotateLeft(x.getLeft().getRight());
						   rotateRight(x.getLeft());
						   x.setHeight(x.getHeight()-2);
						   x.getParent().setHeight(x.getParent().getHeight()+1);
						   x.getParent().getLeft().setHeight(x.getParent().getLeft().getHeight()-1);
						   
						   
						   return 6+rebalanceDelete(x.getParent().getParent());
					   }
					   
				   }
			   }
		   }
	   }


   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    * O(logn)
    */
   public String min()
   {
	  if(empty()) {
		  return null;
	  }
	  IAVLNode curr = this.root;
	  while(curr.getLeft()!=null) {//go all the way down left
		  curr = curr.getLeft();
	  }
	  return curr.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    * O(logn)
    */
   public String max()
   {
	   if(empty()) {
			  return null;
		  }
		  IAVLNode curr = this.root;
		  while(curr.getRight()!=null) {//go all the way down right
			  curr = curr.getRight();
		  }
		  return curr.getValue();
   }
   /**
    * public IAVLNode max()
    *
    * Returns the node with the largest key in the tree,
    * or null if the tree is empty
    * O(logn)
    */
   public IAVLNode max2(IAVLNode node)
   {
	   if(node ==null) {
			  return null;
		  }
		  IAVLNode curr = node;
		  while(curr.getRight()!=null) {//go all the way down right
			  curr = curr.getRight();
		  }
		  return curr;
   }
   

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * O(n)
   */
  public int [] keysToArray()
  {		
	    String keys =keysToArrayRec(this.root, "");	//calls recursive method that returns a string of the keys by order
        String [] keys_strings=keys.split(" ");//splits the string to an array of 1 chars strings
        int [] return_arr= new int [keys_strings.length];
        for (int j=0;j<keys_strings.length;j++) {
        	return_arr[j]=Integer.parseInt(keys_strings[j]);//casting the string to ints 
        }
        return return_arr;              
  }
  /**
   *  private String keysToArrayRec(IAVLNode node, String keys)
   *  Recursive function that returns the keys of the trees as a sorted string
   *  O(n)
   */
  private String keysToArrayRec(IAVLNode node, String keys) {
	  if (node==null)
		  return "";
	  keys=keysToArrayRec(node.getLeft(), keys);//write my left subtree
	  keys+=node.getKey()+" ";//write the subtree's root
	  keys+=keysToArrayRec(node.getRight(), keys);//write my right subtree
	  return keys;
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * O(n)
   */
  public String[] infoToArray()
  {
	  String keys =infoToArrayRec(this.root, "");	//calls recursive method that returns a string of the values sorted by the keys
      String [] keys_strings=keys.split(" ");//splits the string into array as requested
     
      return keys_strings;       
  }
  /**
   *  private String infoToArrayRec(IAVLNode node, String keys)
   *  Recursive function that returns the values of the trees as a sorted string by keys
   *  O(n)
   */
  private String infoToArrayRec(IAVLNode node, String keys) {
	  if (node==null)
		  return "";
	  keys=infoToArrayRec(node.getLeft(), keys);//write my left subtree
	  keys+=node.getValue()+" ";//write the subtree's root
	  keys+=infoToArrayRec(node.getRight(), keys);//write my right subtree
	  return keys;
  }

   /**
    * public int size()
    * Returns the number of nodes in the tree.
    * precondition: none
    * postcondition: none
    * O(n)
    */
   public int size()
   {
	   return sizeRec(this.root);//calls a recursive method
   }
   /**
    * private int sizeRec(IAVLNode node)
    * Returns the number of nodes in the tree.
    * O(n)
    */
   
   private int sizeRec(IAVLNode node) {
	   if(node == null) {//stop condition for recursion
		   return 0;
	   }
	   else {
		   return (sizeRec(node.getLeft()) + 1 + sizeRec(node.getRight()));//returns size of left subtree + right subtree + sub-root
	   }
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    * O(1)
    */
   public IAVLNode getRoot()
   {
	   if(empty()) {
		   return null;
	   }
	   return this.root;
   }
     /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null
    * postcondition: none
    * O(logn)
    */   
   public AVLTree[] split(int x)
   {
	   AVLTree [] res = new AVLTree[2];//the returned array
	   IAVLNode splitter = treePosition(x, this.root);
	   AVLTree right = new AVLTree();// tree of all the right subtrees
	   right.root = splitter.getRight();
	   if(right.getRoot()!=null && right!=null){
	   right.getRoot().setParent(null);
	   }
	   AVLTree left = new AVLTree();//tree of all the left subtrees
	   left.root = splitter.getLeft();
	   if(left.root !=null && left != null){
		   left.root.setParent(null);
		   }
	   IAVLNode par = splitter.getParent();
	   if(par == null) {// in case splitter is the root
		   res[0] = left;
		   res[1] = right;
		   return res;
	   }
	   boolean isRightPar=true;
	   boolean isRightGrandpar=true;
	   // right left par 

		if (par.getRight() == splitter) {
			isRightPar = true;
		} else {
			isRightPar = false;
		}
		IAVLNode grandPar = par.getParent();
		if (grandPar != null) {
			// right left grandpar 
			if (grandPar.getRight() == par) {
				isRightGrandpar = true;
			} else {
				isRightGrandpar = false;
			}
		}
		int max=0;
		int sum=0;
		int count =0;
		while (par != null)// as long as i have father
		{

			if (isRightPar)// i'm right son
			{
				AVLTree temp = new AVLTree();// temp tree to join
				temp.root = par.getLeft(); // make a tree from the left
				if(temp.root!=null) {
				temp.root.setParent(null);
				}
				int tmp = left.join(par, temp);
				sum+=tmp;
				count++;
				if(tmp>max) {
					max=tmp;
				}
			} else // i'm left son
			{
				AVLTree temp = new AVLTree();// temp tree to join
				temp.root = par.getRight(); // make a tree from the right
				if(temp.root!=null) {
					temp.root.setParent(null);
				}
				int tmp =right.join(par, temp);
				sum+=tmp;
				count++;
				if(tmp>max) {
					max=tmp;
				}
			}
			par = grandPar;
			isRightPar = isRightGrandpar;
			if (grandPar != null) {
				grandPar = grandPar.getParent();
				if (grandPar != null)
					if (grandPar.getLeft() == par) {
						isRightGrandpar = false;
					} else {
						isRightGrandpar = true;
					}
			}

		}
	   res[0] = left;
	   res[1] = right;
	   //System.out.println("max join  "+max+ "   avg join   "+(sum/count));
	   return res; 
   }
   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (rank difference between the tree and t)
	  * precondition: keys(x,t) < keys() or keys(x,t) > keys()
    * postcondition: none
    * O(absolute(height(tree)-height(t))+1)
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   if(t.getRoot() == null) {//edge case
		   this.insert(x.getKey(),x.getValue());
		   t.root = this.root;
		   return (this.root.getHeight()+2);
	   }
	   if(this.root == null) {//edge case
		   t.insert(x.getKey(),x.getValue());
		   this.root = t.getRoot();
		   return (t.getRoot().getHeight()+2);
	   }
	   int k1 = this.root.getHeight();//rank of current tree
	   int k2 = t.getRoot().getHeight();// rank of tree t
	   if(k1==k2) {// in case both trees have the same height
		   if(x.getKey()>t.getRoot().getKey()) {
			   x.setRight(this.root);
			   x.setLeft(t.getRoot());
		   }
		   else {
			   x.setLeft(this.root);
			   x.setRight(t.getRoot());
		   }
		   this.root.setParent(x);
		   t.getRoot().setParent(x);
		   x.setHeight(k1+1);
		   this.root = x;
		   t.root = x;
		   return 1;
	   }
	   if(Math.abs(k1-k2)==1) {//specific case
		   x.setHeight(Math.max(k1, k2)+1);
			   if(x.getKey()<t.getRoot().getKey()) {
				   x.setRight(t.getRoot());
				   x.setLeft(this.root);
				   this.root.setParent(x);
				   t.getRoot().setParent(x);
				   this.root=x;
			   }
			   else {
				   x.setLeft(t.getRoot());
				   x.setRight(this.root);
				   this.root.setParent(x);
				   t.getRoot().setParent(x);
				   this.root=x;
			   }
			   return 2;
		   }
	   
	   AVLNode addNode = (AVLNode) x;
		AVLNode newNode = (AVLNode) t.getRoot();
		AVLNode originalNode = (AVLNode) this.root;
		int complexity = Math.abs(k1-k2) + 1;
		if (newNode.getHeight() > originalNode.getHeight()) { // new tree is higher than original tree
			if (newNode.key > originalNode.key) { // new tree keys are bigger than original tree keys
				newNode = (AVLNode) goDownLeft(newNode, originalNode.getHeight());
				joinFromLeft(addNode, originalNode, newNode);
			} else {// new tree keys are smaller than original tree keys
				newNode = goDownRight(newNode, originalNode.getHeight());
				joinFromRight(addNode, newNode, originalNode);
			}
		} else { // original tree is higher than new tree
			if (originalNode.key > newNode.key) {// new tree keys are smaller than original tree keys
				originalNode = (AVLNode) goDownLeft(originalNode, newNode.getHeight());
				joinFromLeft(addNode, newNode, originalNode);
			} else {// new tree keys are bigger than original tree keys
				originalNode = goDownRight(originalNode, newNode.getHeight());
				joinFromRight(addNode, originalNode, newNode);
			}
		}
		rebalanceInsert((AVLNode) addNode.getParent());
		return complexity;
   }
   
   /**
    * private void joinFromLeft(IAVLNode addNode, IAVLNode smallTree, IAVLNode bigTree)
    *  join smallTree from left to bigTree with addNode
    * O(1)
    */
   private void joinFromLeft(IAVLNode addNode, IAVLNode smallTree, IAVLNode bigTree) { 
	   addNode.setParent(bigTree.getParent());
	   if(bigTree.getParent()!=null) {
	   bigTree.getParent().setLeft(addNode);
	   }
	   addNode.setLeft(smallTree);
	   smallTree.setParent(addNode);
	   addNode.setRight(bigTree);
	   bigTree.setParent(addNode);
	   addNode.setHeight(smallTree.getHeight() + 1)  ;
   }
/**
 * private void joinFromRight(IAVLNode addNode, IAVLNode bigTree, IAVLNode root2)
 * join smallTree from right to bigTree with addNode
 * O(1)
 */
   private void joinFromRight(IAVLNode addNode, IAVLNode bigTree, IAVLNode root2) {
	   addNode.setParent(bigTree.getParent());
	   if(bigTree.getParent()!=null) {
	   bigTree.getParent().setRight(addNode);
	   }
	   addNode.setRight(root2);
	   root2.setParent(addNode);
	   addNode.setLeft(bigTree);
	   bigTree.setParent(addNode);
	   addNode.setHeight(root2.getHeight() + 1) ;
   }
/**
 * private IAVLNode goDownLeft(IAVLNode subTree, int wantedRank)
 * return the leftest node in subTree with rank <= wantedRank
 * O(logn)
 */
   private IAVLNode goDownLeft(IAVLNode subTree, int wantedRank) { 
	   while (((AVLNode) subTree.getLeft()) != null && ((AVLNode) subTree.getLeft()).getHeight() >= wantedRank) {
		// go down keep left, to the same height as added tree
		   subTree = (AVLNode) subTree.getLeft();
	}
	   return subTree;
   }
   /**
    * private IAVLNode goDownLeft(IAVLNode subTree, int wantedRank)
    * return the leftest node in subTree with rank <= wantedRank
    * O(logn)
    */
   private AVLNode goDownRight(IAVLNode subTree, int wantedRank) { 
	   while (((AVLNode) subTree.getRight()) != null && ((AVLNode) subTree.getRight()).getHeight() >= wantedRank) {
		   // go down keep left, to the same height as added tree
			subTree = (AVLNode) subTree.getRight();
	   }
	return (AVLNode) subTree;
   }
   
	public void print2DUtil(IAVLNode root, int space)  
	{  
	    // Base case  
	    if (root == null)  
	        return;  
	  
	    // Increase distance between levels  
	    space += 10;  
	  
	    // Process right child first  
	    print2DUtil(root.getLeft(), space);  
	  
	    // Print current node after space  
	    // count  
	    System.out.print("\n");  
	    for (int i = 10; i < space; i++)  
	        System.out.print(" ");
	    System.out.print(root.getKey()+" , r: "+root.getHeight()+ "\n");  
	  
	    // Process left child  
	    print2DUtil(root.getRight(), space);  
	}  

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
	  public class AVLNode implements IAVLNode{
		  private int key;//key of the node
		  private String value;//value of node
		  private int height;// rank of the node;
		  private IAVLNode parent;//pointer to parent
		  private IAVLNode left;//pointer to left child
		  private IAVLNode right;//pointer to right chils
		  public AVLNode() {//empty constructor
			  
		  }
		  public AVLNode(int key, String value,int height) {//constructor if height is known
			  //O(1)
			  this.key=key;
			  this.value=value;
			  this.height=height;
		  }
		  public AVLNode(int key, String value) {//constructor without height
			  //O(1)
			  this.key = key;
			  this.value = value;
		  }
			public int getKey()//O(1)
			{
				return this.key;
				
			}
			public String getValue()//O(1)
			{
				if(this.key != -1) {
					return this.value;
				}
				return null;
			}
			public void setLeft(IAVLNode node)//O(1)
			{
				this.left = node;
			}
			public IAVLNode getLeft()//O(1)
			{
				return this.left;
			}
			public void setRight(IAVLNode node)//O(1)
			{
				this.right = node;
			}
			public IAVLNode getRight()//O(1)
			{
				return this.right;
			}
			public void setParent(IAVLNode node)//O(1)
			{
				if(node == null) {
					this.parent = null;
				}
				else {
				this.parent = node;
				}
			}
			public IAVLNode getParent()//O(1)
			{
				return this.parent;
			}
			// Returns True if this is a non-virtual AVL node
			public boolean isRealNode()
			{
				if(this.key == -1 || this.height == -1 || this.value == null) {
					return false;
				}
				return true;
			}
	    public void setHeight(int height)//O(1)
	    {
	      this.height = height;
	    }
	    public int getHeight()//O(1)
	    {
	      if(this.key == -1 || this.value == null) {
	    	  return -1;
	      }
	      return this.height;
	    }
		public void setKey(int key) {
			this.key=key;
		}
		
		public AVLNode swap(AVLNode x)//O(1)
		   {
			   int temp=this.key;
			   this.key=x.getKey();
			   x.setKey(temp);
			   return x;
			   
		   }
	    public String toString() { // used for self debugging
	    	return("key: "+ this.key + " value: "+ this.value + " rank:" + this.height);
	    }
	    private IAVLNode successor() {//O(logn)
		 	   IAVLNode successor = new AVLNode();
		 	   if (this.right!=null) {//if node has right child
		 		   successor =this.right;
		 		   while (successor .getLeft()!=null) {// get the min in the right subtree
		 			  successor=successor.getLeft();
		 		   }
		 		   return successor;
		 	   }
		 	   else {
		 		   while (successor.getParent().getRight()==successor) {//as long as I am right child
		 			   successor=successor.getParent();
		 		   }
		 		   return successor;
		 	   }
		    }
		    
		    private IAVLNode predeccessor() {//O(logn)
		    	IAVLNode predeccessor =new AVLNode();
			 	   if (this.left!=null) {
			 		   predeccessor =this.left;
			 		   while (predeccessor.getRight()!=null) {
			 			  predeccessor=predeccessor.getRight();
			 		   }
			 		   return predeccessor;
			 	   }
			 	   else {
			 		   if (predeccessor.getParent()==null)
			 		   {
			 			   return null;
			 		   }
			 		   while (predeccessor.getParent().getLeft()==predeccessor) {
			 			   predeccessor=predeccessor.getParent();
			 		   }
			 		   return predeccessor;
			 	   }
			    }

		  }


}
  

