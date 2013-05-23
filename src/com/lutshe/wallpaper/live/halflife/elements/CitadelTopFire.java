package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CitadelTopFire {
    public static final String LUTSHE = "lutshe";

    private static final int X_LAYOUT = 160;
    private static final int Y_LAYOUT = 77;

    private static int layoutX = X_LAYOUT;
    private static int layoutY = Y_LAYOUT;

    private final Bitmap fireA;
    private final Bitmap fireB;

    private List<Fire> fires = new ArrayList<>();

    Random random = new Random();

    public CitadelTopFire(Context context, BitmapFactory.Options options) {
        fireA = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_a, options);
        fireB = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_b, options);

        fires.add(new Fire(fireA));
        fires.add(new Fire(fireB));

    }

    public void scaleFire(float scale) {
        for (Fire fire : fires) {
            fire.scaleFire(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        int index = random.nextInt(10);
        if (index < fires.size())
            fires.get(index).onDraw(canvas);
    }

    public void recycleBitmap() {
        fireA.recycle();
        fireB.recycle();
//        screenC.recycle();
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
