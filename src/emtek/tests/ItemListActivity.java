package emtek.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Stack;

import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class ItemListActivity extends Activity {
	ListView lv;
	TextView tv;
	Item[] list;
	
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.category_picker);
	     
	     lv = (ListView) findViewById(R.id.list);
	     tv = (TextView) findViewById(R.id.selectedCatText);
	     
	     if(getIntent().hasExtra(Constants.SEARCH_QUERYSTRING)){
		  
	    	 String query = Constants.SEARCH_URL + getIntent().getStringExtra(Constants.SEARCH_QUERYSTRING) + "&page=1&rows=30";
	    	 requestJsonSearch(query);
	    	 lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						int id = list[(int) arg3].getId();
						
						Intent itemIntent = new Intent(getApplicationContext(),ItemDetailActivity.class);
						itemIntent.putExtra(Constants.ITEM_DETAIL_ID, id);
						startActivity(itemIntent);	
						
					}
					
				});
	     }else{
				
		     requestJsonWatchList();
	     }

	}
	
	private void requestDetail(int id) {
		// TODO Auto-generated method stub
		
		String url = Constants.LISTING_URL + id + ".json";
		File cachegories = TrademeHelper.getJSON(url, true);
		
		FileReader fRead = null;
		try {
			fRead = new FileReader(cachegories);
			
			Gson gson = new Gson();

			final Item root = gson.fromJson(fRead, Item.class);
			root.getId();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void requestJsonWatchList(){
		File cachegories = TrademeHelper.getJSON(Constants.WATCHLIST_URL, true);
		
		FileReader fRead = null;
		try {
			fRead = new FileReader(cachegories);
			
			Gson gson = new Gson();
			Type listType = new TypeToken<ItemList<Item>>() {}.getType();
			final ItemList<Item> root = gson.fromJson(fRead, listType);
			updateWatchListView(root);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void requestJsonSearch(String searchUrl){
		File cachegories = TrademeHelper.getJSON(searchUrl, true);
		
		FileReader fRead = null;
		try {
			fRead = new FileReader(cachegories);
			
			Gson gson = new Gson();
			Type listType = new TypeToken<ItemList<Item>>() {}.getType();
			final ItemList<Item> root = gson.fromJson(fRead, listType);
			updateWatchListView(root);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public void onBackPressed(){
		super.onBackPressed();
	}
	
	public void updateWatchListView(ItemList<Item> current){
		list = current.getItems();
		ArrayAdapter<Item> addy = new ArrayAdapter<Item>(this.getApplicationContext(), R.layout.list_item, current.getItems());
		lv.setAdapter(addy);
		tv.setText("WatchList");
	}
}
