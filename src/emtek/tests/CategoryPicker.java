package emtek.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class CategoryPicker extends Activity {
	ListView lv;
	TextView tv;
	Stack<Category> st = new Stack<Category>();
	
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.category_picker);
	     
	     lv = (ListView) findViewById(R.id.list);
	     tv = (TextView) findViewById(R.id.selectedCatText);
	        lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Category current = st.peek();
					Category next = current.getChildren()[(int) arg3];
					if(next.hasChildren()){
						st.add(next);
						updateCategoryView(next);
						
					}else{
						Toast.makeText(arg0.getContext(), "You selected: " + current.getChildren()[(int) arg3].getName(), Toast.LENGTH_LONG)
						.show();			
					}
				}
				
			});
	        requestJsonCategories();
	}
	
	private void requestJsonCategories(){
		File cachegories = new File(getCacheDir() + "Categories.json");
		JsonReader reader = null;
		if(!cachegories.exists()){
			HttpGet request = new HttpGet("http://api.trademe.co.nz/v1/Categories.json");
			HttpClient httpClient = new DefaultHttpClient();
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
				Toast.makeText(this.getApplicationContext(), "Client Protocol exception" , Toast.LENGTH_LONG)
				.show();			
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(this.getApplicationContext(), "IO exception" , Toast.LENGTH_LONG)
				.show();
				e.printStackTrace();
			}
		}else{
			
			
		}
		
		FileReader fRead = null;
		try {
			fRead = new FileReader(cachegories);
			Scanner scan = new Scanner(fRead);
			String json = "";
			while(scan.hasNext()){
				json += scan.nextLine();
			}
			reader = new JsonReader(fRead );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		final Category root = gson.fromJson(reader, Category.class);
		st.add(root);
		updateCategoryView(root);
		try {
			fRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onBackPressed(){
		if(st.size()>1){
			st.pop();
			updateCategoryView(st.peek());
		}else{
			super.onBackPressed();
		}
	}
	
	public void updateCategoryView(Category current){
		ArrayAdapter<Category> addy = new ArrayAdapter<Category>(this.getApplicationContext(), R.layout.list_item, current.getChildren());
		lv.setAdapter(addy);
		tv.setText(current.getName());
	}
}
