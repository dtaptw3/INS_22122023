package com.app.model.orm.dbo.wrapper;

public class GridLayout extends Layout {

	private int columns;
	private String columnClasses;

	public GridLayout() {
		super();
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public String getColumnClasses() {
		return columnClasses;
	}

	public void setColumnClasses(String columnClasses) {
		this.columnClasses = columnClasses;
	}

	public String generateColClasses(int cols) {

		String colClass = "";
		int factor = 12 / cols;

		String factorName = "ui-g-12 ui-md-" + factor;

		for (int i = 0; i < cols; i++) {

			if (i > 0) {
				colClass = colClass + ",";
			}

			colClass = colClass + factorName;
		}

		return colClass;
	}

}
