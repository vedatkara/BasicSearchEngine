import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /*
                                                    HASHTABLE OPERATIONS
        */

        //Read and store the words in text documents.
        try {
            HashedDictionary<String, SingleLinkedList<String>> hashTable = new HashedDictionary<>(2477);

            File file = new File("C:\\Users\\vedat\\OneDrive\\Masaüstü\\SearchEngine\\src\\stop_words_en.txt");
            Scanner scan = new Scanner(file);
            ArrayList<String> stop_words = new ArrayList<>();
            while (scan.hasNext())
                stop_words.add(scan.next().replaceAll("\\W+", "").replaceAll("\\d+", "")
                        .replaceAll("\\s+", "").toLowerCase(Locale.ENGLISH));

            File folder = new File("C:\\Users\\vedat\\OneDrive\\Masaüstü\\SearchEngine\\src\\sport");
            List<File> fileList = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
            String word = "";
            long totalTimePassed = 0, timeFirst, timeSecond;

            for (File txt_file : fileList) {
                Scanner scanner = new Scanner(txt_file);
                String fileName = txt_file.getName();

                while (scanner.hasNext()) {
                    word = scanner.next().replaceAll("\\W+", "").replaceAll("\\d+", "")
                            .replaceAll("\\s+", "").toLowerCase(Locale.ENGLISH);
                    timeFirst = timeSecond = 0;

                    if (!stop_words.contains(word) && word.length() > 0) {

                        if (!hashTable.contains(word)) {

                            Node<String> node = new Node<>(fileName);
                            SingleLinkedList<String> linkedList = new SingleLinkedList<>();
                            linkedList.add(node);
                            timeFirst = System.currentTimeMillis();
                            hashTable.add(word, linkedList);
                            timeSecond = System.currentTimeMillis();
                        } else {
                            Node<String> node = new Node<>(fileName);
                            hashTable.getValue(word).add(node);
                        }
                    }
                    totalTimePassed += timeSecond - timeFirst;
                }
            }

            File search = new File("C:\\Users\\vedat\\OneDrive\\Masaüstü\\SearchEngine\\src\\search.txt");
            Scanner scnSearch = new Scanner(search);
            ArrayList<String> searchWords = new ArrayList<>();
            hashTable.setCollisions(0);
            String sWord;
            long searchTimeFirst, searchTimeSecond, searchTime,
                    maxSearchTime = 0, minSearchTime = 999999999, totalTime = 0;

            while (scnSearch.hasNext()) {
                sWord = scnSearch.next();
                searchWords.add(sWord);

                searchTimeFirst = System.nanoTime();
                hashTable.contains(sWord);
                searchTimeSecond = System.nanoTime();

                searchTime = searchTimeSecond - searchTimeFirst;
                totalTime += searchTime;

                if (searchTime > maxSearchTime)
                    maxSearchTime = searchTime;
                else if (searchTime != 0 && searchTime < minSearchTime)
                    minSearchTime = searchTime;
            }

            System.out.println("Number of Collisions: " + hashTable.getCollisions());
            System.out.println("Indexing Time: " + totalTimePassed + "ms");
            System.out.println("Avg. search time : " + totalTime / searchWords.size());
            System.out.println("Max. search time : " + maxSearchTime);
            System.out.println("Min. search time : " + minSearchTime);


        /*
                                                    QUERY OPERATIONS
        */
            Scanner userInput = new Scanner(System.in);
            String[] splittedInput, file_names = new String[3];

            while (true) { //returns until user gives exactly 3 words.
                System.out.print("Please enter 3 words: ");
                String userInputStr = userInput.nextLine();
                splittedInput = userInputStr.split(" ");
                if (splittedInput.length != 3) {
                    System.out.println("Your input contains less or more than 3 words. Try again!");
                } else
                    break;
            }

            boolean found = false;                                                                  //True if at least one word is found.
            String searchWord = " ", file_name, best_file = " ";
            int count = 0, score = 0, best_score = 0, wordsContain = 0, wordsContainMax = 0;        //score: each file has a score
            SingleLinkedList<String> searchList;                                                        //keeps text references list

            //finds the most relevant files for each word
            for (int i = 0; i < 3; i++) {
                searchWord = splittedInput[i].toLowerCase(Locale.ENGLISH);
                if (hashTable.contains(searchWord)) {
                    found = true;
                    file_names[i] = hashTable.getValue(searchWord).findMax().getFile();
                }
            }

            //if none of the word is inside hashtable print prompt
            if (!found)
                System.out.println("The words you have given could not found in the documents.");

                //else at least one of the words is in hashtable
            else {
                for (int i = 0; i < 3; i++) {
                    wordsContain = 0;
                    if (file_names[i] != null) {
                        file_name = file_names[i];

                        for (int j = 0; j < 3; j++) {
                            searchWord = splittedInput[j].toLowerCase(Locale.ENGLISH);

                            if (hashTable.contains(searchWord)) {
                                searchList = hashTable.getValue(searchWord);

                                if (searchList.searchFile(file_name) != null) {
                                    count = searchList.searchFile(file_name).getCount();
                                    wordsContain++;
                                    score += count;
                                }
                            }
                        }
                        if (wordsContain > wordsContainMax) {
                            wordsContainMax = wordsContain;
                            best_file = file_name;
                        } else if (wordsContain == wordsContainMax) {
                            if (score >= best_score) {
                                best_score = score;
                                best_file = file_name;
                            }
                        }
                    }
                }
                System.out.println("Most relevant file is: " + best_file);
            }
        }catch(Exception e){
                System.out.println("The folder could not be founded.");
            }
        }
    }

