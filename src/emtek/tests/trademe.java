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
	
	OAuthProvider provider;
	OAuthConsumer consumer;
	Stack<Category> st = new Stack<Category>();
	ListView lv;
	final int VERIFICATION_DIALOG = 420;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
//        Intent in = new Intent(getBaseContext(), CategoryPicker.class);
//        startActivity(in);
        provider = new CommonsHttpOAuthProvider("https://secure.trademe.co.nz/Oauth/RequestToken?scope=MyTradeMeRead,MyTradeMeWrite,BiddingAndBuying", "https://secure.trademe.co.nz/Oauth/AccessToken",
        		"https://secure.trademe.co.nz/Oauth/Authorize");
        consumer = new CommonsHttpOAuthConsumer("53A1E4E603334A20FDBC23703A250FBDCE", "39AB0FF35C31E58D2627F9F2F58F6CB82F");
//        //consumer.setSendEmptyTokens(true);
//       
//      
        String url;
		try {
			url = provider.retrieveRequestToken(consumer, "oob");
			Uri uri = Uri.parse(url);
			 WebView webview = new WebView(this);
			 webview.setWebViewClient(new WebViewClient(){
				 public void onPageFinished(WebView view , String url){
					 if(url.contains("veri")){
						 setContentView(R.layout.main);
						 String[] values = url.split("&oauth_verifier=");
						 String verifier = values[1].substring(0, 7);
						 try {
								provider.retrieveAccessToken(consumer, verifier);
								
								File cachegories = new File(getCacheDir() + "/Watchlist.json");
								if((!cachegories.exists())||(cachegories.lastModified()< (System.currentTimeMillis()-600000))){
									HttpGet request = new HttpGet("https://api.trademe.co.nz/v1/MyTradeMe/Watchlist.json");
									HttpClient httpClient = new DefaultHttpClient();
									consumer.sign(request);
									try {
										
										HttpResponse response = httpClient.execute(request);
										InputStream inStream = response.getEntity().getContent();
										FileOutputStream outStream = new FileOutputStream(cachegories, false);
										byte[] data = new byte[10240];
										int len = 0;
										while((len = inStream.read(data))>0){
											outStream.write(data, 0, len);
										}
										outStream.close();
										inStream.close();
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										
										e.printStackTrace();
									}
								}else{
									
									
								}
								
								FileReader fRead = null;
								try {
									fRead = new FileReader(cachegories);
									
									Gson gson = new Gson();
									final WatchList root = gson.fromJson(fRead, WatchList.class);
									Thread.sleep(1000);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							} catch (OAuthMessageSignerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OAuthNotAuthorizedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OAuthExpectationFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OAuthCommunicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						  }
					 }
				 });
			 
			 setContentView(webview);
			 webview.loadUrl(url);

		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block with a difference
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Login again
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
	public void onResume(){
		super.onResume();
		//showDialog(VERIFICATION_DIALOG);
	}
	
}
    