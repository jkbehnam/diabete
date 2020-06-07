package com.example.diabetes.name;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_alarm_message_recycle extends RecyclerView.Adapter<adapter_alarm_message_recycle.MyViewHolder> {
    private List<data_alarm_message> data_services_list;

    ViewGroup vg;

    OnCardClickListner onCardClickListner;
    AlertDialog.Builder builder;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public JustifiedTextView service_name;
        public WebView wv;
        public CardView CV;
        public ImageView iv;
        public MyViewHolder(View view) {
            super(view);
            service_name = (JustifiedTextView) view.findViewById(R.id.tv);
            wv = (WebView) view.findViewById(R.id.web);
iv=(ImageView)view.findViewById(R.id.heram);
            //


        }
    }


    public adapter_alarm_message_recycle(ArrayList<data_alarm_message> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_alarm_message, parent, false);

vg=parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final data_alarm_message data_service = data_services_list.get(position);
        String word = "در هرم غذایی";
        if (data_service.getMatn().contains(word)) {

holder.iv.setVisibility(View.VISIBLE);
        }
        word = "table";


        if (data_service.getMatn().contains(word)) {
            holder.service_name.setVisibility(View.GONE);
            holder.wv.setVisibility(View.VISIBLE);
            holder.wv.loadDataWithBaseURL(null, "<html dir=\"rtl\" lang=\"\"><body>" + data_service.getMatn() + "</body></html>", "text/html", "UTF-8", null);
        } else {

            holder.service_name.setText(Html.fromHtml(data_service.getMatn()));
        }

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