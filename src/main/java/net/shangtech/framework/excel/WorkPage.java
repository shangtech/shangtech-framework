package net.shangtech.framework.excel;

import java.util.Map;

public class WorkPage {
	/** sheet序号 */
	private int index;
	
	/** sheet名称 */
	private String name;
	
	/** 列名-索引Mapping */
	private Map<String, Integer> mapping;
	
	/** 给handler缓存数据用 */
	private Object data;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Integer> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, Integer> mapping) {
		this.mapping = mapping;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getData(Class<T> clazz) {
		if(data.getClass().equals(clazz)){
			return (T) data;
		}
		return null;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
}
