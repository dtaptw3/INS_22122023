package com.app.model.orm.dbo.wrapper;

import java.util.List;

public class Layout {
	
	private String view;
	private String order;
	//private int columns;
	private String size;
	private int visibility;
	
	private String style;
	private String styleClass;
	private List<Layout> children;
	
	private List<String> pages;
	
	
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public List<Layout> getChildren() {
		return children;
	}
	public void setChildren(List<Layout> children) {
		this.children = children;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public List<String> getPages() {
		return pages;
	}
	public void setPages(List<String> pages) {
		this.pages = pages;
	}

}
