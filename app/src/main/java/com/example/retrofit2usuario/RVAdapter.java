package com.example.retrofit2usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.Views.CursoViewHolder;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVAdapter extends RecyclerView.Adapter<CursoViewHolder> {

    List<Curso> cursoLista;
    List<Profesor> profesorLista;
    List<Profesor> profesorLista2;
    Profesor profesor;
     public RVAdapter(List<Curso> curso,List<Profesor> profesor){
         this.profesorLista = profesor;

         this.cursoLista=curso;
     }
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso,parent,false);
        CursoViewHolder cvh = new CursoViewHolder(vista);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
      holder.tvCurso.setText(cursoLista.get(position).getNombre());
      holder.tvProfesor.setText(profesorLista.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
         System.out.println( this.profesorLista.size());
        return cursoLista.size();
    }
}
