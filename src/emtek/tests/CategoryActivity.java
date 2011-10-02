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
import android.content.Intent;
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

public class CategoryActivity extends Activity {
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
						Intent data = getIntent().putExtra(Constants.CATEGORY_RESULT, current.getChildren()[(int) arg3].getName());
						data.putExtra(Constants.CATEGORYNUMBER_RESULT, current.getChildren()[(int) arg3].getNumber());
						setResult(Constants.CATEGORY_RESULTCODE, data);
						finish();		
					}
				}
				
			});
	        updateCategoryView(TrademeHelper.categories);
	        st.add(TrademeHelper.categories);
	        //requestJsonCategories();
	}
	
	/*private void requestJsonCategories(){
		File cachegories = TrademeHelper.getJSON(Constants.CATEGORIES_URL, false);

		FileReader fRead = null;
		try {
			fRead = new FileReader(cachegories);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		final Category root = gson.fromJson(fRead, Category.class);
		st.add(root);
		updateCategoryView(root);
		try {
			fRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void onBackPressed(){
		if(st.size()>1){
			st.pop();
			updateCategoryView(st.peek());
		}else{
			Intent data = this.getIntent().putExtra(Constants.CATEGORY_RESULT, "");
			data.putExtra(Constants.CATEGORYNUMBER_RESULT, "");
			setResult(Constants.CATEGORY_RESULTCODE, data);
			finish();
		}
	}
	
	public void updateCategoryView(Category current){
		ArrayAdapter<Category> addy = new ArrayAdapter<Category>(this.getApplicationContext(), R.layout.list_item, current.getChildren());
		lv.setAdapter(addy);
		tv.setText(current.getName());
	}
	
	public void selectCategory(View view){
		Intent data = this.getIntent().putExtra(Constants.CATEGORY_RESULT, st.peek().getName());
		data.putExtra(Constants.CATEGORYNUMBER_RESULT, st.peek().getNumber());
		setResult(Constants.CATEGORY_RESULTCODE, data);
		finish();
	}
	
}

//class CategoryAdapter extends ArrayAdapter<Category> {
//
//    private ArrayList<Order> items;
//
//    public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
//            super(context, textViewResourceId, items);
//            this.items = items;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//            View v = convertView;
//            if (v == null) {
//                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                v = vi.inflate(R.layout.row, null);
//            }
//            Order o = items.get(position);
//            if (o != null) {
//                    TextView tt = (TextView) v.findViewById(R.id.toptext);
//                    TextView bt = (TextView) v.findViewById(R.id.bottomtext);
//                    if (tt != null) {
//                          tt.setText("Name: "+o.getOrderName());                            }
//                    if(bt != null){
//                          bt.setText("Status: "+ o.getOrderStatus());
//                    }
//            }
//            return v;
//    }
//}
