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

public class complaintadapter extends FirebaseRecyclerAdapter<com.example.final_pro.dataholder,complaintadapter.myviewholder> {


    public complaintadapter(@NonNull FirebaseRecyclerOptions<com.example.final_pro.dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull dataholder model) {
        holder.complainttype.setText(model.getComplaint_type());
        holder.category.setText(model.getCategory());
        holder.description.setText(model.getDescription());
        holder.email.setText(model.getEmail());


        holder.delete.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
            builder.setTitle("Delete Panel");
            builder.setMessage("Are you sure you want to delete?");

            builder.setPositiveButton("Yes", (dialogInterface, i) -> FirebaseDatabase.getInstance().getReference().child("complaint")
                    .child(getRef(position).getKey()).removeValue());

            builder.setNegativeButton("No", (dialogInterface, i) -> {

            });

            builder.show();
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        ImageView delete;
        TextView complainttype,category,description,email;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.img1);
            complainttype = itemView.findViewById(R.id.PComplainttype);
            category = itemView.findViewById(R.id.PCategory);
            description = itemView.findViewById(R.id.PDescription);
            email=itemView.findViewById(R.id.Pemail);
            delete= itemView.findViewById(R.id.deleteicon);
        }
    }
}
