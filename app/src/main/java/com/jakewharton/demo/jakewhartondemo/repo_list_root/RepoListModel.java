package com.jakewharton.demo.jakewhartondemo.repo_list_root;

import com.jakewharton.demo.jakewhartondemo.interfaces.RepoListActivityMVP;
import com.jakewharton.demo.jakewhartondemo.interfaces.Repository;
import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;

import io.reactivex.Observable;

public class RepoListModel implements RepoListActivityMVP.Model {

    private Repository repository;

    public RepoListModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Repo> getRepo(int page) {
        return repository.getRepo(page);
    }

    @Override
    public void insertRepo(Repo repo,int pageNo) {
        repository.insertRepo(repo,pageNo);
    }

    @Override
    public void insertRepo(ArrayList<Repo> repos, int pageNo) {
        repository.insertRepo(repos,pageNo);
    }


}
