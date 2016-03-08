package com.scorpiowf.test;

public class FRCNode {
	class NODE {
		public int value;
		public NODE prev;
		public NODE next;
		public NODE(int value) {
			this.value = value;
			this.prev = null;
			this.next = null;
		}
	}
	
	public static void main(String arg[]) {
		FRCNode frcNode = new FRCNode();
		frcNode.test();
	}
	
	public void test() {
		NODE node = insert(null, 3);
		node = insert(node, 5);
		node = insert(node, 2);
		node = insert(node, 7);
		node = insert(node, 9);
		print_list(node);
		node = remove(node, 7);	// 删除中间元素
		print_list(node);
		node = remove(node, 3);	// 删除头元素
		print_list(node);
		node = remove(node, 9);	// 删除尾元素
		print_list(node);
		node = remove(node, 3);
		node = remove(node, 2);
		print_list(node);
		node = remove(node, 5);	// 删除唯一元素
		print_list(node);
	}
	
	public void print_list(NODE node) {
		NODE pNode = findHead(node);
		System.out.print("Print:");
		while (pNode != null) {
			System.out.print(pNode.value + " ");
			pNode = pNode.next;
		}
		System.out.println("");
	}
	
	public NODE remove(NODE node, int value) {
		NODE head = findHead(node);
		if (head == null) {
			return null;	// 空链表
		}
		NODE curNode = head;
		NODE found = null;
		while (curNode != null) {
			if (curNode.value == value) {
				found = curNode;
				break;
			}
			curNode = curNode.next;
		}
		
		if (found != null) {
			if (found.prev != null) {
				found.prev.next = found.next;
			}
			if (found.next != null) {
				found.next.prev = found.prev;
			}
			return found.prev != null ? found.prev:found.next;	// 如果当前是唯一结点，则prev/next都为null，返回null
		} else {
			return node;
		}
	}
	
	protected NODE findHead(NODE node) {
		if (node == null) {
			return null;	// 空链表
		}
		while (node.prev != null) {
			node = node.prev;
		}
		return node;
	}
	
	protected NODE insert(NODE node, int value) {
		// 在当前结点node后插入新元素
		NODE newNode = new NODE(value);
		if (node == null) {
			return newNode;
		} else {
			newNode.next = node.next;
			newNode.prev = node;
			node.next = newNode;
			return newNode;
		}
	}
}
