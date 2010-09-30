package net.anotheria.webutils.bean;

import java.io.Serializable;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

/**
 * A simple id, value pair, which is used for holding the
 * available values and their ids in checkboxes and selects. Actually 
 * it's automatically present for every map in the profiles.xml.
 * @author lrosenberg
 * Created on 16.06.2004
 */
public class LabelValueBean implements IComparable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private String value;
	
	public LabelValueBean(String aValue, String aLabel){
		this.value = aValue;
		this.label = aLabel;
	}
	

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}
	
	public String toString(){
		return ""+value+"="+label;
	}

	/* (non-Javadoc)
	 * @see net.anotheria.util.sorter.IComparable#compareTo(net.anotheria.util.sorter.IComparable, int)
	 */
	public int compareTo(IComparable anotherComparable, int method) {
		switch(method){
			case LabelValueSortType.METHOD_LABEL:
				return BasicComparable.compareString(label, ((LabelValueBean)anotherComparable).label);
			case LabelValueSortType.METHOD_VALUE:
				return BasicComparable.compareString(value, ((LabelValueBean)anotherComparable).value);
			default:
				throw new RuntimeException();
		}
	}
	

    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return label;
    }
    /**
     * @param label The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
