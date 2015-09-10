package pl.jolantawojcik.jsondogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static pl.jolantawojcik.jsondogs.ListViewAdapter.*;


public class Single_item extends Activity {

    private TextView textView, description_text;
    private ImageView imageView;
    private String name, description, imageUrl;
    private ArrayList<Dogs> dogsList;
    LoadImage load;
    private ListViewAdapter lva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);

        description_text = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        imageUrl = intent.getStringExtra("image");
        description_text.setText(description);

        setTitle(name);
        new LoadImage(imageView).execute(imageUrl);
    }
}
