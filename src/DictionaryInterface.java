import java.util.Iterator;

public interface DictionaryInterface<K, V> {

    public V add(K key, V value);

    public V getValue(K key);

    public boolean contains(K key);

}
