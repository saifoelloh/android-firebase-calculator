package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Pertambahan> pertambahans = new ArrayList<>();
    private RecyclerView recyclerView;
    private PertambahanAdapter pertambahanAdapter;
    private Pertambahan pertambahan = new Pertambahan();

    private EditText first, second;
    private RadioButton tambah, kurang, kali, bagi;
    private Button submit, show;

    private RecyclerView.LayoutManager manager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first = (EditText) findViewById(R.id.input_satu);
        second = (EditText) findViewById(R.id.input_dua);
        tambah = (RadioButton) findViewById(R.id.btn_tambah);
        kurang = (RadioButton) findViewById(R.id.btn_kurang);
        kali = (RadioButton) findViewById(R.id.btn_kali);
        bagi = (RadioButton) findViewById(R.id.btn_bagi);
        submit = (Button) findViewById(R.id.btn_submit);
        show = (Button) findViewById(R.id.btn_show);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        manager = new LinearLayoutManager(getApplicationContext());
        pertambahanAdapter = new PertambahanAdapter(pertambahans);

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pertambahanAdapter);

        getData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    protected void addData() {
        Map<String, Object> hitung = new HashMap<>();
        Integer temp = 0;
        pertambahan.setFirst(Integer.parseInt(first.getText().toString()));
        pertambahan.setSecond(Integer.parseInt(second.getText().toString()));
        if (tambah.isChecked()) {
            pertambahan.setOperator("+");
            temp = pertambahan.getFirst() + pertambahan.getSecond();
            pertambahan.setResult(Float.parseFloat(temp.toString()));
        } else if (kurang.isChecked()) {
            pertambahan.setOperator("-");
            temp = pertambahan.getFirst() - pertambahan.getSecond();
            pertambahan.setResult(Float.parseFloat(temp.toString()));
        } else if (kali.isChecked()) {
            pertambahan.setOperator("*");
            temp = pertambahan.getFirst() * pertambahan.getSecond();
            pertambahan.setResult(Float.parseFloat(temp.toString()));
        } else {
            pertambahan.setOperator("/");
            temp = pertambahan.getFirst() / pertambahan.getSecond();
            pertambahan.setResult(Float.parseFloat(temp.toString()));
        }

        hitung.put("first", pertambahan.getFirst());
        hitung.put("second", pertambahan.getSecond());
        hitung.put("operator", pertambahan.getOperator());
        hitung.put("result", pertambahan.getResult());

        db.collection("hitung")
                .add(hitung)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("message", "Sukses menambah data dgn id " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("message", "Gagal menambah data");
                    }
                });

        getData();
    }

    protected void getData() {
        db.collection("hitung")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot hitung : task.getResult()) {
                                Pertambahan tambah = new Pertambahan();
                                tambah.setFirst(Integer.parseInt(hitung.get("first").toString()));
                                tambah.setSecond(Integer.parseInt(hitung.get("second").toString()));
                                tambah.setOperator((String) hitung.get("operator").toString());
                                tambah.setResult(Float.parseFloat(hitung.get("result").toString()));
                                pertambahans.add(tambah);
                            }
                        }
                    }
                });
    }
}
