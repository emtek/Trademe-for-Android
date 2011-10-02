package emtek.tests;

public final class Constants {
	//Oauth
	public static String OAUTH_REQUEST_TOKEN = "https://secure.trademe.co.nz/Oauth/RequestToken?scope=MyTradeMeRead,MyTradeMeWrite,BiddingAndBuying";
	public static String OAUTH_ACCESS_TOKEN = "https://secure.trademe.co.nz/Oauth/AccessToken";
	public static String OAUTH_AUTHORIZE = "https://secure.trademe.co.nz/Oauth/Authorize";
	
	//App key and secret
	public static String APP_KEY = "53A1E4E603334A20FDBC23703A250FBDCE";
	public static String APP_SECRET = "39AB0FF35C31E58D2627F9F2F58F6CB82F";
	
	//Trademe urls
	public static String WATCHLIST_URL = "https://api.trademe.co.nz/v1/MyTradeMe/Watchlist.json";
	public static String CATEGORIES_URL = "http://api.trademe.co.nz/v1/Categories.json";
	public static String SEARCH_URL = "http://api.trademe.co.nz/v1/Search/General.json?search_string=";
	public static String LISTING_URL = "http://api.trademe.co.nz/v1/Listings/";
	
	public static int CACHE_EXPIRY = 600000;
	
	public static final int LOGIN_RESULTCODE = 420;
	public static String LOGIN_VERIFIER = "verifier";
	
	public static final int CATEGORY_RESULTCODE = 520;
	public static String CATEGORY_RESULT = "category";
	public static String CATEGORYNUMBER_RESULT = "categorynumber";
	
	public static final int WATCHLIST_RESULTCODE = 620;
	public static String WATCHLIST_SELECTED = "selected";
	
	public static final String SEARCH_QUERYSTRING = "query";
	
	public static final String ITEM_DETAIL_ID = "itemid";
}
