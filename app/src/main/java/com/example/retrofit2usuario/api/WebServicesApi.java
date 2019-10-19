package com.example.retrofit2usuario.api;

import com.example.retrofit2usuario.Model.Curso;
import com.example.retrofit2usuario.Model.Lenguaje;
import com.example.retrofit2usuario.Model.Profesor;
import com.example.retrofit2usuario.Model.ProfesorLenguaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServicesApi {
    /*
     PROFESORES
    */
    @POST("/api/sign_up")
    Call<Void> registrarProfesor(@Body Profesor profesor);

    @POST("/api/login")
    Call<List<Profesor>> login(@Body Profesor profesor);

    @DELETE("/api/delete/{id}")
    Call<Void> deleteById(@Path("id") Long ID);

    @PUT("/api/update_sql")
    Call<Profesor>  updateProfersor(@Body Profesor profesor);

    @GET("/api/profesores")
    Call<List<Profesor>> getProfesor();

    /*
     CURSOS
     */
    @POST("/api/crear_curso")
    Call<Void> crearCurso(@Body Curso curso);

    @GET("/api/cursos")
    Call<List<Curso>> getCursos();

    @POST("/api/cursos_profesor")
    Call<List<Curso>> getCursosProfesor(@Body Profesor profesor);
    /*
     LENGUAJES
    */
    @POST("/api/crear_lenguaje")
    Call<Void> crearLenguaje(@Body Lenguaje lenguaje);

    @GET("/api/lenguajes")
    Call<List<Lenguaje>> getLenguajes();

    @POST("/api/save_lenguaje_profesor")
    Call<Void> saveLenguajeProfesor(@Body ProfesorLenguaje profesorLenguaje);

}
