package example.com.mvvmintab.ui;



import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.mvvmintab.R;
import example.com.mvvmintab.databinding.FragmentDetailBinding;
import example.com.mvvmintab.viewmodels.FragmentCommunicationViewModel;

public class FragmentDetail extends LifecycleFragment {


    private FragmentCommunicationViewModel mFragmentCommunicationViewModel;
    private FragmentDetailBinding mFragmentDetailBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentCommunicationViewModel = ViewModelProviders.of(getActivity()).get(FragmentCommunicationViewModel.class);
        mFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return mFragmentDetailBinding.getRoot();
    }




    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentCommunicationViewModel.getIssueDetail().observe(this, resource -> {
            {
            mFragmentDetailBinding.setIssue(resource);
            mFragmentDetailBinding.executePendingBindings();
            }
        });

        mFragmentCommunicationViewModel.getContributorContent().observe(this, resource -> {
            {
                mFragmentDetailBinding.setContributor(resource);
                mFragmentDetailBinding.executePendingBindings();
            }
        });
        mFragmentCommunicationViewModel.getIssueContent().observe(this, resource -> {
            {
                mFragmentDetailBinding.setIssue(resource);
                mFragmentDetailBinding.executePendingBindings();
            }
        });
    }


}
