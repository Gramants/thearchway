package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
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
    @Inject
    public IssueRepositoryImpl(IssueDao issueDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.issueDao = issueDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
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
        new IssueDbManager.DeleteIssueByIdAsyncTask(mProjectDb).execute(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<IssueDataModel> issues) {
        new IssueDbManager.AddIssueAsyncTask(mProjectDb).execute(issues);
    }





}