package pl.jolantawojcik.jsondogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String URL = "http://v-ie.uek.krakow.pl/~s148228/dogs.php";
    ListViewAdapter adapter;
    private ProgressDialog progressDialog;
    private ArrayList<Dogs> arrayList;
    private ListView listView;
    private List<String> timeListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<Dogs>();
        listView = (ListView) findViewById(R.id.listView);
        new LoadData().execute(URL);
    }

    private class LoadData extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("\u0141adowanie danych");
            progressDialog.setMessage("loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpGet httppost = new HttpGet(params[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("dogs");
                    timeListString = new ArrayList<String>();
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        Dogs dog = new Dogs();
                        dog.setName(object.getString("name"));
                        dog.setDescription(object.getString("description"));
                        dog.setImage(object.getString("image"));
                        arrayList.add(dog);
                    }
                    return true;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result == false){
                Toast.makeText(MainActivity.this, "Wyst\u0105pi\u0142y problemy z pobieraniem danych", Toast.LENGTH_LONG).show();
            }else{
                adapter = new ListViewAdapter(getApplicationContext(), R.layout.list_view_adapter, arrayList);
                listView.setAdapter(adapter);
            }
            progressDialog.dismiss();
        }
    }
}
