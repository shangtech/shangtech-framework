package net.shangtech.framework.file;

import java.io.InputStream;

public class GenericFile {
	
	private InputStream is;
	
	private String name;
	
	private String extend;

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
}
