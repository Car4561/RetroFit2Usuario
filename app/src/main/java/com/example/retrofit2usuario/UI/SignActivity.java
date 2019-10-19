package com.example.retrofit2usuario.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignActivity extends AppCompatActivity {
    private EditText txtName;
    private TextInputLayout txtPassword;
    private EditText txtEmail;

    private Button btnSing;
    private TextView tvLogin;

    private Profesor profesor;

    Context context=this;
    Activity activity= this;
    Context activityr= activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing);

        setUpView();

    }
    private void setUpView() {
       txtName = findViewById(R.id.txtNomb);
       txtPassword=findViewById(R.id.inputPass);
       txtEmail=findViewById(R.id.txtEmail);
       btnSing=findViewById(R.id.btnSign);
       tvLogin=findViewById(R.id.tvLogin);
       btnSing.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               userSignUp();
           }
       });

       tvLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
               startActivity(intent);
           }
       });
    }

    private void userSignUp() {
       String Email  = txtEmail.getText().toString().trim();
       String name   = txtName.getText().toString().trim();
       String Password= txtPassword.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            txtName.setError(getResources().getString(R.string.name_error));
            txtName.requestFocus();
            return;
        }
        if(Email.isEmpty()){
            txtEmail.setError(getResources().getString(R.string.email_error));
            txtEmail.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            txtPassword.setError(getResources().getString(R.string.password_error));
            txtPassword.requestFocus();
            return; }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            txtEmail.setError(getResources().getString(R.string.email_doesnt_match));
            return;
        }
        if(Password.length()<4){
            txtPassword.setError(getResources().getString(R.string.password_error_less_than));
            return;
        }

        profesor = new Profesor();
        profesor.setNombre(name);
        profesor.setEmail(Email);
        profesor.setPassword(Password);
        crearProfesor();

    }

    private  void crearProfesor() {

        WebServicesApi api= WebService.getInstance().createService(WebServicesApi.class);
        Call<Void> call = api.registrarProfesor(profesor);
        call.enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
              if(response.code()==201){
                  Log.d("TAG1","Profesor Guardado Correctamente");
                  Toast.makeText(getApplicationContext(),"Profesor Registrado Correctamente",Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(getApplicationContext(),LoginActivity.class));

              }else if(response.code()==409){
                  Log.d("TAG1","Profesor ya existe");
                  Toast.makeText(getApplicationContext(),"Profesor ya existe",Toast.LENGTH_SHORT).show();
              }else{
                  Log.d("TAG1","Error no definido "+response.code());
                  System.out.println(profesor);
              }
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t) {
               Log.d("TAG1",t.getMessage().toString());
           }
       });

    }

    @Override
    protected  void onStart(){
        super.onStart();
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
           startActivity(new Intent(this,ProfileActivity.class));
        }

    }





}
