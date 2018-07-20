package com.jakewharton.demo.jakewhartondemo.interfaces;

import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface RepoListActivityMVP {
    public interface View
    {
        public void updateList(ArrayList<Repo> downloadedRepos, int page);
        public void showSnackBarMessage(String massage);
        public void onErrorLoadingData(String message, int page);
    }

    public interface Presenter
    {
        public void loadData(int page);
        public void setView(View view);
        public void rxUnsubscribe();
    }

    public interface Model
    {
        public Observable<Repo> getRepo(int page);
        public void insertRepo(Repo repo, int page);
        public void insertRepo(ArrayList<Repo> repos, int page);
    }
}
