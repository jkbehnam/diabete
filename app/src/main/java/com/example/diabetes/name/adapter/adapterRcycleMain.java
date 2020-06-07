package com.example.diabetes.name.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.diabetes.name.R;
import com.example.diabetes.name.objects.FirstMenu;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterRcycleMain extends RecyclerView.Adapter<adapterRcycleMain.MyViewHolder> {
    private List<FirstMenu> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        CardView cv;
        TextView tv;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);

            iv = (ImageView) view.findViewById(R.id.item_game_img);
            tv = (TextView) view.findViewById(R.id.item_game_tv);
            cv = (CardView) view.findViewById(R.id.item_game_cv);
        }
    }


    public adapterRcycleMain(ArrayList<FirstMenu> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_event, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FirstMenu data_service = data_services_list.get(position);

        holder.tv.setText(data_service.getTitle());
       // holder.iv.setImageResource(R.drawable.transaction);
        Glide.with(context).load(getImage(data_service.getImg())).into(holder.iv);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
}