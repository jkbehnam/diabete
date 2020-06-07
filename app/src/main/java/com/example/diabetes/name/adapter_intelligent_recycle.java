package com.example.diabetes.name;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_intelligent_recycle extends RecyclerView.Adapter<adapter_intelligent_recycle.MyViewHolder> {
    private List<data_intelligent> data_services_list;
    String User_Path = "/data/data/com.behnam_b.bime_2/bime/";

    OnCardClickListner onCardClickListner;
    AlertDialog.Builder builder;
    View alert_dialog_delete;
    AlertDialog ald_delete;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name;
public CardView CV;
        public MyViewHolder(View view) {
            super(view);
            service_name = (TextView) view.findViewById(R.id.menu_txt);
CV=(CardView)view.findViewById(R.id.cardview_be_intelligent);
           // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();



        }
    }


    public adapter_intelligent_recycle(ArrayList<data_intelligent> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drug, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final data_intelligent data_service = data_services_list.get(position);


        holder.service_name.setText(data_service.getTitle());
        holder.CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, data_service.getId());
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
}