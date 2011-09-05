package emtek.tests;

public class Category {
	private String Name;
	private String Number;
	private String Path;
	private Category[] Subcategories;
	
	public Category(){
		
	}
	
	public boolean hasChildren(){
		if(Subcategories == null) return false;
		else return true;
	}
	
	public Category[] getChildren(){
		return Subcategories;
	}
	public String getName(){
		return Name;
	}
	
	public String toString(){
		return Name;
	}
}
