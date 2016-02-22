package com.example.administrator.imgcomposition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Camera的开发，我们可以使用两类方法：一是借助Intent和MediaStroe调用系统Camera App程序来实现拍照和摄像功能，二是根据Camera API自写Camera程序。
 */
public class CameraPicActivity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private ImageView imageView;

    private Uri fileUri;
    private Uri outputMediaFileUri;
    private File outputMediaFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_pic);

        imageView = (ImageView)findViewById(R.id.imageView);
        findViewById(R.id.startCapturePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a file to save the picture
                fileUri = getOutputMediaFileUri();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用照相机
//                intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    public File getOutputMediaFile() {
        File mediaStorageDir;
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"myPicApp");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String tempStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath()+File.separator+"IMG_"+tempStamp+".jpg");
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dsc", "onActivityResult: requestCode: " + requestCode
                + ", resultCode: " + requestCode + ", data: " + data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if (RESULT_OK == resultCode){
                if (data!=null){
                    if (data.hasExtra("data")){
                        Bitmap bitmap = data.getParcelableExtra("data");
                        imageView.setImageBitmap(bitmap);
                    }
                }
                else {
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();

                    BitmapFactory.Options factory = new BitmapFactory.Options();
                    factory.inJustDecodeBounds=true;
                    BitmapFactory.decodeFile(fileUri.getPath(),factory);

                    int imgWidth = factory.outWidth;
                    int imgHeight = factory.outHeight;

                    int scaleFactor = Math.min(imgWidth/width,imgHeight/height);

                    factory.inJustDecodeBounds = false;
                    factory.inSampleSize = scaleFactor;
                    factory.inPurgeable = true;

                    Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),factory);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
