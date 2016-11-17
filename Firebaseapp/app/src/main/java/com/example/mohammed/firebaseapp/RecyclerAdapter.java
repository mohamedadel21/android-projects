package com.example.mohammed.firebaseapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MOHAMMED on 10/13/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    Context c;
    List<CardviewModel> Data;
    LayoutInflater inflater;
    RecyclerAdapter(Context context, List<CardviewModel> data){
             this.Data=data;
        this.c=context;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.cardviewlayout,parent,false);
        MyviewHolder holder=new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyviewHolder holder, int position) {
        CardviewModel cardviewModel=Data.get(position);
        holder.setData(cardviewModel,position);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }
    public void removeItem(int position){
        Data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,Data.size());
        //notifyDataSetChanged();

    }
    public void addItem(int position,CardviewModel cardmodel){
        Data.add(position,cardmodel);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,Data.size());
        //notifyDataSetChanged();

    }

 class MyviewHolder extends RecyclerView.ViewHolder{
    ImageView companyLogo;
     Button applyButton;
     TextView titleTextview,addressTextview;
     TextView descTextview;

int postion;
     CardviewModel cardviewModel;
        MyviewHolder(View view){
    super(view);
            titleTextview=(TextView)view.findViewById(R.id.TiTle);
            descTextview=(TextView)view.findViewById(R.id.desc);
        applyButton=(Button)view.findViewById(R.id.applyButton);
            companyLogo=(ImageView)view.findViewById(R.id.actorImage);
            addressTextview=(TextView)view.findViewById(R.id.addres);

        }



     public void setData(final CardviewModel cardviewModel, int position) {
         descTextview.setText(cardviewModel.getCompanyName());
         titleTextview.setText(cardviewModel.getTitle());
         Picasso.with(c).load(cardviewModel.imageId).into(companyLogo);
        this.postion=position;
         this.cardviewModel=cardviewModel;
         addressTextview.setText(cardviewModel.getAddress());

         applyButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(cardviewModel.getApplyLink()));
                 c.startActivity(i);
             }
         });

     }



 }
}
