/**
 * 
 */
package es.daedalus.newshack;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author daedalus
 *
 */
public class LanguageStats {

	Hashtable<String, Integer> languages;
	Hashtable<String, Integer> themes;
	Hashtable<String, Integer> entities;

	public LanguageStats() {
		super();
		this.languages = new Hashtable<String, Integer>();
		this.themes = new Hashtable<String, Integer>();
		this.entities = new Hashtable<String, Integer>();
	}
	
	public void addLanguage(String name) {
		Integer count = 0;
		if (this.languages.containsKey(name)) 
			count = this.languages.get(name);
		this.languages.put(name, count + 1);
	}

	public void addTheme(String name) {
		Integer count = 0;
		if (this.themes.containsKey(name)) 
			count = this.themes.get(name);
		this.themes.put(name, count + 1);
	}

	public void addEntity(String name) {
		Integer count = 0;
		if (this.entities.containsKey(name)) 
			count = this.entities.get(name);
		this.entities.put(name, count + 1);
	}

	public void printStats(Hashtable<String, Integer> table) {
		Set<Entry<String, Integer>>  set = table.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry.getKey() + " " + entry.getValue() );
		}
	}
	
	public void printLanguageStats() {
		printStats(this.languages);
	}
	public void printThemeStats() {
		printStats(this.themes);
	}
	public void printEntityStats() {
		printStats(this.entities);
	}
	
}
