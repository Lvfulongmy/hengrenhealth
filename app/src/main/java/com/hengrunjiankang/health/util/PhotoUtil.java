package com.hengrunjiankang.health.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.applaction.MyApplication;
import com.hengrunjiankang.health.photo.SelectPhotoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hengrunjiankang.health.util.CommonUtils.resizeBitmap;

/**
 * Created by Administrator on 2017/8/14.
 */

public class PhotoUtil {
    private static int TAKEPHOTO=0;
    private static int PICKPHOTO;
    private static int PICKSOMEPHOTO=2;
    public static int REPICKFINISH=7;
    public static  int REPICKCANCEL=8;
    private int picWidth=500;
    private Activity mContext;
    private File tempFile;
    private DoBack mDoBack;
    public static int MaxSelect;
    public static LinkedHashMap<String,File> selectFile;
    public PhotoUtil(Activity context){
        mContext=context;
        InitGetPhotoWindow();
    }

    public static void setMaxSelect(int maxSelect) {
        MaxSelect = maxSelect;
    }

    public static void setSelectFile(LinkedHashMap<String, File> selectFile) {
        PhotoUtil.selectFile = selectFile;
    }

    public void setDoBack(DoBack doBack) {
        mDoBack = doBack;
    }

    public void onResult(int requestCode, int resultCode, int RESULT_OK){
        if (requestCode == TAKEPHOTO && resultCode ==RESULT_OK) {

            if (CommonUtils.hasSdcard()) {
                FileOutputStream stream = null;
                String path = tempFile.getPath();
                try {
                   Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    saveFile(bitmap);
                    bitmap = ImageCompressL(bitmap);
                    stream = new FileOutputStream(tempFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    stream.flush();
                    stream.close();
                    // cropPhoto(Uri.fromFile(tempFile));
//                    Uri uri = Uri.fromFile(tempFile);
//                    insertPic(uri);
                    mDoBack.doTakeBack(tempFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // Log.e("aaa",e.getMessage());
                }

                // img_path = tempFile.toString();
            } else {
                Toast.makeText(mContext, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT)
                        .show();
            }
            // if (data != null) {
            // Uri uri = data.getData();
            // cropPhoto(uri);
            // }

        } else if (requestCode == PICKPHOTO && resultCode == RESULT_OK) {
//            if (data != null) {
//                try {
//                    tempFile = File.createTempFile("temp", ".png",
//                            MyApplication.cacheDir);
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                Uri uri = data.getData();
//                try {
//                    bitmap = BitmapFactory.decodeStream(getContentResolver()
//                            .openInputStream(uri));
//                    saveFile(bitmap);
//                    bitmap = resizeBitmap(bitmap, 300);
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                FileOutputStream stream = null;
//
//                try {
//                    stream = new FileOutputStream(tempFile);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    stream.flush();
//                    stream.close();
//                    Uri uriFile = Uri.fromFile(tempFile);
//                    insertPic(uriFile);
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }

        } else if (requestCode == PICKSOMEPHOTO && resultCode == REPICKFINISH) {
//            ArrayList <Bitmap> bitmapsList=new ArrayList<>();
            ArrayList<File> fileList=new ArrayList<>();
            for (Map.Entry<String, File> entry : selectFile.entrySet()) {
                FileOutputStream stream = null;
                // try {
                //
                // } catch (IOException e1) {
                // // TODO Auto-generated catch block
                // e1.printStackTrace();
                // }

                // tempFile=new File(path);
                try {

                    String path = entry.getValue().getPath();
                    String suffix = path.substring(path.lastIndexOf("."));
                   tempFile = File.createTempFile("temp", suffix,
                            MyApplication.cacheDir);

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    if (suffix.equals(".gif")) {
//                        saveFileGif(path);
//                    } else {
//                        saveFile(bitmap);
//                    }
//                    if (bitmap.getWidth() > picWidth)
//                        bitmap = resizeBitmap(bitmap, picWidth);
                    bitmap = ImageCompressL(bitmap);
                    stream = new FileOutputStream(tempFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    stream.flush();
                    stream.close();
                    // cropPhoto(Uri.fromFile(tempFile));
//                    Uri uri = Uri.fromFile(tempFile);
//                    insertPic(uri);
//                    bitmapsList.add(bitmap);
                    fileList.add(tempFile);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // Log.e("aaa",e.getMessage());

                }
            }

            mDoBack.doPickBack(fileList);


        } else if (requestCode == PICKSOMEPHOTO && resultCode == REPICKCANCEL) {
        }
    }
    public PopupWindow windowTakePhoto;
    public void InitGetPhotoWindow() {
        View view = View.inflate(mContext, R.layout.layout_select_photo, null);
        Button bt1 = (Button) view.findViewById(R.id.btn_take_phot);
        Button bt2 = (Button) view.findViewById(R.id.btn_pick_phot);
        Button bt3 = (Button) view.findViewById(R.id.btn_cance);
        bt1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                // 判断存储卡是否可以用，可用进行存储
                if (CommonUtils.hasSdcard()) {

                    try {
                        tempFile = File.createTempFile("temp", ".png",
                                MyApplication.cacheDir);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(tempFile));
                }
                mContext.startActivityForResult(intent, TAKEPHOTO);
                windowTakePhoto.dismiss();
            }
        });
        // bt2.setOnClickListener(new OnClickListener() {
        //
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // Intent intent = new Intent(Intent.ACTION_PICK);
        // intent.setType("image/*");
        // startActivityForResult(intent, PICKPHOTO);
        // windowTakePhoto.dismiss();
        // }
        // });

        bt2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext,
                        SelectPhotoActivity.class);
                mContext.startActivityForResult(intent, PICKSOMEPHOTO);
                windowTakePhoto.dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                windowTakePhoto.dismiss();
            }
        });
        windowTakePhoto = new PopupWindow(view);
        windowTakePhoto.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        windowTakePhoto.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // setBackgroundDrawable(new BitmapDrawable());
        windowTakePhoto.setFocusable(true);
        windowTakePhoto.setOutsideTouchable(true);
        windowTakePhoto.setAnimationStyle(R.style.style_anim_for_bmenu);
        // setContentView(view);
    }
    /**
     * 保存图片
     *
     * @param bitmap
     */

    public void saveFile(Bitmap bitmap) {

        FileOutputStream stream = null;
//        if (bitmap.getWidth() > picWidth)
//            bitmap = resizeBitmap(bitmap,picWidth);
        Bitmap temp = ImageCompressL(bitmap);
        try {
            String suffix = tempFile.getPath().substring(
                    tempFile.getPath().lastIndexOf("."));

            File file = File.createTempFile("health", suffix,
                    MyApplication.cacheDir);

            stream = new FileOutputStream(file);
            temp.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
    private double maxLength=800;
    private Bitmap ImageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(maxLength * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
    public static interface DoBack{
        void  doPickBack(List<File> file);
        void  doTakeBack(File file);
    }
    public void selectPhoto(View view){
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
        if (windowTakePhoto != null)
            windowTakePhoto.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

}
