package com.skydrop.jenvi.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skydrop.jenvi.Interfaces.RecyclerViewClickListener;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.skydrop.jenvi.Adapters.rec_view;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;
import com.skydrop.jenvi.singleton.SongsList_singleton;
import com.skydrop.jenvi.singleton.song_singleton;

import static com.skydrop.jenvi.Applications.App.MAINACTIVITY_FLAG;

public class MainActivity extends AppCompatActivity {
    //region Final Variables
    private final SongsList_singleton songdata = SongsList_singleton.getInstance();
    private final song_singleton song = song_singleton.getInstance();
    private final RecyclerViewClickListener Listener = new RecyclerViewClickListener() {
        @Override
        public void onClick(View v, int pos) {
            song.play(getApplicationContext(), pos);
        }
    };
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == next) {
                song.next(getApplicationContext());
            } else if (v == prev) {
                song.prev(getApplicationContext());
            } else if (v == play) {
                if (song.getIsPlaying()) {
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    song.pausesong();
                } else {
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    song.playsong();
                }
            } else if (v == bottombar) {
                startActivity(new Intent(getApplicationContext(), Playeractivity.class));
            }
        }
    };
    //endregion

    private RecyclerView recview;
    private ImageButton next;
    private ImageButton prev;
    private ImageButton play;
    private ConstraintLayout bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        song.setFlag(MAINACTIVITY_FLAG);


        if (songdata.getsize() == 0) {
            runtimepersimissions();
        }

        setmappings();

        //region Rec View
        rec_view adapter = new rec_view(MainActivity.this, Listener);
        recview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recview.setAdapter(adapter);
        //endregion

    }

    private void setmappings() {
        recview = findViewById(R.id.recview);

        play = (ImageButton) findViewById(R.id.play_main);
        next = findViewById(R.id.next_main);
        prev = findViewById(R.id.prev_main);
        bottombar = findViewById(R.id.bottombar);

        song.Main_Play = play;
        song.Main_SongName = (TextView) findViewById(R.id.currentSong);
        song.Main_albumart = (ImageView) findViewById(R.id.albumart_main);

        next.setOnClickListener(listener);
        prev.setOnClickListener(listener);
        play.setOnClickListener(listener);
        bottombar.setOnClickListener(listener);

        song.showdata();
    }

    private void runtimepersimissions() {
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
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

    @RequiresApi(api = Build.VERSION_CODES.R)
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

        @SuppressLint("Recycle")
        Cursor cursor = context.getContentResolver().query(uri, projections, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);
                String id = cursor.getString(5);

                SongModel tempmodel = new SongModel();

                tempmodel.setTitle(title);
                tempmodel.setAlbum(album);
                tempmodel.setArtist(artist);
                tempmodel.setDuration(duration);
                tempmodel.setPath(path);
                tempmodel.setId(id);

                // TODO: Remove after album art
                tempmodel.setAlbumart(BitmapFactory.decodeResource(getResources(), R.drawable.defaultalbumart));

                songdata.setSongslist(tempmodel);
            }
        }
    }
}
