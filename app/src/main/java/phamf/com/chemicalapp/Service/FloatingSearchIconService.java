package phamf.com.chemicalapp.Service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter;
import phamf.com.chemicalapp.ChemicalEquationActivity;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.MainActivity;
import phamf.com.chemicalapp.Manager.FullScreenManager;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
import static android.view.WindowManager.LayoutParams.TYPE_PHONE;

public class FloatingSearchIconService extends Service {

    private WindowManager mWindowManager;
    private View parent;
    float deltaX = 0, deltaY = 0;
    float touchX, touchY;
    private ImageButton fl_ib_search;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        parent = LayoutInflater.from(this).inflate(R.layout.floating_search_view, null, false);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ?
                        TYPE_APPLICATION_OVERLAY : TYPE_PHONE,
                        FLAG_NOT_FOCUSABLE
                , PixelFormat.TRANSLUCENT);
        params.x = 50;
        params.y = 50;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(parent, params);

        addControl();
        addEvent();

        fl_ib_search.performClick();
        fl_ib_search.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN : {


                    deltaX = event.getRawX() - params.x;
                    deltaY = event.getRawY() - params.y;

                    touchX = event.getRawX();
                    touchY = event.getRawY();

                    break;
                }

                case MotionEvent.ACTION_UP: {
                    // If this condition is true means user click icon, not touch
                    if ((Math.abs(event.getRawX() - touchX) < 10) && (Math.abs(event.getRawY() - touchY) < 10)) {
                        OnIconClick();
                    }

                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    float X = event.getRawX() - deltaX;
                    float Y = event.getRawY() - deltaY;
                    params.x = (int) X;
                    params.y = (int) Y;
                    mWindowManager.updateViewLayout(parent, params);
                    return true;
                }
            }

            return false;
        });
    }

    private void addControl () {
        fl_ib_search = parent.findViewById(R.id.fl_ib_search);
    }

    private void addEvent () {

    }

    private void OnIconClick () {
        Intent startSearchIntent = new Intent(getApplicationContext(), MainActivity.class);
        startSearchIntent.putExtra(MainActivity.QUICK_SEACH, true);
        startSearchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startSearchIntent);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(parent);
    }
}
