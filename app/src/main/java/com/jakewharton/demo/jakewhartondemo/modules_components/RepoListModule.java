package com.jakewharton.demo.jakewhartondemo.modules_components;

import com.jakewharton.demo.jakewhartondemo.database.RealmController;
import com.jakewharton.demo.jakewhartondemo.interfaces.RepoListActivityMVP;
import com.jakewharton.demo.jakewhartondemo.repo_list_root.RepoListModel;
import com.jakewharton.demo.jakewhartondemo.repo_list_root.RepoListPresenter;
import com.jakewharton.demo.jakewhartondemo.repo_list_root.RepoListRepository;
import com.jakewharton.demo.jakewhartondemo.interfaces.NetworkApi;
import com.jakewharton.demo.jakewhartondemo.interfaces.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoListModule {

    @Provides
    public Repository provideRepository(NetworkApi networkApi, RealmController realmController) {
        return new RepoListRepository(networkApi, realmController);
    }

    @Provides
    public RepoListActivityMVP.Model provideModel(Repository repository) {
        return new RepoListModel(repository);
    }

    @Provides
    public RepoListActivityMVP.Presenter providePresenter(RepoListActivityMVP.Model model) {
        return new RepoListPresenter(model);
    }
}
