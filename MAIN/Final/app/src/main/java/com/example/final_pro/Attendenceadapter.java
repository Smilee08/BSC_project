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


public class Attendenceadapter extends FirebaseRecyclerAdapter<com.example.final_pro.Attendenceusers,Attendenceadapter.myviewholder>
{
    public Attendenceadapter(@NonNull FirebaseRecyclerOptions<com.example.final_pro.Attendenceusers> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final com.example.final_pro.Attendenceusers model)
    {
        holder.name.setText(model.getName());
        holder.timein.setText(model.getTimein());
        holder.timeout.setText(model.getTimeout());
        holder.date.setText(model.getDate());


        holder.edit.setOnClickListener(view -> {
            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                    .setContentHolder(new ViewHolder(R.layout.dialogcontent1))
                    .setExpanded(true,1100)
                    .create();

            View myview=dialogPlus.getHolderView();
            final EditText timeout=myview.findViewById(R.id.uavailibility);
            Button submit=myview.findViewById(R.id.usubmit);

           timeout.setText(model.getTimeout());
            dialogPlus.show();

            submit.setOnClickListener(view1 -> {
                Map<String,Object> map=new HashMap<>();
                map.put("timeout",timeout.getText().toString());



                FirebaseDatabase.getInstance().getReference().child("Staff")
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
            });


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
                        FirebaseDatabase.getInstance().getReference().child("Staff")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", (dialogInterface, i) -> {

                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item1,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name, timein,timeout,date;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.PName);
            timein = (TextView) itemView.findViewById(R.id.Ptimein);
            timeout = (TextView) itemView.findViewById(R.id.Ptimeout);
            date = (TextView) itemView.findViewById(R.id.Pdate);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
