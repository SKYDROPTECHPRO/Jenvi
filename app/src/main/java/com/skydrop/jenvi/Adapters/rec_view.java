package com.skydrop.jenvi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skydrop.jenvi.Activities.MainActivity;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.singleton.SongsList_singleton;

public class rec_view extends RecyclerView.Adapter<rec_view.MyViewHolder> {
    SongsList_singleton singleton;
    Context context;
    RecyclerViewClickListener Listener;

    public rec_view(Context context, RecyclerViewClickListener Listener){
        this.context = context;
        this.Listener = Listener;
        singleton = SongsList_singleton.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.songName.setText(singleton.getSingslist(position).getPath());
        holder.songTime.setText(singleton.getSingslist(position).getDuration());

        holder.imageView.setImageURI(MainActivity.art);
        //holder.imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return singleton.getsize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView songName;
        Button play;
        TextView songTime;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songname);
            songTime = itemView.findViewById(R.id.songtime);
            imageView = itemView.findViewById(R.id.imageView);
            play = itemView.findViewById(R.id.play);
            play.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Listener.onClick(view,getAdapterPosition());

        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int pos);
    }
}
