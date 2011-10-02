package emtek.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.io.HttpResponseParser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.OAuthProviderListener;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.*;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class trademe extends Activity {
	
	TrademeHelper helper;
	String currentCategoryNumber = "";
	EditText searchBox;
	protected Category fullList;
	Runnable buildCategories = new Runnable(){
		public void run(){
			File cachegories = TrademeHelper.getJSON(Constants.CATEGORIES_URL, false);

			FileReader fRead = null;
			try {
				fRead = new FileReader(cachegories);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Gson gson = new Gson();
			helper.setCategories(gson.fromJson(fRead, Category.class));
			try {
				fRead.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        searchBox = (EditText)this.findViewById(R.id.searchBox);
        helper = new TrademeHelper(this);
        Thread thread =  new Thread(null, buildCategories, "CategoriesBackground");
        thread.start();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
    	  super.onActivityResult(requestCode, resultCode, data); 
    	  switch(requestCode) { 
    	    case (Constants.LOGIN_RESULTCODE) : { 
    	      if (resultCode == Activity.RESULT_OK) { 
    	      String verifier = data.getStringExtra(Constants.LOGIN_VERIFIER);
    	      helper.setVerifier(verifier);
    	      // TODO Switch tabs using the index.
    	      } 
    	      break; 
    	    } 
    	    case (Constants.CATEGORY_RESULTCODE) : {
    	    	String currentCategory = data.getStringExtra(Constants.CATEGORY_RESULT);
    	    	currentCategoryNumber = data.getStringExtra(Constants.CATEGORYNUMBER_RESULT);
    	    	Toast.makeText(this.getApplicationContext(), "You selected: " + currentCategory , Toast.LENGTH_LONG)
				.show();	
    	    	break;
    	    }
    	  } 
    	}
    
    public void onStart(){
    	super.onStart();
    }
    
 
    
	public void onResume(){
		super.onResume();
		//showDialog(VERIFICATION_DIALOG);
	}

	public void openCategories(View view){
		if(TrademeHelper.categories != null){
			Intent catIntent = new Intent(this, CategoryActivity.class);
			this.startActivityForResult(catIntent, Constants.CATEGORY_RESULTCODE);
		}
	}
	
	public void openWatchList(View view){
		Intent catIntent = new Intent(this, ItemListActivity.class);
		this.startActivityForResult(catIntent, Constants.WATCHLIST_RESULTCODE);
	}
	
	public void search(View view){
		Intent searchIntent = new Intent(this, ItemListActivity.class);
		String category = "";
		if(currentCategoryNumber !=null) category = "&category=" + currentCategoryNumber;
		String query = searchBox.getText() + category;
		searchIntent.putExtra(Constants.SEARCH_QUERYSTRING, query);
		this.startActivityForResult(searchIntent, Constants.WATCHLIST_RESULTCODE);
	}
}
    