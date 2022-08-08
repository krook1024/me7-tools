package com.prj.tuning.xdf.binding;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "XDFFORMAT")
@XmlType(propOrder={"XdfHeader", "tables", "constants"})
public abstract class XdfProject {
  public static final String ADDRESS_FORMAT = "0x%X";

  @XmlAttribute(name = "version")
  public String getVersion() {
    return "1.30";
  }

  @XmlElementRef
  public abstract XdfHeader getXdfHeader();

  @XmlElementRef
  public abstract Collection<XdfTable> getTables();

  @XmlElementRef
  public abstract Collection<XdfConstant> getConstants();


}
