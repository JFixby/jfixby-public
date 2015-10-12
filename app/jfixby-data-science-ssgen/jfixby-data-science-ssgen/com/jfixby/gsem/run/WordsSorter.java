package com.jfixby.gsem.run;

import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;

public class WordsSorter {

	private static final boolean COLLECT_SINGLES = false;
	private static final boolean COLLECT_PAIRS = false;
	private static final boolean COLLECT_TRIPLETS = false;
	private static final boolean COLLECT_4 = !false;
	private Set<String> exclude;

	public WordsSorter() throws IOException {
		super();

		File word_sources = LocalFileSystem.ApplicationHome().child(
				"exclude-words.txt");
		exclude = JUtils.newSet(word_sources.readToString().split("\r\n"));
		exclude.print("exclude");
	}

	public WordsSorter(boolean b) throws IOException {
		File word_sources = LocalFileSystem.ApplicationHome().child(
				"exclude-phrases.txt");
		exclude = JUtils.newSet(word_sources.readToString().split("\r\n"));
		exclude.print("exclude");
	}

	final Map<String, WordCollector> collectors = JUtils.newMap();
	private Comparator<WordCollector> comparator = new Comparator<WordCollector>() {

		@Override
		public int compare(WordCollector o1, WordCollector o2) {
			return Integer.compare(o1.n, o2.n);
		}
	};
	private long total;

	public void addAll(Collection<String> split) {
		for (int i = 0; i < split.size(); i++) {
			this.add(split.getElementAt(i));
		}
	}

	public void addOthers(Collection<WordCollector> split) {
		for (WordCollector c : split) {
			this.addOther(c);
		}
	}

	private void addOther(WordCollector elementAt) {
		String word = elementAt.getWord();
		if (exclude.contains(word)) {
			L.d("skip", word);
			return;
		}

		WordCollector collector = getCollector(word);
		collector.add(elementAt.n);
		total = total + elementAt.n;
	}

	List<String> history = JUtils.newList();

	public void add(String word) {
		if (word.length() == 0) {
			return;
		}
		word = word.toLowerCase();
		if (exclude.contains(word)) {
			return;
		}

		history.insertElementAt(word, 0);
		if (history.size() > 10) {
			history.removeLast();
		}
		if (history.size() > 1 && COLLECT_PAIRS) {
			String previous_word = this.history.getElementAt(1);
			WordCollector collector = getCollector(previous_word + " " + word);
			collector.add();
			total++;
		}
		if (history.size() > 2 && COLLECT_TRIPLETS) {
			String previous_word = this.history.getElementAt(1);
			String previous_previous_word = this.history.getElementAt(2);
			WordCollector collector = getCollector(previous_previous_word + " "
					+ previous_word + " " + word);
			collector.add();
			total++;
		}
		if (history.size() > 3 && COLLECT_4) {
			String previous_word = this.history.getElementAt(1);
			String previous_previous_word = this.history.getElementAt(2);
			String previous_previous_previous_word = this.history
					.getElementAt(3);
			WordCollector collector = getCollector(previous_previous_previous_word
					+ " "
					+ previous_previous_word
					+ " "
					+ previous_word
					+ " "
					+ word);
			collector.add();
			total++;
		}

		if (COLLECT_SINGLES) {
			WordCollector collector = getCollector(word);
			collector.add();
			total++;
		}

	}

	private WordCollector getCollector(String word) {
		WordCollector c = collectors.get(word);
		if (c == null) {
			// L.d("new collector", word);
			c = new WordCollector(word, this);
			collectors.put(word, c);
		} else {
			// L.d("        found", word);
		}

		return c;
	}

	public void print() {
		// List<WordCollector> vals = JUtils.newList(collectors.values());
		// vals.sort(comparator);
		collectors.print("");
	}

	public String quantileOf(int n) {
		return ((int) (n * 100d / total)) + "%";
	}

	public void saveTo(String file_name) throws IOException {

		File word_sources = LocalFileSystem.ApplicationHome().child("words")
				.child(file_name);
		word_sources.makeFolder();
		WordCollectorFile file = new WordCollectorFile();
		Vector<WordCollector> vals = new Vector<WordCollector>();
		vals.addAll(this.collectors.values().toJavaList());
		file.values = vals;
		String data = Json.serializeToString(file);
		word_sources.writeString(data);

	}

	public void filter(int S) {
		Collection<WordCollector> vals = collectors.values();
		List<String> bad = JUtils.newList();
		for (long i = 0; i < vals.size(); i++) {
			WordCollector element = vals.getElementAt(i);
			if (element.n <= S) {
				bad.add(element.getWord());
				L.d("removing", element);
			}
		}
		collectors.removeAll(bad);
	}

	public Collection<WordCollector> list() {
		List<WordCollector> results = JUtils.newList();
		for (int i = 0; i < this.collectors.size(); i++) {
			WordCollector val = this.collectors.getValueAt(i);
			results.add(val);
		}
		return results;

	}

	public void sort() {
		Collection<WordCollector> list = this.list();
		List<WordCollector> vals = JUtils.newList(list);
		vals.sort(comparator);
		vals.reverse();
		this.collectors.clear();
		for (WordCollector c : vals) {
			this.collectors.put(c.getWord(), c);
		}
	}
}
