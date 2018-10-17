package phamf.com.chemicalapp.CustomView.CustomViewModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

public class Dot {

    public int radius;

    private int x_pos;

    private int y_pos;

    private Paint paint;

    private TextPaint textPaint;

    private String title;

    public Dot (int x_pos, int y_pos, int radius, String title, int dot_color, int title_color) {

        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;
        this.title = title;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(dot_color);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(title_color);
        textPaint.setTextSize(radius);
        textPaint.setTextAlign(Paint.Align.RIGHT);
    }

    public void drawPoint (Canvas canvas) {
        canvas.drawCircle(x_pos, y_pos, radius, paint);
    }

    public void drawTitle (Canvas canvas) {
        textPaint.setTextSize(radius * 3);
        canvas.drawText(title, x_pos - radius * 5/2, y_pos + 1.5f * radius * 0.75f, textPaint);
    }

    public void setTextAlpha (int alpha) {
        textPaint.setAlpha(alpha);
    }

    public void setTextStyle (Typeface typeface) {
        textPaint.setTypeface(typeface);
    }

    public void setTextColor (int color) {
        textPaint.setColor(color);
    }

    public void setRadius (int newRadius) {
        this.radius = newRadius;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setPaintColor(int color) {
        this.paint.setColor(color);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
