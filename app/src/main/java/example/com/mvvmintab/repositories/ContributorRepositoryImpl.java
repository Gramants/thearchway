package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.pojos.Contributor;
import example.com.mvvmintab.repositories.api.GithubApiService;
import example.com.mvvmintab.repositories.database.ContributorDao;
import example.com.mvvmintab.repositories.database.ProjectDb;
import example.com.mvvmintab.repositories.database.asyncdml.ContributorDbManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.com.mvvmintab.entities.translator.DataTranslator.ContributorTranslator;


public class ContributorRepositoryImpl implements ContributorRepository {

    private ContributorDao contributorDao;
    private GithubApiService mApiService;
    private ProjectDb mProjectDb;
    @Inject
    public ContributorRepositoryImpl(ContributorDao contributorDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.contributorDao = contributorDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
    }


    public LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceRemote) {
        final MutableLiveData<List<ContributorDataModel>> liveData = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Contributor>> call = mApiService.getContributors(owner, repo);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                ArrayList<ContributorDataModel> transformed=new ArrayList();
                transformed=ContributorTranslator(response);
                deleteTableAndSaveDataToLocal(transformed);
                liveData.setValue(transformed);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                liveData.setValue(null);
            }
        });


            return liveData;
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




}