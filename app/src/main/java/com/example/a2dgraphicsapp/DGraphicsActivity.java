package com.example.a2dgraphicsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class DGraphicsActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener {
    private DrawingSurfaceView mDrawingSurfaceView;
    private Button mColorButton;
    private CardView cardView;
    private Button mWidthButton;
    private Button mClearButton;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dgraphics);
        SeekBar lineWidthSeekBar = findViewById(R.id.line_width_seekbar);

        mDrawingSurfaceView = findViewById(R.id.drawing_surface_view);
//        mColorButton = findViewById(R.id.color_button);
        mWidthButton = findViewById(R.id.width_button);
        mClearButton = findViewById(R.id.clear_button);
        mSaveButton = findViewById(R.id.save_button);
        cardView = findViewById(R.id.card);

        Paint mPaint = new Paint();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(DGraphicsActivity.this, DGraphicsActivity.this, mPaint.getColor()).show();

            }
        });

        mWidthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineWidthSeekBar.getVisibility() == View.VISIBLE) {
                    lineWidthSeekBar.setVisibility(View.GONE);
                } else {
                    lineWidthSeekBar.setVisibility(View.VISIBLE);
                }
            }
        });


        lineWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the line width when the seekbar is changed
                int lineWidth = progress;
                mDrawingSurfaceView.setWidth(lineWidth);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not used
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear drawing view
                mDrawingSurfaceView.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = mDrawingSurfaceView.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "signature.png");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                    Toast.makeText(DGraphicsActivity.this, "Saved signature to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DGraphicsActivity.this, "Failed to save signature", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void colorChanged(int color) {

        cardView.setCardBackgroundColor(color);
        mDrawingSurfaceView.setColor(color);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}