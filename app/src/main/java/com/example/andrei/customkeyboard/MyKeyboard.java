package com.example.andrei.customkeyboard;


import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private Keyboard keyboard1;
    private Keyboard keyboard2;
    private Keyboard keyboard3;
    private Keyboard keyboard4;


    private boolean caps = false;
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.kankey);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();

        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
               ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                keyboard1 = new Keyboard(this, R.xml.kannum);
                kv.setKeyboard(keyboard1);
                kv.invalidateAllKeys();
                //caps=!caps;
                  //  keyboard.setShifted(caps);
                   //kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            case -198:
                keyboard = new Keyboard(this, R.xml.kankey);//loads kannada keyboard from kannada Numeric Keyboard
                kv.setKeyboard(keyboard);
                kv.invalidateAllKeys();
                break;

            case -199:
                keyboard2 = new Keyboard(this, R.xml.langcha);//loads Language Change Keypad through which user can change language.
                kv.setKeyboard(keyboard2);
                kv.invalidateAllKeys();
                break;

            case -200:
                keyboard3 = new Keyboard(this, R.xml.qwerty2);//loads Language Change Keypad through which user can change language.
                kv.setKeyboard(keyboard3);
                kv.invalidateAllKeys();
                break;

            case -201:
                keyboard4 = new Keyboard(this, R.xml.qwerty);//loads Language Change Keypad through which user can change language.
                kv.setKeyboard(keyboard4);
                kv.invalidateAllKeys();
                break;




            default:
               char code = (char) primaryCode;
                if(Character.isLetter(code) && caps) {
                   code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
