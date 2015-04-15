package net.shangtech.framework.excel;


public interface WorkbookReadHandler<T> {
	boolean process(WorkPage page, T object);
}
