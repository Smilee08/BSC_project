package com.example.final_pro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_pro.databinding.ActivityCircularBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CircularActivity extends com.example.final_pro.DrawerBaseActivity {


    ActivityCircularBinding activityCircularBinding;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    FloatingActionButton fb1;
    private EditText title1, date1, sub1, name1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    com.example.final_pro.FileAdapter fileAdapter;
    ArrayList<com.example.final_pro.ImageFile> list1;
    RecyclerView recyclerView;
    private Uri imageUri;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCircularBinding = ActivityCircularBinding.inflate(getLayoutInflater());
        setContentView(activityCircularBinding.getRoot());
        allocateActivityTitle("Circular");

        Load_Settings();

        databaseReference = FirebaseDatabase.getInstance().getReference("ImageFile");
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list1 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    com.example.final_pro.ImageFile image1 = dataSnapshot.getValue(com.example.final_pro.ImageFile.class);
                    list1.add(image1);
                    notification();
                }
                fileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fileAdapter = new com.example.final_pro.FileAdapter(CircularActivity.this, list1);
        recyclerView.setAdapter(fileAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fb1 = (FloatingActionButton) findViewById(R.id.f1);

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin").equals("1")) {
                            createNewDialog();
                        }
                        else {
                            fb1.hide();
                        }
                    }
                });
            }
        });

    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n").setContentText("Code Sphere")
                .setSmallIcon(R.drawable.ic_baseline_add_a_photo_24)
                .setAutoCancel(true)
                .setContentText("New Circular Uploaded");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    public void createNewDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.choice, null);

        Button gallery = (Button) popupView.findViewById(R.id.bt1);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);

                if (imageUri != null) {
                    StorageReference fileref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                    fileref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    com.example.final_pro.ImageFile image = new com.example.final_pro.ImageFile(uri.toString());
                                    String imageId = databaseReference.push().getKey();
                                    databaseReference.child(imageId).setValue(image);
                                    Toast.makeText(CircularActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), CircularActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CircularActivity.this, "Upload Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(CircularActivity.this, "Please Select ImageFile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }


    public void Load_Settings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String orien=sp.getString("ORIENTATION","false");
        if ("1".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        } else if ("2".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if ("3".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
    }
    @Override
    public void onResume() {
        overridePendingTransition(0,0);
        Load_Settings();
        super.onResume();
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, com.example.final_pro.HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}