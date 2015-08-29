package pl.jolantawojcik.jsondogs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static pl.jolantawojcik.jsondogs.ListViewAdapter.*;


public class Single_item extends ActionBarActivity {

    private TextView textView, description_text;
    private ImageView imageView;
    private String name, description, imageUrl;
    ArrayList<Dogs> dogsList;
    ListViewAdapter.LoadImage load;
    ListViewAdapter lva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);

        textView = (TextView) findViewById(R.id.textView);
        description_text = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        imageUrl = intent.getStringExtra("image");
        textView.setText(name);
        description_text.setText(description);

        ListViewAdapter.LoadImage load = lva.new LoadImage(imageView);
        load.execute(imageUrl);
    }
}
