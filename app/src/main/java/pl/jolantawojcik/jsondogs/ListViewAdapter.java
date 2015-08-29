package pl.jolantawojcik.jsondogs;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ListViewAdapter extends BaseAdapter {

    ArrayList<Dogs> dogsList;
    Context context;
    int resource; //Resource
    private TextView dogType;
    private ImageView dogImage;
    LayoutInflater layoutInflater;
    LinearLayout list_item_view;
    View view;

    public ListViewAdapter(Context context, ArrayList<Dogs> dogsList) {
        this.context = context;
        this.dogsList = dogsList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.list_view_adapter, parent);
        list_item_view = (LinearLayout) view.findViewById(R.id.list_item_view);
        dogsList.get(position);
        dogImage = (ImageView) list_item_view.findViewById(R.id.imageView);
        dogType = (TextView) list_item_view.findViewById(R.id.textView);
        dogType.setText(dogsList.get(position).getName());
        //dogImage.setImageResource(R.mipmap.logodogs);
        new LoadImage(dogImage).execute(dogsList.get(position).getImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Single_item.class);
                intent.putExtra("name", dogsList.get(position).getName());
                intent.putExtra("image", dogsList.get(position).getImage());
                intent.putExtra("description", dogsList.get(position).getDescription());
                context.startActivity(intent);
            }
        });
        return view;
    }

    public class LoadImage extends AsyncTask<String, Void, Bitmap>{
        private ImageView photo;

        public LoadImage(ImageView photo){
            this.photo = photo;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlToImage = params[0];
            Bitmap bitmap = null;

            try {
                InputStream is = new java.net.URL(urlToImage).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            photo.setImageBitmap(bitmap);
        }
    }
}
