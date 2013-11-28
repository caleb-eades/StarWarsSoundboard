package com.foursevengames.starwarssounds.controller;

import com.foursevengames.starwarssounds.R;
import com.foursevengames.starwarssounds.util.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Context;
import android.widget.GridView;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.util.Log;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    Activity act = this;
    Context context = (Context) act;
    final SoundPool sp = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
    private MyItemClickListener click;
    public static int[] soundIds = new int[16];
    public static int[] sounds = {
        R.raw.c3po_yourfault, R.raw.chewbacca_01, R.raw.darthvader_failedme,
        R.raw.darthvader_poweroftheforce, R.raw.darthvader_yourfather, R.raw.hansolo_badfeeling,
        R.raw.jabba_laugh, R.raw.lightsaber_01, R.raw.lightsaber_03,
        R.raw.lightsaber_04, R.raw.luke_greetings, R.raw.luke_junk,
        R.raw.obiwan_stretchout, R.raw.obiwan_theforce, R.raw.r2d2_01,
        /*R.raw.yoda_doordonot, R.raw.yoda_whyareyouhere,*/ R.raw.leia_what
    };
    Integer[] images = {
        R.drawable.c3po, R.drawable.chewbacca, R.drawable.vader,
        R.drawable.vader, R.drawable.vader, R.drawable.solo,
        R.drawable.jabba, R.drawable.lightsaber, R.drawable.lightsaber,
        R.drawable.lightsaber, R.drawable.luke, R.drawable.luke,
        R.drawable.obiwan, R.drawable.obiwan, R.drawable.r2d2,
        /*R.drawable.yoda, R.drawable.yoda,*/ R.drawable.leia
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        GridView main = (GridView) findViewById(R.id.main_grid);
        ImageAdapter adapt = new ImageAdapter(context);
        //registerForContextMenu(main);
        adapt.giveImageIds(images);
        main.setAdapter(adapt);
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        (new LoadSounds()).execute();
        click = new MyItemClickListener();
        click.giveSoundPool(sp);
        click.giveAudioManager(audioManager);
        click.setContext(context);
        main.setOnItemClickListener(click);
    }

    private class LoadSounds extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Media...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < sounds.length; i++) {
                soundIds[i] = sp.load(context, sounds[i], 1);
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            click.giveSoundIds(soundIds);
        }
    }

/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) { 
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_set_ringtone:
                Ringtone ring = new Ringtone(item, getContentResolver(), act);
                ring.setAsRingtone();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { 
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case R.id.menu_about:
            Toast.makeText(context, "About was Selected, mobs", Toast.LENGTH_SHORT).show();
            return true;

        case R.id.menu_help:
            Toast.makeText(context, "Help was Selected, mobs", Toast.LENGTH_SHORT).show();
            return true;

        case R.id.menu_report_bug:
            Toast.makeText(context, "Report a bug was Selected, mobs", Toast.LENGTH_SHORT).show();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }*/
}
