package com.roomdb.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roomdb.example.FaunaActivity;
import com.roomdb.example.R;
import com.roomdb.example.utils.FaunaAddingData;
import com.roomdb.example.utils.ItemClick;

import java.util.ArrayList;

public class FaunaAdapter extends RecyclerView.Adapter<FaunaAdapter.ViewHolder>{
    Context context;
    String[]    imageTitles;
    int[] images;
    ItemClick itemClickListener;

    // Constructor for initialization
    public FaunaAdapter(Context context,String[] imageTitles,int[] images) {
        this.context = context;
        this.imageTitles = imageTitles;
        this.images=images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageitem, parent, false);

        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TypeCast Object to int type

        holder.images.setImageResource(images[position]);
        holder.txtview.setText(imageTitles[position]);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent faunaItent=new Intent(context, FaunaAddingData.class);
                faunaItent.putExtra("textValue",imageTitles[position]);
                context.startActivity(faunaItent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns number of items currently available in Adapter
        return images.length;

    }

  /*  @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());

    }*/

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView txtview;
        public ViewHolder(View view) {
            super(view);
            images = (ImageView) view.findViewById(R.id.imageView);
            txtview= (TextView) view.findViewById(R.id.txtview);


        }
    }

    //SHALL BE CALLED OUTSIDE
    public void serItemClickListener(ItemClick ic)

    {
        this.itemClickListener=ic;
    }
}
