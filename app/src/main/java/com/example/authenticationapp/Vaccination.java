package com.example.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Vaccination extends AppCompatActivity {

    //vaccination
    private EditText et_vname, et_date, et_des, et_got;
    public Button btn_Vadd, btn_Vshow, btn_addvaccines;

    FirebaseFirestore db;
    private String upvname, upvdate, upvdesc, upvgot, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        //vaccination
        et_vname = findViewById(R.id.et_vname);
        et_date = findViewById(R.id.et_date);
        et_des = findViewById(R.id.et_des);
        et_got = findViewById(R.id.et_got);

        btn_Vadd = findViewById(R.id.btn_Vadd);
        btn_Vshow = findViewById(R.id.btn_Vshow);
        btn_addvaccines = findViewById(R.id.btn_addvaccines);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            btn_Vadd.setText("Update");

            upvname = bundle.getString("upvname");
            upvdate = bundle.getString("upvdate");
            upvdesc = bundle.getString("upvdesc");
            upvgot = bundle.getString("upvgot");
            uid = bundle.getString("uid");

            et_vname.setText(upvname);
            et_date.setText(upvdate);
            et_des.setText(upvdesc);
            et_got.setText(upvgot);

        } else
            btn_Vadd.setText("Add");


        btn_Vshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Vaccination.this, ViewVaccines.class));
            }
        });

        btn_Vadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_vname.getText().toString();
                String date = et_date.getText().toString();
                String desc = et_des.getText().toString();
                String got = et_got.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uid;
                    updateToFireStore(id, name, date, desc, got);
                } else {
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, name, date, desc, got);
                }
            }

            private void updateToFireStore(String id, String name, String date, String desc, String got) {

                db.collection("Documents").document(id).update("name", name, "date", date, "desc", desc, "got", got).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Vaccination.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Vaccination.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Vaccination.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private void saveToFireStore(String id, String name, String date, String desc, String got) {

        if (!name.isEmpty() && !date.isEmpty() && !got.isEmpty()) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("date", date);
            map.put("description", desc);
            map.put("got", got);

            db.collection("Documents").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Vaccination.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Vaccination.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        } else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }


}