package com.example.retrofit2usuario.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogVisualizar extends DialogFragment {

      private String[] datos;

    public DialogVisualizar (String[] datos){
      this.datos = datos;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(datos,null);
        builder.setTitle("Lista de Profesores");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("TAG1",String.valueOf(Instance1.getInstance().getCont()));
               Instance1.getInstance().setCont(0);

            }
        });


        return builder.create();
    }
}
