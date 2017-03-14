package alex.com.alexp2p.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;

import alex.com.alexp2p.R;
import alex.com.alexp2p.ui.CircleImg;
import alex.com.alexp2p.util.FileUtil;
import alex.com.alexp2p.util.SelectPicPopupWindow;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleActivity extends Activity {

    private static final int REQUESTCODE_TAKE = 0;
    private static final int REQUESTCODE_PICK = 1;
    @Bind(R.id.imageView)
    CircleImg mImageView;
    SelectPicPopupWindow menuWindow;
    private String urlpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_simple, null);
        setContentView(contentView);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.imageView)
    public void onClick(View view){
        menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
        menuWindow.showAtLocation(findViewById(R.id.nav_headerView),
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private String IMAGE_FILE_NAME = "P2Picon.png";
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                setPicToView(data);
                break;
            case REQUESTCODE_TAKE:
                setPicToView(data);
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
//            case REQUESTCODE_CUTTING:
//                if (data != null) {
//                    setPicToView(data);
//                }
//                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
//        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(this, "temphead.jpg", photo);
            mImageView.setImageDrawable(drawable);
        }
    }


}
