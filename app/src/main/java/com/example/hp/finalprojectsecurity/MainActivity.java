package com.example.hp.finalprojectsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
   private TextView tvDetails;
   private int t;
   private static DataBase   database;
    TextInputLayout name,password;
    private MaterialButton logIn;
    private TextView timer;
    int wrrog_LogIn;
    final Handler handler = new Handler();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        database = new DataBase(getApplicationContext(), DataBase.DATABASE_NAME, null, DataBase.DATABASE_VERSION);
        tvDetails = findViewById(R.id.signUp);
        name=findViewById(R.id.U_edit_text_name);
        timer=findViewById(R.id.timer);
        password=findViewById(R.id.U_password);
        logIn=findViewById(R.id.btn_go);

        final long Long = prefs.getLong("timer",0);
        final int sub= (int) (Calendar.getInstance().getTimeInMillis()-Long);
        if(sub<10000&&sub>0){
            logIn.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setVisibility(View.VISIBLE);
                            logIn.setEnabled(false);

                        }
                    });
                    Log.d("ttt", String.valueOf(sub/1000)+"     "+sub);
                    for (int i=sub/1000;i>=1;i--){
                        t=i;
                        timer.post(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(t+"");

                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setVisibility(View.INVISIBLE);
                            logIn.setEnabled(true);
                        }
                    });
                }
            }).start();

        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!check_edit_text()){
                    get_User_Password();

                }else{
                    Toast.makeText(getApplicationContext(),"You should fill all Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        makeTxtViewDetailsSpanable();
    }
    private void makeTxtViewDetailsSpanable() {
        Spannable wordtoSpan = new SpannableString(getResources().getString(R.string.If));

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 30, 38, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new UnderlineSpan(), 30, 38, 38);

        wordtoSpan.setSpan(new UnderlineSpan(), 30, 38, 38);

        wordtoSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sign_upActivity.class);
                startActivity(i);
            }
        }, 30, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        tvDetails.setText(wordtoSpan);
        tvDetails.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean check_edit_text(){
        if((name.getEditText().getText().toString().trim()!=null&&name.getEditText().getText().toString().trim().equals(""))
                ||(password.getEditText().getText().toString().trim()!=null&&password.getEditText().getText().toString().trim().equals(""))

                ){
            return true;

        }
        return false;
    }
    public void get_User_Password(){
        Cursor cursor=database.Return_data(name.getEditText().getText().toString(),password.getEditText().getText().toString());
        if(name.getEditText().getText().toString().equals("username")&&password.getEditText().getText().toString().equals("password")){
            backDoor();
        }else if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
            if(wrrog_LogIn==2){

                Timer();
            }else{
                wrrog_LogIn++;

            }

        }else{
            while (cursor.moveToNext()){
                Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();

            }
        }
    }


    public void Timer(){
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong("timer",Calendar.getInstance().getTimeInMillis());
        editor.apply();


        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timer.setVisibility(View.VISIBLE);
                        logIn.setEnabled(false);

                    }
                });

                for (int i=10;i>=1;i--){
                    t=i;
                    timer.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText(t+"");

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timer.setVisibility(View.INVISIBLE);
                        logIn.setEnabled(true);
                    }
                });
            }
        }).start();
        wrrog_LogIn=0;

    }

    public  void backDoor(){
        startActivity(new Intent(getApplicationContext(),BackDoorActivity.class));
    }
}

