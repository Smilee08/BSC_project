package com.example.final_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.EmergencyViewHolder>{

    private ArrayList<Emergency_ContactModel> modelArrayList;
private Context context;
private OnNoteListener mOnNoteListner;

    public EmergencyAdapter(ArrayList<Emergency_ContactModel> modelArrayList, Context context, OnNoteListener mOnNoteListner) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.mOnNoteListner = mOnNoteListner;
    }

    public void setFilteredList(ArrayList<Emergency_ContactModel> filteredList){
        this.modelArrayList=filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public EmergencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency,parent,false);
        return new EmergencyViewHolder(view,mOnNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyViewHolder holder, int position) {
        Emergency_ContactModel model= modelArrayList.get(position);
        holder.ename.setText(model.getName());
        holder.econtact.setText(model.getContact());

        //Random random=new Random();
        //int color= Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(255));
        //holder.
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class EmergencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ename,econtact;
        OnNoteListener onNoteListener;


        public EmergencyViewHolder(@NonNull View itemView ,OnNoteListener onNoteListener) {
            super(itemView);
            ename=(TextView) itemView.findViewById(R.id.name_p);
           econtact=(TextView) itemView.findViewById(R.id.phone_p);

            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
