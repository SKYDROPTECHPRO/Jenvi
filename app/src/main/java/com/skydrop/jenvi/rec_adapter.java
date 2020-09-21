package com.skydrop.jenvi;

import android.content.Context;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class rec_adapter extends RecyclerView.Adapter<rec_adapter.Myview> {
    private Context context;
    private SongsListSingleton songslist;
    private RecyclerviewClickListener listener;

    public rec_adapter(Context context,RecyclerviewClickListener listener) {
        this.context = context;
        this.listener = listener;
        songslist = SongsListSingleton.getInstance();
    }

    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myview(LayoutInflater.from(context).inflate(R.layout.rec_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {
        SongModel tempmodel = songslist.get(position);
        holder.title.setText(tempmodel.getTitle());
        holder.duration.setText(tempmodel.getDuration());
        // TODO: album art
        Glide.with(context).asBitmap().load(tempmodel.getAlbumart()).into(holder.album_art);
    }

    @Override
    public int getItemCount() {
        return songslist.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView title;
        TextView duration;
        ImageView album_art;
        public Myview(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.songname);
            duration = itemView.findViewById(R.id.songtime);
            album_art = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(v,getAdapterPosition());
                }
            });
        }
    }
}
