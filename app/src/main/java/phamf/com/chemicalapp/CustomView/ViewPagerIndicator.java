package phamf.com.chemicalapp.CustomView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;

import phamf.com.chemicalapp.Abstraction.SpecialDataType.IsomerismType;
import phamf.com.chemicalapp.CustomView.CustomViewModel.Dot;

import java.util.ArrayList;

import phamf.com.chemicalapp.Manager.FontManager;
import phamf.com.chemicalapp.R;


public class ViewPagerIndicator extends View {

    private static final int VERTICAL = 2;

    private static final int HORIZONTAL = 1;

    private int dot_count;

    private int selected_radius;

    private int distance_between_2_dots;

    private int normal_radius;

    private int normal_dot_color, selected_dot_color;

    private int normal_title_color, selected_title_color;

    private boolean isWrapWidth, isWrapHeight;

    private int orientation;

    ArrayList<Dot> dot_list = new ArrayList<>();

    ArrayList<String> title_list = new ArrayList<>();

    private int startPosX, startPosY;

    private int preDotPos, selectedDotPos;

    private boolean wantShowText;

    private boolean hasTitle;

    ValueAnimator dotScaleAnim;

    ValueAnimator title_fade_in, title_fade_out;

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray properties = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        normal_dot_color = properties.getColor(R.styleable.ViewPagerIndicator_normal_color, Color.GRAY);

        selected_dot_color = properties.getColor(R.styleable.ViewPagerIndicator_selected_color, Color.GRAY);

        normal_radius = (int) properties.getDimension(R.styleable.ViewPagerIndicator_normal_radius, 20);

        selected_radius = (int) properties.getDimension(R.styleable.ViewPagerIndicator_seleted_radius, 20);

        distance_between_2_dots = (int) properties.getDimension(R.styleable.ViewPagerIndicator_distance_between_two_dots, 20);

        dot_count = properties.getInt(R.styleable.ViewPagerIndicator_dot_count, 2);

        isWrapWidth = properties.getBoolean(R.styleable.ViewPagerIndicator_wrap_width, false);

        isWrapHeight = properties.getBoolean(R.styleable.ViewPagerIndicator_wrap_height, false);

        orientation = properties.getInt(R.styleable.ViewPagerIndicator_orientation, HORIZONTAL);

        hasTitle = properties.getBoolean(R.styleable.ViewPagerIndicator_hasTitle, false);

        wantShowText = properties.getBoolean(R.styleable.ViewPagerIndicator_wantShowText, true);

        selected_title_color = properties.getColor(R.styleable.ViewPagerIndicator_selected_title_color, Color.GRAY);

        normal_title_color = properties.getColor(R.styleable.ViewPagerIndicator_normal_title_color, Color.GRAY);

        createAnim();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0, height = 0;

        if (!hasTitle) {
            if (orientation == HORIZONTAL) {
                int expected_width = normal_radius * dot_count
                        + distance_between_2_dots * (dot_count - 1)
                        + selected_radius * 2;

                width = isWrapWidth ? expected_width : MeasureSpec.getSize(widthMeasureSpec);

                height = isWrapHeight ? selected_radius * 2 : MeasureSpec.getSize(heightMeasureSpec);

                startPosX = isWrapWidth ? selected_radius : (width - expected_width) / 2 + selected_radius;
            } else if (orientation == VERTICAL) {

                int expected_height = normal_radius * dot_count
                        + distance_between_2_dots * (dot_count - 1)
                        + selected_radius * 3;

                height = isWrapHeight ? expected_height : MeasureSpec.getSize(heightMeasureSpec);

                width = isWrapWidth ? selected_radius * 2 : MeasureSpec.getSize(widthMeasureSpec);

                startPosY = isWrapHeight ? selected_radius * 3 / 2 : (height - expected_height) / 2 + selected_radius * 3 / 2;
            }
        } else {
            if (orientation == VERTICAL) {

                if (isWrapWidth) {

                    int expected_width = (int) (getLongestTitleLength() * selected_radius * 2 + selected_radius * 2.5);
                    width = expected_width;
                } else {

                    width = MeasureSpec.getSize(widthMeasureSpec) ;

                }

                if (isWrapHeight) {
                    int expected_height = normal_radius * dot_count
                            + distance_between_2_dots * (dot_count - 1)
                            + selected_radius * 3;
                    height = expected_height;
                } else {
                    height = MeasureSpec.getSize(heightMeasureSpec);
                }

                startPosY = selected_radius * 3 / 2;

                startPosX = width - selected_radius;
            }
        }


