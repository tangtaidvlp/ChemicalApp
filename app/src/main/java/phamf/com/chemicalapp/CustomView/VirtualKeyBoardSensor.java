package phamf.com.chemicalapp.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class VirtualKeyBoardSensor extends android.support.v7.widget.AppCompatEditText {

  private OnHideVirtualKeyboardListener onHideListener;

  private OnTextChangeLite liteOnTextChangeListener;

  public VirtualKeyBoardSensor(Context context) {
    super(context);
      addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
              if (liteOnTextChangeListener != null) {
                  liteOnTextChangeListener.onTextChange(s, start, before, count);
              }
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });
  }

  public VirtualKeyBoardSensor(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (liteOnTextChangeListener != null) {
                liteOnTextChangeListener.onTextChange(s, start, before, count);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
  }

  public void setOnHideVirtualKeyboardListener (OnHideVirtualKeyboardListener listener) {
    this.onHideListener = listener;
  }

  public void addLiteTextChangeListener (OnTextChangeLite onTextChangeListener) {
      this.liteOnTextChangeListener = onTextChangeListener;
  }


  /** Catch event user click turn off virtual keyboard **/
  @Override
  public boolean onKeyPreIme(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      if (onHideListener != null) {
          onHideListener.onHide();
      }
    }
    return super.dispatchKeyEvent(event);
  }



  /** INTERFACE **/
  public interface OnHideVirtualKeyboardListener {
    void onHide();
  }



    /** This interface is used to hide 2 unused override function of TextWatcherListener interface
     * This make code seem shorter and easier to read
     */
  public interface OnTextChangeLite {
      void onTextChange (CharSequence s, int start, int before, int count);
  }
}
