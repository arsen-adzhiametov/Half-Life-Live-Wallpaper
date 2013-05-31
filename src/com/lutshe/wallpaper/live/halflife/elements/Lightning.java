package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lightning {

    public static final String LUTSHE = "lutshe";

    private final Bitmap lightA;
    private final Bitmap lightB;
    private final Bitmap lightC;

    private List<Light> lights = new ArrayList<>();

    Random random = new Random();

    public Lightning(Context context, BitmapFactory.Options options) {
        lightA = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_a, options);
        lightB = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_b, options);
        lightC = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_c, options);

        lights.add(new Light(lightA, 150, 200));
        lights.add(new Light(lightB, 350, 200));
        lights.add(new Light(lightC, 100, 100));

    }

    public void scaleLights(float scale) {
        for (Light light : lights) {
            light.scaleLight(scale);
        }
    }

    public void onDraw(Canvas canvas) {
        int index = random.nextInt(20);
        if (index < lights.size())
            lights.get(index).onDraw(canvas);
    }

    public void recycleBitmap() {
        lightA.recycle();
        lightB.recycle();
        lightC.recycle();
        for (Light light : lights) {
            light.recycleBitmap();
        }
    }

    private static class Light {

        private final int X_LAYOUT;
        private final int Y_LAYOUT;

        private int layoutX;
        private int layoutY;

        private final Bitmap light;
        private Bitmap scaledLight;

        public Light(Bitmap light, int x, int y) {
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
