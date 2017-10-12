package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;
import example.com.mvvmintab.entities.pojos.Issue;
import example.com.mvvmintab.repositories.api.GithubApiService;
import example.com.mvvmintab.repositories.database.asyncdml.IssueDbManager;
import example.com.mvvmintab.repositories.database.IssueDao;
import example.com.mvvmintab.repositories.database.ProjectDb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.com.mvvmintab.entities.translator.DataTranslator.IssueTranslator;


public class IssueRepositoryImpl implements IssueRepository {

    private IssueDao issueDao;
    private GithubApiService mApiService;
    private ProjectDb mProjectDb;
    private MutableLiveData<NetworkErrorObject> liveDataError = new MutableLiveData<>();

    @Inject
    public IssueRepositoryImpl(IssueDao issueDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.issueDao = issueDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
    }

    public LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceRemote) {

        final MutableLiveData<List<IssueDataModel>> liveDataResult = new MutableLiveData<>();


        if (forceRemote)
        {
        Call<List<Issue>> call = mApiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                if (response.isSuccessful()) {
                    ArrayList<IssueDataModel> transformed=IssueTranslator(response);
                    deleteTableAndSaveDataToLocal(transformed);
                    liveDataResult.setValue(transformed);
                }
                else
                {
                 //REST ERROR
                    Log.e("STEFANO",response.toString());
                 liveDataError.setValue(new NetworkErrorObject(response.code(),response.message()));
                }

            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                // generic error
                liveDataError.setValue(new NetworkErrorObject(0,"Unknown error"));
            }
        });


            return liveDataResult;
        }
        else
        {

            Log.e("STEFANO","sono qui");
            return issueDao.getAllIssue();

        }

    }

    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {return liveDataError;}



    @Override
    public LiveData<IssueDataModel> getIssueFromDb(int id) {
        return issueDao.getIssueById(id);

    }

    @Override
    public void deleteIssueRecordById(int id) {
        new IssueDbManager.DeleteIssueByIdAsyncTask(mProjectDb).execute(id);
    }



    private void deleteTableAndSaveDataToLocal(ArrayList<IssueDataModel> issues) {
        new IssueDbManager.AddIssueAsyncTask(mProjectDb).execute(issues);
    }





}