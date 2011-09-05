package emtek.tests;

import java.util.Date;

public class WatchList {
	private int TotalCount;
	private int Page;
	private int PageSize;
	private Item[] List;
}

class Item {
	private int ListingId;
	private String Title;
	private String Category;
	private double StartPrice;
	private String StartDate;
	private String EndDate;
	private int ListingLength;
	private String AsAt;
	private String CategoryPath;
	private String PictureHref;
	private int PhotoId; 
	private int RegionId;
	private String Region;
	private boolean IsReserveMet;
	private String Note;
	private String NoteDate;
	private String CategoryName;
	private int ReserveState;
	private Attribute[] Attributes;
	
	public Item(){
		
	}
	
	public boolean hasAttributes(){
		if(Attributes == null) return false;
		else return true;
	}
	
	public Attribute[] getAttributes(){
		return Attributes;
	}
	public String getName(){
		return Title;
	}
	
	public String toString(){
		return Title;
	}

}

class Attribute {
	private String Name;
	private String DisplayName;
	private String Value;
	
	public Attribute(){
		
		
	}
}

