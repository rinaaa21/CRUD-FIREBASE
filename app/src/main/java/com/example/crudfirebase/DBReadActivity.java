package com.example.crudfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBReadActivity extends AppCompatActivity {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Dosen> daftarDosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_read);

        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("dosen").addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            daftarDosen = new ArrayList<>();
            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                Dosen dosen = noteDataSnapshot.getValue(Dosen.class);
                dosen.setKey(noteDataSnapshot.getKey());

                daftarDosen.add(dosen);
            }
            adapter = new AdapterDosenRecyclerView(daftarDosen, DBReadActivity.this);
            rvView.setAdapter(adapter);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
        }
        });
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, DBReadActivity.class);
    }
}