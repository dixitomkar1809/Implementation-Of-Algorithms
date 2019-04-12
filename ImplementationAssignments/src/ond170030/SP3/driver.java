package ond170030.SP3;

import ond170030.SP3.BinaryHeap;

import java.util.Arrays;

public class driver {
	public static void main(String[] args) {
		Integer[] arr = new Integer[10];
		BinaryHeap<Integer> pq = new BinaryHeap<>(arr);
		for(int i = 0; i< 10; i++) {
			pq.add(10-i);
			System.out.println(Arrays.toString(arr));
		}
	}
}
