package com.example.andrei.customkeyboard;


import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.sax.StartElementListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private KeyboardView kv1;
    private Keyboard keyboard1;

    private boolean caps = false;
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty1);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }
    public View onCreateInputView1() {
        kv1 = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard1 = new Keyboard(this, R.xml.qwerty);
        kv1.setKeyboard(keyboard);
        kv1.setOnKeyboardActionListener(this);
        return kv1;
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
                keyboard1 = new Keyboard(this, R.xml.qwerty2);
                kv.setKeyboard(keyboard1);
                kv.invalidateAllKeys();
                //caps=!caps;
                  //  keyboard.setShifted(caps);
                   //kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case 0/1:
                keyboard1 = new Keyboard(this, R.xml.qwerty1);
                kv.setKeyboard(keyboard1);
                kv.invalidateAllKeys();

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
