package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;

public class CitadelTopFire {

    private static final int X_LAYOUT = 243;
    private static final int Y_LAYOUT = 102;

    private static int layoutX = X_LAYOUT;
    private static int layoutY = Y_LAYOUT;

    private final Bitmap fire1;
    private final Bitmap fire2;
    private final Bitmap fire3;
    private final Bitmap fire4;
    private final Bitmap fire5;
    private final Bitmap fire6;
    private final Bitmap fire7;
    private final Bitmap fire8;
    private final Bitmap fire9;
    private final Bitmap fire10;
    private final Bitmap fire11;
    private final Bitmap fire12;
    private final Bitmap fire13;
    private final Bitmap fire14;
    private final Bitmap fire15;
//    private final Bitmap fire16;
//    private final Bitmap fire17;
//    private final Bitmap fire18;
//    private final Bitmap fire19;
//    private final Bitmap fire20;
//    private final Bitmap fire21;

    private static int nextIndex = 0;
    private static boolean directionOfMovingIndex = true;

    private List<Fire> fires = new ArrayList<>();

    public CitadelTopFire(Context context, BitmapFactory.Options options) {
        fire1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_1, options);
        fire2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_2, options);
        fire3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_3, options);
        fire4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_4, options);
        fire5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_5, options);
        fire6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_6, options);
        fire7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_7, options);
        fire8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_8, options);
        fire9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_9, options);
        fire10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_10, options);
        fire11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_11, options);
        fire12 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_12, options);
        fire13 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_13, options);
        fire14 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_14, options);
        fire15 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_15, options);
//        fire16 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_16, options);
//        fire17 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_17, options);
//        fire18 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_18, options);
//        fire19 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_19, options);
//        fire20 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_20, options);
//        fire21 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_21, options);

        fires.add(new Fire(fire1));
        fires.add(new Fire(fire2));
        fires.add(new Fire(fire3));
        fires.add(new Fire(fire4));
        fires.add(new Fire(fire5));
        fires.add(new Fire(fire6));
        fires.add(new Fire(fire7));
        fires.add(new Fire(fire8));
        fires.add(new Fire(fire9));
        fires.add(new Fire(fire10));
        fires.add(new Fire(fire11));
        fires.add(new Fire(fire12));
        fires.add(new Fire(fire13));
        fires.add(new Fire(fire14));
        fires.add(new Fire(fire15));
//        fires.add(new Fire(fire16));
//        fires.add(new Fire(fire17));
//        fires.add(new Fire(fire18));
//        fires.add(new Fire(fire19));
//        fires.add(new Fire(fire20));
//        fires.add(new Fire(fire21));

    }

    public void scaleFire(float scale) {
        for (Fire fire : fires) {
            fire.scaleFire(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        fires.get(nextIndex).onDraw(canvas);
        moveIndex();
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
