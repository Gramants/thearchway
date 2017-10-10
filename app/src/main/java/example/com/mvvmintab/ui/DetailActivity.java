package example.com.mvvmintab.ui;


import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import example.com.mvvmintab.viewmodels.InterFragmentsViewModel;

public class DetailActivity extends LifecycleActivity {


    private InterFragmentsViewModel mViewModel;
    private int id;
    private FragmentDetail fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        id = -1;
        if(b != null)
            id = b.getInt("id");


        mViewModel = ViewModelProviders.of(this).get(InterFragmentsViewModel.class);
        if (savedInstanceState == null) {
            Fragment newFragment = new FragmentDetail();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content,newFragment,"FragmentDetail").commit();
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.loadIssue(id);
            }
        }, 300);





    }


}


//https://caster.io/lessons/android-architecture-components-transforming-livedata/