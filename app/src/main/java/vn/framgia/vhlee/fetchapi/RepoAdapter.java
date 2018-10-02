package vn.framgia.vhlee.fetchapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoHolder> {
    private List<Repo> mRepoes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RepoAdapter(Context context) {
        mRepoes = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.repo_item, parent, false);
        return new RepoHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder holder, int position) {
        holder.bindData(mRepoes.get(position));
    }

    @Override
    public int getItemCount() {
        return (mRepoes != null) ? mRepoes.size() : 0;
    }

    public void addRepo(Repo repo) {
        mRepoes.add(repo);
        notifyItemInserted(mRepoes.size() - 1);
    }

    public void setClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    static class RepoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mAvatar;
        private TextView mName;
        private TextView mDescription;
        private ImageView mInfo;
        private Repo mRepo;
        private ItemClickListener mClickListener;

        public RepoHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            mClickListener = listener;
            mAvatar = itemView.findViewById(R.id.image_avatar);
            mName = itemView.findViewById(R.id.text_name);
            mDescription = itemView.findViewById(R.id.text_description);
            mInfo = itemView.findViewById(R.id.image_info);
            itemView.setOnClickListener(this);
            mInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_info:
                    mClickListener.onInfoClick(mRepo);
                    break;
                default:
                    mClickListener.onItemClick(mRepo);
                    break;
            }
        }

        public void bindData(Repo repo) {
            mRepo = repo;
            mName.setText(repo.getName());
            mDescription.setText(repo.getDescription());
            Glide.with(itemView.getContext()).load(mRepo.getAvatar()).into(mAvatar);
        }
    }
}
