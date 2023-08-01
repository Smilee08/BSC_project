package com.example.final_pro;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.final_pro.databinding.ActivityProfBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Nav_HeaderActivity extends AppCompatActivity {

    TextView email,phone,about,name;
FirebaseAuth firebaseAuth;
    CircleImageView circleImageViewProfile;
DatabaseReference databaseReferencePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_header);
       // activityProBinding= ActivityProfBinding.inflate(getLayoutInflater());
        name=findViewById(R.id.fn);
        about=findViewById(R.id.about);
        phone=findViewById(R.id.ph);
        email=findViewById(R.id.em);

        String userIda= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReferenceProf = FirebaseDatabase.getInstance().getReference("Profile").child(userIda);

        Profile_data profile=new Profile_data();

        databaseReferenceProf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    name.setText(snapshot.child("name").getValue().toString());
                   // name.setText(snapshot.child("name").getValue().toString());
                    about.setText(snapshot.child("about").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    email.setText(snapshot.child("email").getValue(String.class));
                   // email.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Nav_HeaderActivity.this, "failed to upload the profile details", Toast.LENGTH_SHORT).show();

            }
        });
        circleImageViewProfile=findViewById(R.id.pic);

        databaseReferencePic=FirebaseDatabase.getInstance().getReference().child("prof");
       // StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profile_pic");
        databaseReferencePic.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                  //Prof_img pro = snapshot.getValue(Prof_img.class);
                //  circleImageViewProfile.addChildrenForAccessibility(pro);
               String image = snapshot.child("image").getValue().toString();
               //Glide.load(getImg()).into(image);
                     //String link=snapshot.getValue(Prof_img.class);
                Picasso.get().load(image).placeholder(R.drawable.ic_baseline_photo_24).error(R.drawable.ic_baseline_error_outline_24).into(circleImageViewProfile);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private String getFileExtension(Uri imageUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imageUri));
    }

}