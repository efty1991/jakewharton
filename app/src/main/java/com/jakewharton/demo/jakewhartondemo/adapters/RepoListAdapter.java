package com.jakewharton.demo.jakewhartondemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.demo.jakewhartondemo.R;
import com.jakewharton.demo.jakewhartondemo.pojos.Repo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Repo> repos = new ArrayList<>();
    public static final int VIEW_TYPE_NORMAL = 2;
    public static final int VIEW_TYPE_PROGRESS_BAR = 1;

    public static class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        public ProgressBarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_avatar)
        ImageView repo_avatar;
        @BindView(R.id.repo_name)
        TextView repo_name;
        @BindView(R.id.repo_desc)
        TextView repo_desc;
        @BindView(R.id.language_image)
        ImageView language_image;
        @BindView(R.id.language_name)
        TextView language_name;
        @BindView(R.id.fork_image)
        ImageView fork_image;
        @BindView(R.id.fork_count)
        TextView fork_count;
        @BindView(R.id.user_image)
        ImageView user_image;
        @BindView(R.id.user_count)
        TextView user_count;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void addProgressBar() {
        repos.add(null);
        notifyDataSetChanged();
//        notifyItemChanged(repos.size() - 1);
    }

    public void removeProgressBar() {
        repos.remove(null);
        notifyDataSetChanged();
    }

    public void clearAllData() {
        repos.clear();
    }

    LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_PROGRESS_BAR:
                final View progressView = inflater.inflate(R.layout.progress_bar_item, parent, false);
                return new ProgressBarViewHolder(progressView);
            case VIEW_TYPE_NORMAL:
            default:
                View view = inflater.inflate(R.layout.repo_list_item, parent, false);
                final NormalViewHolder normalHolder = new NormalViewHolder(view);
                return normalHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_PROGRESS_BAR:
                break;
            case VIEW_TYPE_NORMAL:
            default:
                NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                normalViewHolder.repo_name.setText(repos.get(position).getName());
                normalViewHolder.repo_desc.setText(repos.get(position).getDescription());
                if (repos.get(position).getLanguage() != null && !"".equalsIgnoreCase(repos.get(position).getLanguage())) {
                    normalViewHolder.language_image.setVisibility(View.VISIBLE);
                    normalViewHolder.language_name.setVisibility(View.VISIBLE);
                    normalViewHolder.language_name.setText(repos.get(position).getLanguage());
                } else {
                    normalViewHolder.language_image.setVisibility(View.GONE);
                    normalViewHolder.language_name.setVisibility(View.GONE);
                }
                normalViewHolder.fork_count.setText("" + repos.get(position).getForks());
                normalViewHolder.user_count.setText("" + repos.get(position).getWatchers());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (repos.get(position) == null)
            return VIEW_TYPE_PROGRESS_BAR;
        return VIEW_TYPE_NORMAL;
    }


    public void addMoreData(ArrayList<Repo> arrayList) {
        repos.addAll(arrayList);
        notifyDataSetChanged();
    }

}
