/**
 * This class, the driver class, contains the main method of the program. In an attempt to make the program
 * more modular and the main method simpler, the main method makes use of the "InputProcessor" class to
 * process the input in the text file, rather than processing it solely in the main method itself.
 * 
 * Having the input processing loop take place in InputProcessor adds more modularity to the program, and
 * makes the main method more flexible, allowing it to be more easily modified, if desired, in the future.
 * 
 * As for the program itself, this is a driver class of a program that will insert values into, and remove 
 * values from a binary tree. This program will also print a binary tree out, with the root node on the left
 * and its children subtrees to the top-right and bottom-right of it. Finally, this program will print the
 * values of the binary tree in the inOrder format.
 * 
 * These processes are all carried out in the Binary Search Tree class, and the decision of which process
 * to execute takes place in the InputProcessor class.
 * 
 * @author Steven Wojsnis
 * Binary Search Tree Project
 * 		CS313, Dr.Svitak
 */
public class Project2 {
	public static void main(String[] args){
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<>(); //creates new instance of BinarySearchTree
		new InputProcessor(tree); //calls constructor of InputProcessor to read input of text file
		
	}
}
