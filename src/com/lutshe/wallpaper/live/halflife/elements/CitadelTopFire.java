package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;

public class CitadelTopFire {

    private static final int X_LAYOUT = 310;
    private static final int Y_LAYOUT = 190;

    private static int layoutX = X_LAYOUT;
    private static int layoutY = Y_LAYOUT;

    private static int nextIndex = 0;
    private static boolean directionOfMovingIndex = true;
    private static int count = 0;

    //    private final Bitmap fire1;
//    private final Bitmap fire2;
//    private final Bitmap fire3;
//    private final Bitmap fire4;
//    private final Bitmap fire5;
//    private final Bitmap fire6;
//    private final Bitmap fire7;
    private final Bitmap fire8;
    private final Bitmap fire9;
    private final Bitmap fire10;
    private final Bitmap fire11;
    private final Bitmap fire12;
    private final Bitmap fire13;
    private final Bitmap fire14;
//    private final Bitmap fire15;



    private List<Fire> fires = new ArrayList<>();

    public CitadelTopFire(Context context, BitmapFactory.Options options) {
//        fire1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.a, options);
//        fire2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b, options);
//        fire3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.c, options);
//        fire4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.d, options);
//        fire5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e, options);
//        fire6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.f, options);
//        fire7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.g, options);
        fire8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.h, options);
        fire9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.i, options);
        fire10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.j, options);
        fire11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.k, options);
        fire12 = BitmapFactory.decodeResource(context.getResources(), R.drawable.l, options);
        fire13 = BitmapFactory.decodeResource(context.getResources(), R.drawable.m, options);
        fire14 = BitmapFactory.decodeResource(context.getResources(), R.drawable.n, options);
//        fire15 = BitmapFactory.decodeResource(context.getResources(), R.drawable.o, options);

//        fires.add(new Fire(fire1));
//        fires.add(new Fire(fire2));
//        fires.add(new Fire(fire3));
//        fires.add(new Fire(fire4));
//        fires.add(new Fire(fire5));
//        fires.add(new Fire(fire6));
//        fires.add(new Fire(fire7));
        fires.add(new Fire(fire8));
        fires.add(new Fire(fire9));
        fires.add(new Fire(fire10));
        fires.add(new Fire(fire11));
        fires.add(new Fire(fire12));
        fires.add(new Fire(fire13));
        fires.add(new Fire(fire14));
//        fires.add(new Fire(fire15));

    }

    public void scaleFire(float scale) {
        for (Fire fire : fires) {
            fire.scaleFire(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        fires.get(nextIndex).onDraw(canvas);
        count++;
        if (count == 2) {
            moveIndex();
            count = 0;
        }
    }

    private void moveIndex() {
        if (directionOfMovingIndex) {
            nextIndex++;
            if (nextIndex == fires.size() - 1) directionOfMovingIndex = false;
        } else {
            nextIndex--;
            if (nextIndex == 0) directionOfMovingIndex = true;
        }
    }

    public void recycleBitmap() {
        for (Fire fire : fires) {
            fire.recycleBitmap();
        }
    }

    private static class Fire {

        private final Bitmap fire;
        private Bitmap scaledFire;

        public Fire(Bitmap fire) {
            this.fire = fire;
            scaledFire = this.fire;
        }

        public void scaleFire(float scale) {
            layoutX = (int) (X_LAYOUT * scale);
            layoutY = (int) (Y_LAYOUT * scale);
            scaledFire = Bitmap.createScaledBitmap(fire, (int) (fire.getWidth() * scale), (int) (fire.getHeight() * scale), true);
        }

        public void onDraw(Canvas canvas) {
            canvas.save();
            canvas.drawBitmap(scaledFire, layoutX, layoutY, null);
            canvas.restore();
        }

        public void recycleBitmap() {
            scaledFire.recycle();
            fire.recycle();
        }
    }
}
