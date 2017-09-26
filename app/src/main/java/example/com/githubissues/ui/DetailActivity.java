package example.com.githubissues.ui;


import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import example.com.githubissues.R;
import example.com.githubissues.databinding.ActivityDetail2Binding;
import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.viewmodels.DetailViewModel;

public class DetailActivity extends LifecycleActivity {


    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int id = -1;
        if(b != null)
            id = b.getInt("id");

        ActivityDetail2Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_detail2);

        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.loadIssue(id);

        mViewModel.getdbResponse().observe(this, apiResponse -> {
                handleResponse(apiResponse,binding);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void handleResponse(IssueDataModel issue, ActivityDetail2Binding binding) {
        binding.setIssue(issue);
        Log.e("STEFANO","Issue title: "+issue.getTitle());

    }



}


//https://caster.io/lessons/android-architecture-components-transforming-livedata/