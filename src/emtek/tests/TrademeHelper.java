package emtek.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Currency;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class TrademeHelper {
	static OAuthProvider provider;
	static OAuthConsumer consumer;
	static Activity parentActivity;
	static Category categories;
	SharedPreferences prefs;

	public TrademeHelper(Activity parent) {
		parentActivity = parent;
		prefs = parentActivity.getPreferences(Context.MODE_PRIVATE);
		provider = new CommonsHttpOAuthProvider(Constants.OAUTH_REQUEST_TOKEN,
				Constants.OAUTH_ACCESS_TOKEN, Constants.OAUTH_AUTHORIZE);
		consumer = new CommonsHttpOAuthConsumer(Constants.APP_KEY,
				Constants.APP_SECRET);
		if ((prefs.contains("Token")) && (prefs.contains("Secret"))) {
			consumer.setTokenWithSecret(prefs.getString("Token", ""),
					prefs.getString("Secret", ""));
		} else {
			login();
		}
	}

	public void login() {
		String url;
		try {
			url = provider.retrieveRequestToken(consumer, "oob");

			Intent loginIntent = new Intent(parentActivity, LoginActivity.class);
			loginIntent.putExtra("url", url);
			parentActivity.startActivityForResult(loginIntent, Constants.LOGIN_RESULTCODE);

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
	
	public void setCategories(Category root){
		categories = root;
	}
	
	
	public static File getJSON(String url, boolean overrideCache){
		try{
			File cachegories = new File(parentActivity.getCacheDir()
					+ url.substring(url.lastIndexOf("/"), url.length()));
			if ((!cachegories.exists())||(overrideCache)) {
				HttpGet request = new HttpGet(url);
				HttpClient httpClient = new DefaultHttpClient();
				if (url.contains("https")) {
					consumer.sign(request);
				}
				try {
					HttpResponse response = httpClient.execute(request);
					InputStream inStream = response.getEntity().getContent();
					FileOutputStream outStream = new FileOutputStream(
							cachegories, false);
					byte[] data = new byte[10240];
					int len = 0;
					while ((len = inStream.read(data)) > 0) {
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
			} 
			
			return cachegories;
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setVerifier(String verifier) {
		try {
			provider.retrieveAccessToken(consumer, verifier);
			Editor edit = prefs.edit();
			edit.putString("Token", consumer.getToken());
			edit.putString("Secret", consumer.getTokenSecret());
			edit.apply();
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
		return;
	}

	/*
	 * public WatchList getWatchList(){
	 * 
	 * }
	 */
}
