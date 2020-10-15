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

import com.skydrop.jenvi.Interfaces.RecyclerViewClickListener;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;
import com.skydrop.jenvi.singleton.SongsList_singleton;

public class rec_view extends RecyclerView.Adapter<rec_view.MyViewHolder> {
    private final SongsList_singleton singleton = SongsList_singleton.getInstance();
    private final Context context;
    private final RecyclerViewClickListener Listener;

    public rec_view(Context context, RecyclerViewClickListener Listener) {
        this.context = context;
        this.Listener = Listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SongModel model = singleton.getSingslist(position);

        holder.songName.setText(model.getPath());
        holder.songTime.setText(model.getDuration());
        holder.imageView.setImageBitmap(model.getAlbumart());
    }

    @Override
    public int getItemCount() {
        return singleton.getsize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView songName;
        public Button play;
        public TextView songTime;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songname);
            songTime = itemView.findViewById(R.id.songtime);

            play = itemView.findViewById(R.id.play);

            imageView = itemView.findViewById(R.id.imageView);

            play.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Listener.onClick(view, getAdapterPosition());
        }
    }


}
