package DictionaryCMD;

import java.util.ArrayList;
import java.util.Iterator;

public class Dictionary implements Iterable<Word> {
    public ArrayList<Word> words = new ArrayList<>();

    public void addWord(Word newWord) {
        if (words.indexOf(newWord) == -1) {
            words.add(newWord);
            System.out.println("Added: " + newWord.getWord_target());
        } else {
            System.out.println("This word have been added before.");
        }
    }

    public void removeWord(Word word) {
        if (words.indexOf(word) != -1) {
            words.remove(word);
            System.out.println("Removed: " + word.getWord_target());
        } else {
            System.out.println("This dictionary do not have your word.");
        }
    }

    public int indexOf(Word word) {
        return words.indexOf(word);
    }

    public Word get(int index) {
        return words.get(index);
    }

    public int size() {
        return words.size();
    }

    @Override
    public Iterator<Word> iterator() {
        return words.iterator();
    }
}