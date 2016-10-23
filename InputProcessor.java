import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * This class processes a text file, and calls appropriate BinarySearchTree methods
 * according to the processed input.
 * 
 * A BufferedReader is used to read in the lines of the textfile, and tokenizer is used to
 * process individual lines of input.
 * 
 * This class will call either the "insert" "remove" "print_tree" or "inorder_list" methods from
 * Binary Search Tree, and will print an error message if an invalid command is used.
 * 
 * @author Steven Wojsnis
 *
 */
public class InputProcessor {
	public InputProcessor(BinarySearchTree<Integer> tree){
		
		try (BufferedReader br = new BufferedReader(new FileReader("project2.txt")))
		{
		
			String line = br.readLine();
			
			//iterates through whole textfile
			while(line != null){
				StringTokenizer tokenizer = new StringTokenizer(line, " ");
			
				String command = tokenizer.nextToken();
			
				//checks if the desired command is insert
				if(command.equals("insert")){
					if(tokenizer.hasMoreTokens()){
						String strValue = tokenizer.nextToken();
						
						//try-catch ensures that the value being inserted is an integer
						try{
							Integer value = Integer.parseInt(strValue);
							tree.insert(value);
						}
						catch(NumberFormatException e){
							System.out.println(strValue+" is not a valid input for an Integer Binary Search Tree, insert an integer");
						}
					}
					
					//ensures that "insert" is followed by a value
					else
						System.out.println("The insert command needs to be followed by a value. Insert failed.");
				}
				
				//checks if the desired command is remove
				else if(command.equals("remove")){
					if(tokenizer.hasMoreTokens()){
						String strValue = tokenizer.nextToken();
						
						//try-catch ensures that the value being removed is an integer
						try{
							Integer value = Integer.parseInt(strValue);
							tree.remove(value);
						}
						catch(NumberFormatException e){
							System.out.println(strValue+" is not a valid input for an Integer Binary Search Tree");
						}
					}
					
					//ensures that "remove" is followed by a value
					else
						System.out.println("The remove command needs to be followed by a value. Remove failed.");
				}
				
				//checks if the desired command is print_tree, if so, the tree is printed
				else if(command.equals("print_tree")){
					System.out.println("");
					tree.printTree();
					System.out.println("");
				}
				
				//checks if the desired command is inorder_list, if so, the list is printed
				else if(command.equals("inorder_list")){
					Iterator<Integer> it = tree.iterator(); //used to iterate through the LinkedList with node values
					while(it.hasNext()){
						System.out.print(it.next());
						
						//makes sure the last value printed doesn't have a comma after it.
						if(it.hasNext())
							System.out.print(", ");
					}
					System.out.println("");
				}
				
				//if the command is not one of the accepted four commands, an error message is displayed
				else
					System.out.println("Invalid command");
			
				line = br.readLine(); //reads in the next line to be processed
			}
		}
		
		//in case a file that is not named "project2.txt" is used, an error message is displayed.
		catch(IOException e){
			System.out.println("Please use a text filed named 'project2.txt'");
		}
	}
}
