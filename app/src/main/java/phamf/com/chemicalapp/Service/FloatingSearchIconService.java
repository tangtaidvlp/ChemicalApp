package phamf.com.chemicalapp.Service;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import phamf.com.chemicalapp.Adapter.Search_List_Adapter;
import phamf.com.chemicalapp.R;

public class FloatingSearchIconService extends Service {

    private WindowManager mWindowManager;
    private View fl_search_view;
    float deltaX = 0, deltaY = 0;
    float touchX, touchY;
    private RecyclerView fl_rcv_search;
    private EditText fl_edt_search;
    private ImageButton fl_ib_search;
    private Search_List_Adapter fl_adapter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show();


        fl_search_view = LayoutInflater.from(this).inflate(R.layout.floating_search_view, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 50;
        params.y = 50;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(fl_search_view, params);

        addControl();
        addEvent();

        fl_ib_search.performClick();
        fl_ib_search.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN : {


                        deltaX = event.getRawX() - params.x;
                        deltaY = event.getRawY() - params.y;

                        touchX = event.getRawX();
                        touchY = event.getRawY();

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if ((Math.abs(event.getRawX() - touchX) < 10) && (Math.abs(event.getRawY() - touchY) < 10)) {
                            if (fl_edt_search.getVisibility() == View.VISIBLE) {
                                fl_edt_search.setVisibility(View.GONE);
                                fl_rcv_search.setVisibility(View.GONE);
                            } else if ((fl_edt_search.getVisibility() == View.INVISIBLE) | (fl_edt_search.getVisibility() == View.GONE)) {
                                fl_edt_search.setVisibility(View.VISIBLE);
                                fl_rcv_search.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float X = event.getRawX() - deltaX;
                        float Y = event.getRawY() - deltaY;
                        params.x = (int) X;
                        params.y = (int) Y;
                        mWindowManager.updateViewLayout(fl_search_view, params);
                        return true;
                    }
                }

                return false;
            }
        });
    }

    private void addControl () {
        View parent = fl_search_view;
        fl_rcv_search = parent.findViewById(R.id.fl_rcv_equ_search);
        fl_adapter = new Search_List_Adapter(this);
        fl_adapter.adaptFor(fl_rcv_search);

        fl_edt_search = parent.findViewById(R.id.fl_edt_search);
        fl_ib_search = parent.findViewById(R.id.fl_ib_search);
    }

    private void addEvent () {
        fl_adapter.setOnItemClickListener(new Search_List_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Toast.makeText(FloatingSearchIconService.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        fl_edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                fl_adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaPlayer player = MediaPlayer.create(this, R.raw.emmoilanguoiyeuanh);
        player.start();
        player.seekTo(80000);
        Toast.makeText(this, "StartCommand", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mWindowManager.removeView(fl_search_view);
        Log.e("Des", "true");
    }
}
