package example.com.mvvmintab.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.DetailViewModel;
import example.com.mvvmintab.viewmodels.RootViewModel;


@SuppressLint("ValidFragment")
public class DummyFragment extends LifecycleFragment {
    int color;
    private RootViewModel mRootViewModel;
    private List<IssueDataModel> caches;

    @SuppressLint("ValidFragment")
    public DummyFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        mRootViewModel = ViewModelProviders.of(getActivity()).get(RootViewModel.class);

        return view;
    }


    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mRootViewModel.getApiIssueResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.size()>0)
                {
                handleResponse(apiResponse);
                }
            }
        });

    }


    private void handleResponse(List<IssueDataModel> issues) {


        if (!((IssueDataModel)issues.get(0)).getError().isEmpty()) {

            Toast.makeText(
                    getContext(),
                    ((IssueDataModel)issues.get(0)).getError(),
                    Toast.LENGTH_SHORT
            ).show();
            //mAdapter.clearIssues();

        } else {
            Toast.makeText(
                    getContext(),
                   "trovati!",
                    Toast.LENGTH_SHORT
            ).show();


        }
    }


}

