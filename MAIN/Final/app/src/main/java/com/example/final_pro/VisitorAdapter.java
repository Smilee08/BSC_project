package com.example.final_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.ViewHolder> {

    Context context;
    ArrayList<VisitorString> Vlist;

    public VisitorAdapter() {
    }

    public VisitorAdapter(Context context, ArrayList<VisitorString> vlist) {
        this.context = context;
        this.Vlist = vlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitorlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VisitorString visitorlist = Vlist.get(position);
        holder.name.setText(visitorlist.getName());
        holder.contact.setText(visitorlist.getContact());
        holder.date.setText(visitorlist.getDate());
        holder.time_in.setText(visitorlist.getTime());
        holder.guest_of.setText(visitorlist.getGuest_of());
        holder.roomno.setText(visitorlist.getRoomno());
    }

    @Override
    public int getItemCount() {
        return Vlist.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, contact, date, time_in, guest_of, roomno;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            date = itemView.findViewById(R.id.date);
            time_in = itemView.findViewById(R.id.time_in);
            guest_of = itemView.findViewById(R.id.guest_of);
            roomno = itemView.findViewById(R.id.roomno);
        }
    }
}
