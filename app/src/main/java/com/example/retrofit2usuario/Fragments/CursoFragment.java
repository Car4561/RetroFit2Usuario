package com.example.retrofit2usuario.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.R;
import com.example.retrofit2usuario.RVAdapterCursos;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CursoFragment extends Fragment {

    public List<Lenguaje> lenguajeList;
    public RecyclerView rvCursos;
    public List<Curso> cursoList;
    public List<Profesor> profesorList;
    public CursoFragment(List<Curso> curso, List<Profesor> profesor, List<Lenguaje> lenguajes){
        this.cursoList =curso;
        this.profesorList= profesor;
        this.lenguajeList= lenguajes;
    }
    public CursoFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cursos, container, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        if(cursoList==null || profesorList==null) {
            params.addRule(RelativeLayout.BELOW,R.id.btnVerTodosLenguajes);
            params.setMargins(0,10,0,0);
            view.setLayoutParams(params);
        }else{
            params.addRule(RelativeLayout.BELOW,R.id.btnVerTodosCursos);
            params.setMargins(0,10,0,0);
            view.setLayoutParams(params);
        }

        rvCursos= view.findViewById(R.id.rvCursos);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvCursos.setLayoutManager(lm);

        RVAdapterCursos adapter = new RVAdapterCursos(cursoList,profesorList,lenguajeList);
        rvCursos.setAdapter(adapter);
        return view;

    }

}
