package com.example.diabetes.name;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class alarm_alarm extends AppCompatActivity {
    int n = 0;
    MediaPlayer mp;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_alarm);


        //---------------------------
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mp = MediaPlayer.create(alarm_alarm.this, R.raw.ring1);
        TextView tv=(TextView)findViewById(R.id.tv_alarm_show);
        Intent i=getIntent();
        String s ="";
        switch ( i.getExtras().getInt("when2")%10) {
            case 0:
                s = "صرف صبحانه";
                break;
            case 1:
                s = "صرف نهار";
                break;
            case 2:
                s = "صرف شام";
                break;
            case 3:
                s = "خواب ";
                break;


        }
        if( i.getIntExtra("when2",0)<20)
        tv.setText("زمان اندازه گیری قند خون قبل از"+s);
        else tv.setText("زمان اندازه گیری قند خون بعد از"+s);
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = this.getAssets().open("profile_info.db");

                OutputStream out = new FileOutputStream(User_Path + "profile_info.db");

                int read;
                byte[] buffer = new byte[1024];

                while ((read = in.read(buffer)) != -1) {

                    out.write(buffer, 0, read);

                }

                in.close();
                out.close();


            } catch (Exception exp) {


            }
        } else {
        }
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor  cr = db.rawQuery("select * from option_tb", null);
        cr.moveToFirst();
        if ((cr.getInt(0) == 0)) {
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
            mp.setVolume(currentVolume,currentVolume);}

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {



                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (n < 3) {
                        mp.start();
                        n++;
                    }
                }
            });
            mp.start();



        Button btnstop=(Button)findViewById(R.id.stopring);
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                finish();
            }
        });
mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
    @Override
    public void onSeekComplete(MediaPlayer mp) {

        mp.start();
    }
});

    }
    @Override
    public void onBackPressed() {
        mp.pause();
        finish();
    }

}
