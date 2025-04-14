package com.example.kalozteka;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kalozteka.models.VideoModel;

import java.time.Instant;
import java.util.List;
import com.bumptech.glide.Glide;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoModel> videoList;
    private Context context;

    public VideoAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        // További beállítások, pl. kép beállítása Glide-al vagy más eszközzel


        Glide.with(holder.itemView)
                .load(video.getKep())
                .placeholder(R.drawable.kaloztekafull)  // amíg töltődik
                .error(R.drawable.kaloztekafull)              // ha hiba van
                .into(holder.imageView);
        // Kattintás kezelése
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoDetailActivity.class);
            intent.putExtra("video_url", video.getUrl()); // Átadod a videó URL-jét
            intent.putExtra("video_id", video.getId());
            intent.putExtra("video_kuldo_id", video.getUid());
            intent.putExtra("video_text", video.getTitle());
            context.startActivity(intent);
        });

        // Animáció alkalmazása az elem megjelenésekor
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);  // 0.5 másodperc

        holder.itemView.startAnimation(fadeIn); // Az egész elemre alkalmazzuk
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView imageView ;

        public VideoViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.videoTitleTextView);  // Az item layout megfelelő ID-jével

            imageView=itemView.findViewById(R.id.thumbnailImageView);
        }
    }

    public void updateList(List<VideoModel> ujLista) {
        this.videoList = ujLista;
        notifyDataSetChanged();
    }

}
