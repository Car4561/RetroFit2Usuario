package com.example.retrofit2usuario.Views;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2usuario.Fragments.CursoFragment;
import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.UI.LenguajeActivity;
import com.example.retrofit2usuario.api.WebService;
import com.example.retrofit2usuario.api.WebServicesApi;
import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursoViewHolder extends RecyclerView.ViewHolder {


    public TextView tvCursoLenguaje;
    public TextView tvProfesor;
    public TextView tvCursolenguajeNomb;
    public TextView tvProfesorNomb;
    List<Profesor> listProfesor;
    List<Lenguaje> lenguajeList;
    List<Curso> cursoList;
     public  CursoViewHolder(@NonNull View itemView, List<Profesor> profesor,List<Curso> cursos) {
        super(itemView);
        tvCursoLenguaje= itemView.findViewById(R.id.tvCursoLenguaje);
        tvProfesor = itemView.findViewById(R.id.tvProfesor);
        tvCursolenguajeNomb=itemView.findViewById(R.id.tvCursoLenguajeNomb);
        tvProfesorNomb=itemView.findViewById(R.id.tvProfesorNomb);
        this.listProfesor=profesor;
        this.cursoList= cursos;
        if(listProfesor==null && cursoList==null) {
            ((ViewGroup) tvProfesor.getParent()).removeView(tvProfesor);
            ((ViewGroup) tvProfesorNomb.getParent()).removeView(tvProfesorNomb);
        }
    }
}
