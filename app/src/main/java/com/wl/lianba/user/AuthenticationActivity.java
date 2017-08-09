package com.wl.lianba.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wl.lianba.R;
import com.wl.lianba.config.Constant;
import com.wl.lianba.utils.SharedPrefrenceUtils;
import com.wl.lianba.utils.Tool;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/13.
 * 身份认证
 */
public class AuthenticationActivity extends BaseLoginActivity implements View.OnClickListener {

    private TextView mNameView;

    private TextView mNumView;

    private TextView show_or_hide_text;

    private LinearLayout the_style_of_authen_card;

    private ImageView personal_first_page;

    private LinearLayout personal_first_page_alpha_content;

    private ImageView personal_second_page;

    private LinearLayout personal_second_page_alpha_content;

    private LinearLayout newPos;

    private final String IMAGE_TYPE = "image/*";

    private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的

    private final int IMAGE_CODE2 = 1;   //这里的IMAGE_CODE是自己任意定义的

    private Bitmap mBitmap = null;

    private Bitmap mBitmap2 = null;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bitmap bit = (Bitmap) msg.obj;
                    personal_first_page.setImageBitmap(bit);
                    personal_first_page_alpha_content.setVisibility(View.VISIBLE);
                    personal_second_page.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    personal_second_page.setImageBitmap(bitmap);
                    personal_second_page_alpha_content.setVisibility(View.VISIBLE);
                default:
                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_third_space_authentication_layout);
        initView();

    }

    private void initView() {
        setupTitle(getString(R.string.person_identity));
        newPos = (LinearLayout) findViewById(R.id.bottom_next_position_anthentication_page);
        newPos.setOnClickListener(this);

        mNameView = (EditText) findViewById(R.id.name_edit);
        mNumView = (EditText) findViewById(R.id.edit_authentication_num);
        show_or_hide_text = (TextView) findViewById(R.id.show_or_hide_text);
        show_or_hide_text.setOnClickListener(this);
        the_style_of_authen_card = (LinearLayout) findViewById(R.id.the_style_of_authen_card);

        personal_first_page = (ImageView) findViewById(R.id.personal_first_page);
        personal_first_page.setOnClickListener(this);

        personal_first_page_alpha_content = (LinearLayout) findViewById(R.id.personal_first_page_alpha_content);
        personal_second_page = (ImageView) findViewById(R.id.personal_second_page);
        personal_second_page.setOnClickListener(this);

        personal_second_page_alpha_content = (LinearLayout) findViewById(R.id.personal_second_page_alpha_content);
        String firstCardNumImage = SharedPrefrenceUtils.getString(this, "firstCardNumImage", null);
        String secondCardNumImage = SharedPrefrenceUtils.getString(this, "secondCardNumImage", null);
        if (!TextUtils.isEmpty(firstCardNumImage)) {
            Bitmap bitmap = BitmapFactory.decodeFile(firstCardNumImage);
            if (bitmap != null) {
                personal_first_page.setImageBitmap(bitmap);
                personal_first_page_alpha_content.setVisibility(View.VISIBLE);
                personal_second_page.setVisibility(View.VISIBLE);
            }
        }
        if (!TextUtils.isEmpty(secondCardNumImage)) {
            Bitmap bitmap = BitmapFactory.decodeFile(secondCardNumImage);
            if (bitmap != null) {
                personal_first_page.setImageBitmap(bitmap);
                personal_first_page_alpha_content.setVisibility(View.VISIBLE);
                personal_second_page.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "ActivityResult resultCode error");
            return;
        }
        ContentResolver resolver = getContentResolver();
        if (requestCode == IMAGE_CODE) {
            final Uri originalUri = data.getData();
//            compressImage(originalUri);
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                if (Tool.isNeedCompress(mBitmap, Constant.IMAGE_MAX_SIZE_200KB)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap newBitmap = Tool.compressImage(mBitmap, Constant.IMAGE_MAX_SIZE_200KB);
                            Message msg =new Message();
                            msg.obj = newBitmap;
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    }).start();

                } else {
                    personal_first_page.setImageBitmap(mBitmap);
                    personal_first_page_alpha_content.setVisibility(View.VISIBLE);
                    personal_second_page.setVisibility(View.VISIBLE);
                    SharedPrefrenceUtils.saveString(AuthenticationActivity.this, "firstCardNumImage", mBitmap.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_CODE2) {
            final Uri uri = data.getData();
            try {
                mBitmap2 = MediaStore.Images.Media.getBitmap(resolver, uri);
                personal_second_page.setImageBitmap(Tool.compressImage(mBitmap2, Constant.IMAGE_MAX_SIZE_200KB));
                personal_second_page_alpha_content.setVisibility(View.VISIBLE);
                if (Tool.isNeedCompress(mBitmap2, Constant.IMAGE_MAX_SIZE_200KB)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = Tool.compressImage(mBitmap2, Constant.IMAGE_MAX_SIZE_200KB);
                            Message message = new Message();
                            message.obj = bitmap;
                            message.what = 2;
                            mHandler.sendMessage(message);
                        }
                    }).start();
                } else {
                    personal_second_page.setImageBitmap(mBitmap2);
                    personal_second_page_alpha_content.setVisibility(View.VISIBLE);
                    SharedPrefrenceUtils.saveString(AuthenticationActivity.this, "secondCardNumImage", uri.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_first_page:
                if (mBitmap != null && !mBitmap.isRecycled()) {
                    mBitmap.recycle();
                    mBitmap = null;
                    System.gc();
                }
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType(IMAGE_TYPE);
                startActivityForResult(getAlbum, IMAGE_CODE);
                break;
            case R.id.personal_second_page:
                if (mBitmap2 != null && !mBitmap2.isRecycled()) {
                    mBitmap2.recycle();
                    mBitmap2 = null;
                    System.gc();
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(IMAGE_TYPE);
                startActivityForResult(intent, IMAGE_CODE2);
                break;
            case R.id.bottom_next_position_anthentication_page:
                Intent intent1 = new Intent(this,SelectPayMethodActivity.class);
                startActivity(intent1);
                break;
            case R.id.show_or_hide_text:
                if (show_or_hide_text.getText().equals(getString(R.string.hide_case))) {
                    show_or_hide_text.setText(getString(R.string.show_case));
                    the_style_of_authen_card.setVisibility(View.GONE);
                } else {
                    show_or_hide_text.setText(getString(R.string.hide_case));
                    the_style_of_authen_card.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
