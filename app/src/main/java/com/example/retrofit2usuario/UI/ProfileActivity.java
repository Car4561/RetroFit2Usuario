package com.example.retrofit2usuario.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtEmail;
    private ImageView ivProfesor;

    private Button btnUpdate;
    private TextView tvLog0ut;
    private TextView tvDelete;
    private TextView tvCurso;
    private TextView tvLenguajes;

    private Profesor profesor;
    private Bitmap bitmap;
    Context context=this;
    Activity activity= this;
    private  static  final int IMG_REQUEST =4561;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpProfesor();
        setUpView();

        System.out.println(context);
        System.out.println(ProfileActivity.this);
        System.out.println(this);
        System.out.println(activity);
        System.out.println(getBaseContext());

    }

    private void setUpView() {
        txtName = findViewById(R.id.txtNomb);
        txtEmail = findViewById(R.id.txtEmail);
        ivProfesor= findViewById(R.id.imageView);
        btnUpdate= findViewById(R.id.btnUpdate);
        tvLog0ut=findViewById(R.id.tvLogOut);
        tvDelete= findViewById(R.id.tvDelete);
        tvCurso=findViewById(R.id.tvCursos);
        tvLenguajes=findViewById(R.id.tvLenguajes);
        txtName.setText(profesor.getNombre());
        txtEmail.setText(profesor.getEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo update profeosor
                updateProfesor();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
            }
        });

        tvLog0ut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  // todo logout
                logout();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteById();
            }
        });

        if(profesor.getFoto()!=null){
            ivProfesor.setImageBitmap(stringToImage(profesor.getFoto()));
        }
        ivProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarImagenPerfil();
            }
        });
        tvCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(), CursosActivity.class));
                 obtenerProfesores();
            }
        });
        tvLenguajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LenguajeActivity.class));
            }
        });

    }
    private  void obtenerProfesores(){
        Call<List<Profesor>> call = WebService.getInstance().createService(WebServicesApi.class).getProfesor();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i=0;i<response.body().size();i++){
                        Log.d("TAG1","Nombre:"+response.body().get(i).getNombre());

                    }
                }else if(response.code()==404){
                    Log.d("TAG1","No hay profesores guardados");
                }else{
                    Log.d("TAG1","Error no definido");

                }

            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {

            }
        });



    }
    private void cambiarImagenPerfil() {
          Intent intent = new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode==RESULT_OK && data != null){
            Uri path = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                if(bitmap!=null){
                    ivProfesor.setImageBitmap(bitmap);
                    profesor.setFoto(imageToString());
                    Log.d("TAG1",imageToString());
                    Toast.makeText(getApplicationContext(),"Foto de Perfil Ingresada",Toast.LENGTH_SHORT).show();
                }else{
                    profesor.setFoto("");
                }
            }catch (IOException e){

            }
        }
    }

    private Bitmap stringToImage(String encodedString) {
       try{
           byte[] encodedByte  = Base64.decode(encodedString,Base64.DEFAULT);
           Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0,encodedByte.length);
           return bitmap;

       }catch (Exception e){
           return null;
       }
    }
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
    private void updateProfesor() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        if(name.isEmpty()){
            txtName.setError(getResources().getString(R.string.name_error));
            txtName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            txtEmail.setError(getResources().getString(R.string.email_error));
            txtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError(getResources().getString(R.string.email_doesnt_match));
            return;
        }
        profesor.setEmail(email);
        profesor.setNombre(name);
        Call<Profesor> call = WebService.getInstance().createService(WebServicesApi.class).updateProfersor(profesor);
        call.enqueue(new Callback<Profesor>() {
            @Override
            public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                if(response.code()==200){
                    Log.d("TAG1", "Profesor Actualizado Correctamente");
                    Toast.makeText(getApplicationContext(),"Profesor Actualizado Correctamente",Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext()).saveProfesor(response.body());
                }else if(response.code()==400){
                    Log.d("TAG1", "Usuario no existe");
                    Toast.makeText(getApplicationContext(),"Usuario no existe",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG1", "Error indeterminado");
                }

            }

            @Override
            public void onFailure(Call<Profesor> call, Throwable t) {

            }
        });
    }

    private void deleteById() {
        Call<Void> call = WebService.getInstance().createService(WebServicesApi.class).deleteById(profesor.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d("TAG1", "Profesor Eliminado Correctamente");
                    logout();
                    Toast.makeText(getApplicationContext(),"Profesor Eliminado Correctamente",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG1", "Error no definido");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(  Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);

    }

    private void setUpProfesor() {
       profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
    }

    @Override
    protected  void onStart(){
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
                                    