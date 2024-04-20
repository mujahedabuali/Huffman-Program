package application;

 class Node {
	Node next;
	Object value;
	
	public Node(Object value) {
		super();
		this.value = value;
	}
	
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}

 class arr {
	Node head;
	Node rear;
	int cnt;

	public void insertFirst(Object value) {
		Node node = new Node(value);
		if (head == null) head = rear = node;

		else {
			node.setNext(head);
			head = node;
		}
		cnt++;
	}
	public void insertLast(Object value) {
		Node node = new Node(value);
		if (head == null) {
			rear = head = node;

		} else {
			rear.setNext(node);
			rear = node;

		}
		cnt++;

	}

	public void insert(int pos, Object value) {

		if (pos == 0) {
			insertFirst(value);
			return;

		} else if (pos >= cnt) {
			insertLast(value);
			return;
		}
		Node temp = head;

		for (int i = 0; i < pos - 1; i++) {
			temp = temp.getNext();
		}
		Node node = new Node(value);
		node.setNext(temp.next);
		temp.setNext(node);
		cnt++;
	}

	public Object deleteFirst() {
		if (head == null) return null;
		else if (head == rear) {
			Object o = head.value;
			head = rear = null;
			cnt--;
			return o;
		} else {
			Object o = head.value;
			head = head.getNext();
			cnt--;
			return o;
		}

	}
}

public class Stack extends arr {

	public void push(Object o) {
		insertFirst(o);
	}
	public Object pop() {
		return deleteFirst();
	}
}
