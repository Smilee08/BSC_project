package com.example.final_pro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {



    private Context context;
    private ArrayList<com.example.final_pro.ImageFile> imglist;

    public FileAdapter(Context context, ArrayList<ImageFile> imglist) {
        this.context = context;
        this.imglist = imglist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.design, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        com.example.final_pro.ImageFile temp = imglist.get(position);
        Glide.with(context).load(imglist.get(position).getImageUri()).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, com.example.final_pro.ImageActivity.class);
            intent.putExtra("imageUri", temp.getImageUri());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgview);
        }

    }

}
