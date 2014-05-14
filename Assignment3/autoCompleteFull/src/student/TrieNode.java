package student;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
	
	//children nodes: You can implement this as a list too!
	private List<TrieNode> children;
	
	//the (word, count) pair of this trie node
	private WordCountPair currentString;
	
	//preprocessed most probable autocomplete result for the entire subtree rooted at this node
	private WordCountPair mostFrequentCompletion;

	/**
	 * Create a default TrieNode instance
	 * @param word String which represents the word
	 */
	public TrieNode() {
		children = new ArrayList<TrieNode>();
	}

	/**
	 * Create a TrieNode instance with the input string
	 * @param word String which represents the word
	 */
	public TrieNode(String word) {
		WordCountPair wcp = new WordCountPair(currentString.getWord(), currentString.getCount());
		children = new ArrayList<TrieNode>();
	}

	public List<TrieNode> getChildren() {
		return children;
	}

	public void setChildren(String inputword) {
		children.add(new TrieNode(inputword));
	}

	public WordCountPair getCurrentString() {
		return currentString;
	}

	public void setCurrentString(WordCountPair currentString) {
		this.currentString = currentString;
	}

	public WordCountPair getMostFrequentCompletion() {
		return mostFrequentCompletion;
	}

	public void setMostFrequentCompletion(WordCountPair mostFrequentCompletion) {
		this.mostFrequentCompletion = mostFrequentCompletion;
	}
	
	//Please fill in methods that you find useful to implement the linked data-structure to perform the operations outlined in Trie.java
}
