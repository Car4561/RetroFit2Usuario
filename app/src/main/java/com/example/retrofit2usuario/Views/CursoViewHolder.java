package com.example.retrofit2usuario.Views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2usuario.R;

import org.w3c.dom.Text;

public class CursoViewHolder extends RecyclerView.ViewHolder {

    public TextView tvCurso;
    public TextView tvProfesor;

     public  CursoViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCurso = itemView.findViewById(R.id.tvCurso);
        tvProfesor = itemView.findViewById(R.id.tvProfesor);
    }
}
