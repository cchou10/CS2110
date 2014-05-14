package student;

import java.io.File;	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trie {

	//Helper variable to denote mapping from digits to characters on a typical mobile keypad
	private static char[][] digitToCharMapping = {{}, {}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
		{'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
	
	//Pointer to the root of the trie
	private TrieNode root;
	
	/** This function takes a complete word, initializes the root if it is null, 
	 * and inserts the word into the trie pointed to by the root
	 * @param inputWord
	 * @return
	 */
	public void insert(String inputWord){
		if (root == null)
			root = new TrieNode(inputWord);
		root.setChildren(inputWord);
	}
	
	/** This takes a filename, and calls insert for each word it discovers in the file
	 * @param filename
	 * @return
	 * @throws IOException, FileNotFoundException
	 */
	public void createTrieFromFile(String filename) throws IOException, FileNotFoundException {
		List<String> fileInput = new ArrayList<String>();

		try { // only completes the task if file is found
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				String newLine = scanner.nextLine();
				fileInput.add(newLine);
			}
			scanner.close();

		} catch (FileNotFoundException e) {

			System.out.println("Could not find file. Please check given path");
		}
		for (int num = 0; num < fileInput.size(); num++) {
			insert(fileInput.get(num));
		}
	}
	
	/** This takes a complete query, and descends through the trie pointed by root, and returns its appearances
	 * in query history, if found. 0 otherwise.
	 * @param inputWord
	 * @return numAppearances
	 */
	public int lookup(String inputWord){
		int numappearances = 0;
		for (int i = 0; i < root.getChildren().size(); i++){
			TrieNode test = root.getChildren().get(i);
			if (test.getCurrentString().getWord() == inputWord)
				numappearances++;
			root = test;
			lookup(inputWord);
		}
		return numappearances;
	}
	
	
	/** This takes a partial query, and descends through the trie pointed 
	 * by root, and returns mostFrequentCompletion info
	 * @param inputPrefix
	 * @return WordCountPair of mostFrequentCompletion
	 */
	public WordCountPair findComplete(String inputPrefix){
		root = new TrieNode(inputPrefix);
		prepareTrieForQueries();
		WordCountPair most = root.getMostFrequentCompletion();
		/* for (int i = 0; i < root.getChildren().size(); i++){
			TrieNode test = root.getChildren().get(i);
			most = WordCountPair.max(test.getCurrentString(), most);
			root = test;
			findComplete(test.getCurrentString().getWord());
		} */
		return most;
	}
	
	/** This takes a numeric prefix, and first maps into several possible 
	 * character prefixes (you may use a list to store these)
	 * Next, it does findComplete appropriately and returns the string that has 
	 * the most frequent appearance over all these prefixes.
	 * @param digits
	 * @return completeString
	 */
	public String challenge(String digits){
		List<String> num = recurseChallenge(digits);
		WordCountPair most = null;
		for (int i = 0; i < num.size(); i++){
			String tester = num.get(i);
			WordCountPair biggest = null;
			biggest = findComplete(tester);
			most = WordCountPair.max(biggest, most);			
		}
		return most.getWord();
	}
	public List<String> recurseChallenge(String digits){
		List<String> num = new ArrayList<String>();
		String input = null;
		for (int i = 0; i < Integer.parseInt(digits.substring(0,0)); i++){
			input = input + digitToCharMapping[Integer.parseInt(digits.substring(0,0))][i];
			if (digits.length() > 0)
				recurseChallenge(digits.substring(1));
			else {
				num.add(input);
				input = null;
			}
		}
		return num;
	}
	
	/** This recursively populates the mostFrequentCompletion 
	 * in each TrieNode, to make the findComplete method more efficient
	 * @param 
	 * @return 
	 */
	public void prepareTrieForQueries(){
		for (int i = 0; i < root.getChildren().size(); i++){
			root.getChildren().get(i).getCurrentString().increment();
			root = root.getChildren().get(i);
			prepareTrieForQueries();
		}
		return;
	}
}
