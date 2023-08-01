package com.example.final_pro;


import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Delivery_adapter extends FirebaseRecyclerAdapter<com.example.final_pro.Delivery_users,Delivery_adapter.myviewholder>
{
    public Delivery_adapter(@NonNull FirebaseRecyclerOptions<com.example.final_pro.Delivery_users> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final com.example.final_pro.Delivery_users model)
    {
        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        holder.recievername.setText(model.getRecievername());
        holder.contactno.setText(model.getContactno());
        holder.flatno.setText(model.getFlatno());
        holder.typeofdelivery.setText(model.getTypeofdelivery());



    } // End of OnBindViewMethod



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;

        TextView name, time, date,typeofdelivery,recievername,flatno,contactno;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img= itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.Pname);
            time = itemView.findViewById(R.id.Ptime);
            date = itemView.findViewById(R.id.PDate);
            typeofdelivery = itemView.findViewById(R.id.Ptypeofdelivery);
            contactno = itemView.findViewById(R.id.Pcontactno);
            flatno= itemView.findViewById(R.id.Pflatno);
            recievername= itemView.findViewById(R.id.Precievername);



        }
    }
}

