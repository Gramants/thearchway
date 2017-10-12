package example.com.mvvmintab.ui;



import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.mvvmintab.R;
import example.com.mvvmintab.databinding.FragmentDetailBinding;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.InterFragmentsViewModel;

public class FragmentDetail extends LifecycleFragment {


    private InterFragmentsViewModel mInterFragmentsViewModel;
    private FragmentDetailBinding mFragmentDetailBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInterFragmentsViewModel = ViewModelProviders.of(getActivity()).get(InterFragmentsViewModel.class);
        mFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return mFragmentDetailBinding.getRoot();
    }




    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        subscribeFragmentObservers();
    }
    
    
    private void subscribeFragmentObservers() {

        mInterFragmentsViewModel.getIssueDetail().observe((LifecycleOwner) getActivity(),
                new Observer<IssueDataModel>() {
                    @Override
                    public void onChanged(@Nullable IssueDataModel item) {
                        if (item != null) {
                            mFragmentDetailBinding.setIssue(item);
                        }
                    }
        });


        mInterFragmentsViewModel.getIssueContent().observe((LifecycleOwner) getActivity(),
                new Observer<IssueDataModel>() {
                    @Override
                    public void onChanged(@Nullable IssueDataModel item) {
                        if (item != null) {
                            mFragmentDetailBinding.setIssue(item);
                        }
                    }
                });


        mInterFragmentsViewModel.getContributorContent().observe((LifecycleOwner) getActivity(),
                new Observer<ContributorDataModel>() {
                    @Override
                    public void onChanged(@Nullable ContributorDataModel item) {
                        if (item != null) {
                            mFragmentDetailBinding.setContributor(item);
                        }
                    }
                });

    }


}
