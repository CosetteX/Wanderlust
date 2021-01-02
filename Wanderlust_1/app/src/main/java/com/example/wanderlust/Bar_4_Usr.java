package com.example.wanderlust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.wanderlust.activity.InsertAttendanceInfoActivity;
import com.example.wanderlust.utils.BaseDialog;
import com.example.wanderlust.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;


public class Bar_4_Usr extends Activity {

    String name=null;
    EditText editText = null;
    Button btn_name=null;
    Button btn_head=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        setContentView(R.layout.bar_4_usr);
        editText = findViewById(R.id.bar_4_name);
        editText.setText(name);
        btn_name = findViewById(R.id.bar_4_btn_name);
        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                Log.e("name",name);
                Intent i = new Intent();
                i.putExtra("name", name);
                setResult(1, i);
                finish();
            }
        });

        btn_head = findViewById(R.id.bar_4_btn_head);
        btn_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraDialog();

            }
        });
    }

    private void showCameraDialog() {
        BaseDialog baseDialog = BaseDialog.getInstance();
        if (baseDialog != null) {
            final Dialog dialog = baseDialog.showDialog(this, R.layout.photo_dialog_layout);
            dialog.findViewById(R.id.take_photo_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PictureSelector.create(Bar_4_Usr.this)
                            .openCamera(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .compress(true)
                            .minimumCompressSize(100)
                            .forResult(PictureConfig.REQUEST_CAMERA);
                }
            });
            dialog.findViewById(R.id.pick_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PictureSelector.create(Bar_4_Usr.this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .isWeChatStyle(true)
                            .selectionMode(PictureConfig.SINGLE)// 单选
                            .isZoomAnim(false)
                            .previewImage(false)
                            .compress(true)
                            .minimumCompressSize(100)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            });
            dialog.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String selectUrl = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            Intent i = new Intent();
            i.putExtra("url", selectUrl);
            setResult(2, i);
            finish();
        }
    }

}
