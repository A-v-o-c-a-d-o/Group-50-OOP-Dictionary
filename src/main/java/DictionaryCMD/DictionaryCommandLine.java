package DictionaryCMD;

public class DictionaryCommandLine extends DictionaryManagement {
    public void showAllWord() {
        System.out.println("No    |English             |Vietnamese");
        for (int i = 1; i <= dictionary.size(); i++) {
            System.out.printf("%-5d ", i);
            System.out.println(dictionary.get(i-1));
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWord();
    }

    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWord();
        dictionaryLookup();
    }

    public void dictionarySearcher() {
        System.out.print("What you want to find: ");
        String find = sc.next().toLowerCase().trim();
        int count = 0;
        for (Word i: super.dictionary) {
            if (i.getWord_target().indexOf(find) == 0) {
                count++;
                System.out.println(count + ". " + i.getWord_target() + ": " + i.getWord_explain());
            }
        }
        System.out.println("Number of result: " + count);
    }
}