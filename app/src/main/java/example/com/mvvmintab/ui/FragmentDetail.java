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




    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mInterFragmentsViewModel.getIssueDetail().observe(this, resource -> {
            {
            mFragmentDetailBinding.setIssue(resource);
            mFragmentDetailBinding.executePendingBindings();
            }
        });

        mInterFragmentsViewModel.getContributorContent().observe(this, resource -> {
            {
                mFragmentDetailBinding.setContributor(resource);
                mFragmentDetailBinding.executePendingBindings();
            }
        });

    }


}
