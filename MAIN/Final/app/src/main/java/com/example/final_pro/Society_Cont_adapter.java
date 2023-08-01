package com.example.final_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Society_Cont_adapter extends RecyclerView.Adapter<Society_Cont_adapter.Contact_ViewHolder> {

    Context context;
    ArrayList<Emergency_ContactModel> list;
    OnNoteListner monNoteListner;

    public void setFiltedList(ArrayList<Emergency_ContactModel> filtedList){
        this.list=filtedList;
        notifyDataSetChanged();
    }

    public Society_Cont_adapter(Context context, ArrayList<Emergency_ContactModel> list, OnNoteListner monNoteListner) {
        this.context = context;
        this.list = list;
        this.monNoteListner = monNoteListner;
    }

    @NonNull
    @Override
    public Contact_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency,parent,false);
        return new Contact_ViewHolder(view,monNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull Contact_ViewHolder holder, int position) {
        Emergency_ContactModel model= list.get(position);
        holder.n.setText(model.getName());
        holder.p.setText(model.getContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Contact_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView n,p;
        OnNoteListner onNoteListner;

        public Contact_ViewHolder(@NonNull View itemView ,OnNoteListner onNoteListner) {
            super(itemView);

            n=(TextView) itemView.findViewById(R.id.name_p);
            p=(TextView) itemView.findViewById(R.id.phone_p);
            this.onNoteListner=onNoteListner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListner.onNoteClick(getAdapterPosition());
        }
    }
public interface OnNoteListner{
        void onNoteClick(int position);
        void onItemLongClick(int position);
}

}
