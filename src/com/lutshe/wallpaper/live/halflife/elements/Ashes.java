package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;

public class Ashes {

    public static ArrayList<Ash> ashes = new ArrayList<>();

    private Bitmap ashBitmap;

    public Ashes(Context context, BitmapFactory.Options options) {
        this.ashBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ash, options);
//        while (ashes.size()<50) ashes.add(new Ash(ashBitmap));
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < ashes.size(); i++) {
            Ash ash = ashes.get(i);
//            if (ash.y < Ash.height && ash.x < Ash.width)
//                ash.onDraw(canvas);
//            else {
//                ash.setStartPosition();
//            }
        }
        canvas.restore();
    }
}
