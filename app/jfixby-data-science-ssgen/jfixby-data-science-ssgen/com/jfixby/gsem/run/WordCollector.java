package com.jfixby.gsem.run;

public class WordCollector {
	int n = 0;
	private String word;

	// private WordsSorter container;

	WordCollector() {
	}

	public WordCollector(String word, WordsSorter container) {
		// this.container = container;
		this.word = word;
	}

	public void add() {
		n++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordCollector other = (WordCollector) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return word + " : " + n + " ";
	}

	public String getWord() {
		return word;
	}

	public void add(int n) {
		this.n = this.n + n;
	}

}
