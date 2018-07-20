package com.jakewharton.demo.jakewhartondemo.interfaces;

import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.Observable;

public interface Repository {
    Observable<Repo> getRepoFromDb(int pageNo);

    Observable<Repo> getRepoFromNetwork(int pageNo);

    Observable<Repo> getRepo(int page);

    void insertRepo(Repo repo, int page);

    void insertRepo(ArrayList<Repo> repos, int page);
}
