package com.example.hp.finalprojectsecurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import static android.widget.Toast.*;

public class Sign_upActivity extends AppCompatActivity {
    TextInputLayout name,password,confirm_password,phoone;
    MaterialButton sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.edit_text_name);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        phoone=findViewById(R.id.phoone);
        sign=findViewById(R.id.btn_go);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check_edit_text()){
                    if(check_password()){
                        Boolean Is_Insert=MainActivity.database.Insert_data(name.getEditText().getText().toString(),phoone.getEditText().getText().toString(),password.getEditText().getText().toString());
                        if(Is_Insert==true){
                            makeText(getApplicationContext(),"Success", LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();

                        }

                    }else{
                        makeText(getApplicationContext(),"confirm password wrong", LENGTH_SHORT).show();
                    }

                }else{
                    makeText(getApplicationContext(),"You should fill all Fields", LENGTH_SHORT).show();
                }

            }
        });


    }
    private boolean check_edit_text(){
        if((name.getEditText().getText().toString().trim()!=null&&name.getEditText().getText().toString().trim().equals(""))
                ||(password.getEditText().getText().toString().trim()!=null&&password.getEditText().getText().toString().trim().equals(""))
                ||(confirm_password.getEditText().getText().toString().trim()!=null&&confirm_password.getEditText().getText().toString().trim().equals(""))
                ||(phoone.getEditText().getText().toString().trim()!=null&&phoone.getEditText().getText().toString().trim().equals(""))){
            return true;

        }
        return false;
    }

    private boolean check_password(){
        if(password.getEditText().getText().toString().equals(confirm_password.getEditText().getText().toString())){
            return true;
        }
        return false;
    }



}


