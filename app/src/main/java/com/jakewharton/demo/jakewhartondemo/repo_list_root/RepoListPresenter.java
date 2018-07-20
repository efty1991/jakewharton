package com.jakewharton.demo.jakewhartondemo.repo_list_root;

import android.util.Log;

import com.jakewharton.demo.jakewhartondemo.interfaces.RepoListActivityMVP;
import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RepoListPresenter implements RepoListActivityMVP.Presenter {

    RepoListActivityMVP.Model model;
    RepoListActivityMVP.View view;
    Disposable subscription;

    public RepoListPresenter(RepoListActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData(final int page) {
        final ArrayList<Repo> repos = new ArrayList<Repo>();
        subscription = model.getRepo(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Repo>() {
                    @Override
                    public void onNext(Repo repo) {
                        repos.add(repo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorLoadingData("Sorry no data found.", page);
                    }

                    @Override
                    public void onComplete() {
                        view.updateList(repos,page);
                    }
                });
    }

    @Override
    public void setView(RepoListActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose();
    }
}
