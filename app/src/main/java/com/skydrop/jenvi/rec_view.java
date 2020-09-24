package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;

public class rec_view extends RecyclerView.Adapter<rec_view.MyViewHolder> {
    SongsList_singleton singleton;
    Context context;
   // SongModel model;
    RecyclerViewClickListener Listener;
    // song_singleton song = song_singleton.instance;

    public rec_view(Context context, RecyclerViewClickListener Listener){
        this.context = context;
        this.Listener = Listener;
        singleton = SongsList_singleton.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MyViewHolder(inflater.inflate(R.layout.rec_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        System.out.println(position+":"+model.toString());
        holder.songName.setText(singleton.getSingslist(position).getPath());
        holder.songTime.setText(singleton.getSingslist(position).getDuration());

        holder.imageView.setImageURI(MainActivity.art);
        //holder.imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        System.out.println("size "+singleton.songslist.size());
        return singleton.songslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView songName;
        Button play;
        TextView songTime;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = (TextView)itemView.findViewById(R.id.songname);
            songTime = (TextView)itemView.findViewById(R.id.songtime);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            play = (Button)itemView.findViewById(R.id.play);
            play.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // song.play(context.getApplicationContext());
            Listener.onClick(view,getAdapterPosition());

        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int pos);
    }
}