        setMeasuredDimension(width, height);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (orientation == HORIZONTAL) createHorizontalDot_List();
        else if (orientation == VERTICAL) createVerticalDot_List();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Dot dot : dot_list) {
            dot.drawPoint(canvas);
        }

        if (wantShowText) {
            for (Dot dot : dot_list) {
                dot.drawTitle(canvas);
            }
        }
    }


    private void createAnim () {
        dotScaleAnim = ValueAnimator.ofInt(normal_radius, selected_radius);

        dotScaleAnim.setDuration(300);

        dotScaleAnim.addUpdateListener(animation -> {
            // increasing
            int big_radius = (int) animation.getAnimatedValue();

            // decreasing
            int small_radius = (selected_radius - big_radius) + normal_radius;

            dot_list.get(selectedDotPos).setRadius(big_radius);

            dot_list.get(preDotPos).setRadius(small_radius);

            invalidate();
        });

        title_fade_in = ValueAnimator.ofInt(0, 255);
        title_fade_in.setDuration(300);
        title_fade_in.addUpdateListener(animation -> {
            int alpha = (int) animation.getAnimatedValue();

            // Start
            if (alpha == 0) setWantShowText(true);

            for (Dot dot : dot_list) {
                dot.setTextAlpha(alpha);
                invalidate();
            }

        });

        title_fade_out = ValueAnimator.ofInt(255, 0);
        title_fade_out.setDuration(300);
        title_fade_out.addUpdateListener(animation -> {
            int alpha = (int) animation.getAnimatedValue();
            for (Dot dot : dot_list) {
                dot.setTextAlpha(alpha);
                invalidate();
            }

            if (alpha == 0) setWantShowText(false);
        });

    }


    public void setDot_count(int dot_count) {
        this.dot_count = dot_count;

    }

    public void setSelected_radius(int selected_radius) {
        this.selected_radius = selected_radius;
    }

    public void setDistance_between_2_dots(int distance_between_2_dots) {
        this.distance_between_2_dots = distance_between_2_dots;
    }

    public void setNormal_radius(int normal_radius) {
        this.normal_radius = normal_radius;
    }

    public void setNormal_dot_color(int normal_dot_color) {
        this.normal_dot_color = normal_dot_color;
    }

    public void setSelected_dot_color(int selected_dot_color) {
        this.selected_dot_color = selected_dot_color;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setWantShowText(boolean wantShowText) {
        this.wantShowText = wantShowText;
        invalidate();
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    public void hightLightDotAt(int index) {
        preDotPos = selectedDotPos;
        selectedDotPos = index;

        Dot previous_dot = dot_list.get(preDotPos);
        previous_dot.setPaintColor(normal_dot_color);
        previous_dot.setTextColor(normal_title_color);
        previous_dot.setTextStyle(FontManager.comfortaa_regular);

        Dot selected_dot = dot_list.get(selectedDotPos);
        selected_dot.setPaintColor(selected_dot_color);
        selected_dot.setTextColor(selected_title_color);
        selected_dot.setTextStyle(FontManager.comfortaa_bold);

        dotScaleAnim.start();
    }

    public void showTitle () {
        title_fade_in.start();
    }

    public void hideTitle () {
        title_fade_out.start();
    }

    private void createHorizontalDot_List () {

        dot_list = new ArrayList<>();

        Dot first_Dot = new Dot(startPosX, getHeight()/2,
                                selected_radius, "", selected_dot_color, selected_title_color);
        first_Dot.setTextStyle(FontManager.comfortaa_bold);
        dot_list.add(first_Dot);
        for (int i = 1; i < dot_count; i++) {
            int x_pos = startPosX + i * (normal_radius + distance_between_2_dots);
            Dot dot = new Dot(x_pos, getHeight()/2
                    , normal_radius ,""
                    , normal_dot_color, normal_title_color);
            dot.setTextStyle(FontManager.comfortaa_regular);
            dot_list.add(dot);
        }

    }

    private void createVerticalDot_List () {

        dot_list = new ArrayList<>();

        Dot first_Dot = new Dot(startPosX, startPosY,
                selected_radius, title_list.get(0), selected_dot_color, selected_title_color);
        dot_list.add(first_Dot);
        first_Dot.setTextStyle(FontManager.comfortaa_bold);

        for (int i = 1; i < title_list.size(); i++) {
            int y_pos = startPosY + i * (normal_radius + distance_between_2_dots);
            Dot dot = new Dot(startPosX, y_pos
                    ,normal_radius , title_list.get(i), normal_dot_color, normal_title_color);
            dot.setTextStyle(FontManager.comfortaa_regular);
            dot_list.add(dot);
        }

    }

    public void setTitle_list (ArrayList<String> list) {
        this.title_list = new ArrayList<>();
        this.title_list = list;

        if (orientation == HORIZONTAL) createHorizontalDot_List();
        else if (orientation == VERTICAL) createVerticalDot_List();

        measure(getMeasuredWidth(), getMeasuredHeight());
        requestLayout();
    }

    private int getLongestTitleLength () {
        int highest_length = 0;
        for (String title : title_list) {
            if (title.length() > highest_length) {
                highest_length = title.length();
            }
        }
        return highest_length;
    }


    public static int getVERTICAL() {
        return VERTICAL;
    }

    public static int getHORIZONTAL() {
        return HORIZONTAL;
    }

    public int getDot_count() {
        return dot_count;
    }

    public int getSelected_radius() {
        return selected_radius;
    }

    public int getDistance_between_2_dots() {
        return distance_between_2_dots;
    }

    public int getNormal_radius() {
        return normal_radius;
    }

    public int getNormal_dot_color() {
        return normal_dot_color;
    }

    public int getSelected_dot_color() {
        return selected_dot_color;
    }

    public int getNormal_title_color() {
        return normal_title_color;
    }

    public int getSelected_title_color() {
        return selected_title_color;
    }

    public boolean isWrapWidth() {
        return isWrapWidth;
    }

    public boolean isWrapHeight() {
        return isWrapHeight;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getPreDotPos() {
        return preDotPos;
    }

    public int getSelectedDotPos() {
        return selectedDotPos;
    }

    public boolean isWantShowText() {
        return wantShowText;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }
}


