package com.anshul5404834.rm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Save_images {
    String hello;
    InterstitialAd mInterstitialAd;

    public String save_image(final ImageView view, final Context context, final String msg) {
        MobileAds.initialize(context,
                "ca-app-pub-8775257823411929~8119804698");
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-8775257823411929/2773885347");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final String[] imageFileName = new String[1];
        view.post(new Runnable() {
            @Override
            public void run() {
                //        Bitmap bitmap =view.getDrawingCache(true);
                Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
                String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());
                imageFileName[0] = timeStamp + ".jpeg";
                File storageDir = Environment.getExternalStorageDirectory();
                Log.d("hwaaaaaaaaaaatttttt", "run: " + storageDir.getAbsolutePath());
                File image_file = new File(storageDir, imageFileName[0]);
                if (!image_file.exists()) {
                    try {
                        image_file.createNewFile();
                        //  if(image_file.exists()){ Toast.makeText(context,"file_exist",Toast.LENGTH_SHORT).show();}
                        //   else { Toast.makeText(context,"fuck",Toast.LENGTH_SHORT).show();}
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                }
                try {
                    FileOutputStream fout = new FileOutputStream(image_file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);

                    //       Toast.makeText(context,String.valueOf(image_file.getAbsoluteFile().getTotalSpace()), Toast.LENGTH_SHORT).show();
                    fout.flush();
                    fout.close();

                    try {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            try {
                                //Toast.makeText(context, "i am not working ", Toast.LENGTH_SHORT).show();
                                Intent share = new Intent();
                                share.setAction(Intent.ACTION_SEND);
                                share.setType("image/*");
                                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Mitsumori", null);
                                Uri imageUri = Uri.parse(path);
                                share.putExtra(Intent.EXTRA_TEXT, msg);
                                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                                context.startActivity(Intent.createChooser(share, "Share via"));
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, "image couldnt be shared ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (Exception e) {
                    }


                } catch (Exception e) {
                    Toast.makeText(context, "i am not working ", Toast.LENGTH_SHORT);
                }
                final Bitmap bitmap1 = bitmap;
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        try {
                            Intent share = new Intent();
                            share.setAction(Intent.ACTION_SEND);
                            share.setType("image/*");
                            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap1, "Mitsumori", null);
                            Uri imageUri = Uri.parse(path);
                            share.putExtra(Intent.EXTRA_TEXT, msg);
                            share.putExtra(Intent.EXTRA_STREAM, imageUri);
                            context.startActivity(Intent.createChooser(share, "Share via"));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "image couldnt be shared ", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        });
        return hello;
    }


}

