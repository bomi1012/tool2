package de.hska.awp.palaver2.util;

public class ViewDataObject<T> implements IViewData {
	private T m_data;
	private Object m_object;

	public ViewDataObject(T data) {
		this(data, null);
	}
	
	public ViewDataObject(T data, Object obj) {
		m_data = data;
		m_object = obj;
	}

	public T getData() {
		return m_data;
	}
	
	public Object getObject() {
		return m_object;
	}
}
