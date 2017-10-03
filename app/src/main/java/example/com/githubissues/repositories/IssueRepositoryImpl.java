package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.githubissues.App;
import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.entities.pojos.Issue;
import example.com.githubissues.repositories.api.GithubApiService;
import example.com.githubissues.repositories.database.DbAsyncOp;
import example.com.githubissues.repositories.database.IssueDao;
import example.com.githubissues.repositories.database.IssueDb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static example.com.githubissues.entities.translator.DataTranslator.IssueTranslator;


public class IssueRepositoryImpl implements IssueRepository {

    private IssueDao issueDao;
    private GithubApiService mApiService;
    private IssueDb mIssuedb;
    @Inject
    public IssueRepositoryImpl(IssueDao issueDao, IssueDb mIssuedb, GithubApiService mApiService) {
        this.issueDao = issueDao;
        this.mApiService = mApiService;
        this.mIssuedb = mIssuedb;
    }





    public LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceRemote) {
        final MutableLiveData<List<IssueDataModel>> liveData = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Issue>> call = mApiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                ArrayList<IssueDataModel> transformed=new ArrayList();
                transformed=IssueTranslator(response);
                deleteTableAndSaveDataToLocal(transformed);
                liveData.setValue(transformed);
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
            return issueDao.getAllIssue();

        }

    }



    @Override
    public LiveData<IssueDataModel> getIssueFromDb(int id) {
        return issueDao.getIssueById(id);

    }

    @Override
    public void deleteRecordById(int id) {
        new DbAsyncOp.DeleteIssueByIdAsyncTask(mIssuedb).execute(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<IssueDataModel> issues) {
        new DbAsyncOp.AddIssueAsyncTask(mIssuedb).execute(issues);
    }





}