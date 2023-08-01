package com.example.final_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Vendor_Attendenceadapter extends FirebaseRecyclerAdapter<com.example.final_pro.Vendor_attendanceusers,Vendor_Attendenceadapter.myviewholder>
{
    public Vendor_Attendenceadapter(@NonNull FirebaseRecyclerOptions<com.example.final_pro.Vendor_attendanceusers> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final com.example.final_pro.Vendor_attendanceusers model)
    {
        holder.name.setText(model.getName());
        holder.type.setText(model.getType());
        holder.timein.setText(model.getTimein());
        holder.timeout.setText(model.getTimeout());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent_vendorattendance))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText timeout=myview.findViewById(R.id.utimeout);
                Button submit=myview.findViewById(R.id.usubmit);

                timeout.setText(model.getTimeout());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("timeout",timeout.getText().toString());



                        FirebaseDatabase.getInstance().getReference().child("VenderAT")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("VenderAT")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendorattendance,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name, type,timein,timeout;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img= itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.PName);
            type = itemView.findViewById(R.id.Ptype);
            timein = itemView.findViewById(R.id.PTimein);
            timeout = itemView.findViewById(R.id.PTimeout);



            edit= itemView.findViewById(R.id.editicon);
            delete= itemView.findViewById(R.id.deleteicon);
        }
    }
}


