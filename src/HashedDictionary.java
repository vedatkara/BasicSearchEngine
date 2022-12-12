import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {
    private TableEntry<K, V>[] hashTable;
    private int collisions;
    private int numberOfEntries;
    private int locationsUsed;
    private static final int DEFAULT_SIZE = 2477;
    private static final double MAX_LOAD_FACTOR = 0.5;

    public HashedDictionary() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")
    public HashedDictionary(int tableSize) {
        int primeSize = getNextPrime(tableSize);
        hashTable = new TableEntry[primeSize];
        numberOfEntries = 0;
        locationsUsed = 0;
    }

    public int getCollisions() {
        return collisions;
    }

    public void setCollisions(int collisions) {
        this.collisions = collisions;
    }

    public boolean isPrime(int num) {
        boolean prime = true;
        for (int i = 2; i <= num / 2; i++) {
            if ((num % i) == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }

    public int getNextPrime(int num) {
        if (num <= 1)
            return 2;
        else if (isPrime(num))
            return num;
        boolean found = false;
        while (!found) {
            num++;
            if (isPrime(num))
                found = true;
        }
        return num;
    }

    public boolean isHashTableTooFull() {
        double load_factor = (double) locationsUsed / hashTable.length;
        return load_factor >= MAX_LOAD_FACTOR;
    }

    @SuppressWarnings("unchecked")
    public void rehash() {
        TableEntry<K, V>[] oldTable = hashTable;
        int oldSize = hashTable.length;
        int newSize = getNextPrime(2 * oldSize);
        hashTable = new TableEntry[newSize];
        numberOfEntries = 0;
        locationsUsed = 0;
        for (int index = 0; index < oldSize; index++) {
            if ((oldTable[index] != null) && oldTable[index].isIn())
                add(oldTable[index].getKey(), oldTable[index].getValue());
        }
    }

    private int linearProbe(int index) {
        while (hashTable[index] != null) {
            index = (index + 1) % hashTable.length;
        }
        return index;
    }

    private int doubleHashing(int index, K key){
        int firstHashFunction = index % hashTable.length;
        int secondaryHashFunction = 31 - index % 31;
        int doubleHashIndex = 0;
        for (int i = 0; i < hashTable.length - 1; i++) {
            doubleHashIndex = (firstHashFunction + i * secondaryHashFunction) % hashTable.length;
            if (hashTable[doubleHashIndex] == null || key.equals(hashTable[doubleHashIndex].getKey())) {
                break;
            }

        }
        return doubleHashIndex;

    }

    public V add(K key, V value) {
        V oldValue;
        if (isHashTableTooFull())
            rehash();

        //int index = hashFunction(key);
        //index = linearProbe(index);
        int index = doubleHashing(hashFunction(key),key);

        if ((hashTable[index] == null) || hashTable[index].isRemoved()) {
            hashTable[index] = new TableEntry<>(key, value);
            numberOfEntries++;
            locationsUsed++;
            oldValue = null;
        } else {
            oldValue = hashTable[index].getValue();
            hashTable[index].setValue(value);
        }
        return oldValue;
    }

    private int hashFunction(K key) {
        //int hashIndex = SSF(key) % hashTable.length;
        int hashIndex = horner(key) % hashTable.length;
        if (hashIndex < 0)
            hashIndex = hashIndex + hashTable.length;
        return hashIndex;
    }

    //Simple Summation Function
    public int SSF(K key) {
        String strKey = key.toString();
        char c = ' ';
        int sum = 0;

        for (int i = 0; i < strKey.length(); i++) {
            c = strKey.charAt(i);
            sum += c;
        }

        return sum;
    }

    //Horner's Rule
    private int horner(K key) {
        int[] a = new int[key.toString().length()];
        for(int i = 0; i < a.length; i++){
            a[i] = key.toString().charAt(i);
        }
        int n = a.length - 1;
        int result = a[n];

        for (int i = n - 1; i >= 0; i--) {
            result = result * 31 + a[i];
        }
        return result;
    }

    private int locate(int index, K key) {
        boolean found = false;
        while (!found && (hashTable[index] != null)) {
            if (hashTable[index].isIn() && key.equals(hashTable[index].getKey()))
                found = true;
            else {
                //index = (index + 1) % hashTable.length;
                index = doubleHashing(hashFunction(key), key);
                collisions++;
            }
        }
        int result = -1;
        if (found)
            result = index;
        return result;
    }

    public V getValue(K key) {
        V result = null;
        int index = hashFunction(key);
        index = locate(index, key);
        if (index != -1)
            result = hashTable[index].getValue();
        return result;
    }

    public boolean contains(K key) {
        int index = hashFunction(key);
        index = locate(index, key);
        if (index != -1)
            return true;
        return false;
    }

    private class TableEntry<S, T> {
        private S key;
        private T value;
        private boolean inTable;

        private TableEntry(S key, T value) {
            this.key = key;
            this.value = value;
            inTable = true;
        }

        private S getKey() {
            return key;
        }

        private T getValue() {
            return value;
        }

        private void setValue(T value) {
            this.value = value;
        }

        private boolean isRemoved() {
            return inTable == false;
        }

        private void setToRemoved() {
            inTable = false;
        }

        private void setToIn() {
            inTable = true;
        }

        private boolean isIn() {
            return inTable == true;
        }
    }

}
