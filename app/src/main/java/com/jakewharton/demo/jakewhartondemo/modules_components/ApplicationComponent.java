package com.jakewharton.demo.jakewhartondemo.modules_components;

import com.jakewharton.demo.jakewhartondemo.repo_list_root.RepoListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkApiModule.class, DataBaseModule.class, RepoListModule.class})
public interface ApplicationComponent {
    void inject(RepoListActivity target);
}
