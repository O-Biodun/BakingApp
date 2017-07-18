package com.example.biodun.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExoplayerActivity extends AppCompatActivity {


    private SimpleExoPlayer mExoplayer;
    private  final String VIDEO_URL="video_url";
    private  final String STEP="step";
    private String videoUrl;

    //Media state variables

    private static final String AUTO_PLAY="autoplay";
    private static final String PLAY_BACK_POSITION="playback_position";
    private static final String CURRENT_WINDOW_INDEX="current_window_index";

    private Boolean autoplay=true;
    private long playbackPosition;
    private int currentWindow;


    @BindView(R.id.exoPlayer)
    SimpleExoPlayerView  mExoplayerView;
    @BindView(R.id.step_description_tv)
    TextView stepTv;
    @BindView(R.id.step_description)
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);
        ButterKnife.bind(this);

        getIntentAndSetVideoUrlAndVideoStep();
        checkIfInstanceIsSaved(savedInstanceState);




    }

    private void initializeExoPlayer(){

        mExoplayer= ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        mExoplayerView.setPlayer(mExoplayer);
        mExoplayer.setPlayWhenReady(autoplay);
        mExoplayer.seekTo(currentWindow,playbackPosition);
        MediaSource mediaSource=getMediaSource(Uri.parse(videoUrl));
        mExoplayer.prepare(mediaSource);

    }

    private MediaSource getMediaSource(Uri uri){
        String userAgent= Util.getUserAgent(this,"BakingApp");
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory=
                new DefaultHttpDataSourceFactory(userAgent);
        DefaultExtractorsFactory defaultExtractorsFactory=
                new DefaultExtractorsFactory();

        return new ExtractorMediaSource(uri,defaultHttpDataSourceFactory,
                defaultExtractorsFactory,null,null);


    }


    private void checkIfInstanceIsSaved( Bundle savedInstanceState){

        if (savedInstanceState!=null){
            autoplay=savedInstanceState.getBoolean(AUTO_PLAY,false);
           playbackPosition=savedInstanceState.getLong(PLAY_BACK_POSITION,0);
            currentWindow=savedInstanceState.getInt(CURRENT_WINDOW_INDEX,0);


        }


    }
    public void releasePlayer(){
        if(mExoplayer!=null){
            currentWindow=mExoplayer.getCurrentWindowIndex();
            playbackPosition=mExoplayer.getCurrentPosition();
            autoplay=mExoplayer.getPlayWhenReady();
            mExoplayer.release();
            mExoplayer=null;

        }



    }

    public void getIntentAndSetVideoUrlAndVideoStep(){
      Intent intent=getIntent();
      String videoUrlHolder=  intent.getStringExtra(VIDEO_URL);
        String step=intent.getStringExtra(STEP);
      if(videoUrlHolder!=null && step!=null){
          videoUrl=videoUrlHolder;
          stepTv.setText(step);
      }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mExoplayer==null  ){
            outState.putInt(CURRENT_WINDOW_INDEX,currentWindow);
            outState.putBoolean(AUTO_PLAY,autoplay);
            outState.putLong(PLAY_BACK_POSITION,playbackPosition);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT >23){
            initializeExoPlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23){
            releasePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int screenOrientation=this.getResources().getConfiguration().orientation;
        if(screenOrientation== Configuration.ORIENTATION_LANDSCAPE){
            showFullScreen();
            cardView.setVisibility(View.GONE);
            stepTv.setVisibility(View.GONE);
        }
        if(Util.SDK_INT<=23|| mExoplayer==null){
            initializeExoPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


        if(Util.SDK_INT<=23){
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void showFullScreen() {
        getSupportActionBar().hide();
        mExoplayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
