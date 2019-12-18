package domain.dataObjects;

import java.util.Objects;

public class Pair<K, V> {

    private K key;
    private V value;

    /**
     * Constructs a new {@link Pair}
     *
     * @param key   the key for this pair
     * @param value the value to use for this pair
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key for this pair
     *
     * @return the key for this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Gets the value for this pair
     *
     * @return the value for this pair
     */
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
