package com.android.cubemaster.colors;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.ChromaUtil;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new ChromaDialog.Builder()
                        .initialColor(Color.GREEN)
                        .colorMode(ColorMode.RGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                        .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                        .onColorSelected(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(@ColorInt int color) {
                                String hexString = ChromaUtil.getFormattedColorString(color, false);

                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(view.getContext());
                                Display display = getWindowManager().getDefaultDisplay();
                                Point point = new Point();
                                display.getSize(point);
                                int width = point.x;
                                int height = point.y;

                                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(bitmap);

                                canvas.drawRGB(Color.red(color), Color.green(color), Color.blue(color));

//                                Paint paint = new Paint();
//                                paint.setColor(Color.WHITE);
//                                paint.setTextAlign(Paint.Align.CENTER);
//
//                                float testSize = 48f;
//                                paint.setTextSize(testSize);
//                                Rect textBounds = new Rect();
//                                paint.getTextBounds(hexString, 0, hexString.length(), textBounds);
//                                paint.setTextSize((int)(testSize * width / textBounds.width() * .618));
//
//                                int centerX = canvas.getWidth() / 2;
//                                int centerY = (int) (canvas.getHeight() / 2 - (paint.descent() + paint.ascent()) / 2);
//
//                                canvas.drawText(hexString, centerX, centerY, paint);

                                try {
                                    wallpaperManager.setBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .create()
                        .show(getSupportFragmentManager(), "ChromaDialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
