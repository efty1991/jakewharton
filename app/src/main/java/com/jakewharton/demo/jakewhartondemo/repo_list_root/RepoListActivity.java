package com.jakewharton.demo.jakewhartondemo.repo_list_root;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.jakewharton.demo.jakewhartondemo.JakeWhartonApplication;
import com.jakewharton.demo.jakewhartondemo.R;
import com.jakewharton.demo.jakewhartondemo.adapters.RepoListAdapter;
import com.jakewharton.demo.jakewhartondemo.interfaces.RepoListActivityMVP;
import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListActivity extends AppCompatActivity implements RepoListActivityMVP.View {

    @BindView(R.id.repo_list)
    RecyclerView repo_list;
    @BindView(R.id.repo_list_root)
    ConstraintLayout repo_list_root;
    @BindView(R.id.progress_bar_parent)
    RelativeLayout progress_bar_parent;
    @Inject
    RepoListActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        ((JakeWhartonApplication) getApplication()).getComponent().inject(this);
        initRecyclerView();
        presenter.setView(this);
        presenter.loadData(1);
    }

    private boolean isScrolling = false;
    private int currentItems, totalItems, scrolledOutItems;
    private RepoListAdapter repoListAdapter;
    private boolean isRecyclerSet = false;
    int pageCount;

    public void initRecyclerView() {
        isRecyclerSet = true;
        repo_list.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        repo_list.setLayoutManager(mLayoutManager);
        repo_list.setItemAnimator(new DefaultItemAnimator());
        repoListAdapter = new RepoListAdapter();
        repo_list.setAdapter(repoListAdapter);
        repo_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems   = mLayoutManager.getItemCount();
                scrolledOutItems = mLayoutManager.findFirstVisibleItemPosition();
                if (dy>0 && isScrolling && (currentItems + scrolledOutItems) == totalItems-2) {
                    loadMore();
                }
            }
        });
    }

    private boolean isAlreadyFetching = false;
    private boolean isMoreDataAvailable = true;

    private void loadMore() {
        if (isMoreDataAvailable && !isAlreadyFetching) {
            showProgressBar(true, pageCount);
            isScrolling = false;
            isAlreadyFetching = true;
            presenter.loadData(pageCount + 1);
        }
    }

    public void showProgressBar(boolean isShow, int pageNo) {
        if (isShow) {
            if (pageNo == 0)
                progress_bar_parent.setVisibility(View.VISIBLE);
            else
                repoListAdapter.addProgressBar();
        } else {
            if (pageNo == 1)
                progress_bar_parent.setVisibility(View.GONE);
            else
                repoListAdapter.removeProgressBar();
        }
    }

    @Override
    public void updateList(ArrayList<Repo> downloadedRepos, int page) {
        ++pageCount;
        isAlreadyFetching = false;
        showProgressBar(false, page);
        if ((downloadedRepos == null || downloadedRepos.size() == 0) && pageCount > 2)
            isMoreDataAvailable = false;
        if (isMoreDataAvailable)
            repoListAdapter.addMoreData(downloadedRepos);
    }

    @Override
    public void showSnackBarMessage(String message) {
        Snackbar.make(repo_list_root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingData(String message, int page) {
        isAlreadyFetching = false;
        showProgressBar(false, page);
        showSnackBarMessage(message);
    }

    @Override
    protected void onDestroy() {
        presenter.rxUnsubscribe();
        super.onDestroy();
    }
}
