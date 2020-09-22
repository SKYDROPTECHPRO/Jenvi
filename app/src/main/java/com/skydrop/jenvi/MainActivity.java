package com.skydrop.jenvi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MainActivity extends AppCompatActivity {

    private SongsListSingleton songslist;
    private CurrentPlayingsongSingleton currentsong;

    private RecyclerView recview;
    private TextView currentsongtitle;
    private ImageView currentsongalbumart;
    private ImageButton currentsongplaypause;

    private RecyclerviewClickListener songsclicklistener;
    private ConstraintLayout layout;

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        setmappings();
        setsingletons();
        runtimepersimissions();
        showdata();
    }

    private void setsingletons() {
        songslist = SongsListSingleton.getInstance();
        currentsong = CurrentPlayingsongSingleton.getInstance();
        currentsong.setContext(getApplicationContext());
    }

    private void setmappings() {
        recview = findViewById(R.id.recview);
        currentsongtitle = findViewById(R.id.main_songtitle);
        currentsongalbumart=findViewById(R.id.main_albumart);
        System.out.println("in main album art:"+currentsongalbumart);
        currentsongplaypause = findViewById(R.id.main_pausepause);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        layout = findViewById(R.id.currenstsonglayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playingsong = new Intent(MainActivity.this,SongPlaying.class);
                startActivity(playingsong);
            }
        });
    }

    private void showdata() {
        songsclicklistener =new RecyclerviewClickListener() {
            @Override
            public void OnClick(View v, int pos) {
                currentsong.setCurrentsong(pos,getApplicationContext());
                setdata();
            }
        };
        rec_adapter adapter = new rec_adapter(MainActivity.this,songsclicklistener);
        LinearLayoutManager manger = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        recview.setAdapter(adapter);
        recview.setLayoutManager(manger);
    }

    private void setdata() {
        currentsongalbumart.setImageResource(currentsong.getAlbumart());
        currentsongtitle.setText(currentsong.getTitle());
        if(currentsong.isIsplaying()){
            currentsongplaypause.setImageResource(R.drawable.pause);
        }
        else{
            currentsongplaypause.setImageResource(R.drawable.pause);
        }
        currentsongplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentsong.isIsplaying()){
                    currentsong.pause();
                    currentsongplaypause.setImageResource(R.drawable.play);
                }
                else{
                    currentsong.play();
                    currentsongplaypause.setImageResource(R.drawable.pause);
                }
            }
        });
        System.out.println("Data setted in main activity,title:"+currentsong.getTitle());
    }


    private void getdata(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri,projections,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);
                SongModel tempmodel = new SongModel(path,title,artist,album,duration,R.drawable.defaultalbumart);
                songslist.add(tempmodel);
            }
            cursor.close();
        }

    }


    private void runtimepersimissions() {
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {

            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getdata(MainActivity.this);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

}