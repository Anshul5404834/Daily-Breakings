package com.anshul5404834.rm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Appearance extends AppCompatActivity {
    public String imageFileName;
    String username;
    ImageButton solid_colour;
    ImageButton draw_external;
    ImageButton font_size_body;
    ImageButton font_size;
    ImageButton upload_image;
    ImageButton fonts;
    ListView listView;
    List<Integer> size;
    List<Integer> colour;
    List<Integer> drawablelist;
    List<Typeface> typefaceArrayList;
    int[] var;
    int[] size_var;
    int[] colour_var;
    Typeface[] typeface_var;
    Paint bodypaint;
    Paint headpaint;
    private String body;
    private String head;
    private String message;
    private ImageView background_image;
    private InterstitialAd mInterstitialAd;

    public static int convertToPixels(Context context, int nDP) {
        final float conversionScale = context.getResources().getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this,
                "ca-app-pub-8775257823411929~8119804698");
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-8775257823411929/2773885347");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        setContentView(R.layout.photo_layout);
        font_size = findViewById(R.id.font_size);

        font_size_body = findViewById(R.id.font_size_body);
        upload_image = findViewById(R.id.upload_image);
        imageFileName = "cache";
        solid_colour = findViewById(R.id.solid_colour);

        draw_external = findViewById(R.id.draw_external);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                try {
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    save_image(background_image, Appearance.this);
                    Intent intent = new Intent(Appearance.this, all_stuff.class);
                    intent.putExtra("a", 12);
                    intent.putExtra("mess", getIntent().getStringExtra("m"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please log in to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Appearance.this, Authorization.class));
                }

            }

        });

        fonts = findViewById(R.id.fonts);
        background_image = findViewById(R.id.imageView);
        background_image.setImageResource(R.drawable.a4);
        message = getIntent().getStringExtra("message");
        listView = findViewById(R.id.listview);
        body = getIntent().getStringExtra("BODY");
        head = getIntent().getStringExtra("HEAD");
        headpaint = new Paint();
        bodypaint = new Paint();
        size = new ArrayList<>();
        colour = new ArrayList<>();
        drawablelist = new ArrayList<>();
        typefaceArrayList = new ArrayList<>();
        colour.add(Color.DKGRAY);
        colour.add(Color.BLACK);
        colour.add(Color.WHITE);
        colour.add(Color.RED);
        colour.add(Color.GRAY);
        colour.add(Color.YELLOW);
        colour.add(Color.rgb(75, 0, 130));
        colour.add(Color.rgb(255, 255, 240));
        colour.add(Color.rgb(219, 112, 147));
        colour.add(Color.rgb(128, 128, 0));
        colour.add(Color.rgb(85, 107, 47));
        colour.add(Color.rgb(0, 128, 128));
        colour.add(Color.rgb(255, 255, 51));
        colour.add(Color.rgb(139, 69, 19));
        colour.add(Color.rgb(250, 235, 215));
        colour.add(Color.rgb(255, 105, 180));
        colour.add(Color.rgb(255, 0, 255));
        colour.add(Color.rgb(65, 105, 225));
        colour.add(Color.rgb(186, 85, 211));
        colour.add(Color.rgb(123, 104, 238));
        colour.add(Color.rgb(30, 144, 255));
        colour.add(Color.rgb(25, 25, 112));
        colour.add(Color.rgb(224, 255, 255));
        colour.add(Color.rgb(47, 79, 79));
        colour.add(Color.rgb(0, 255, 127));
        colour.add(Color.rgb(124, 252, 0));
        colour.add(Color.rgb(255, 215, 0));
        colour.add(Color.rgb(255, 127, 80));
        colour.add(Color.BLUE);
        colour.add(Color.GREEN);
        colour.add(Color.MAGENTA);
        colour.add(Color.CYAN);
        drawablelist.add(R.drawable.a2);
        drawablelist.add(R.drawable.a3);
        drawablelist.add(R.drawable.a4);
        drawablelist.add(R.drawable.a5);
        drawablelist.add(R.drawable.a6);
        drawablelist.add(R.drawable.a7);
        drawablelist.add(R.drawable.a8);
        drawablelist.add(R.drawable.a9);
        drawablelist.add(R.drawable.a10);
        drawablelist.add(R.drawable.a11);
        drawablelist.add(R.drawable.a12);
        drawablelist.add(R.drawable.c2);
        drawablelist.add(R.drawable.c3);
        drawablelist.add(R.drawable.c4);
        drawablelist.add(R.drawable.c5);
        drawablelist.add(R.drawable.c7);
        size.add(190);
        size.add(180);
        size.add(170);
        size.add(160);
        size.add(150);
        size.add(140);
        size.add(130);
        size.add(120);
        size.add(110);
        size.add(100);
        size.add(95);
        size.add(90);
        size.add(85);
        size.add(80);
        size.add(75);
        size.add(70);
        size.add(60);
        size.add(50);
        size.add(40);
        size.add(30);
        size.add(25);
        size.add(20);
        size.add(15);
        size.add(10);
        size.add(9);
        size.add(8);
        size.add(7);
        size.add(6);
        size.add(5);
        size.add(4);
        size.add(3);
        typefaceArrayList.add(Typeface.DEFAULT_BOLD);
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "AguafinaScript-Regular.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "AlexBrush-Regular.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Amatic-Bold.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "AmaticSC-Regular.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "FFF_Tusj.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "GreatVibes-Regular.otf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Lato-BlackItalic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Lato-Thin.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "OpenSans-ExtraBoldItalic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Oswald-BoldItalic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "D3 Roadsterism Italic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dael Calligraphy.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Damn the Man.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "DanceStep.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Data Transfer.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Davis.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dazey.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Deathhead KeltCaps.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Decorina.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Densmore.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dick Lucas.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Die Nasty.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Diehl Deco - Alts.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "DirtyBaker'sDozen.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Disco Inferno.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "discotech.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Disgusting Behavior.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "distracted musician.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dogs on Mars_.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dot.com.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Dot2Dot.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Oswald-HeavyItalic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Oswald-Demi-BoldItalic.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Oswald-Stencil.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Pacifico.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Quicksand-BoldItalic.otf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Quicksand-Italic.otf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Quicksand_Dash.otf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "SEASRN__.ttf"));
        typefaceArrayList.add(Typeface.createFromAsset(getAssets(), "Windsong.ttf"));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        colour_var = new int[]{1};
        var = new int[]{0};

        size_var = new int[]{7};

        typeface_var = new Typeface[1];
        int background_int = 1;
        //////////////////////////////////////////////paint initialization

        headpaint.setTextAlign(Paint.Align.CENTER);
        bodypaint.setColor(colour.get(1));
        headpaint.setColor(colour.get(1));
        bodypaint.setTextAlign(Paint.Align.CENTER);
        headpaint.setTextSize(convertToPixels(getApplicationContext(), size.get(size_var[0])));
        bodypaint.setTextSize(convertToPixels(getApplicationContext(), size.get(size_var[0])));
        bodypaint.setTypeface(typefaceArrayList.get(1));
        headpaint.setTypeface(typefaceArrayList.get(1));


        font_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new adapters(getApplicationContext(), size));

                Toast.makeText(getApplicationContext(), "Heading Sizes", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        size_var[0] = size.get(position);
                        headpaint.setTextSize(convertToPixels(getApplicationContext(), size_var[0]));
                        background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
                    }
                });
            }
        });

        font_size.callOnClick();
        font_size_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new adapters(getApplicationContext(), size));

                Toast.makeText(getApplicationContext(), "Body Sizes", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        size_var[0] = size.get(position);
                        bodypaint.setTextSize(convertToPixels(getApplicationContext(), size_var[0]));
                        background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
                    }
                });
            }
        });
        fonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new typeface_adapter(getApplicationContext(), typefaceArrayList));

                Toast.makeText(getApplicationContext(), "Fonts", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        typeface_var[0] = typefaceArrayList.get(position);
                        bodypaint.setTypeface(typeface_var[0]);
                        headpaint.setTypeface(typeface_var[0]);
                        background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
                    }
                });
            }
        });
        solid_colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new adapters(getApplicationContext(), colour));

                Toast.makeText(getApplicationContext(), "Colors", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        colour_var[0] = colour.get(position);
                        bodypaint.setColor(colour_var[0]);
                        headpaint.setColor(colour_var[0]);

                        background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
                    }
                });
            }
        });
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                            save_image(background_image, Appearance.this);
                            Intent intent = new Intent(Appearance.this, all_stuff.class);
                            intent.putExtra("a", 12);
                            intent.putExtra("mess", getIntent().getStringExtra("m"));
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Please log in to continue", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Appearance.this, Authorization.class));
                        }
                    }
                } catch (Exception e) {
                }


                Log.e("save image tag", "hello");
                //  startActivity(intent);
                // Save_images save_images= new Save_images();
                //  save_images.save_image(background_image,getApplicationContext());

            }
        });
        draw_external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Choose from Gallery", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                try {
                    username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                } catch (Exception e) {
                    username = "username_is_null";
                }
                if (!username.equals("username_is_null")) {
                    startActivityForResult(Intent.createChooser(intent, "Complete action using "), 2112);
                } else {
                    Toast.makeText(getApplicationContext(), "Login_first", Toast.LENGTH_SHORT).show();
                    Intent intent123 = new Intent(Appearance.this, Authorization.class);
                    startActivity(intent123);
                }
            }
        });


        background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
        background_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var[0] += 1;
                if (var[0] == drawablelist.size()) {
                    var[0] = 0;
                }
                background_image.setImageDrawable(writeTextOnDrawable(null, drawablelist.get(var[0]), head, body, headpaint, bodypaint));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2112) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                //last path segment because images location detail comes in format like /fies/android/obb/anshul.png where anshul is the name of photo
                //compressing image
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                background_image.setClickable(false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //here you can choose quality factor in third parameter(ex. i choosen 25)
                bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                final Bitmap finalBmp = bmp;
                background_image.setImageDrawable(writeTextOnDrawable(finalBmp, 0, head, body, headpaint, bodypaint));
//            background_image.setImageDrawable(writeTextOnDrawable_own(bmp,head,body,size_int,colour[j[0]]));
                // adding to data base
                font_size.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Heading Sizes", Toast.LENGTH_SHORT).show();
                        listView.setAdapter(new adapters(getApplicationContext(), size));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                size_var[0] = size.get(position);
                                headpaint.setTextSize(convertToPixels(getApplicationContext(), size_var[0]));
                                background_image.setImageDrawable(writeTextOnDrawable(finalBmp, 0, head, body, headpaint, bodypaint));
                            }
                        });
                    }
                });
                font_size_body.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Body Sizes", Toast.LENGTH_SHORT).show();
                        listView.setAdapter(new adapters(getApplicationContext(), size));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                size_var[0] = size.get(position);
                                bodypaint.setTextSize(convertToPixels(getApplicationContext(), size_var[0]));
                                background_image.setImageDrawable(writeTextOnDrawable(finalBmp, 0, head, body, headpaint, bodypaint));
                            }
                        });
                    }
                });
                fonts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Fonts", Toast.LENGTH_SHORT).show();
                        listView.setAdapter(new typeface_adapter(getApplicationContext(), typefaceArrayList));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                typeface_var[0] = typefaceArrayList.get(position);
                                bodypaint.setTypeface(typeface_var[0]);
                                headpaint.setTypeface(typeface_var[0]);
                                background_image.setImageDrawable(writeTextOnDrawable(finalBmp, 0, head, body, headpaint, bodypaint));
                            }
                        });
                    }
                });
                solid_colour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "Colors", Toast.LENGTH_SHORT).show();
                        listView.setAdapter(new adapters(getApplicationContext(), colour));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                colour_var[0] = colour.get(position);
                                bodypaint.setColor(colour_var[0]);
                                headpaint.setColor(colour_var[0]);
                                background_image.setImageDrawable(writeTextOnDrawable(finalBmp, 0, head, body, headpaint, bodypaint));
                            }
                        });
                    }
                });

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private BitmapDrawable writeTextOnDrawable(Bitmap b, int drawableId, String head, String body, Paint headpaint, Paint bodypaint) {
        Bitmap bm;
        if (b == null) {
            bm = BitmapFactory.decodeResource(getResources(), drawableId)
                    .copy(Bitmap.Config.ARGB_8888, true);
        } else {
            bm = b.copy(Bitmap.Config.ARGB_8888, true);
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bm);
        bitmapDrawable.setAlpha(255
        );


        //   headpaint.getTextBounds(head, 2, head.length(), textRect);

        Canvas canvas = new Canvas(bitmapDrawable.getBitmap());
        headpaint.setShadowLayer(5f, 5f, 5f, Color.LTGRAY);
        bodypaint.setShadowLayer(5f, 5f, 5f, Color.LTGRAY);


        //If the text is bigger than the canvas , reduce the font size
        // if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
        //     headpaint.setTextSize(convertToPixels(getApplicationContext(), 40));        //Scaling needs to be used for different dpi's
        //     bodypaint.setTextSize(convertToPixels(getApplicationContext(), 40));
        //Calculate the positions
        int xPos = (canvas.getWidth() / 2);     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.   //- ((paint.descent() + paint.ascent()) / 2))
        int yPos_head = ((canvas.getHeight() / 2) - canvas.getHeight() / 10);

        int yPos_body = (canvas.getHeight() / 8 + canvas.getHeight() / 2);
        Path path = new Path();


        canvas.drawText(head, xPos, yPos_head, headpaint);
        canvas.drawText(body, xPos, yPos_body, bodypaint);

        return new BitmapDrawable(getResources(), bm);
    }

    public String save_image(final ImageView view, final Context context) {
        final View screenview = view.getRootView();
        screenview.setDrawingCacheEnabled(true);
        screenview.buildDrawingCache();
        view.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
                File storageDir = Environment.getExternalStorageDirectory();
                Log.d("hwaaaaaaaaaaatttttt", "run: " + storageDir.getAbsolutePath());
                File image_file = new File(storageDir, imageFileName);
                if (!image_file.exists()) {
                    //Toast.makeText(context,"file_dont_exist",Toast.LENGTH_SHORT).show();
                    try {
                        image_file.createNewFile();
                        //  if(image_file.exists()){ Toast.makeText(context,"file_exist",Toast.LENGTH_SHORT).show();}
                        //   else { Toast.makeText(context,"fuck",Toast.LENGTH_SHORT).show();}
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "file not exist ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
                try {
                    FileOutputStream fout = new FileOutputStream(image_file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fout);

                    //       Toast.makeText(context,String.valueOf(image_file.getAbsoluteFile().getTotalSpace()), Toast.LENGTH_SHORT).show();
                    fout.flush();
                    fout.close();
                } catch (Exception e) {
                    Toast.makeText(context, "i am not working ", Toast.LENGTH_SHORT);
                }
            }
        });
        return imageFileName;
    }

}

