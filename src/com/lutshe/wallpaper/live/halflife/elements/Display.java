package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Display {

    private final Bitmap screenA;
    private final Bitmap screenB;

    private List<Screen> screens = new ArrayList<>();

    Random random = new Random();

    public Display(Context context, BitmapFactory.Options options) {
        screenA = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen_a, options);
        screenB = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen_b, options);

        screens.add(new Screen(screenA, 484, 523));
        screens.add(new Screen(screenB, 484, 523));
    }

    public void scaleLights(float scale) {
        for (Screen screen : screens) {
            screen.scaleLight(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        int index = random.nextInt(10);
        if (index < screens.size())
            screens.get(index).onDraw(canvas);
    }

    public void recycleBitmap() {
        screenA.recycle();
        screenB.recycle();
        for (Screen screen : screens) {
            screen.recycleBitmap();
        }
    }

    private static class Screen {

        private final int X_LAYOUT;
        private final int Y_LAYOUT;

        private int layoutX;
        private int layoutY;

        private final Bitmap light;
        private Bitmap scaledLight;

        public Screen(Bitmap light, int x, int y) {
            this.light = light;
            scaledLight = this.light;
            this.X_LAYOUT = x;
            this.Y_LAYOUT = y;
            this.layoutX = x;
            this.layoutY = y;
        }

        public void scaleLight(float scale) {
            layoutX = (int) (X_LAYOUT * scale);
            layoutY = (int) (Y_LAYOUT * scale);
            scaledLight = Bitmap.createScaledBitmap(light, (int) (light.getWidth() * scale), (int) (light.getHeight() * scale), true);
        }

        public void onDraw(Canvas canvas) {
            canvas.save();
            canvas.drawBitmap(scaledLight, layoutX, layoutY, null);
            canvas.restore();
        }

        public void recycleBitmap() {
            scaledLight.recycle();
            light.recycle();
        }
    }
}
