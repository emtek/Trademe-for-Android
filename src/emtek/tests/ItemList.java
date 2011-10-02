package emtek.tests;

import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

class ItemList<T> {
	private int TotalCount;
	private int Page;
	private int PageSize;
	private String DidYouMean;
	private FoundCategory[] FoundCategories;
	private T[] List;
	public T[] getItems(){
		return List;
	}
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
	
	//Extra for search items.
	private int BuyNowPrice;
	private boolean HasPayNow;
	private boolean HasReserve;
	private boolean HasBuyNow;
	private String PriceDisplay;
	
	//Extra for listings
	private boolean HasGallery;
	private double MaxBidAmount;
	private String Suburb;
	private int BidCount;
	private int ViewCount;
	private String Body;
	private ItemList<Bid> Bids;
	
	private PhotoKeyValue[] Photos;
	
	//TODO: Open homes.....
	
	
	
	
	//Array of attributes
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
	
	public int getId(){
		return ListingId;
	}
	
	public PhotoKeyValue[] getPhotos(){
		return Photos;
	}
	
	public String getTitle(){
		return Title;
	}
	
	public String getBody(){
		return Body;
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

class Member {
	private int MemberId;
	private String Nickname;
	private String DateAddressVerified;
	private String DateJoined;
	private int UniqueNegative;
	private int UniquePositive;
	private int FeedbackCount;
	private boolean IsAddressVerified;
	private boolean IsDealer;
	private boolean IsAuthenticated;
}



class Bid {
	private String Account;
	private double BidAmount;
	private boolean IsBuyNow;
	private Member Bidder;
}

class Category {
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
	
	public String getNumber(){
		return Number;
	}
	
	public String getPath(){
		return Path;
	}
	
	public String toString(){
		return Name;
	}
}

class Photo{
	private int PhotoId;
	private String Thumbnail;
	private String List;
	private String Medium;
	private String Gallery;
	private String Large;
	private String FullSize;
	public String getMedium(){
		return Medium;
	}
}

class PhotoKeyValue{
	private int Key;
	private Photo Value;
	public Photo getPhoto(){
		return Value;
	}
}

class FoundCategory {
	private int Count;
	private String Category;
	private String Name;
	
	public String toString(){
		return Name;
	}	
	public int getCount(){
		return Count;
	}	
	public String getCategory(){
		return Category;
	}
	
}
