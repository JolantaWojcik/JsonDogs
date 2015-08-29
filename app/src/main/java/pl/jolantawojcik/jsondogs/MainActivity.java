package pl.jolantawojcik.jsondogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String URL = "http://v-ie.uek.krakow.pl/~s148228/dogs.php";
    ListViewAdapter listViewAdapter;
    private ProgressDialog progressDialog;
    ArrayList<Dogs> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<Dogs>();
        new LoadData().execute(URL);
    }

    private class LoadData extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Rasy Psów");
            progressDialog.setMessage("Trwa pobieranie danych...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpGet httpGet = new HttpGet(params[0]);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity);
                JSONObject jsonObject = new JSONObject(data);

                JSONArray jsonArray = jsonObject.getJSONArray("dogs");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Dogs dog = new Dogs();
                    dog.setName(object.getString("name"));
                    dog.setDescription(object.getString("description"));
                    dog.setImage(object.getString("image"));
                    arrayList.add(dog);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            listView = (ListView) findViewById(R.id.listView);
            listViewAdapter = new ListViewAdapter(MainActivity.this, arrayList);
            listView.setAdapter(listViewAdapter);
            progressDialog.dismiss();

            if(result==false){
                Toast.makeText(MainActivity.this, "Wyst?pi?y problemy z pobieraniem danych", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
