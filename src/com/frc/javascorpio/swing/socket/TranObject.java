package com.frc.javascorpio.swing.socket;

import java.io.Serializable;
import java.util.List;

/**
 * ����Ķ���,ֱ��ͨ��Socket�����������
 * 
 * @author way
 */
public class TranObject<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TranObjectType type;// ���͵���Ϣ����

	private int fromUser;// �����ĸ��û�
	private int toUser;// �����ĸ��û�

	private T object;// ����Ķ�������������ǿ����Զ����κ�
	private List<Integer> group;// Ⱥ������Щ�û�
	public TranObject(TranObjectType message) {
		this.type = message;
	}
	public TranObjectType getType() {
		return type;
	}
	public void setType(TranObjectType type) {
		this.type = type;
	}
	public int getFromUser() {
		return fromUser;
	}
	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}
	public int getToUser() {
		return toUser;
	}
	public void setToUser(int toUser) {
		this.toUser = toUser;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public List<Integer> getGroup() {
		return group;
	}
	public void setGroup(List<Integer> group) {
		this.group = group;
	}

}