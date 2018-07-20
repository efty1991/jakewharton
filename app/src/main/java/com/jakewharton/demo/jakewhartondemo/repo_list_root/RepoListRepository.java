package com.jakewharton.demo.jakewhartondemo.repo_list_root;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.demo.jakewhartondemo.database.RealmController;
import com.jakewharton.demo.jakewhartondemo.database.RealmRepo;
import com.jakewharton.demo.jakewhartondemo.interfaces.NetworkApi;
import com.jakewharton.demo.jakewhartondemo.interfaces.Repository;
import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.RealmResults;

public class RepoListRepository implements Repository {

    NetworkApi networkApi;
    RealmController realmController;
    Gson gson;

    public RepoListRepository(NetworkApi networkApi, RealmController realmController) {
        this.networkApi = networkApi;
        this.realmController = realmController;
        this.gson = new Gson();
    }

    @Override
    public Observable<Repo> getRepoFromDb(int pageNo) {
        RealmResults<RealmRepo> result = realmController.queriedReposPageWise(pageNo);
        ArrayList<Repo> allRepos = new ArrayList<Repo>();
        for (int i = 0; i < result.size(); i++) {
            RealmRepo realmRepo = result.get(i);
            Repo repo = gson.fromJson(realmRepo.getRepoData(), Repo.class);
            allRepos.add(repo);
        }

        if (allRepos.size() == 0)
            return Observable.empty();
        else {
            Observable<ArrayList<Repo>> just = Observable.just(allRepos);
            return just.concatMap(new Function<List<Repo>, ObservableSource<Repo>>() {
                @Override
                public ObservableSource<Repo> apply(List<Repo> repos) throws Exception {
                    return Observable.fromIterable(repos);
                }
            });
        }

    }

    @Override
    public Observable<Repo> getRepoFromNetwork(final int pageNo) {
        Observable<List<Repo>> repoListObservable = networkApi.getRepoListObservable(pageNo, NetworkApi.PER_PAGE);
        return repoListObservable.concatMap(new Function<List<Repo>, ObservableSource<Repo>>() {
            @Override
            public ObservableSource<Repo> apply(List<Repo> repos) throws Exception {
                return Observable.fromIterable(repos);
            }
        }).doOnNext(new Consumer<Repo>() {
            @Override
            public void accept(Repo repo) throws Exception {
                insertRepo(repo, pageNo);
            }
        });
    }


    @Override
    public Observable<Repo> getRepo(int page) {
        return getRepoFromDb(page).switchIfEmpty(getRepoFromNetwork(page));
    }

    @Override
    public void insertRepo(Repo repo, int pageNo) {
        RealmRepo realmRepo = new RealmRepo(repo.getId(), pageNo, System.currentTimeMillis(), gson.toJson(repo));
        realmController.insertOnRealmSameThread(realmRepo);
    }

    @Override
    public void insertRepo(ArrayList<Repo> repos, int page) {
        for (int i = 0; i < repos.size(); i++)
            realmController.insertObject(new RealmRepo(repos.get(i).getId(), page, System.currentTimeMillis(), gson.toJson(repos.get(i))));
    }

}
