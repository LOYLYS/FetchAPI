package vn.framgia.vhlee.fetchapi;

import java.util.List;

public interface AsyncTaskListener {
    void onItemLoadComplete(Repo repo);

    void onLoadFail();

    void onLoadComplete(List<Repo> repos);
}
