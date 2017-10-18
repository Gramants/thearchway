package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import example.com.mvvmintab.App;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.IssueRepository;


public class FragmentCommunicationViewModel extends AndroidViewModel {

    private MediatorLiveData<IssueDataModel> mIssueDetail;
    final MutableLiveData<IssueDataModel> liveDataShowIssueContent= new MutableLiveData<>();
    final MutableLiveData<ContributorDataModel> liveDataShowContributorContent= new MutableLiveData<>();

    @Inject
    IssueRepository mIssueRepository;

    @Inject
    ContributorRepository mContributorRepository;

    public FragmentCommunicationViewModel(Application application) {
        super(application);
        mIssueDetail = new MediatorLiveData<>();
        ((App) application).getAppRepositoryComponent().inject(this);

    }


    @NonNull
    public LiveData<IssueDataModel> getIssueDetail() {
        return mIssueDetail;
    }


    public LiveData<IssueDataModel> loadIssue(int id) {
        mIssueDetail.addSource(
                mIssueRepository.getIssueFromDb(id),
                dbResponse -> mIssueDetail.setValue(dbResponse)
        );

        return mIssueDetail;
    }



    public LiveData<IssueDataModel> getIssueContent() {

        return Transformations.map(liveDataShowIssueContent, new Function<IssueDataModel, IssueDataModel>() {
            @Override
            public IssueDataModel apply(IssueDataModel input) {
                input.setTitle(input.getTitle()+" !MAP transformed");
                return input;
            }
        });

    }
    public void showIssueContent(IssueDataModel issueDataModel) {

        liveDataShowIssueContent.setValue(issueDataModel);

    }

    public MutableLiveData<ContributorDataModel> getContributorContent() {
        return liveDataShowContributorContent;
    }
    public void showContributorContent(ContributorDataModel contributorDataModel) {
        liveDataShowContributorContent.setValue(contributorDataModel);
    }
}