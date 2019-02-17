package com.xxl.module.media;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
@Route(path = IConstantMedia.MEDIA_MEDIA_ACTIVITY)
public class MediaActivity extends AppCompatActivity {

    private AudioCapture mAudioCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.activity_media_start_btn, R2.id.activity_media_stop_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.activity_media_start_btn) {
            RxPermissions rxPermissions = new RxPermissions(this);
            Disposable disposable = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO)
                    .observeOn(Schedulers.io())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            mAudioCapture = new AudioCapture();
                            mAudioCapture.startCapture();
                        } else {
                            Toast.makeText(this, "请开启录制权限！", Toast.LENGTH_SHORT).show();
                        }

                    });
        }else if (id == R.id.activity_media_stop_btn) {
            if (mAudioCapture != null) {
                mAudioCapture.stopCapture();
            }
        }
    }
}
