package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.githubissues.App;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.repositories.api.GithubApiService;
import example.com.githubissues.repositories.database.IssueDb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class IssueRepositoryImpl implements IssueRepository {

    public static final String BASE_URL = "https://api.github.com/";
    private GithubApiService mApiService;

    public IssueRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApiService = retrofit.create(GithubApiService.class);
    }






    public LiveData<List<Issue>> getIssues(String owner, String repo,Boolean forceRemote) {
        final MutableLiveData<List<Issue>> liveData = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Issue>> call = mApiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                deleteTableAndSaveDataToLocal(response.body());
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                liveData.setValue(null);
            }
        });
            return liveData;
        }
        else
        {
            // pick from the DB
            Log.e("STEFANO","carico da tabella locale!");
            return App.get().getDB().issueDao().getAllIssue();

        }

    }

    @Override
    public LiveData<Issue> getIssueFromDb(int id) {
        return App.get().getDB().issueDao().getIssueById(id);

    }


    private void deleteTableAndSaveDataToLocal(List<Issue> issues) {

        new AddIssueAsyncTask(App.get().getDB()).execute(issues);
       // https://stackoverflow.com/questions/44241861/room-persistent-library-with-new-thread-and-data-binding-issue
       //https://stackoverflow.com/questions/44241861/room-persistent-library-with-new-thread-and-data-binding-issue

    }




    private static class AddIssueAsyncTask extends AsyncTask<List<Issue>, Void, Void> {

        private IssueDb db;

        public AddIssueAsyncTask(IssueDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<Issue>... issues) {
            db.issueDao().deleteAll();
            List<Issue> results = new ArrayList<Issue>();
            results=issues[0];
            for (Issue issue : results) {
                db.issueDao().insert(issue);
            }
            return null;

    }
}
}