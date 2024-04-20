package application;

public class Heap {
	int length;
	int index;
	HNode array[];


	public Heap(int size, HNode arr[]) {
		super();
		array = arr;
		this.length = size;
		this.index = size-1;
		build(arr);
	}

	public void build(HNode ar[]) {
		int x = index / 2;
		for (int i = x; i >= 1; i--) 
			Heapify(i);
	}

	public void Heapify(int i) {
		int x = i;
		if ((i * 2) <= index && array[x].freq > array[i * 2].freq) x = i * 2;
		if ((i * 2 + 1) <= index && array[x].freq > array[i * 2 + 1].freq) x = i * 2 + 1;
		if (array[x] != array[i]) {
			HNode y = array[x];
			array[x] = array[i];
			array[i] = y;
			Heapify(x);
		}
	}
	
	public void insert(HNode x) {
		if (isFull()) return;
		
		int i = (++index);

		while (i > 1 && x.freq < array[i / 2].freq) {
			array[i] = array[i / 2];
			i /= 2;
		}
		array[i] = x;
	}

	public HNode deleteMin() {
		int i, child;
		HNode min, last;
		if (isEmpty()) {
			System.out.println("it's empty");
			return null;
		}
		min = this.array[1];
		last = this.array[index--];
		for (i = 1; i * 2 <= index; i = child) {
			child = i * 2;
			if ((child != index) && this.array[child + 1].freq < array[child].freq)
				child++;
			if (last.freq > array[child].freq) {
				array[i] = array[child];
			} else {
				break;
			}
		}
		array[i] = last;
		return min;
	}
	public boolean isEmpty() {
		if (index == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull() {
		if (index + 1 == length) {
			return true;
		} else {
			return false;
		}
	}

	

}
