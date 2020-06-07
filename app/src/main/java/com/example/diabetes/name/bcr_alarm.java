package com.example.diabetes.name;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class bcr_alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();

        Bundle extras = arg1.getExtras();
        int p = extras.getInt("when");
        Intent i = new Intent(arg0, alarm_alarm.class);
        i.putExtra("when2",p);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(i);
    }
}