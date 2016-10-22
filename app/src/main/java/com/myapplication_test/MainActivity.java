/**
 * Created by fangjun.xu on 9/26/16.
 *  Mainactivity for test
 *
 */
package com.myapplication_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;


public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{
       private final String TAGS= "MainActivity";
       private final String USER_NAME ="tcl";
        private final String USER_PWD  ="123456";
        private final boolean DEBUG = true;

        private EditText muser_edit; //edit text
        private EditText mpwd_edit;

        private CheckBox mcheckboxpwd ;

        private Button mbutton_ok ;
        private Button mbutton_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAGS,"### onCreate begin ! ### ");
        //create Table layout
        final TableLayout mtable = (TableLayout)findViewById(R.id.Table_layout1);

        muser_edit = (EditText) findViewById(R.id.username);
        mpwd_edit  = (EditText) findViewById(R.id.password);

        mcheckboxpwd = (CheckBox)findViewById(R.id.checkboxpwd);
        mcheckboxpwd.setOnCheckedChangeListener(this);

        mbutton_ok = (Button)findViewById(R.id.ok);
        mbutton_help= (Button)findViewById(R.id.help);


        // button ok check user name and password
        mbutton_ok.setOnClickListener(new View.OnClickListener() {
            private String username;
            private String passwd;

            @Override
            public void onClick(View v) {
                username = muser_edit.getText().toString();
                passwd   = mpwd_edit.getText().toString();
                Log.v(TAGS,"onClick button(OK), username: "+ username +" password : "+passwd);
                if (username.length() == 0 && passwd.length() == 0) {
                    Toast.makeText(MainActivity.this, "警告：用户名和密码为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //SharedPreferences  sp = getSharedPreferences("config", MODE_PRIVATE); //get from mem
                //check user name and password whether matched !
                if (USER_NAME.equals(this.username) && USER_PWD.equals(this.passwd)) {
                   Intent intent = new Intent(MainActivity.this,SettingActivity.class); // jump to another screen
                   startActivity(intent);
                   //finish();   //finish current screen
                } else {
                    Toast.makeText(MainActivity.this, "警告：用户名和密码不匹配！", Toast.LENGTH_LONG ).show();
                }
            }
        });

        mbutton_help.setOnClickListener(new View.OnClickListener() {
            private int clickmun = 0;
            private boolean mode = false;
            Intent intent = new Intent(MainActivity.this,SettingActivity.class); // jump to another screen
            @Override
            public void onClick(View v) {
                clickmun++;
                if (!mode){
                   if (clickmun >= 3) {
                       clickmun = 0;
                       mode = true;
                       Toast.makeText(MainActivity.this, "进入调试界面 ->", Toast.LENGTH_SHORT).show();
                       startActivity(intent);
                       //finish(); //finish current screen
                   } else {
                       Toast.makeText(MainActivity.this, "再点击  " + (3 - clickmun) + "次进入调试界面 !", Toast.LENGTH_SHORT).show();
                   }
                } else {
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "已经进入调试界面!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //user name length check
        muser_edit.addTextChangedListener(new TextWatcher() {
            private CharSequence char_tmp;
            private int edit_start;
            private int edit_end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (DEBUG)
                    Log.v(TAGS, "beforeTextChanged, s.length : " + s.length() + " start: " + start + " count: " + count + " after: " + after);
                char_tmp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DEBUG)
                    Log.v(TAGS, "onTextChanged, s.length " + s.length() + " start: " + start + " before : " + before + " count: " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_start = muser_edit.getSelectionStart();
                edit_end = muser_edit.getSelectionEnd();
                if (DEBUG)
                    Log.v(TAGS, "afterTextChanged , s.lenth : " + s.length() + " edit_start: " + edit_start + " edit_end :" + edit_end);
                if (char_tmp.length() > 3) {
                    Toast.makeText(MainActivity.this, "user name too long ! ", Toast.LENGTH_LONG).show();
                    s.delete(edit_start - 1, edit_end);
                    int tempSelection = edit_start;
                    muser_edit.setText(s);
                    muser_edit.setSelection(tempSelection);
                }
            }
        });
        // password length check
        mpwd_edit.addTextChangedListener(new TextWatcher() {
            private  CharSequence char_tmp ;
            private  int  edit_start;
            private  int  edit_end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (DEBUG)
                    Log.v(TAGS,"beforeTextChanged, s.length : "+ s.length()+" start: "+start+" count: "+count+" after: "+after);
                char_tmp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DEBUG)
                    Log.v(TAGS,"onTextChanged, s.length "+s.length()+ " start: "+start+" before : "+before+" count: "+ count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_start = mpwd_edit.getSelectionStart();
                edit_end   = mpwd_edit.getSelectionEnd();
                if (DEBUG)
                    Log.v(TAGS,"afterTextChanged , s.lenth : " + s.length()+" edit_start: "+edit_start + " edit_end :" + edit_end);
                if (char_tmp.length() > 6 ) {
                    Toast.makeText(MainActivity.this,"password too long ! ", Toast.LENGTH_LONG).show();
                    s.delete(edit_start - 1, edit_end);
                    int tempSelection = edit_start;
                    mpwd_edit.setText(s);
                    mpwd_edit.setSelection(tempSelection);
                }
            }
        });
        Log.v(TAGS,"### Oncreate finish ! ###");
    }

    //checkbox for password
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.v(TAGS,"b is : " + b);
        if(b){
            //hide
            mpwd_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            //light
            mpwd_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // curers on bottom
        mpwd_edit.setSelection(mpwd_edit.getText().length());
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

//        //noinspection SimplifiableIfStatement
//        if (id == id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
