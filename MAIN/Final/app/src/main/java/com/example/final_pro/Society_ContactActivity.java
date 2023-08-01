package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_pro.databinding.ActivitySocietyContactBinding;
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

import java.util.ArrayList;

public class Society_ContactActivity extends DrawerBaseActivity implements Society_Cont_adapter.OnNoteListner {

    ActivitySocietyContactBinding activitySocietyContactBinding;
    FloatingActionButton call;
    RecyclerView recyclerView;
    ArrayList<Emergency_ContactModel> list;
    DatabaseReference databaseReference;
    Society_Cont_adapter adapter;
    SearchView searchView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySocietyContactBinding= ActivitySocietyContactBinding.inflate(getLayoutInflater());
        setContentView(activitySocietyContactBinding.getRoot());
        allocateActivityTitle("Intercom");

        Load_Settings();

        call=findViewById(R.id.fab_sm_call);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Society_ContactActivity.this,SMCont_FormActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);

       /* String userId = firebaseAuth.getCurrentUser().getEmail();
        DocumentReference df = firebaseFirestore.collection("Society_contact").document(userId);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ((documentSnapshot.getString("isAdmin").equals("1")) || (documentSnapshot.getString("isAdmin").equals("2"))) {
                    Intent intent=new Intent(Society_ContactActivity.this,SMCont_FormActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0,0);
                }
                else {
                    call.hide();
                }
            }
        }); */
    }
});
searchView=findViewById(R.id.search_cont);
//searchView.clearFocus();
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return true;
    }
});
recyclerView=findViewById(R.id.recycle_scont);
databaseReference= FirebaseDatabase.getInstance().getReference("Society_contact");
databaseReference.keepSynced(true);
list=new ArrayList<>();
recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
adapter=new Society_Cont_adapter(this,list,this);
recyclerView.setAdapter(adapter);

databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
            Emergency_ContactModel model=dataSnapshot.getValue(Emergency_ContactModel.class);
            list.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }

    private void filterList(String Text){
       ArrayList<Emergency_ContactModel>  filterList=new ArrayList<>();
       for (Emergency_ContactModel filteredList :list){

           if (filteredList.getName().toLowerCase().contains(Text.toLowerCase())){
               filterList.add(filteredList);
           }
           if (filterList.isEmpty()){
               Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
           }else{
               adapter.setFiltedList(filterList);
           }
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
    public void onBackPressed() {
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

    @Override
    public void onNoteClick(int position) {
        list.get(position);
        Intent intent=new Intent(this,SMCViewActivity.class);
       // intent.putExtra("name",modelArrayList.get(position).getName());

        intent.putExtra("name",list.get(position).getName());
        intent.putExtra("contact",list.get(position).getContact());
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}