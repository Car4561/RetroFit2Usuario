package com.example.retrofit2usuario.Dialogs;

import android.content.Context;

import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

public class Instance1 {
    private static Instance1 instance;
    private int cont=0;
    private Instance1 (){

    }

    public  static synchronized Instance1 getInstance(){
        if(instance==null){
            instance= new Instance1();

        }
        return instance;
    }

    public int getCont(){
        return  cont;
    }
    public  void  setCont(int cont){
        this.cont=cont;
    }
}

