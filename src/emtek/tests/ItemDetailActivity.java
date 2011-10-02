package emtek.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ItemDetailActivity extends Activity {
	Gallery gallery;
	TextView title;
	TextView bodyText;
	ImageAdapter iA;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);

		gallery = (Gallery) findViewById(R.id.gallery1);

		title = (TextView) findViewById(R.id.titleText);
		bodyText = (TextView) findViewById(R.id.bodyText);
		int itemId = (int) getIntent().getIntExtra(Constants.ITEM_DETAIL_ID, 0);
		if (itemId > 0) {
			requestDetail(itemId);

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
			updateItemView(root);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateItemView(Item item) {
		title.setText(item.getTitle());
		bodyText.setText(item.getBody());
		iA = new ImageAdapter(this, item.getPhotos());
		gallery.setAdapter(iA);
	}

	public void onBackPressed() {
		super.onBackPressed();
	}

}

class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private int position;
	private PhotoKeyValue[] photos;

	public ImageAdapter(Context c, PhotoKeyValue[] pkv) {
		mContext = c;
		position = 0;
		photos = pkv;
	}

	public int getCount() {
		return photos.length;
	}

	public Object getItem(int position) {
		return this.position;
	}

	public long getItemId(int position) {
		return this.position;
	}

	public void setPhotos(PhotoKeyValue[] pkv) {
		photos = pkv;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		URL aURL;
		try {
			aURL = new URL(photos[position].getPhoto().getMedium());

			URLConnection conn;

			conn = aURL.openConnection();

			conn.connect();

			InputStream is;

			is = conn.getInputStream();

			/* Buffered is always good for a performance plus. */
			BufferedInputStream bis = new BufferedInputStream(is);
			/* Decode url-data to a bitmap. */
			Bitmap bm = BitmapFactory.decodeStream(bis);

			bis.close();
			is.close();

			imageView.setImageBitmap(bm);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageView;
	}
}
