import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //delimiters
        String DELIMITERS = "[-+=" +
                " " +        //space
                "\r\n " +    //carriage return line fit
                "1234567890" + //numbers
                "’'\"" +       // apostrophe
                "(){}<>\\[\\]" + // brackets
                ":" +        // colon
                "," +        // comma
                "‒–—―" +     // dashes
                "…" +        // ellipsis
                "!" +        // exclamation mark
                "." +        // full stop/period
                "«»" +       // guillemets
                "-‐" +       // hyphen
                "?" +        // question mark
                "‘’“”" +     // quotation marks
                ";" +        // semicolon
                "/" +        // slash/stroke
                "⁄" +        // solidus
                "␠" +        // space?
                "·" +        // interpunct
                "&" +        // ampersand
                "@" +        // at sign
                "*" +        // asterisk
                "\\" +       // backslash
                "•" +        // bullet
                "^" +        // caret
                "¤¢$€£¥₩₪" + // currency
                "†‡" +       // dagger
                "°" +        // degree
                "¡" +        // inverted exclamation point
                "¿" +        // inverted question mark
                "¬" +        // negation
                "#" +        // number sign (hashtag)
                "№" +        // numero sign ()
                "%‰‱" +      // percent and related signs
                "¶" +        // pilcrow
                "′" +        // prime
                "§" +        // section sign
                "~" +        // tilde/swung dash
                "¨" +        // umlaut/diaeresis
                "_" +        // underscore/understrike
                "|¦" +       // vertical/pipe/broken bar
                "⁂" +        // asterism
                "☞" +        // index/fist
                "∴" +        // therefore sign
                "‽" +        // interrobang
                "※" +          // reference mark
                "]";

        //String[] splitted = text.split(DELIMITERS);

        //Read and store the words in text documents.
        File folder = new File("C:\\Users\\vedat\\OneDrive\\Masaüstü\\SearchEngine\\src\\sport");
        List<File> fileList = Arrays.asList(Objects.requireNonNull(folder.listFiles()));

        Keeper[] keepers = new Keeper[fileList.size()]; //Array that will keep Keeper class's objects.
        int index = 0;
        for (File file :fileList) {
            Scanner scanner = new Scanner(file);
            Keeper _new = new Keeper(file.getName());
            while(scanner.hasNext()){
                _new.add(scanner.next());
            }
            keepers[index] = _new;
            index ++;
        }





    }
}
