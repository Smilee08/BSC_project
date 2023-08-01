package com.example.final_pro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;


import com.example.final_pro.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;

public class ProfileActivity extends DrawerBaseActivity {


ActivityProfileBinding activityProfileBinding;
    CircleImageView circleImageViewProfile;
    FloatingActionButton floatingActionButton;
    EditText Pname,Pabout;
    TextView Pemail,Pphone;
    Button Pbutton,sbtn;
    private FirebaseAuth firebaseAuthprofile;
    private Task<Void> databaseReferenceProfile;
    String myUri="";
    private FirebaseDatabase firebaseDatabaseProfile;

   private StorageReference storageReferenceProfile;

    String uid;

    CardView Name,About,Email,Phone;
    TextView n,a,p,e;
    EditText About_et,Username,mobile,mail;
    DatabaseReference databaseReferenceProfil,databaseReference;
    Profile_data profileData;
    ActionBar actionBar;
    ///
    String mCurrentPhotoPath;
    Uri filePath;
    StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_prof);
        activityProfileBinding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("Profile");
       /* Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Profile");
        }*/

        Load_Settings();

       // sbtn=findViewById(R.id.save);
        /*sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });*/
        circleImageViewProfile=findViewById(R.id.prof_pic);
        floatingActionButton =findViewById(R.id.fab_prof);
        floatingActionButton.setOnClickListener(view -> chooseProfilePicture());

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        // firebaseDatabaseProfile = FirebaseDatabase.getInstance();
        //  FirebaseAuth prof=FirebaseAuth.getInstance().getCurrentUser();
        databaseReferenceProfil = FirebaseDatabase.getInstance().getReference("Profile").child(userId);
        profileData = new Profile_data();

        Name=findViewById(R.id.name);
        // n=findViewById(R.id.firstname);
        Name.setOnClickListener(v -> nameDailog());

        About=findViewById(R.id.about);
        //a=findViewById(R.id.et_about);
        About.setOnClickListener(v -> aboutDailog());

        Email=findViewById(R.id.em);
        // e=findViewById(R.id.ed_email);
        emailData();

        Phone=findViewById(R.id.ph);
        Phone.setOnClickListener(v -> phoneDailog());


        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("prof");
        StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("profile_pic");
        //firebaseDatabaseProfile = FirebaseDatabase.getInstance();
       // databaseReferenceProfile = firebaseDatabaseProfile.getReference("Profile").child("user");
     //   databaseReferenceProfile.keepSynced(true);
     mStorageRef = FirebaseStorage.getInstance().getReference();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
       // Pname=findViewById(R.id.ed_fn);
       // Pabout=findViewById(R.id.ed_ab);
        //Pemail=findViewById(R.id.ed_email);
        /*
        Pbutton.setOnClickListener(view -> {
            firebaseAuthprofile=FirebaseAuth.getInstance();
            uid=firebaseAuthprofile.getCurrentUser().getUid();
           // String imageurl=firebaseAuthprofile.getCurrentUser().getPhotoUrl();
            String fakename=Pname.getText().toString();
            String about=Pabout.getText().toString();
            String phone=firebaseAuthprofile.getCurrentUser().getPhoneNumber();
            String mail=firebaseAuthprofile.getCurrentUser().getEmail();

            Profile_data pro=new Profile_data(uid,"no img",fakename,about,phone,mail);
            databaseReferenceProfile=FirebaseDatabase.getInstance().getReference().child("user").setValue(pro).addOnSuccessListener(unused -> {
                Toast.makeText(ProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
*/
        //    });




            // databaseReferenceProfile.push().setValue(userMap).addOnCompleteListener(task -> {
            //   Toast.makeText(ProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();
            // Intent intent=new Intent(ProfileActivity.this,Nav_HeaderActivity.class);
            //  startActivity(intent);
            //});

