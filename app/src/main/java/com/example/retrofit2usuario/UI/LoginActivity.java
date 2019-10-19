package com.example.retrofit2usuario.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
     private TextInputLayout txtPassword;
     private EditText txtEmail;
     private Button btnLogin;
     private TextView tvSignUp;

     private Profesor profesor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();



    }
    private void setUp(){
        txtPassword=  findViewById(R.id.inputPass);
        txtEmail=  findViewById(R.id.txtEmail);
        tvSignUp=findViewById(R.id.tvSignUp);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignActivity.class));
            }
        });

    }
    private void userLogin() {

        String Email  = txtEmail.getText().toString().trim();
        String Password= txtPassword.getEditText().getText().toString().trim();


        if(Email.isEmpty()){
            txtEmail.setError(getResources().getString(R.string.email_error));
            txtEmail.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            txtPassword.setError(getResources().getString(R.string.password_error));
            txtPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            txtEmail.setError(getResources().getString(R.string.email_doesnt_match));
            return;
        }
        if(Password.length()<4){
            txtPassword.setError(getResources().getString(R.string.password_error_less_than));

            return;
        }



        profesor = new Profesor();
        profesor.setEmail(Email);
        profesor.setPassword(Password);
        login();


    }

    private void login() {

        Call<List<Profesor>> call = WebService.getInstance().createService(WebServicesApi.class).login(profesor);
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
               if(response.code()==200){
                   Log.d("TAG1","Profesor logeado "+"id:"+response.body().get(0).getId()+" email:"+response.body().get(0).getEmail());
                   SharedPrefManager.getInstance(getApplicationContext()).saveProfesor(response.body().get(0));
                   startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                   Toast.makeText(getApplicationContext(),"Profesor Logeado Correctamente" ,Toast.LENGTH_SHORT).show();

               }else if(response.code()==404){
                   Log.d("TAG1","Profesor no existe");
                   Toast.makeText(getApplicationContext(),"Correo y/o Contraseña Incorrecta" ,Toast.LENGTH_SHORT).show();
               }


            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {


                Log.d("TAG1",t.getMessage().toString());
                Toast.makeText(getApplicationContext(),t.getMessage() ,Toast.LENGTH_LONG).show();

            }
        });

    }

}
