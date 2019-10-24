package com.example.retrofit2usuario.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit2usuario.Fragments.BlankFragment;
import com.example.retrofit2usuario.Fragments.CursoFragment;
import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.Model.ProfesorLenguaje;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LenguajeActivity extends AppCompatActivity {
    private Button btnVerTodosLenguajes, btnVerLenguajesProfesor,btnAsignarLenguajeProfesor,btnCrearLenguaje;
    private EditText txtLenguaje;
    private Profesor profesor;
    private Lenguaje lenguaje;
    private ProfesorLenguaje profesorLenguaje;

    private List<Profesor> profesorList;
    private List<Lenguaje> lenguajeList;
    private List<Lenguaje> lenguajeProfeList;
    int contC=2;
    int contCp=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje);
        setUpProfesor();
        setUpView();
    
    }

    private void setUpProfesor() {
        profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
    }

    private void setUpView() {
        btnVerTodosLenguajes=findViewById(R.id.btnVerTodosLenguajes);
        btnAsignarLenguajeProfesor=findViewById(R.id.btnAsignarLenguajeProfesor);
        btnCrearLenguaje=findViewById(R.id.btCrearLenguaje);
        btnVerLenguajesProfesor=findViewById(R.id.btnVerLenguajesProfesor);
        txtLenguaje=findViewById(R.id.txtLenguaje);

        btnCrearLenguaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearLenguaje();
                
            }
        });

        btnAsignarLenguajeProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsignarLenguajeProfesor();
            }
        });

        btnVerTodosLenguajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerTodosLenguajes();
            }
        });

        btnVerLenguajesProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerLenguajeProfesor();
            }
        });



    }

    private void VerLenguajeProfesor() {
        BlankFragment bk = new BlankFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,bk).commit();
        Call<List<Lenguaje>> call = WebService.getInstance().createService(WebServicesApi.class).getLenguajeProfesor(profesor);
        call.enqueue(new Callback<List<Lenguaje>>() {
            @Override
            public void onResponse(Call<List<Lenguaje>> call, Response<List<Lenguaje>> response) {
                if (contCp % 2 == 0 || contC % 2 != 0) {
                    if (response.code() == 200) {
                        contC = 2;
                        contCp = 2;
                        lenguajeProfeList = response.body();
                        profesorList = new ArrayList<>();
                        profesorList.add(profesor);
                        BlankFragment bk = new BlankFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,bk).commit();
                        CursoFragment cf = new CursoFragment(null, profesorList, lenguajeProfeList);
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, cf).commit();

                        for (int i = 0; i < response.body().size(); i++) {
                            Log.d("TAG1", "Lenguajes del profesor: " + response.body().get(i).getNombre());
                        }
                    } else if (response.code() == 404) {
                        Log.d("TAG1", "No hay Lenguajes");
                    }
                    System.out.println(response.code());
                }
                contCp++;
                contC+=2;
            }
            @Override
            public void onFailure(Call<List<Lenguaje>> call, Throwable t) {

            }
        });


    }

    private void VerTodosLenguajes() {
        BlankFragment bk = new BlankFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,bk).commit();
        Call<List<Lenguaje>> call = WebService.getInstance().createService(WebServicesApi.class).getLenguajes();
        call.enqueue(new Callback<List<Lenguaje>>() {
            @Override
            public void onResponse(Call<List<Lenguaje>> call, Response<List<Lenguaje>> response) {
                if (contC % 2 == 0 || contCp % 2 != 0) {
                    contC = 2;
                    contCp = 2;
                    if (response.code() == 200) {

                        lenguajeList = response.body();
                        BlankFragment bk = new BlankFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,bk).commit();
                        CursoFragment cf = new CursoFragment(null, null, lenguajeList);
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, cf).commit();
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.d("TAG1", "Lenguaje: " + response.body().get(i).getNombre());
                        }
                    } else if (response.code() == 404) {
                        Log.d("TAG1", "No hay Lenguajes ");
                    }
                }

                contC++;
                contCp += 2;
            }
            @Override
            public void onFailure(Call<List<Lenguaje>> call, Throwable t) {

            }
        });
    }

    private void AsignarLenguajeProfesor() {
        lenguaje= new Lenguaje();
        profesorLenguaje =new ProfesorLenguaje();
        String id = txtLenguaje.getText().toString().trim();
        if(id.isEmpty()){
            txtLenguaje.setError("Error curso vacio");
            return;
        }
        lenguaje.setId(Long.parseLong(id));
        profesorLenguaje.setLenguaje(lenguaje);
        profesorLenguaje.setProfesor(profesor);
        Call<Void> call = WebService.getInstance().createService(WebServicesApi.class).saveLenguajeProfesor(profesorLenguaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==201){
                    Log.d("TAG1","Hemos asociado un lenguaje a un profesor");
                    Toast.makeText(getApplicationContext(),"Hemos asociado un lenguaje a un profesor",Toast.LENGTH_LONG).show();
                }else if(response.code()==404){
                    Log.d("TAG1","No existe el id del curso");
                    Toast.makeText(getApplicationContext(),"No existe el id del curso",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void crearLenguaje() {
        lenguaje=new Lenguaje();
        String nomb = txtLenguaje.getText().toString().trim();
        if(nomb.isEmpty()){
            txtLenguaje.setError("Error curso vacio");
            return;
        }
        lenguaje.setNombre(nomb);
        Call<Void> call = WebService.getInstance().createService(WebServicesApi.class).crearLenguaje(lenguaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==201){
                    Toast.makeText(getApplicationContext(),"Lenguaje Creado Correctamente",Toast.LENGTH_LONG).show();
                    Log.d("TAG1","Lenguaje Creado Correctamente");
                } else if (response.code() ==409) { //NO SE USA
                    Toast.makeText(getApplicationContext(),"Lenguaje Ya Existe",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
