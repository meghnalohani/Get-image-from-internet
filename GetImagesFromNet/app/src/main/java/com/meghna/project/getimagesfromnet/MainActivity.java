package com.meghna.project.getimagesfromnet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    ImageView i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=(ImageView) findViewById(R.id.image1);
    }
    public void downloadimage(View view)
    {
        Log.i("Info","Button clicked");
        ImageDownloader im=new ImageDownloader();
        Bitmap myimage;
        try {
            myimage=im.execute("https://www.google.com/search?q=avengers+endgame+wallpaper&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiY-8Cp36DiAhWTbn0KHaQMDPAQ_AUIDigB&biw=1366&bih=608#imgrc=JNePXUGtYbKKlM:").get();
            //this task will be done in background using a different thread
            i.setImageBitmap(myimage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{
        //1st arg is url, 3rd is return type

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.connect();//connect to get image
                InputStream is=connection.getInputStream();
                Bitmap mybitmap= BitmapFactory.decodeStream(is);//image is downloaded as bitmap
                if (mybitmap==null)
                Log.i("Info","Null returned");
                return mybitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
