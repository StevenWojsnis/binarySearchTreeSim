import java.util.Iterator;
import java.util.LinkedList;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
  private static class Node<AnyType>
  {
    private AnyType data;
    private Node<AnyType> parent;
    private Node<AnyType> left;
    private Node<AnyType> right;

    public Node(AnyType d, Node<AnyType> p, Node<AnyType> l, Node<AnyType> r)
    {
      setData(d);
      setParent(p);
      setLeft(l);
      setRight(r);
    }

    public AnyType getData() { return data; }
    public Node<AnyType> getParent() { return parent; }
    public Node<AnyType> getLeft() { return left; }
    public Node<AnyType> getRight() { return right; }

    public void setData(AnyType d) { data = d; }
    public void setParent(Node<AnyType> p) { parent = p; }
    public void setLeft(Node<AnyType> l) { left = l; }
    public void setRight(Node<AnyType> r) { right = r; }
  }

  private Node<AnyType> root;

  public BinarySearchTree() { makeEmpty(); }

  public void makeEmpty() { root = null; } 

  public boolean isEmpty() { return (root == null); }

  public boolean contains(AnyType v) { return contains(v, root); }

  private boolean contains(AnyType v, Node<AnyType> t)
  {
    if (t == null) return false;

    int compareResult = v.compareTo(t.getData());

    if (compareResult < 0)
      return contains(v, t.getLeft());
    else
      if (compareResult > 0)
        return contains(v, t.getRight());
      else
        return true;
  }

  public AnyType findMin() throws IllegalStateException
  {
    if (isEmpty()) throw new IllegalStateException("Search Tree is empty.");

    Node<AnyType> minNode = findMin(root);
    return minNode.getData();
  }

  private Node<AnyType> findMin(Node<AnyType> t)
  {
    if (t == null)
      return null;
    else
      if (t.getLeft() == null)
        return t;

    return findMin(t.getLeft());
  }

  public AnyType findMax() throws IllegalStateException
  {
    if (isEmpty()) throw new IllegalStateException("Search Tree is empty.");

    Node<AnyType> maxNode = findMax(root);
    return maxNode.getData();
  }

  private Node<AnyType> findMax(Node<AnyType> t)
  {
    if (t == null)
      return null;
    else
      if (t.getRight() == null)
        return t;

    return findMax(t.getRight());
  }

  /**
   * Inserts a value, v, into the binary search tree in the proper position. If the value already
   * exists in the tree, then this method returns, and does nothing.
   * 
   * @param v : value to be inserted
   */
  public void insert(AnyType v)
  {
	  boolean left = false, right = false; // Flags to determine if inserted value should be left or right child
	  
	  // If the tree is empty, insert the value as the new root
	  if(root == null){
		  root = new Node<AnyType>(v,null,null,null);
	  }
	  
	  // Otherwise, find the correct position and insert it.
	  else{
		  
		  // If the value is already in the tree, since we do not accept duplicates, the method returns
		  if(contains(v))
			  return;
	  
		  Node<AnyType> nextNode = root; // The next node whose value is compared to v (initialized to root)
		  Node<AnyType>  parentNode= null; // The parent of nextNode
		  
		  //while there exists another node to be compared to v...
		  while(nextNode != null){
			  left = right = false; 
			  
			  //if v is greater than nextNode's value, nextNode becomes its right child, and right is set to true
			  if(v.compareTo(nextNode.getData())>0){
				  parentNode = nextNode;
				  nextNode = nextNode.getRight();
				  right = true;
			  }
			  
			  //otherwise nextNode becomes its left child, and left is set to true
			  else{
				  parentNode = nextNode;
				  nextNode = nextNode.getLeft();
				  left = true;
			  }
		  }
		  //Once the while loop ends, we have found an empty position to insert the new node
		  nextNode = new Node<AnyType>(v,parentNode,null,null);
		  
		  //Depending on which flag is true, nextNode(The newly inserted node) is set to be the left or right child
		  //of parentNode(The last node whose value was compared to v
		  if(right){
			  parentNode.setRight(nextNode);
		  }
		  else if(left){
			  parentNode.setLeft(nextNode);
		  }	
	  }
  }

  /**
   * Removes a value, v, from the binary search tree. If the value is not present in the list, then
   * a message is printed, informing the user that the list did not exist in the tree and therefore
   * couldn't be removed. In this case, the method then returns, and does nothing.
   * 
   * @param v : value to be removed
   */
  public void remove(AnyType v)
  {
	  //Checks if v is in the tree, if not, message is printed and method returns.
	  if(!contains(v)){
		System.out.println(v+" is not part of the tree, and therefore cannot be removed.");
		return;  
	  }
	  
	  Node<AnyType> currNode = root; //Node whose value is checked against v, to see if this is the node that needs to be removed
	  
	  //While currNode is not the node that needs to be removed...
	  while(v != currNode.getData()){
		  
		  //If v is less than the data in currNode, currNode becomes its left child
		  if(v.compareTo(currNode.getData())< 0){
			  currNode = currNode.getLeft();
		  }
		  
		  //If v is greater than the data in currNode, currNode becomes its right child
		  else if(v.compareTo(currNode.getData())> 0){
			  currNode = currNode.getRight();
		  }
	  }
	  int numOfChildren = countChildren(currNode); //Finds the number of children of the node to be removed
	  
	  //Depending on its number of children, the appropriate case is called to remove the node
	  if(numOfChildren == 0)
		  caseOneRemove(currNode);
	  else if(numOfChildren == 1)
		  caseTwoRemove(currNode);
	  else if(numOfChildren == 2)
		  caseThreeRemove(currNode);

  }
  
  /**
   * The first case for remove is implemented when the node that is to be removed has no children.
   * This method severs the removed node's tie with its parent, and then sets the node to null.
   * 
   * @param n : the node to be removed
   */
  private void caseOneRemove(Node<AnyType> n){
	  
	  //Determines if the node to be removed is a left or right child of its parent, then
	  //sets the appropriate child to null.
	  if(n.getData().compareTo(n.getParent().getData())<0)
		  n.getParent().setLeft(null);
	  else
		  n.getParent().setRight(null);
	  
	  n = null;
  }
  
  /**
   * The second case for remove is implemented when the node that is to be removed has one child.
   * This method set the child of the removed node, n, to the child of n's parent node. Effectively
   * replacing n with its child.
   * 
   * @param n : the node to be removed
   */
  private void caseTwoRemove(Node<AnyType> n){
	  Node<AnyType> child = null;
	  
	  //Determines if n's only child is its left child, or right child. Then sets the child node accordingly.
	  if(n.getLeft() != null)
		  child = n.getLeft();
	  else if(n.getRight() != null)
		  child = n.getRight();
	  
	  //Sets the child node's parent to n's parent.
	  child.setParent(n.getParent());
	  
	  //Determines if n was a right child or a left child, and then set the appropriate child of n's parent
	  //to the child node.
	  if(n.getData().compareTo(n.getParent().getData()) < 0){
		  n.getParent().setLeft(child);
	  }
	  else{
		  n.getParent().setRight(child);
	  }
	  
	  n = null;
  }
 
  /**
   * The third case for remove is implemented when the node to be removed, n, has two children.
   * This method replaces n's value with the smallest value in its right subtree, and then removes
   * the node that had the smallest value in the right subtree.
   * 
   * @param n : the node to be removed
   */
  private void caseThreeRemove(Node<AnyType> n){
	 Node<AnyType> rightChild = n.getRight();
	 
	 //finds the smallest value in the right subtree of n
	 Node<AnyType> replacerNode = findMin(rightChild);
	 
	 //sets the value of n equal to the value of the replacerNode
	 n.setData(replacerNode.getData());
	 
	 //severs the connection between the replacerNode and its parent, removing it from the bottom of the tree
	 if(replacerNode.equals(n.getRight()))
		 n.setRight(null);
	 else
		 replacerNode.getParent().setLeft(null);
	 
 }
  
  /**
   * Counts the number of children any given node, n, has. Note, this does not return the number of nodes
   * that n is an ancestor of, but simply the number of nodes directly attached to n (n's children).
   * 
   * Due to the properties of a binary search tree, this method will either return a 0, 1, or 2.
   * 
   * @param n : the node whose children are being counted
   * @return : the number of children belonging to n
   */
  private int countChildren(Node<AnyType> n){
	  
	  //Checks if both, one, or none of the children of n are null
	  if(n.getLeft() == null && n.getRight() == null)
		  return 0;
	  else if (n.getLeft() != null && n.getRight() != null)
		  return 2;
	  else
		  return 1;
  }

  /**
   * Creates a LinkedList, and stores the node data values of the tree in the desired order. 
   * These values are then printed out from the LinkedList in the desired order with appropriate spacing. 
   * (In this case, desired order means in order of decreasing node data value).
   * 
   * The spacing works by calculating the depth of each node, and letting that be the number of times
   * a set amount of spaces are printed before printing the value.
   * 
   * It should be noted that this printTree method prints the tree horizontally, with the root on the
   * left, the left subtree below the root, and the right subtree above the root.
   */
  public void printTree()
  {
	  //Creates a new LinkedList to hold the nodes in the correct order
	  LinkedList<AnyType> customOrderList = new LinkedList<>();
	  
	  //Fills the LinkedList with tree nodes appropriately, using the customOrderTraversal method.
	  customOrderTraversal(root, customOrderList);
	  
	  Iterator<AnyType> it = customOrderList.iterator();
	  
	  AnyType value;
	  
	  //iterates over the whole LinkedList
	  while(it.hasNext()){
		  value = it.next(); //systematically sets the value placeholder to each value in the LinkedList
		  int depthOfRightMost = findDepth(value); //calculates the depth of the node with data value "value"
		  
		  //Prints a set number of spaces a number of times that is equivalent to the depth of the node
		  //to ensure appropriate spacing
		  while(depthOfRightMost > 0){
			  System.out.print("       ");
			  depthOfRightMost--;
		  }
		  
		  //After the correct number of spaces are printed, the value is printed.
		  System.out.println(value);
	  }
	  
	  System.out.println("\n"+"\n");

  }
  
  /**
   * Calculates the depth of a node with a data value equivalent to parameter v.
   * 
   * This method works by first finding the correct node, by comparing v to a series of nodes (moving left
   * if v is less than the data value of the node, and right if greater than) until a node is found with
   * data value equivalent to v.
   * 
   * Then, starting at the node with data value v, we move back to the root, incrementing a counter by one
   * for each level we pass. This counter will be equivalent to the node's depth.
   * 
   * If there is no node with data value v in the tree, then a depth value of -1 is returned.
   * 
   * @param v : value of the node whose depth is to be calculated
   * @return : the depth of the node with data value v
   */
  private int findDepth(AnyType v){
	  //Checks if v is in the tree, if not then returns -1.
	  if(!contains(v))
		  return -1;
	  
	  Node<AnyType> currNode = root;
	  
	  //Finds the node with data value v, setting it equal to currNode.
	  while(v != currNode.getData()){
		  if(v.compareTo(currNode.getData())< 0){
			  currNode = currNode.getLeft();
		  }
		  else if(v.compareTo(currNode.getData())> 0){
			  currNode = currNode.getRight();
		  }
	  }
	  
	  int count = 0;
	  
	  //Moves upward from currNode, by setting currNode equal to its parent, until the root is hit.
	  while(currNode != root){
		  currNode = currNode.getParent();
		  count++; //For each time currNode is set equal to its parent, the count is incremented.
	  }
	  return count; //The count is equal to the nodes depth.
  }
  
  /**
   * Fills a LinkedList, l, with node data values in a specific (decreasing) order.
   * 
   * This is done by recursively calling itself, with the pattern of right-node-left.
   * This process is similar to a reverse inOrderTraversal.
   * 
   * @param t : the node whose data value will be added to the LinkedList
   * @param l : the LinkedList that will hold the data values
   */
  private void customOrderTraversal(Node<AnyType> t, LinkedList<AnyType> l){
	  {
		    if (t != null) //acts similar to base case.
		    {
		      customOrderTraversal(t.getRight(), l); //calls itself recursively with t's right child
		      l.add(t.getData()); //adds the value of t to LinkedList l
		      customOrderTraversal(t.getLeft(), l); //calls itself recursively with t's left child
		    }
		  }
  }

  public Iterator<AnyType> iterator()
  {
    LinkedList<AnyType> snapshot = new LinkedList<>();

    inOrderTraversal(root, snapshot);
    return snapshot.iterator();
  }

  private void inOrderTraversal(Node<AnyType> t, LinkedList<AnyType> l)
  {
    if (t != null)
    {
      inOrderTraversal(t.getLeft(), l);
      l.add(t.getData());
      inOrderTraversal(t.getRight(), l);
    }
  }
}
