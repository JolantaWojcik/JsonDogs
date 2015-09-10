package pl.jolantawojcik.jsondogs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
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