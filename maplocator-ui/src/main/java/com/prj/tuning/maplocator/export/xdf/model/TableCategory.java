package com.prj.tuning.maplocator.export.xdf.model;

import java.util.List;
import java.util.Arrays;
import com.prj.tuning.maplocator.model.LocatedMap;

public abstract class TableCategory {

protected static final TableCategory AXES_CATEGORY = new TableCategoryAxes("Axes", 255);
protected static final TableCategory FUEL_CATEGORY = new TableCategoryKeywords("Fuel", 101, new String[]{"KFLF"});
protected static final TableCategory IGNITION_CATEGORY = new TableCategoryKeywords("Ignition", 102, new String[]{"KFZW"});
protected static final TableCategory BOOST_CATEGORY = new TableCategoryKeywords("Boost", 103, new String[]{"KFDDK", "KFLDS", "LDSMXN", "KFLDHKO"});
protected static final TableCategory INJECTOR_CATEGORY = new TableCategoryKeywords("Injector", 104, new String[]{"TVUB", "FGAT0", "TEMIN"});
protected static final TableCategory GEAR_CATEGORY = new TableCategoryKeywords("Gearbox", 105, new String[]{"TNMAXK", "NMAXAT", "VNMAXAT"});
protected static final TableCategory MISC_CATEGORY = new TableCategoryDefault("Misc", 106);

protected static final List<TableCategory> CATEGORIES = Arrays.asList(AXES_CATEGORY, FUEL_CATEGORY, IGNITION_CATEGORY, BOOST_CATEGORY, INJECTOR_CATEGORY,GEAR_CATEGORY, MISC_CATEGORY);

private String name;
private int index;

private TableCategory(String name, int index) {
	this.name = name;
	this.index = index;
}

public String getName() {
	return name;
}

public int getIndex() {
	return index;
}

public abstract boolean isMatching(LocatedMap lmap);

static class TableCategoryKeywords extends TableCategory {
	
	private String[] keywords;
	
	private TableCategoryKeywords(String name, int index, String[] keywords) {
	super(name, index);
	this.keywords = keywords;
	}
	
	public boolean isMatching(LocatedMap locatedMap) {
		if (keywords==null) return false;
		String str = locatedMap.getTitle() != null ? locatedMap.getId() + " " + locatedMap.getTitle() : locatedMap.getId();
		str = str.toUpperCase();
		for (String keyword : keywords) {
			if (str.contains(keyword.toUpperCase())) return true;
		}
		return false;
	}
}

static class TableCategoryAxes extends TableCategory {
	
	private TableCategoryAxes(String name, int index) {
	super(name, index);
	}
	
	public boolean isMatching(LocatedMap locatedMap) {
		if (locatedMap.isAxis()) return true;
		else return false;
	}
}

static class TableCategoryDefault extends TableCategory {
	
	private TableCategoryDefault(String name, int index) {
	super(name, index);
	}
	
	public boolean isMatching(LocatedMap locatedMap) {
		return true;
	}
}

}