//        });

    }
    private void getProfPic(){

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() >0 ) {
                    if (snapshot.hasChild("image")) {
                       // String image=snapshot.child("image").getValue(String.class);
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(circleImageViewProfile);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void chooseProfilePicture(){
        AlertDialog.Builder builder= new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.alertdialog_profilepic,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        CircleImageView circleImageViewCamera=dialogView.findViewById(R.id.cam);
        CircleImageView circleImageViewGallery=dialogView.findViewById(R.id.gal);
        CircleImageView circleImageViewRemove=dialogView.findViewById(R.id.rem);

        AlertDialog alertDialogProfilePicture= builder.create();
        alertDialogProfilePicture.show();
        circleImageViewCamera.setOnClickListener(view -> {
            if (checkAndRequestPermission()) {
                takePictureFromCamera();
                alertDialogProfilePicture.cancel();
                getProfPic();
            }
        });

        circleImageViewGallery.setOnClickListener(view ->{ takePictureFromGallery();
            alertDialogProfilePicture.cancel();
        });
        circleImageViewRemove.setOnClickListener(view -> {
            removepicture();
            alertDialogProfilePicture.cancel();
        });


    }

    private void takePictureFromCamera(){
        Intent takepicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepicture.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takepicture.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                filePath = Uri.fromFile(photoFile);
                Log.e("Uri ", filePath + "");
                startActivityForResult(takepicture, 2);
            }
          //  startActivityForResult(takepicture,2);
        }

    }

    private void takePictureFromGallery(){
        Intent pickphoto=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // pickphoto.setAction(Intent.ACTION_GET_CONTENT);
        //pickphoto.setType("image/*");
        startActivityForResult(pickphoto,1);

    }

    private void removepicture(){
        // Intent removepic=new Intent()
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("photo path = " , mCurrentPhotoPath);

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    assert data != null;
                    Uri selectedImageUri = data.getData();
                    circleImageViewProfile.setImageURI(selectedImageUri);

                    ProgressDialog progressDialog=new ProgressDialog(this);
                    progressDialog.setTitle("Set you profile");
                    progressDialog.setMessage("Please wait,while pic is getting upload");
                    progressDialog.show();
                    if(selectedImageUri != null){
                        StorageReference file=FirebaseStorage.getInstance().getReference().child("profile_pic").child(firebaseAuth.getCurrentUser().getUid()+".Jpg");

                        StorageTask uploadTask;
                        uploadTask=file.putFile(selectedImageUri);
                        uploadTask.continueWithTask(new Continuation() {
                            @Override
                            public Object then(@NonNull Task task) throws Exception {
                                if (!task.isSuccessful()){
                                    throw task.getException();
                                }
                                return file.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    Uri downloadUri=(Uri) task.getResult();
                                    myUri=downloadUri.toString();
                                    HashMap<String,Object> userMap=new HashMap<>();
                                    userMap.put("image",myUri);
                                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(userMap);
                                    progressDialog.dismiss();;
                                }
                            }
                        });
                    }else
                    {
                        Toast.makeText(ProfileActivity.this,"image not selected",Toast.LENGTH_SHORT).show();
                    }


                    //  storageReferenceProfile =FirebaseStorage.getInstance().getReference().child("profile_pic"+firebaseAuthprofile.getCurrentUser().getUid());
                    //storageReferenceProfile.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> storageReferenceProfile.getDownloadUrl().addOnSuccessListener(uri -> firebaseDatabaseProfile.getReference().child("users").setValue(uri.toString()).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"image uploaded",Toast.LENGTH_SHORT).show())));
                }
                break;
            case 2:
                if (resultCode==RESULT_OK){

                    assert data != null;
                    Bundle bundle=data.getExtras();
                   Bitmap SelectedImageUri=(Bitmap)bundle.get("data");

                   circleImageViewProfile.setImageBitmap(SelectedImageUri);

                    //CropImage.activity(filePath).setAspectRatio(1,1)
                         //   .start(this);

              //  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                //    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                  //  Uri resultUri = result.getUri();
                    //circleImageViewProfile.setImageURI(resultUri);
                }
                    if(filePath != null) {
                  ProgressDialog progressDialog=new ProgressDialog(this);
                   progressDialog.setTitle("Set you profile");
                   progressDialog.setMessage("Please wait,while pic is getting upload");
               progressDialog.show();
                        final String _imgName=System.currentTimeMillis()/1000+"_img.jpg";

                        StorageReference riversRef = mStorageRef.child("userImages/"+_imgName);

                        Bitmap bitmap=null;
                        try{
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        //compress image

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                        byte[] d = baos.toByteArray();

                        riversRef.putBytes(d)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                                ,Toast.LENGTH_SHORT).show();
                                        //imageView.setImageBitmap(null);
                                        Log.e("upload : ","Success");
                                        @SuppressWarnings("VisibleForTests")Uri downloadUrl =
                                                taskSnapshot.getUploadSessionUri();
                                        Log.e("downloadUrl-->", "" + downloadUrl);




                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),exception.getMessage()
                                                ,Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                                        @SuppressWarnings("VisibleForTests")double progress
                                                = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                                        progressDialog.setMessage(((int) progress) + "% uploading....");

                                    }
                                }) ;

                    //  storageReferenceProfile = FirebaseStorage.getInstance().getReference().child("profile_pic"+firebaseAuthprofile.getCurrentUser().getUid());
                    //  storageReferenceProfile.putFile(SelectedImageUri).addOnSuccessListener(taskSnapshot -> storageReferenceProfile.getDownloadUrl().addOnSuccessListener(uri -> firebaseDatabaseProfile.getReference().child("users").setValue(uri.toString()).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"image uploaded",Toast.LENGTH_SHORT).show())));
                }
                break;
            case 3:
                //need to add remove code
                break;


        }
    }
    private boolean checkAndRequestPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            int cameraPermission= ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA);
            if (cameraPermission== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{Manifest.permission.CAMERA},20);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==20&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            takePictureFromCamera();
        }else{
            Toast.makeText(ProfileActivity.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void emailData(){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String em=auth.getCurrentUser().getEmail();
        profileData.setEmail(em);
        databaseReferenceProfil.setValue(profileData);


        e=findViewById(R.id.ed_email);
        databaseReferenceProfil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String em=snapshot.child("email").getValue().toString();
                e.setText(em);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"can not retrive email",Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void nameDailog(){
        // Dailog_name dailog_name=new Dailog_name();
        //dailog_name.show(getSupportFragmentManager(),"dailog_name");
        AlertDialog.Builder Namebuilder=new AlertDialog.Builder(ProfileActivity.this);
        View view=getLayoutInflater().inflate(R.layout.name_dailog,null);
        // final  EditText input=new EditText(ProfActivity.this);
        //Namebuilder.setView(input);


        Namebuilder.setView(view).setCancelable(false).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Username=view.findViewById(R.id.fn2);
                String name=Username.getText().toString();
                // listener.applyText(name);
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"name can't be empty",Toast.LENGTH_SHORT).show();
                }else{
                    profileData.setName(name);
                    databaseReferenceProfil.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReferenceProfil.setValue(profileData);
                            Toast.makeText(ProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();

                            n = findViewById(R.id.firstname);
                            //String val=snapshot.getValue(String.class);
                            // Log.d(Tag,"Value"+val);
                            n.setText(Username.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ProfileActivity.this, "Fail to enter", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        AlertDialog naAlert=Namebuilder.create();
        naAlert.show();

        // n=findViewById(R.id.firstname);

    }


    public void aboutDailog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
        final View view=getLayoutInflater().inflate(R.layout.about_dailog,null);

        builder.setView(view).setCancelable(false).setNegativeButton("cancel", (dialog, which) -> dialog.cancel()).setPositiveButton("save", (dialog, which) -> {
            String about=About_et.getText().toString();

            if(about.isEmpty()) {
                Toast.makeText(getApplicationContext(), "about can't be empty", Toast.LENGTH_SHORT).show();
            }else {
                profileData.setAbout(about);
                databaseReferenceProfil.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReferenceProfil.setValue(profileData);
                        Toast.makeText(ProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();

                        a=findViewById(R.id.et_about);
                        a.setText(about);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfileActivity.this, "Fail to enter", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        About_et=view.findViewById(R.id.ed_about);
        AlertDialog abAlert=builder.create();
        abAlert.show();
    }

    public void phoneDailog(){

        AlertDialog.Builder Phonebuilder=new AlertDialog.Builder(ProfileActivity.this);
        View view=getLayoutInflater().inflate(R.layout.phone_dailog,null);

        Phonebuilder.setView(view).setCancelable(false).setNegativeButton("cancel", (dialog, which) -> dialog.cancel()).setPositiveButton("save", (dialog, which) -> {
            String ph=mobile.getText().toString();
            if(ph.isEmpty()){
                Toast.makeText(getApplicationContext(),"phone no can't be empty",Toast.LENGTH_SHORT).show();
            }else{
                profileData.setPhone(ph);
                databaseReferenceProfil.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReferenceProfil.setValue(profileData);//.setValue(profileData);
                        Toast.makeText(ProfileActivity.this, "saved", Toast.LENGTH_SHORT).show();

                        p=findViewById(R.id.ed_phone);
                        p.setText(ph);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfileActivity.this, "Fail to enter", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        mobile=view.findViewById(R.id.ed_ph);
        // mobile.setText(ph);
        AlertDialog phAlert=Phonebuilder.create();
        phAlert.show();
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
    public void onBackPressed() {
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

}