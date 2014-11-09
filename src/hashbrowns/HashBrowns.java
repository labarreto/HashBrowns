/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashbrowns;

interface HashMappy<K extends Comparable, V> {

    V lookup(K k, V value);

    HashMappy<K, V> add(K k, V v);
}

class HashyMT<K extends Comparable, V> implements HashMappy<K, V> {

    public HashyMT() {
    }

    public V lookup(K key, V value) {
        return value;
    }

    public HashMappy<K, V> add(K key, V value) {
        return this; //adding a value, so you want to return a new HashMap
        //with the K and V, on this. 
    }
}

class Hashy<K extends Comparable, V> implements HashMappy<K, V> {

    K coolkey;
    V coolvalue;
    Hashy<K, V> next;

    public Hashy(K coolkey, V coolvalue, Hashy<K, V> next) {
        this.coolkey = coolkey;
        this.coolvalue = coolvalue;
        this.next = next;
    }

    public V lookup(K k, V value) {
        System.out.println("Comparing " + k + " to " + this.coolkey);
        if (coolkey.compareTo(k) == 0) {
            
            return value;
        } else {
            return this.next.lookup(k, value);
        }
    }

    public HashMappy<K, V> add(K k, V v) {
        return new Hashy<K, V>(k, v, this);
    }

}

interface HashMap<K extends Comparable, V> {

    //returning a value after looking up a key and it's value
    V lookup(K key, V def);

    //returns a map after you add a key and a value
    void add(K key, V value);
}

class HashBrown<K extends Comparable, V> implements HashMap<K, V> {

    Object data[];

    public HashBrown(int size) {
        this.data = new Object[size];
        HashMappy<K, V> empty = new HashyMT<K, V>();
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = empty;
            //initializing data

        }

    }

    private int indexOf(K key) {
        return key.hashCode() % this.data.length;
            //hasCode turns object into a number
        //what is this doing?
        //every key has to be assigned to some cell of the data array
        //20 spots, 50 things? can't assign a unique one
        //therefor combine them together
        //indexOf function - job to figure out what cell to assign to

        //there are an infinite number of hashCodes that all go to a certain cell
    }

    private Hashy<K, V> small(int index) {
        return ((Hashy<K, V>) (this.data[index]));
    }

    public V lookup(K key, V value) {
        return this.small(this.indexOf(key)).lookup(key, value);
    }

    public void add(K key, V value) {
        int index = this.indexOf(key);
        this.data[index] = this.small(index).add(key, value);
        return;
    }
}

class HashBrowns {

    public static void main(String[] args) {
        HashMappy<String, String> ab0 = new HashyMT<String, String>();
        HashMappy<String, String> ab1 = ab0.add("Laura Barreto", "201-xxx-xxxx");
        HashMappy<String, String> ab2 = ab1.add("Harry Potter", "888-xxx-xxxx");
        HashMappy<String, String> ab3 = ab2.add("Miral Kotb", "123-xxx-xxxx");
        HashMappy<String, String> ab4 = ab3.add("The Doctor", "101-xxx-xxxx");
        HashMappy<String, String> ab5 = ab4.add("Miral Kotb", "123-xxx-xxxx");

        System.out.println(ab5.lookup("Miral Kotb", "123-xxx-xxxx"));

    }
}
