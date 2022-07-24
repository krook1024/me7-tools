package com.prj.tuning.maplocator.export.xdf.model;

import com.prj.tuning.maplocator.model.LocatedMap;
import com.prj.tuning.xdf.binding.XdfAxis;
import com.prj.tuning.xdf.binding.XdfLabel;

import java.util.List;
import java.util.ArrayList;

public class ExternalAxis extends XdfAxis {
  private String dimension;
  private List<String> values;
  
  public ExternalAxis(String dimension, LocatedMap locatedMap) {
    this.dimension = dimension;
    //this.values = values;
	values = new ArrayList<String>();
	double factor = locatedMap.getFactor() == 0 ? 1 : locatedMap.getFactor();
	int[] external = locatedMap.getExternal();
	for (int i = 0; i < external.length; i++) {
		values.add(String.format("%.2f",external[i] * factor));
	}
  }

  @Override
  public String getDimension() {
    return dimension;
  }

  @Override
  public Integer getXdfIndexcount() {
    return new Integer(values.size());
  }
  
  @Override
  public List<XdfLabel> getLabels() {
	  List<XdfLabel> labels = new ArrayList<XdfLabel>();
	  int index = 0;
	  for (String value: values) {
		  Label label = new Label(index++, value);
		  labels.add(label);
	  }
	  return labels;
  }
      
  private class Label extends XdfLabel {
	private int index;
	private String value;
	
	private Label(int index, String value) {
		this.index = index;
		this.value = value;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getValue() {
		return value;
	}		
  }
  
}
