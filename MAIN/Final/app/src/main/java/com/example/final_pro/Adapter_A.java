package com.example.final_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_A extends RecyclerView.Adapter<Adapter_A.ViewHolder> {

    Context context;
    ArrayList<List_ClassA> userList;

    public Adapter_A(Context context, ArrayList<List_ClassA> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_design,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        List_ClassA user = userList.get(position);
        holder.room.setText(user.getRoom());
        holder.name.setText(user.getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                        .setExpanded(true, 400)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText roomno1 = myview.findViewById(R.id.roomno);
                EditText name1 = myview.findViewById(R.id.name);
                Button update = myview.findViewById(R.id.update);

                roomno1.setText(user.getRoom());
                name1.setText(user.getName());

                dialogPlus.show();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("room", roomno1.getText().toString());
                        map.put("name", name1.getText().toString());


                        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("WingA").child(user.getName()).getRef();
                        df.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialogPlus.dismiss();
                                userList.remove(position);
                                Toast.makeText(context.getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView room;
        private TextView name;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            room = itemView.findViewById(R.id.room);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image1);
        }
    }
}
