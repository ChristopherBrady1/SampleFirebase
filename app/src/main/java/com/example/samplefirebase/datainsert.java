package com.example.samplefirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class datainsert extends AppCompatActivity {
EditText txtname, txtage, txtnum, txtheight;
Button btnsave;
DatabaseReference reff;
Member member;
long maxid =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtname = (EditText)findViewById(R.id.txtname);
        txtage = (EditText)findViewById(R.id.txtage);
        txtnum = (EditText)findViewById(R.id.txtnum);
        txtheight = (EditText)findViewById(R.id.txtheight);
        btnsave = (Button)findViewById(R.id.btnsave);
        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(txtage.getText().toString().trim());
                Float height = Float.parseFloat(txtheight.getText().toString().trim());
                Long num = Long.parseLong(txtnum.getText().toString().trim());

                member.setName(txtname.getText().toString().trim());
                member.setAge(age);
                member.setHeight(height);
                member.setPhone(num);

                //can add a child to this using: reff.child("member1").setValue(member);
                //reff.push().setValue(member);

                reff.child(String.valueOf(maxid+1)).setValue(member);

                Toast.makeText(datainsert.this, "data inserted successfully", Toast.LENGTH_LONG).show();

            }
        });
    }
}
