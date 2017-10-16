package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.Config;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;
import example.com.mvvmintab.entities.pojos.Contributor;
import example.com.mvvmintab.repositories.api.GithubApiService;
import example.com.mvvmintab.repositories.database.ContributorDao;
import example.com.mvvmintab.repositories.database.ProjectDb;
import example.com.mvvmintab.repositories.database.asyncdml.ContributorDbManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.com.mvvmintab.entities.translator.DataTranslator.ContributorTranslator;
import static example.com.mvvmintab.entities.translator.DataTranslator.IssueTranslator;


public class ContributorRepositoryImpl implements ContributorRepository {

    private ContributorDao contributorDao;
    private GithubApiService mApiService;
    private ProjectDb mProjectDb;
    private MutableLiveData<NetworkErrorObject> liveDataError = new MutableLiveData<>();
    @Inject
    public ContributorRepositoryImpl(ContributorDao contributorDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.contributorDao = contributorDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
    }


    public LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceRemote) {
        final MutableLiveData<List<ContributorDataModel>> liveDataResult = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Contributor>> call = mApiService.getContributors(owner, repo);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                if (response.isSuccessful()) {
                    ArrayList<ContributorDataModel> transformed=new ArrayList();
                    transformed=ContributorTranslator(response);
                    deleteTableAndSaveDataToLocal(transformed);
                    //liveDataResult.setValue(transformed);
                }
                else
                {
                    //REST ERROR
                    liveDataError.setValue(new NetworkErrorObject(response.code(),response.message() , Config.CONTRIBUTOR_ENDPOINT));
                }

            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                liveDataError.setValue(new NetworkErrorObject(0,"Unknown error",Config.CONTRIBUTOR_ENDPOINT));
            }
        });




            return liveDataResult;
        }
        else
        {
            // pick from the DB
            return contributorDao.getAllContributors();

        }

    }

    @Override
    public LiveData<ContributorDataModel> getContributorFromDb(int id) {
        return contributorDao.getContributorById(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<ContributorDataModel> contributors) {
        new ContributorDbManager.AddContributorsAsyncTask(mProjectDb).execute(contributors);
    }


    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {return liveDataError;}

}