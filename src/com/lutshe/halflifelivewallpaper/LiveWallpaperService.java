package com.lutshe.halflifelivewallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new SampleEngine();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class SampleEngine extends Engine {

        private LiveWallpaperPainting painting;

        SampleEngine() {
            SurfaceHolder holder = getSurfaceHolder();
            painting = new LiveWallpaperPainting(holder, getApplicationContext());
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            // remove listeners and callbacks here
            painting.stopPainting();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            painting.setSurfaceSize(width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            painting.start();

        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                painting.resumePainting();
            } else {
                // remove listeners and callbacks here
                painting.pausePainting();
            }
        }


        @Override
        public void onOffsetsChanged(float xOffset,
                                     float yOffset,
                                     float xStep,
                                     float yStep,
                                     int xPixels,
                                     int yPixels) {
            painting.dx = (painting.width - (painting.bg.getWidth()*painting.scale)) * xOffset;
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            boolean retry = true;
            painting.stopPainting();
            while (retry) {
                try {
                    painting.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

    }

}