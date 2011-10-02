package emtek.tests;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity{
	WebView webView;
	String url;
	
	public void onCreate(Bundle savedInstanceState){
			 super.onCreate(savedInstanceState);
			 
			 Bundle extras = getIntent().getExtras();
			 url = extras.getString("url");
			 
			 WebViewClient client = new WebViewClient(){
				 public void onPageFinished(WebView view , String url){
					 if(url.contains("veri")){
						 String[] values = url.split("&oauth_verifier=");
						 String verifier = values[1].substring(0, 7);
							setResult(RESULT_OK, getIntent().putExtra(Constants.LOGIN_VERIFIER,verifier));
							finish();
							//onBackPressed();
							return;
						  }
					 }    
				 };
			 
			 webView = new WebView(this);
			 webView.setWebViewClient(client);
	}
	
	public void onStart(){
		super.onStart();
		this.setContentView(webView);
		webView.loadUrl(url);
	}
}