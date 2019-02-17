package com.xxl.module.media.record;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by xxl on 2018/11/12.
 *
 * Description :
 */

public class MediaCapture {
    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_AUDIO_FORMAT = MediaRecorder.OutputFormat.DEFAULT;
    private static final int DEFAULT_AUDIO_ENCODER = MediaRecorder.AudioEncoder.DEFAULT;
    private MediaRecorder mMediaRecorder;

    public MediaCapture() {
        if (mMediaRecorder == null) {
            recordeConfig();
        }
    }

    private void recordeConfig() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"test.wav");
        mMediaRecorder.setAudioSource(DEFAULT_SOURCE);
        mMediaRecorder.setAudioSamplingRate(DEFAULT_SAMPLE_RATE);
        mMediaRecorder.setOutputFormat(DEFAULT_AUDIO_FORMAT);
        mMediaRecorder.setAudioEncoder(DEFAULT_AUDIO_ENCODER);
        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                Log.e("aaaa", "onInfo: " );
            }
        });
    }


    public void startCapture() {
        if (mMediaRecorder == null) {
            recordeConfig();
        }
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void stopCapture() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
}
