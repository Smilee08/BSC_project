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


public class Staffadapter extends FirebaseRecyclerAdapter<com.example.final_pro.Staffusers,Staffadapter.myviewholder>
{
    public Staffadapter(@NonNull FirebaseRecyclerOptions<com.example.final_pro.Staffusers> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final com.example.final_pro.Staffusers model)
    {
        holder.name.setText(model.getName());
        holder.work.setText(model.getWork());
        holder.department.setText(model.getDepartment());
        holder.availability.setText(model.getAvailability());
        holder.contactno.setText(model.getContactno());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent1))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText availability=myview.findViewById(R.id.uavailibility);
                Button submit=myview.findViewById(R.id.usubmit);

                availability.setText(model.getAvailability());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("availability",availability.getText().toString());



                        FirebaseDatabase.getInstance().getReference().child("staffavail")
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
                        FirebaseDatabase.getInstance().getReference().child("staffavail")
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name, work,department,availability,contactno;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.PName);
            work = (TextView) itemView.findViewById(R.id.Pwork);
            department = (TextView) itemView.findViewById(R.id.Pdepartment);
            availability = (TextView) itemView.findViewById(R.id.Pavailability);
            contactno = (TextView) itemView.findViewById(R.id.Pcontact);


            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
