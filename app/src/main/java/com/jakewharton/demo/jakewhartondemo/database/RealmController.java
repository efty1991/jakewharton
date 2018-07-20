package com.jakewharton.demo.jakewhartondemo.database;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    Handler handler = new Handler(Looper.getMainLooper());
    private static RealmController instance;
    private final Realm realm;

    private RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null)
            instance = new RealmController(fragment.getActivity().getApplication());
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null)
            instance = new RealmController(activity.getApplication());
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null)
            instance = new RealmController(application);
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void refresh() {
        realm.refresh();
    }

    public RealmResults<RealmRepo> getRepos() {
        return realm.where(RealmRepo.class).findAll();
    }

    public RealmRepo getRepo(String id) {
        return realm.where(RealmRepo.class).equalTo("id", id).findFirst();
    }

    public boolean hasRepo() {
        return !realm.where(RealmRepo.class).findAll().isEmpty();
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public RealmResults<RealmRepo> queriedReposPageWise(int pageNo) {
        return realm.where(RealmRepo.class)
                .equalTo("pageNo", pageNo)
                .findAll();
    }

    public void insertObject(RealmRepo repo) {
        realm.beginTransaction();
        realm.insertOrUpdate(repo);
        realm.commitTransaction();
    }

    public void insertOnRealmSameThread(final RealmRepo repo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                insertObject(repo);
            }
        });
    }
}
