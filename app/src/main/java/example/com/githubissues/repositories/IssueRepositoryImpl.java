package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.githubissues.App;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.entities.IssueLinearized;
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






    public LiveData<List<IssueLinearized>> getIssues(String owner, String repo, Boolean forceRemote) {
        final MutableLiveData<List<IssueLinearized>> liveData = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Issue>> call = mApiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                ArrayList<IssueLinearized> transformed=new ArrayList();
                transformed=LinearizeIssue(response);
                deleteTableAndSaveDataToLocal(transformed);


                liveData.setValue(transformed);


                // use the transformation for livedata!
                /*
                LiveData<List<String>> liveCustomerIds=
                        Transformations.map(store.allCustomers(),
                                new Function<List<Customer>, List<String>>() {
                                    @Override
                                    public List<String> apply(List<Customer> customers) {
                                        ArrayList<String> result=new ArrayList<>();

                                        for (Customer customer : customers) {
                                            result.add(customer.id);
                                        }

                                        return(result);
                                    }
                                });

                  */





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

    private ArrayList<IssueLinearized> LinearizeIssue(Response<List<Issue>> issues) {
        ArrayList<IssueLinearized> transformed=new ArrayList();

        for (Issue issue : issues.body()) {
            transformed.add(new IssueLinearized(issue.getId(),issue.getUrl(),issue.getRepositoryUrl(),issue.getNumber(),issue.getTitle(),issue.getState(),issue.getCreatedAt(),issue.getBody(),issue.getUser().getLogin(),issue.getUser().getUrl()));
        }

        return transformed;
    }

    @Override
    public LiveData<IssueLinearized> getIssueFromDb(int id) {
        return App.get().getDB().issueDao().getIssueById(id);

    }

    @Override
    public void deleteRecordById(int id) {
        new DeleteIssueByIdAsyncTask(App.get().getDB()).execute(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<IssueLinearized> issues) {

        new AddIssueAsyncTask(App.get().getDB()).execute(issues);
       // https://stackoverflow.com/questions/44241861/room-persistent-library-with-new-thread-and-data-binding-issue
       //https://stackoverflow.com/questions/44241861/room-persistent-library-with-new-thread-and-data-binding-issue

    }




    private static class AddIssueAsyncTask extends AsyncTask<List<IssueLinearized>, Void, Void> {

        private IssueDb db;

        public AddIssueAsyncTask(IssueDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<IssueLinearized>... issues) {
            db.issueDao().deleteAll();
            List<IssueLinearized> results = new ArrayList<IssueLinearized>();
            results=issues[0];
            for (IssueLinearized issue : results) {
                db.issueDao().insert(issue);
            }
            return null;

    }
}

    private class DeleteIssueByIdAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private IssueDb db;
        public DeleteIssueByIdAsyncTask(IssueDb userDatabase) {db = userDatabase;}

        @Override
        protected Void doInBackground(Integer... integers) {
            db.issueDao().deleteById(integers[0]);
            return null;
        }
    }
}