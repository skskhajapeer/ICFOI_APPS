package com.roomdb.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.roomdb.example.adapter.FaunaAdapter;
import com.roomdb.example.model.FuanaData;

import java.util.ArrayList;
import java.util.Arrays;

public class

SelectFauna extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<FuanaData> faunadata;

    // Using ArrayList to store images data
    int[] images={R.drawable.ic_bird, R.drawable.ic_mammals, R.drawable.ic_reptile,
            R.drawable.ic_tadpole, R.drawable.ic_butterfly, R.drawable.ic_dragonfly,R.drawable.ic_moths,R.drawable.ic_spider,R.drawable.birds,R.drawable.ic_fly,R.drawable.ic_bee,R.drawable.ic_bee,R.drawable.ic_bee};
    String[] names = {"Birds", "Mammals", "Reptiles",
            "Amphibians", "Butterflies", "Dragonefies & Damsefllies","Moths",
            "Arachmids","Flies","Bees","Others","Fishes","Fishes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagelayout);

        // Getting reference of recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

//        // Setting the layout as Staggered Grid for vertical orientation
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

//        LinearLayoutManager linearLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.V,false);
//        recyclerView.setLayoutManager(linearLayoutManager);

        // Sending reference and data to Adapter
        FaunaAdapter adapter = new FaunaAdapter(SelectFauna.this,names,images);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }
}