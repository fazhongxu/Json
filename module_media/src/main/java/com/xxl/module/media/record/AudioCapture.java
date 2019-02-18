package com.xxl.module.media.record;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by xxl on 2019/2/17
 * <p>
 * Description :
 */
public class AudioCapture {
    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private AudioRecord mAudioRecord;
    private int mMinBufferSize = 0;
    private static final String TAG = AudioCapture.class.getSimpleName();
    private Thread mCaptureThread;
    private boolean mIsRecord = true;

    private AudioCapture() {
        recordConfig();
    }

    private static class Holder {
        private static final AudioCapture INSTANCE = new AudioCapture();
    }

    public static AudioCapture getInstance() {
        return Holder.INSTANCE;
    }

    private void recordConfig() {
        mMinBufferSize = AudioRecord.getMinBufferSize(DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);
        mAudioRecord = new AudioRecord(DEFAULT_SOURCE, DEFAULT_SAMPLE_RATE,
                DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT, mMinBufferSize);
    }

    public void startRecord() {
        if (mAudioRecord == null) {
            recordConfig();
        }
        if (mAudioRecord.getRecordingState() == AudioRecord.STATE_UNINITIALIZED) { // 初始化失败
            Log.e(TAG,"初始化失败！");
            return;
        }

        if (mAudioRecord.getRecordingState() == AudioRecord.STATE_UNINITIALIZED) { // 已经开始录制了
            Log.e(TAG,"已经开始录制～");
            return;
        }

        mIsRecord = true;
        mAudioRecord.startRecording();
        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();

    }

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {
            while (mIsRecord && !mCaptureThread.isInterrupted() && mAudioRecord !=null) {
                byte[] buffer = new byte[mMinBufferSize];
                int read = mAudioRecord.read(buffer, 0, mMinBufferSize); // 取出数据

                if (read == AudioRecord.ERROR_INVALID_OPERATION) {
                    Log.e(TAG, "Error ERROR_INVALID_OPERATION");
                } else if (read == AudioRecord.ERROR_BAD_VALUE) {
                    Log.e(TAG, "Error ERROR_BAD_VALUE");
                } else {
                    Log.e(TAG, "run: " + read);
                }
            }
        }
    }

    public void stopRecord() {
        if (mAudioRecord != null) {
            if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) { // 正在录音
                mAudioRecord.stop();
            }
            mAudioRecord.release();
            mCaptureThread.interrupt();
            mIsRecord = false;
        }
    }


}
