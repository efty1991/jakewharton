package com.jakewharton.demo.jakewhartondemo.interfaces;

import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkApi {

    final int PER_PAGE=15;
    @GET("users/JakeWharton/repos")
    Observable<List<Repo>> getRepoListObservable(
            @Query("page") int pageNo, @Query("per_page") int perPage);
}
