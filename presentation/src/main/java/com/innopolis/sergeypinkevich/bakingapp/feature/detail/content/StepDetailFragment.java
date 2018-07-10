package com.innopolis.sergeypinkevich.bakingapp.feature.detail.content;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements StepDetailView, ExoPlayer.EventListener {

    public static final String TAG = StepDetailFragment.class.getSimpleName();
    public static final String STEP_KEY = "step";
    public static final String PLAYER_POSITION = "player_position";

    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.previous)
    Button previous;
    @BindView(R.id.next)
    Button next;

    private SimpleExoPlayer player;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaSessionCompat mediaSession;
    private long position;

    @Inject
    @InjectPresenter
    StepDetailPresenter presenter;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        position = player.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        player.seekTo(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        BaseApp.component.inject(this);
        super.onActivityCreated(savedInstanceState);

        initializeMediaSession();
        initializePlayer();
        initializeDescription();
        initializeButtons();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter.attachView(this);
        presenter.handleCurrentStep(getArguments());

        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(PLAYER_POSITION);
            player.seekTo(position);
        }
    }

    private void initializeButtons() {
        previous.setOnClickListener(v -> presenter.previousStep(getArguments()));
        next.setOnClickListener(v -> presenter.nextStep(getArguments()));
    }

    private void initializeDescription() {
        description.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showVideo(String url) {
        if (url.isEmpty()) {
            playerView.setVisibility(View.GONE);
        } else {
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void showMessageAboutLastStep() {
        Toast.makeText(getContext(), getString(R.string.last_step), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageAboutFirstStep() {
        Toast.makeText(getContext(), getString(R.string.first_step), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPlayer() {
        playerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayer() {
        playerView.setVisibility(View.GONE);
    }

    /**
     * Initialize ExoPlayer.
     */
    private void initializePlayer() {
        if (player == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);

            // Set the ExoPlayer.EventListener to this activity.
            player.addListener(this);
        }
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession.setActive(true);
    }

    // ExoPlayer Event Listeners


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mediaSession.setActive(false);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }
}
