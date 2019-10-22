package com.example.retrofit2usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.Views.CursoViewHolder;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVAdapterCursos extends RecyclerView.Adapter<CursoViewHolder> {

    List<Curso> cursoLista;
    List<Profesor> profesorLista;
    List<Lenguaje> lenguajeList;
    boolean a=true;

    public RVAdapterCursos(List<Curso> curso, List<Profesor> profesor, List<Lenguaje> lenguajes) {
        this.profesorLista = profesor;
        this.cursoLista = curso;
        this.lenguajeList = lenguajes;

    }

    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        CursoViewHolder cvh = new CursoViewHolder(vista,profesorLista,cursoLista);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {

        if (cursoLista == null && profesorLista == null) {
            holder.tvCursolenguajeNomb.setText(R.string.lenguaje);
            holder.tvCursoLenguaje.setText(lenguajeList.get(position).getNombre());

        }else if(cursoLista ==null ){
            holder.tvCursolenguajeNomb.setText(R.string.lenguaje);
            holder.tvCursoLenguaje.setText(lenguajeList.get(position).getNombre());
            holder.tvProfesor.setText(SharedPrefManager.getInstance(holder.itemView.getContext()).getProfesor().getNombre());

        }

        else {
            holder.tvCursoLenguaje.setText(cursoLista.get(position).getNombre());
            holder.tvProfesor.setText(profesorLista.get(position).getNombre());
        }

    }
    @Override
    public int getItemCount() {
        if (lenguajeList != null) {
            return lenguajeList.size();
        } else {
            return cursoLista.size();
        }
    }
}
