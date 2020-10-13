package com.skydrop.jenvi.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.skydrop.jenvi.Adapters.rec_adapter;
import com.skydrop.jenvi.Interfaces.RecyclerviewClickListener;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.Singletons.CurrentPlayingsongSingleton;
import com.skydrop.jenvi.Singletons.SongsListSingleton;
import com.skydrop.jenvi.models.SongModel;


public class MainActivity extends AppCompatActivity {

    private SongsListSingleton songslist;
    private CurrentPlayingsongSingleton currentsong;

    private RecyclerView recview;
    private TextView currentsongtitle;
    private ImageView currentsongalbumart;
    private ImageButton play_pause_bnt;
    private ImageButton next_bnt;
    private ImageButton prev_bnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setmappings();
        setsingletons();
        runtimepersimissions();
    }

    private void setsingletons() {
        songslist = SongsListSingleton.getInstance();
        currentsong = CurrentPlayingsongSingleton.getInstance();
        currentsong.setContext(getApplicationContext());
    }

    private void setmappings() {
        recview = findViewById(R.id.recview);
        currentsongtitle = findViewById(R.id.main_song_title);
        currentsongalbumart=findViewById(R.id.main_album_art);
        play_pause_bnt = findViewById(R.id.main_play_pause);
        prev_bnt = findViewById(R.id.main_prev);
        next_bnt = findViewById(R.id.main_next);
        ConstraintLayout layout = findViewById(R.id.main_song_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playingsong = new Intent(MainActivity.this, SongPlaying.class);
                startActivity(playingsong);
            }
        });
        play_pause_bnt.setOnClickListener(playerlistener);
        next_bnt.setOnClickListener(playerlistener);
        prev_bnt.setOnClickListener(playerlistener);
    }

    private View.OnClickListener playerlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == play_pause_bnt){
                currentsong.playorpause();
                if(!currentsong.isIsplaying()){
                    play_pause_bnt.setImageResource(R.drawable.play);
                }
                else{
                    play_pause_bnt.setImageResource(R.drawable.pause);
                }
            }
            else if(v==next_bnt){
                currentsong.nextsong();
                setdata();
            }
            else if(v==prev_bnt){
                currentsong.prevsong();
                setdata();
            }
        }
    };

    private void setdataandadapter() {
        RecyclerviewClickListener songsclicklistener = new RecyclerviewClickListener() {
            @Override
            public void OnClick(View v, int pos) {
                currentsong.setCurrentsong(pos);
                setdata();
            }
        };
        rec_adapter adapter = new rec_adapter(MainActivity.this, songsclicklistener);
        LinearLayoutManager manger = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        recview.setAdapter(adapter);
        recview.setLayoutManager(manger);
    }

    private void setdata() {
        currentsongalbumart.setImageResource(currentsong.getAlbumart());
        currentsongtitle.setText(currentsong.getTitle());
        if(currentsong.isIsplaying()){
            play_pause_bnt.setImageResource(R.drawable.pause);
        }
        else{
            play_pause_bnt.setImageResource(R.drawable.play);
        }
    }


    private void getdata(Context context) {
        Bitmap defaultart = BitmapFactory.decodeResource(getResources(),R.drawable.defaultalbumart);
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
                SongModel tempmodel = new SongModel(path,title,artist,album,duration,R.drawable.defaultalbumart,defaultart);
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
                setdataandadapter();

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