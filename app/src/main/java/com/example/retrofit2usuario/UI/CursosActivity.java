package com.example.retrofit2usuario.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursosActivity extends AppCompatActivity {
    private EditText txtCursos;
    private Button btnCrearCurso;
    private Button btnVerCursosProfesor;
    private Button btnVerTodosCursos;

    private Profesor profesor;
    private Curso curso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        setUpView();
    }

    private void setUpView() {
        profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
        txtCursos = findViewById(R.id.txtCurso);
        btnCrearCurso = findViewById(R.id.btnCrearCurso);
        btnVerCursosProfesor = findViewById(R.id.btnVerCursosProfesor);
        btnVerTodosCursos = findViewById(R.id.btnVerTodosCursos);
        btnCrearCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCurso();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtCursos.getWindowToken(), 0);
            }
        });
        btnVerTodosCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verTodosCursos();
            }
        });
        btnVerCursosProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verCursosporProfesor();
            }
        });
    }

    private void verCursosporProfesor() {

        Call<List<Curso>> call = WebService.getInstance().createService(WebServicesApi.class).getCursosProfesor(profesor);
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.code()==200){
                    for(int i =0;i<response.body().size();i++) {
                        Log.d("TAG1", "Nombre del Curso:" + response.body().get(i).getNombre()+" Codigo Profesor: "+response.body().get(i).getProfesorId());
                    }
                }else if (response.code()==404){
                    Log.d("TAG1","No existen cursos");
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });

    }

    private void verTodosCursos() {
        Call<List<Curso>> call = WebService.getInstance().createService(WebServicesApi.class).getCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.code()==200){
                    for(int i =0;i<response.body().size();i++) {
                        Log.d("TAG1", "Nombre del Curso:" + response.body().get(i).getNombre()+" Codigo Profesor: "+response.body().get(i).getProfesorId());
                    }
                }else if (response.code()==404){
                    Log.d("TAG1","No existen cursos");
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });

    }

    private void crearCurso() {
        String name = txtCursos.getText().toString().trim();

        if(name.isEmpty()){
            txtCursos.setError("Error curso vacio");
            return;
        }

        curso = new Curso();
        curso.setNombre(name);
        curso.setProfesorId(profesor.getId());
        Call<Void> call = WebService.getInstance().createService(WebServicesApi.class).crearCurso(curso);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               if(response.code()==201){
                   Log.d("TAG1","Curso Creado Correctamente");
                   txtCursos.setText("");
                   Toast.makeText(getApplicationContext(),"Curso Creado Correctamente",Toast.LENGTH_SHORT).show();
               }else if (response.code()==409){
                   Log.d("TAG1","Curso ya existe");
                   Toast.makeText(getApplicationContext(),"Curso ya existe",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG1",t.getMessage());
            }
        });

    }
}
