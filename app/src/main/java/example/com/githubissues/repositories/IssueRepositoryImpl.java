package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import example.com.githubissues.entities.ApiResponse;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.api.GithubApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James on 5/21/2017.
 */

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

    public LiveData<ApiResponse> getIssues(String owner, String repo,Boolean forceRemote) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Issue>> call = mApiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        }
        else
        {
            // pick from the DB
            Log.e("STEFANO","carico da tabella per ora un insieme vuoto!");
            List temp = null;
            liveData.setValue(new ApiResponse(temp));

        }
        return liveData;
    }

}
