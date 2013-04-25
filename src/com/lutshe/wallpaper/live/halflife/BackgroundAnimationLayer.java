package com.lutshe.wallpaper.live.halflife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class BackgroundAnimationLayer {

    private final Bitmap img1;
    private final Bitmap img2;
    private final Bitmap img3;

    private Bitmap rImg1;
    private Bitmap rImg2;
    private Bitmap rImg3;

    Random random = new Random();

    BackgroundAnimationLayer(Context context, BitmapFactory.Options options) {
        img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim01, options);
        img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim02, options);
        img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim03, options);

        setActualSize(1);
    }

    public void setActualSize(float scale) {
        rImg1 = Bitmap.createScaledBitmap(img1, (int) (img1.getWidth() * scale), (int) (img1.getHeight() * scale), true);
        rImg2 = Bitmap.createScaledBitmap(img2, (int) (img1.getWidth() * scale), (int) (img1.getHeight() * scale), true);
        rImg3 = Bitmap.createScaledBitmap(img3, (int) (img1.getWidth() * scale), (int) (img1.getHeight() * scale), true);
    }


    public Bitmap getNextImage() {

        int counter = random.nextInt(3);

        switch (counter) {
            case 0:
                return rImg1;

            case 1:
                return rImg2;

            case 2:
                return rImg3;

            default:
                return rImg1;
        }
    }
}
