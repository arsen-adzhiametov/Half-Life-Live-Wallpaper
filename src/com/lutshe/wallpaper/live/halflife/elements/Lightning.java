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
    private final Bitmap lightD;
    private final Bitmap lightE;
    private final Bitmap lightF;
    private final Bitmap lightG;
    private final Bitmap lightH;
    private final Bitmap lightI;
    private final Bitmap lightJ;
    private final Bitmap lightK;
    private final Bitmap lightL;
    private final Bitmap lightM;
    private final Bitmap lightN;

    private List<Light> next = null;
    private List<List<Light>> lights = new ArrayList<>();

    Random random = new Random();

    public Lightning(Context context, BitmapFactory.Options options) {
        lightA = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_a, options);
        lightB = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_b, options);
        lightC = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_c, options);
        lightD = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_d, options);
        lightE = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_e, options);
        lightF = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_f, options);
        lightG = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_g, options);
        lightH = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_h, options);
        lightI = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_i, options);
        lightJ = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_j, options);
        lightK = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_k, options);
        lightL = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_l, options);
        lightM = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_m, options);
        lightN = BitmapFactory.decodeResource(context.getResources(), R.drawable.light_n, options);

        lights.add(new ArrayList<Light>());
        lights.add(new ArrayList<Light>());
        lights.add(new ArrayList<Light>());
        lights.add(new ArrayList<Light>());
        lights.add(new ArrayList<Light>());

        lights.get(0).add(new Light(lightA, 410, 195));
        lights.get(0).add(new Light(lightB, 410, 195));
        lights.get(0).add(new Light(lightC, 410, 195));
        lights.get(0).add(new Light(lightB, 410, 195));
        lights.get(0).add(new Light(lightA, 410, 195));

        lights.get(1).add(new Light(lightD, 191, 195));
        lights.get(1).add(new Light(lightE, 191, 144));
        lights.get(1).add(new Light(lightF, 191, 142));
        lights.get(1).add(new Light(lightG, 191, 142));
        lights.get(1).add(new Light(lightH, 191, 142));
        lights.get(1).add(new Light(lightI, 418, 144));

        lights.get(2).add(new Light(lightJ, 304, 219));
        lights.get(2).add(new Light(lightK, 303, 221));
        lights.get(2).add(new Light(lightL, 303, 219));
        lights.get(2).add(new Light(lightK, 303, 221));
        lights.get(2).add(new Light(lightJ, 304, 219));

        lights.get(3).add(new Light(lightM, 138, 120));
        lights.get(3).add(new Light(lightM, 150, 90));
        lights.get(3).add(new Light(lightM, 170, 85));
        lights.get(3).add(new Light(lightM, 240, 80));
        lights.get(3).add(new Light(lightM, 310, 60));

        lights.get(4).add(new Light(lightN, 480, 170));
        lights.get(4).add(new Light(lightN, 470, 200));
        lights.get(4).add(new Light(lightN, 450, 210));
        lights.get(4).add(new Light(lightN, 380, 215));

    }

    public void scaleLights(float scale) {
        for (List<Light> list : lights) {
            for (Light light : list) {
                light.scaleLight(scale);
            }
        }
    }

    int counter = 0;

    public void onDraw(Canvas canvas) {
        if (next == null) {
            int index = random.nextInt(30);
            if (index < lights.size()) {
                next = lights.get(index);
            }
        }
        if (next != null) {
            next.get(counter++).onDraw(canvas);
            if (counter == next.size() - 1) {
                next = null;
                counter = 0;
            }
        }
    }

    public void recycleBitmap() {
        for (List<Light> list : lights) {
            for (Light light : list) {
                light.recycleBitmap();
            }
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
