package vn.framgia.vhlee.fetchapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ItemClickListener, AsyncTaskListener {
    private static final String URL_API = "https://api.github.com/users/google/repos";
    private RepoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        fetchData();
    }

    @Override
    public void onItemClick(Repo repo) {
        showToast(repo.getName());
    }

    @Override
    public void onInfoClick(Repo repo) {
        showToast(repo.getUrl());
    }

    @Override
    public void onItemLoadComplete(Repo repo) {
        mAdapter.addRepo(repo);
    }

    @Override
    public void onLoadFail() {
        showToast(getString(R.string.msg_load_fail));
    }

    @Override
    public void onLoadComplete(List<Repo> repos) {
        showToast(append(String.valueOf(repos.size()), getString(R.string.msg_load_completed)));
    }

    private void initUI() {
        RecyclerView recycler = findViewById(R.id.recycler_repoes);
        DividerItemDecoration decor =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAdapter = new RepoAdapter(this);
        mAdapter.setClickListener(this);
        recycler.addItemDecoration(decor);
        recycler.setAdapter(mAdapter);
    }

    private void fetchData() {
        RepoAsyncTask task = new RepoAsyncTask();
        task.setListener(this);
        task.execute(URL_API);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String append(String... strings) {
        StringBuffer result = new StringBuffer();
        for (String element : strings) {
            result.append(element);
        }
        return result.toString();
    }
}
