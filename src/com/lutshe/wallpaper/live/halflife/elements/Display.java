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
    private final Bitmap screenC;

    private List<Screen> screens = new ArrayList<>();

    Random random = new Random();

    public Display(Context context, BitmapFactory.Options options) {
        screenA = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen_a, options);
        screenB = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen_b, options);
        screenC = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen_c, options);

        screens.add(new Screen(screenA));
        screens.add(new Screen(screenB));
        screens.add(new Screen(screenC));
    }

    public void scaleLights(float scale) {
        for (Screen screen : screens) {
            screen.scaleLight(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        int index = random.nextInt(3);
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

        private static final int X_LAYOUT = 581;
        private static final int Y_LAYOUT = 542;

        private int layoutX;
        private int layoutY;

        private final Bitmap screen;
        private Bitmap scaledScreen;

        public Screen(Bitmap screen) {
            this.screen = screen;
            scaledScreen = this.screen;
            this.layoutX = X_LAYOUT;
            this.layoutY = Y_LAYOUT;
        }

        public void scaleLight(float scale) {
            layoutX = (int) (X_LAYOUT * scale);
            layoutY = (int) (Y_LAYOUT * scale);
            scaledScreen = Bitmap.createScaledBitmap(screen, (int) (screen.getWidth() * scale), (int) (screen.getHeight() * scale), true);
        }

        public void onDraw(Canvas canvas) {
            canvas.save();
            canvas.drawBitmap(scaledScreen, layoutX, layoutY, null);
            canvas.restore();
        }

        public void recycleBitmap() {
            scaledScreen.recycle();
            screen.recycle();
        }
    }
}
