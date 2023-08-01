package com.example.final_pro;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class paymentadapter extends FirebaseRecyclerAdapter<paymentusers,paymentadapter.myviewholder>
{

    public paymentadapter(@NonNull FirebaseRecyclerOptions<paymentusers> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final paymentusers model)
    {
        holder.name.setText(model.getName());
        holder.wing.setText(model.getWing());
        holder.flatno.setText(model.getFlatno());


        holder.delete.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
            builder.setTitle("Delete Panel");
            builder.setMessage("Delete...?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase.getInstance().getReference().child("Pay")
                            .child(getRef(position).getKey()).removeValue();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();
        });

    } // End of OnBindViewMethod




    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_paybutton,parent,false);
        return new myviewholder(view);
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name, wing, flatno;
        public Button b1;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.PName);
            wing = (TextView) itemView.findViewById(R.id.Pwing);
            flatno= (TextView) itemView.findViewById(R.id.PFlatno);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

            }


        }

    }

