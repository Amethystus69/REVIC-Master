package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.revic_capstone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Posts;
import Models.Videos;

public class AdapterVideoItem extends RecyclerView.Adapter<AdapterVideoItem.ItemViewHolder> {

    private List<Posts> arr;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public AdapterVideoItem() {
    }

    public AdapterVideoItem(List<Posts> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterVideoItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterVideoItem.ItemViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideoItem.ItemViewHolder holder, int position) {

        Posts posts = arr.get(position);

        String imageUrl = posts.getFileUrl();

        Glide.with(context)
                .load(imageUrl)
                .into(holder.iv_photoItem);

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_photoItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_photoItem = itemView.findViewById(R.id.iv_photoItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
