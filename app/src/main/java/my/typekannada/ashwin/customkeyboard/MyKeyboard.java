package my.typekannada.ashwin.customkeyboard;


import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import java.security.Key;


public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private Keyboard keyboard1;
    private Keyboard keyboard2;
    private Keyboard keyboard4;
    private Keyboard keyboard5;



    private boolean caps = false;

    @Override
    public View onCreateInputView() {



        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.kankey);
        keyboard1= new Keyboard(this,R.xml.kannum);
        keyboard2= new Keyboard(this,R.xml.langcha);
        keyboard4= new Keyboard(this,R.xml.qwerty);
        keyboard5= new Keyboard(this,R.xml.engnum);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }
   // @Override
   // public View onCreateCandidatesView(){
      //  setCandidatesViewShown(true);
      //  return null;
   // }





    @Override
    public void onPress(int primaryCode) {
        if (primaryCode==32 || primaryCode==-5 || primaryCode==-4 || primaryCode==-201 || primaryCode==-198 || primaryCode==-203 ){
            kv.setPreviewEnabled(false);
        }
        else{
            kv.setPreviewEnabled(true);
        }


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
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                //caps=!caps;
                  //  keyboard.setShifted(caps);
                //kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;



            case -198:
                keyboard = new Keyboard(this, R.xml.kankey);//loads l1 keyboard from l1 Numeric Keyboard
                kv.setKeyboard(keyboard);//keyboard
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;

            case -199:
                keyboard2 = new Keyboard(this, R.xml.langcha);//loads Language Change Keypad through which user can change language.
                kv.setKeyboard(keyboard2);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;



            case -201:
                keyboard4 = new Keyboard(this, R.xml.qwerty);
                kv.setKeyboard(keyboard4);//keyboard4
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;

            case -202:
                caps=!caps;
                keyboard4.setShifted(caps);
                //keyboard6 = new Keyboard(this, R.xml.qwertycapital);
             // kv.setKeyboard(keyboard6);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;

            case -203:
                keyboard5 = new Keyboard(this, R.xml.engnum);
                kv.setKeyboard(keyboard5);
                kv.setOnKeyboardActionListener(this);
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

