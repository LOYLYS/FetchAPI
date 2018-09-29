package vn.framgia.vhlee.fetchapi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepoAsyncTask extends AsyncTask<String, Repo, List<Repo>> {
    private static final String REQUEST_METHOD = "GET";
    private static final String RESPONSE_NAME = "name";
    private static final String RESPONSE_DESCRIPTION = "description";
    private static final String RESPONSE_OWNER = "owner";
    private static final String RESPONSE_HTML_URL = "html_url";
    private static final String RESPONSE_AVATAR_URL = "avatar_url";
    private AsyncTaskListener mAsyncTaskListener;
    private HttpURLConnection mConnection;

    @Override
    protected List<Repo> doInBackground(String... urls) {
        String response = null;
        try {
            URL url = new URL(urls[0]);
            mConnection = (HttpURLConnection) url.openConnection();
            mConnection.setRequestMethod(REQUEST_METHOD);
            mConnection.connect();
            response = readResponse(mConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadData(response);
    }

    @Override
    protected void onProgressUpdate(Repo... values) {
        super.onProgressUpdate(values);
        mAsyncTaskListener.onItemLoadComplete(values[0]);
    }

    @Override
    protected void onPostExecute(List<Repo> repos) {
        super.onPostExecute(repos);
        if (repos != null) mAsyncTaskListener.onLoadComplete(repos);
        else mAsyncTaskListener.onLoadFail();
    }

    public void setListener(AsyncTaskListener listener) {
        mAsyncTaskListener = listener;
    }

    private List<Repo> loadData(String response) {
        List<Repo> repos = new ArrayList<>();
        try {
            JSONArray repoArray = new JSONArray(response);
            for (int i = 0; i < repoArray.length(); i++) {
                JSONObject repoObject = repoArray.getJSONObject(i);
                String name = repoObject.getString(RESPONSE_NAME);
                String description = repoObject.getString(RESPONSE_DESCRIPTION);
                String url = repoObject.getString(RESPONSE_HTML_URL);
                JSONObject owner = repoObject.getJSONObject(RESPONSE_OWNER);
                String avatarUrl = owner.getString(RESPONSE_AVATAR_URL);
                Repo repo = new Repo(name, description, avatarUrl, url);
                publishProgress(repo);
                repos.add(repo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repos;
    }

    private String readResponse(InputStream inputStream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        return buffer.toString();
    }
}
