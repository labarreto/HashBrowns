/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashbrowns;

public class HashBrowns {
    
    /**
     * 
     * @param <K> key
     * @param <V> value
     */
    
    interface HashMappy<K extends Comparable, V> {
        V lookup (K k, V value);
        HashMappy<K,V> add(K k, V v);
    }
    
    class HashyMT<K extends Comparable, V> implements HashMappy<K,V> {
        public HashyMT() {}
        public V lookup(K key, V value) {
            return value;
        }
        public HashMappy<K,V> add(K key, V value) {
            return this; //adding a value, so you want to return a new HashMap
            //with the K and V, on this. 
        }
        
        
    }
    
    class Hashy<K extends Comparable, V> implements HashMappy<K,V> {
        K coolkey;
        V coolvalue;
        Hashy<K,V> next;
        
        public Hashy(K coolkey, V coolvalue, Hashy<K,V> next) {
            this.coolkey = coolkey;
            this.coolvalue = coolvalue;
            this.next = next;
        }


        public V lookup(K k, V value) {
           if (coolkey.compareTo(k) == 0){
               return value;
           } else {
               return this.next.lookup(k, value);
           }
        }

      
        public HashMappy<K, V> add(K k, V v) {
            return new Hashy<K,V>(k, v, this);
        }
        
    }
    interface HashMap<K extends Comparable, V> {
        
        //returning a value after looking up a key and it's value
        V lookup(K key, V def);
        //returns a map after you add a key and a value
        void add( K key, V value);
    }
   
    

    class HashBrown<K extends Comparable, V> implements HashMap<K,V> {
        Object data[];
        
        public HashBrown(int size) {
            this.data = new Object[size];
            HashMappy<K,V> empty = new HashyMT<K,V>();
            for (int i = 0 ; i <  this.data.length; i ++) {
                this.data[i] = empty;
                //are you putting an empty hashtable in an array? 
               
            }
            
        }
        
        private int indexOf(K key) {
            return key.hashCode() % this.data.length;
            //what is this doing?
        }
        
        private Hashy<K,V> small(int index) {
            return ((Hashy<K,V>) (this.data[index]));
        }
        
        
        public V lookup (K key, V value) {
            return this.small(this.indexOf(key)).lookup(key, value);
        }
        
        public void add (K key, V value) {
            int index = this.indexOf(key);
            this.data[index] = this.small(index).add(key, value);
            return; //why?
        }
    }
    

    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
