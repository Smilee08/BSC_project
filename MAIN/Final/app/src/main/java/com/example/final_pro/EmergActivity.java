package com.example.final_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmergActivity extends AppCompatActivity {

FloatingActionButton folder;
    DatabaseReference databaseReference;
    folder fol;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emerg);


        databaseReference = FirebaseDatabase.getInstance().getReference("Emergency_folder");
        fol = new folder();

        folder=findViewById(R.id.f);
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                folderDailog();
            }
        });




    }
   private  void folderDailog(){
       //dailog_name.show(getSupportFragmentManager(),"dailog_name");
       AlertDialog.Builder Folderbuilder=new AlertDialog.Builder(EmergActivity.this);
       View view=getLayoutInflater().inflate(R.layout.folder,null);
       // final  EditText input=new EditText(ProfActivity.this);

        Folderbuilder.setView(view).setCancelable(false).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              name=view.findViewById(R.id.f_n);
                String fname=name.getText().toString();

                if(fname.isEmpty()){
                    Toast.makeText(getApplicationContext(),"name can't be empty",Toast.LENGTH_SHORT).show();
                }else{
                    fol.setFname(fname);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.setValue(fol);
                            Toast.makeText(EmergActivity.this, "saved", Toast.LENGTH_SHORT).show();

                            // n = findViewById(R.id.firstname);
                            //String val=snapshot.getValue(String.class);
                            // Log.d(Tag,"Value"+val);
                            //n.setText(fname.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EmergActivity.this, "Fail to enter", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        AlertDialog naAlert=Folderbuilder.create();
        naAlert.show();

    }
}