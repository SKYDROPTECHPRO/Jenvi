package com.skydrop.jenvi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.skydrop.jenvi.Playeractivity;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.SongModel;
import com.skydrop.jenvi.SongsList_singleton;
import com.skydrop.jenvi.rec_view;
import com.skydrop.jenvi.song_singleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SongsList_singleton songdata;
    song_singleton song = song_singleton.instance;
    SongModel model;
    RecyclerView recview;
    TextView currentSong;
    rec_view.RecyclerViewClickListener Listener;
    public static int position;
    public static Bitmap album_art;
    ContentResolver resolver;
    public static Uri art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("main activity started");
        recview = findViewById(R.id.recview);
        currentSong = (TextView)findViewById(R.id.currentSong);
        songdata = SongsList_singleton.getInstance();
        currentSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Playeractivity.class);
                startActivity(intent);
            }
        });

        runtimepersimissions();
        Listener = new rec_view.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                position = pos;

               Long uriId = Long.valueOf(songdata.getSingslist(position).getId());
               Uri uri = Uri.parse("content://media/external/audio/albumart");
               Uri art = ContentUris.withAppendedId(uri, uriId);
                resolver = new ContentResolver(getApplicationContext()) {
                    @NonNull
                    @Override
                    public Bitmap loadThumbnail(@NonNull Uri uri, @NonNull Size size, @Nullable CancellationSignal signal) throws IOException {
                        return super.loadThumbnail(uri, size, signal);
                    }
                };
                Size size = new Size(50,50);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        album_art = resolver.loadThumbnail(art,size,null);
                    }
                }catch (IOException e){
                    Log.d("errorthumbnail",e.toString());
                }
                System.out.print("main albumart"+album_art);
                currentSong.setText(songdata.getSingslist(position).getTitle());

                song.play(getApplicationContext());
               // Intent intent = new Intent(getApplicationContext(), Playeractivity.class);
               // startActivity(intent);
            }

        };
        rec_view adapter = new rec_view(MainActivity.this,Listener);
        recview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recview.setAdapter(adapter);
    }


    private void runtimepersimissions() {
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getsongs(MainActivity.this);
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

    private void getsongs(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        Cursor cursor = context.getContentResolver().query(uri,projections,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);
                String id = cursor.getString(5);
                // System.out.println(album+":"+title+":"+duration);
                SongModel tempmodel = new SongModel();
                tempmodel.setTitle(title);
                tempmodel.setAlbum(album);
                tempmodel.setArtist(artist);
                tempmodel.setDuration(duration);
                tempmodel.setPath(path);
                tempmodel.setId(id);
                songdata.setSongslist(tempmodel);
            }
        }

        for(int i=0;i<songdata.getsize();i++){
            System.out.println(
                    "Each song:"+
                            songdata.getSingslist(i).toString()
            );
        }
    }
}
