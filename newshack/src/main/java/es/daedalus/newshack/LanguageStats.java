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

	public LanguageStats() {
		super();
		this.languages = new Hashtable<String, Integer>();
	}
	
	public void addLanguage(String name) {
		Integer count = 0;
		if (this.languages.containsKey(name)) 
			count = this.languages.get(name);
		this.languages.put(name, count + 1);
	}
	
	public void printLanguageStats() {
		Set<Entry<String, Integer>>  set = this.languages.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry.getKey() + " " + entry.getValue() );
		}
		
	}
	
	
}
