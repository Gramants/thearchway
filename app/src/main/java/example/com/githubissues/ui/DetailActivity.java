package example.com.githubissues.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.githubissues.R;
import example.com.githubissues.adapters.DataAdapter;
import example.com.githubissues.adapters.RecyclerItemClickListener;
import example.com.githubissues.databinding.ActivityDetailBinding;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.viewmodels.DetailViewModel;
import example.com.githubissues.viewmodels.ListIssuesViewModel;

public class DetailActivity extends LifecycleActivity {

    private final String TAG = DetailActivity.class.getName();
    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int id = -1;
        if(b != null)
            id = b.getInt("id");

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

    private void handleResponse(Issue issue, ActivityDetailBinding binding) {

        Log.e("STEFANO","Issue title: "+issue.getTitle());

    }



}


//https://caster.io/lessons/android-architecture-components-transforming-livedata/