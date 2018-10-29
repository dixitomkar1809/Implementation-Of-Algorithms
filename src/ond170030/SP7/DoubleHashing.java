/**
 * Author: Omkar Dixit, DC Vishwanath
 */

package ond170030.SP7;

import java.util.HashMap;
import java.util.Random;

/**
 * DoubleHashing Implementation
 * TABLE_SIZE is size of the table
 * size is the actual number of elements in the table
 * table contains the k,v
 * @param <K>
 * @param <V>
 */
public class DoubleHashing<K extends HashInterface, V>  {

	/**
	 * Entry class for DoubleHashing table
	 * status determines the boolean for the element being deleted or not
	 */
	static class HashEntry<E> {
		E key;
		E value;
		boolean status;

		HashEntry(E key, E value) {
			this.key = key;
			this.value = value;
			status = false;
		}
	}

	public int TABLE_SIZE;
	private int size;
	private HashEntry[] table;
	private int loadFactor;

	/**
	 * Constructor
	 */
	public DoubleHashing()
	{
		size = 0;
		TABLE_SIZE = 8;
		table = new HashEntry[TABLE_SIZE];
		loadFactor = 0;
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}


	/**
	 * This function ensures that hashCodes that differ only by
	 * constant multiples at each bit position have a bounded
	 * number of collisions (approximately 8 at default load factor).
	 * @param h
	 * @return
	 */
	int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return (h ^ (h >>> 7) ^ (h >>> 4))&(TABLE_SIZE - 1);
	}
	static int indexFor(int h, int length) { // length = table.length is a power of 2
		return h & (length-1);
	}

	/**
	 * First Hash Function h1(x)
	 * @param key
	 * @return
	 */
	public int hash1(K key) {
		return (int)indexFor(hash(key.hashCode()), TABLE_SIZE);
	}

	/**
	 * Second HashFunction h2(x)
	 * @param key
	 * @return
	 */
	public int hash2(int key) {
		return 1 + key%9;
	}

	/**
	 * returns Index
	 * @param key
	 * @param k
	 * @return
	 */
	public int getIndex(K key, int k) {
		return (hash1(key) + k * hash2(key.hashCode2())) % TABLE_SIZE;
	}

	/**
	 * return possible index
	 * @param key
	 * @return
	 */
	public int find(K key) {
		int k = 0;
		int index = 0;
		int xspot = 0;
		while(true) {
			index = getIndex(key, k);
			if(index < 0){
				index = index * -1;
			}
			if(table[index] != null) {
				if(table[index].key.equals(key) && table[index].status == false) {
					return index;
				}

				else if(table[index].status == true) {
					break;
				}
				else
					k++;
			}
			else
				return index;

		}

		xspot = index;
		while(true) {
			k++;
			index = getIndex(key, k);
			if(table[index]!=null) {
				if(table[index].key.equals(key) && !this.table[index].status ) {
					return index;
				}
				if(table[index].status == true) {
					return xspot;
				}
				else
					break;
			}
			else
				return index;
		}
		return 0;
	}

	/**
	 * adds the kv pair to the DoubleHashing table
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean add(K key,  V value)
	{
		int location = 0;
		if (size == TABLE_SIZE)
		{
			return false;

		}
		location  = find(key);
		if(table[location] == null) {
			table[location] = new HashEntry<>(key, value);
			size++;
			loadFactor++;
			if(loadFactor >= TABLE_SIZE/2){
				this.reHash();
			}
			return true;
		}
		//else if(table[location].key.equals(key)) {
		//	return false;
		//}
		else
			return false;
	}

	/**
	 * sets the status of the entry corresponding to the key to false from the table
	 * @param key
	 * @return
	 */
	public K remove(K key)
	{
		int location = 0;
		location = find(key);
		if(this.table[location] != null){
			if(this.table[location].key.equals(key)){
				HashEntry result = this.table[location];
				result.status = true;
				return (K) result.key;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}

	/**
	 * returns true if the table contains an entry of the key
	 * @param key
	 * @return
	 */
	public boolean contains(K key) {
		int location = 0;
		location = find(key);
		if(table[location] != null){
			if(table[location].key.equals(key)) {
				return true;
			}
			else
				return false;
		}else{
			return false;
		}

	}

	/**
	 * prints the kv pair of the DoubleHashing table
	 */
	public void printHashTable()
	{
		System.out.println("\nHash Table");
		for (int i = 0; i < TABLE_SIZE; i++)
			if (table[i] != null && table[i].status==false)
				System.out.print(table[i].value+", ");
	}

	/**
	 * rehashes the table to new table with double size of the previos one
	 */
	public void reHash(){
		HashEntry[] tempEntries = new HashEntry[size];
		int j=0;
		for (int i = 0; i < TABLE_SIZE  ; i++) {
			if(table[i] != null && !table[i].status){
				tempEntries[j] = table[i];
				j++;
			}
		}
		TABLE_SIZE  =  TABLE_SIZE * 2;
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++) {
			table[i] = null;
			size = 0;
			loadFactor = 0;
		}
		for (int i = 0; i < tempEntries.length; i++) {
			if(tempEntries[i] != null && !tempEntries[i].status){
				this.add((K)tempEntries[i].key, (V)tempEntries[i].value);
			}
		}
	}

	/**
	 * returns count of the distinct elements of DoubleHashing table
	 * @param arr
	 * @param <T>
	 * @return
	 */
	public static<T> int distinctElements(T[] arr){
		DoubleHashing<HashInteger, T> tempDHMap = new DoubleHashing<>();
		for (int i = 0; i < arr.length ; i++) {
			tempDHMap.add(new HashInteger((Integer) arr[i]), arr[i]);
		}
		return tempDHMap.size();
	}

	/**
	 * returns the count of entries of the DoubleHashing table
	 * @return
	 */
	private int size(){
		return this.size;
	}

	/**
	 * Driver program to display the comparison of the DoubleHashing table and Java's Hashmap
	 * Also determines the distinct elements in the same
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		DoubleHashing<HashInteger, Integer> doubleHashing = new DoubleHashing<>();
		Random random = new Random();
		Integer[] loaderArray = new Integer[1000000];
		int loaderArraySize = loaderArray.length;
		for(int i = 0; i < loaderArraySize; i++) {
			loaderArray[i] = random.nextInt(1000000);
//			loaderArray[i] = i;
		}
		int Key, Value;
		System.out.println("***Adding "+loaderArraySize+" number of entries to the Java's HashMap and DoubleHashing***");
		/**
		 * Java HashMap Implementation
		 */
		HashMap<Integer, Integer> hashMap = new HashMap<>();
		Timer hashMapTimer = new Timer();
		for(int i = 0; i < loaderArraySize; i++) {
			Key = Value = loaderArray[i];
			hashMap.put(Key, Value);
		}
		hashMapTimer.end();
		System.out.println("HashMap time to add elements");
		System.out.println(hashMapTimer);
//		System.out.println(hashMap.values());

		/**
		 * DoubleHashing Implementation
		 */
		Timer doubleHashingTimer = new Timer();
		for(int i = 0; i < loaderArraySize; i++) {
			Key = Value = loaderArray[i];
			doubleHashing.add(new HashInteger(Key), Value);
		}
		doubleHashingTimer.end();
		System.out.println("\nDoubleHashing time to add elements");
		System.out.println(doubleHashingTimer);
//		doubleHashing.printHashTable();

		/**
		 * Distinct Elements of Java HashMap
		 */
		System.out.println("\nDistinct Elements of HashMap - "+hashMap.size());

		/**
		 * Distinct Elements of DoubleHashing
		 */
		System.out.println("\nDistinct Elements of DoubleHashing - "+doubleHashing.distinctElements(loaderArray));
		int x = random.nextInt(1000000);
		System.out.println("\nRemoving -> "+x);
		System.out.println(doubleHashing.remove(new HashInteger(x)));
		x = random.nextInt(1000000);
		System.out.println("\nContains -> "+x+" "+doubleHashing.contains(new HashInteger(x)));
	}
}