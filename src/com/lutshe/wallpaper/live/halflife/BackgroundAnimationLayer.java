package com.lutshe.wallpaper.live.halflife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class BackgroundAnimationLayer {

    private Context context;
    private Bitmap img1;
    private Bitmap img2;
    private Bitmap img3;

    Random random = new Random();

    BackgroundAnimationLayer(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim01);
        img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim02);
        img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim03);

        int width = (int)(img1.getWidth()/2.025f);
        int height = (int)(img1.getHeight()/2.025f);

        System.out.println("toppppp " + img1.getWidth() +"   " + img1.getHeight());

        img1 = Bitmap.createScaledBitmap(img1, width, height, true);
        img2 = Bitmap.createScaledBitmap(img2, width, height, true);
        img3 = Bitmap.createScaledBitmap(img3, width, height, true);
    }

    public Bitmap getNextImage() {

        int counter = random.nextInt(3);

        switch (counter){
            case 0: return img1;

            case 1: return img2;

            case 2: return img3;

            default: return img1;
        }
    }
}
