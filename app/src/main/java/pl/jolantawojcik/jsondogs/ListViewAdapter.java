package pl.jolantawojcik.jsondogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
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


public class ListViewAdapter extends ArrayAdapter<Dogs> {

    private ArrayList<Dogs> dogsList;
    Context context;
    private LayoutInflater layoutInflater;
    int resource;
    ViewHolder holder;

    public ListViewAdapter(Context context, int resource, ArrayList<Dogs> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        dogsList = objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
     View  view = convertView;
        if(view == null){
            view = layoutInflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.imageview = (ImageView) view.findViewById(R.id.imageView);
            holder.tvName = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
            holder.tvName.setText(dogsList.get(position).getName());
            new LoadImage(holder.imageview).execute(dogsList.get(position).getImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Single_item.class);
                intent.putExtra("name", dogsList.get(position).getName());
                intent.putExtra("image", dogsList.get(position).getImage());
                intent.putExtra("description", dogsList.get(position).getDescription());
                Log.d("desc1", dogsList.get(position).getDescription());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
    }
}
