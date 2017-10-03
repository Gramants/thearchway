/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example.com.githubissues.ui;



import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import example.com.githubissues.R;
import example.com.githubissues.databinding.FragmentDetailBinding;
import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.viewmodels.DetailViewModel;


/**
 * Shows a SeekBar that is synced with a value in a ViewModel.
 */
public class FragmentDetail extends Fragment {


    private DetailViewModel mViewModel;
    private FragmentDetailBinding mFragmentDetailBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
        mFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);

        subscribeDetailLoaded();
        return mFragmentDetailBinding.getRoot();


    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }
    
    
    private void subscribeDetailLoaded() {

        mViewModel.getdbResponse().observe((LifecycleOwner) getActivity(),

                new Observer<IssueDataModel>() {
                    @Override
                    public void onChanged(@Nullable IssueDataModel detailFromDb) {

                        if (detailFromDb != null) {
                            handleResponse(detailFromDb,mFragmentDetailBinding);

                        }
                    }


        });
    }


    private void handleResponse(IssueDataModel issue, FragmentDetailBinding binding) {
        binding.setIssue(issue);
    }
}
