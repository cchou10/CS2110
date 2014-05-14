package student;

public class WordCountPair {

	private String word;
	private int count;
	
	/**
	 * WordCountPair is a simple class with two variables: word, count, and their getter and compare methods
	 * NOTE: You must implement the compare method (max) below.
	 * @param word
	 * @param count
	 */
	public WordCountPair (String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void increment() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public String toString() {
		return word + ", " + count;
	}
	
	/**
	 * Given two WordCountPair instances, returns the one with more count.
	 * If they have the same count, return one with lexicographically smaller word
	 * @param wcp1
	 * @param wcp2
	 * @return wcp1 or wcp2
	 */
	public static WordCountPair max(WordCountPair wcp1, WordCountPair wcp2) {
		if (wcp1.getCount() > wcp2.getCount())
			return wcp1;
		if (wcp1.getCount() < wcp2.getCount())
			return wcp2;
		if (wcp1.getCount() == wcp2.getCount()){
			String c1 = wcp1.getWord();
			String c2 = wcp2.getWord();
			int com = c1.compareTo(c2);
			if (com < 0)
				return wcp1;
			if (com > 0)
				return wcp2;
		}
		return null;		
	}
}
