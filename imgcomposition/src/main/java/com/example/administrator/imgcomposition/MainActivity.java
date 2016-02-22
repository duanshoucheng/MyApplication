package com.example.administrator.imgcomposition;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageView originalImg;
    private ImageView imageView;

    //锟襟滑菜碉拷锟斤拷锟斤拷Activity锟斤拷锟斤拷
    private Button menuBtn;
    private Button btnImgCompositionMenu;
    private Button btnImgConvertGrayMenu;
    private Button btnImgScaleMenu;
    private Button btnImgRotateMenu;
    private Button btnImgScrewMenu;
    private Button btnImgReflectedMenu;
    private Button btnImgRoundedCornerMenu;
    private Button btnImgCropMenu;
    private Button btnActionMenu;
    private Button btnAction02Menu;
    private Button btnCameraMenu;
    private Button btnImgFilteMenu;

    //锟斤拷锟斤拷图片锟斤拷锟斤拷
    private Button btnImgscrawlMenu;

    private DrawerLayout mDrawerLayout;

    private int lastX;
    private int lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();

        initView();

    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        originalImg = (ImageView)findViewById(R.id.view);
        imageView = (ImageView) findViewById(R.id.iv);

        menuBtn = (Button) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(this);
        btnImgCompositionMenu = (Button) findViewById(R.id.btnImgCompositionMenu);
        btnImgCompositionMenu.setOnClickListener(this);
        btnImgConvertGrayMenu = (Button) findViewById(R.id.btnImgConvertGrayMenu);
        btnImgConvertGrayMenu.setOnClickListener(this);
        btnImgScaleMenu = (Button) findViewById(R.id.btnImgScaleMenu);
        btnImgScaleMenu.setOnClickListener(this);
        btnImgRotateMenu = (Button) findViewById(R.id.btnImgRotateMenu);
        btnImgRotateMenu.setOnClickListener(this);
        btnImgScrewMenu = (Button) findViewById(R.id.btnImgScrewMenu);
        btnImgScrewMenu.setOnClickListener(this);
        btnImgReflectedMenu = (Button) findViewById(R.id.btnImgReflectedMenu);
        btnImgReflectedMenu.setOnClickListener(this);
        btnImgRoundedCornerMenu = (Button) findViewById(R.id.btnImgRoundedCornerMenu);
        btnImgRoundedCornerMenu.setOnClickListener(this);
        btnImgscrawlMenu = (Button)findViewById(R.id.btnImgscrawlMenu);
        btnImgscrawlMenu.setOnClickListener(this);
        btnImgCropMenu = (Button)findViewById(R.id.btnImgCropMenu);
        btnImgCropMenu.setOnClickListener(this);
        btnActionMenu = (Button)findViewById(R.id.btnActionMenu);
        btnActionMenu.setOnClickListener(this);
        btnAction02Menu = (Button)findViewById(R.id.btnAction02Menu);
        btnAction02Menu.setOnClickListener(this);
        btnCameraMenu = (Button)findViewById(R.id.btnCameraMenu);
        btnCameraMenu.setOnClickListener(this);
        btnImgFilteMenu = (Button)findViewById(R.id.btnImgFilteMenu);
        btnImgFilteMenu.setOnClickListener(this);
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuBtn:
                Toast.makeText(MainActivity.this, "change", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnImgCompositionMenu:
                Bitmap resultCompositionImg = ImgUtil.compositionNewImgForWater(BitmapFactory.decodeResource(getResources(), R.drawable.bg),
                        BitmapFactory.decodeResource(getResources(), R.drawable.barcode));
                imageView.setImageBitmap(resultCompositionImg);
                break;
            case R.id.btnImgConvertGrayMenu:
                Bitmap resultGreyImg = ImgUtil.convertGreyImg02(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
                imageView.setImageBitmap(resultGreyImg);
                break;
            case R.id.btnImgScaleMenu:
//                Bitmap actionScaleImg = ImgUtil.actionScaleImg(BitmapFactory.decodeResource(getResources(),R.drawable.barcode));
//                imageView.setImageBitmap(actionScaleImg);
                BitmapDrawable mBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.barcode);
                imageView.setImageBitmap(ImgUtil.actionScaleImg(mBitmapDrawable.getBitmap(),80,80));
                break;
            case R.id.btnImgRotateMenu:
                BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.barcode);
                imageView.setImageBitmap(ImgUtil.actionRotatedImg(bitmapDrawable.getBitmap(), 45));
                break;
            case R.id.btnImgScrewMenu:
                BitmapDrawable screwBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.barcode);
                imageView.setImageBitmap(ImgUtil.actionScrewImg(screwBitmap.getBitmap(), 1.0f, 0.15f));
                break;
            case R.id.btnImgReflectedMenu:
                BitmapDrawable reflectedBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.barcode);
                imageView.setImageBitmap(ImgUtil.actionReflectedImg(reflectedBitmap.getBitmap()));
                break;
            case R.id.btnImgRoundedCornerMenu:
                Bitmap roundedCornerImg = ImgUtil.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg), 80);
                imageView.setImageBitmap(roundedCornerImg);
                break;
            case R.id.btnImgscrawlMenu:
                startActivity(new Intent(MainActivity.this, ImgScrawlActivity.class));
                break;
            case R.id.btnImgCropMenu:
                openAndroidCropTool();
                break;
            case R.id.btnActionMenu:
                startActivity(new Intent(MainActivity.this, GestureActivity.class));
                break;
            case R.id.btnAction02Menu:
                startActivity(new Intent(MainActivity.this,Gesture2Activity.class));
                break;
            case R.id.btnCameraMenu:
                startActivity(new Intent(MainActivity.this,CameraPicActivity.class));
                break;
            case R.id.btnImgFilteMenu:
                startActivity(new Intent(MainActivity.this,PhotoFilterActivity.class));
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void openAndroidCropTool() {
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())){
                    Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    if (null == cursor) {
                        Toast.makeText(MainActivity.this, "没有找到图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cursor.moveToNext();
                    final String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(bitmap);
//                    imageView.setImageURI(Uri.parse(path));
                }
            }
        }
    }
}
