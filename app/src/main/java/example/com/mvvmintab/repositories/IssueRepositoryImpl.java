package example.com.mvvmintab.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.Config;
import example.com.mvvmintab.Utils;
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


        if (forceRemote) {
            Call<List<Issue>> call = mApiService.getIssues(owner, repo);
            call.enqueue(new Callback<List<Issue>>() {
                @Override
                public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                    if (response.isSuccessful()) {
                        deleteTableAndSaveDataToLocal(IssueTranslator(response));
                    } else {
                        liveDataError.setValue(new NetworkErrorObject(response.code(), response.message(), Config.ISSUE_ENDPOINT));
                    }

                }

                @Override
                public void onFailure(Call<List<Issue>> call, Throwable t) {
                    liveDataError.setValue(new NetworkErrorObject(0, "Unknown error", Config.ISSUE_ENDPOINT));
                }
            });

            return liveDataResult;
        } else {

            LiveData<List<IssueDataModel>> transformedDbOutput =
                    Transformations.switchMap(issueDao.getAllIssue(),
                            new Function<List<IssueDataModel>, LiveData<List<IssueDataModel>>>() {
                                @Override
                                public LiveData<List<IssueDataModel>> apply(List<IssueDataModel> issueDataModels) {

                                    ArrayList<IssueDataModel> result = new ArrayList<>();
                                    for (IssueDataModel issue : issueDataModels) {
                                        issue.setTitle(issue.getTitle().trim().toUpperCase() + " ORDERED");
                                        result.add(issue);
                                    }
                                    Collections.sort(result, new Utils.CustomComparator());
                                    return getTransformedDbResult(result);
                                }
                            });

            return transformedDbOutput;


        }

    }

    private LiveData<List<IssueDataModel>> getTransformedDbResult(List<IssueDataModel> result) {
        return new LiveData<List<IssueDataModel>>() {
            @Override
            protected void onActive() {
                setValue(result);
            }
        };
    }


    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {
        return liveDataError;
    }


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