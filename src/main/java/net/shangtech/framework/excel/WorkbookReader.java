package net.shangtech.framework.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.shangtech.framework.excel.converter.ColumnConverter;
import net.shangtech.framework.excel.support.Column;
import net.shangtech.framework.excel.support.ModelMappingException;
import net.shangtech.framework.excel.support.ModelMethod;
import net.shangtech.framework.excel.support.WorkbookReadException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookReader<T> {
	
	private boolean recordSuccess = true;
	
	private boolean recordFailed = true;
	
	private List<WorkPage> pages;
	
	private List<LinkedList<T>> successList;
	
	private List<LinkedList<T>> failedList;
	
	private Workbook book;
	
	List<ModelMethod> modelMethods;
	
	public void read(InputStream is, WorkbookReadHandler<T> handler) {
		doRead(is, handler);
	}
	
	public void read(InputStream is, WorkbookReadHandler<T> handler, boolean recordSuccess, boolean recordFailed) {
		this.recordFailed = recordFailed;
		this.recordSuccess = recordSuccess;
		
		doRead(is, handler);
	}
	
	private void doRead(InputStream is, WorkbookReadHandler<T> handler) {
		try {
			book = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new WorkbookReadException();
		}
		
		int sheetNum = book.getNumberOfSheets();
		if(sheetNum == 0){
			return;
		}
		if(recordFailed){
			failedList = new ArrayList<LinkedList<T>>(sheetNum);
		}
		if(recordSuccess){
			successList = new ArrayList<LinkedList<T>>(sheetNum);
		}
		pages = new ArrayList<WorkPage>(sheetNum);
		for(int i = 0; i < sheetNum; i++){
			WorkPage page = buildWorkPage(i);
			pages.add(page);
		}
		
		try {
			buildModelMethods();
			for(WorkPage page : pages){
				Sheet sheet = book.getSheetAt(page.getIndex());
				LinkedList<T> successList = new LinkedList<T>();
				LinkedList<T> failedList = new LinkedList<T>();
				int rowNum = sheet.getPhysicalNumberOfRows();
				for(int i = 1; i < rowNum; i++){
					Row row = sheet.getRow(i);
					T object = row2Object(page, row);
					boolean success = handler.process(page, object);
					if(success && recordSuccess){
						successList.add(object);
					}
					if(!success && recordFailed){
						failedList.add(object);
					}
				}
				if(recordSuccess){
					this.successList.add(successList);
				}
				if(recordFailed){
					this.failedList.add(failedList);
				}
			}
		} catch (Exception e) {
			throw new ModelMappingException(e);
		}
	}
	
	private void buildModelMethods() throws InstantiationException, IllegalAccessException{
		Class<T> clazz = getModelClass();
		Method[] methods = clazz.getMethods();
		modelMethods = new ArrayList<ModelMethod>();
		for(Method method : methods){
			Column column = method.getAnnotation(Column.class);
			if(column == null){
				continue;
			}
			ModelMethod modelMethod = new ModelMethod();
			ColumnConverter<?> converter = column.converter().newInstance();
			converter.setPattern(column.pattern());
			modelMethod.setConverter(converter);
			modelMethod.setColumnName(column.name());
			modelMethods.add(modelMethod);
		}
	}
	
	private T row2Object(WorkPage page, Row row) throws Exception{
		Class<T> clazz = getModelClass();
		T object = clazz.newInstance();
		
		for(ModelMethod method : modelMethods){
			method.invoke(object, row.getCell(page.getMapping().get(method.getColumnName())));
		}
		
		return object;
	}
	
	private WorkPage buildWorkPage(int i){
		WorkPage page = new WorkPage();
		page.setIndex(i);
		Sheet sheet = book.getSheetAt(i);
		page.setName(sheet.getSheetName());
		Row row = sheet.getRow(0);
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		Integer cellNum = row.getPhysicalNumberOfCells();
		for(int j = 0; j < cellNum; j++){
			mapping.put(row.getCell(j).getRichStringCellValue().toString(), j);
		}
		page.setMapping(mapping);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getModelClass(){
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz;
	}
}
