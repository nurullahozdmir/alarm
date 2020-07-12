package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.database.DatabaseHelper;
import com.example.alarm.helper.TinyDB;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private List<VeriModeli> arama_listesi;
    private AranacaklarAdapter aranacaklarAdapter;
    Button buttonEkle;

    private DatabaseHelper db;
    List<VeriModeli> telefonListesi=new ArrayList<VeriModeli>();
    ArrayAdapter<String> telefonListesiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        buttonEkle = findViewById(R.id.buttonEkle);

        db = new DatabaseHelper(this);

        arama_listesi = new ArrayList<VeriModeli>();

        telefonListesi = db.getTelefonListesi();

        recycler_view = findViewById(R.id.recyclerview);

        aranacaklarAdapter =  new AranacaklarAdapter(telefonListesi,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recycler_view.setLayoutManager(layoutManager);

        recycler_view.setAdapter(aranacaklarAdapter);

        aranacaklarAdapter.notifyDataSetChanged();


        int PERMISSION_ALL = 1;

        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        }

        buttonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Formekrani.class);
                startActivity(intent);
            }
        });


    }
    public static boolean hasPermissions(Context context,String[] permissions) {
        if (context != null &&  permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
