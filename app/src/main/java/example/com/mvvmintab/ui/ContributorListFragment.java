package example.com.mvvmintab.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import example.com.mvvmintab.Config;
import example.com.mvvmintab.R;
import example.com.mvvmintab.adapters.ContributorDataAdapter;
import example.com.mvvmintab.adapters.RecyclerItemClickListener;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;
import example.com.mvvmintab.viewmodels.FragmentCommunicationViewModel;
import example.com.mvvmintab.viewmodels.RepositoryViewModel;
import example.com.mvvmintab.viewmodels.UtilityViewModel;


public class ContributorListFragment extends LifecycleFragment {
    int color;
    private RepositoryViewModel mRepositoryViewModel;
    private FragmentCommunicationViewModel mFragmentCommunicationViewModel;
    private UtilityViewModel mUtilityViewModel;

    private RecyclerView mRecyclerView;
    private ContributorDataAdapter mAdapter;
    private ProgressBar marker_progress;


    private List<ContributorDataModel> cache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.dummyfrag_bg);
        relativeLayout.setBackgroundColor(color);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);


        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mAdapter = new ContributorDataAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);

        ;
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mFragmentCommunicationViewModel.showContributorContent((ContributorDataModel) cache.get(position));
                        mUtilityViewModel.setSnackBar("Contributor name added to Detail tab");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRepositoryViewModel = ViewModelProviders.of(getActivity()).get(RepositoryViewModel.class);
        mFragmentCommunicationViewModel = ViewModelProviders.of(getActivity()).get(FragmentCommunicationViewModel.class);
        mUtilityViewModel = ViewModelProviders.of(getActivity()).get(UtilityViewModel.class);


        mRepositoryViewModel.getApiContributorResponse().observe(this,
                apiResponse -> {
                    handleResponse(apiResponse);
                }
        );


        mUtilityViewModel.getShowDialogIssueAndContributor().observe(this, showDialog -> {
            marker_progress.setVisibility(showDialog ? View.VISIBLE : View.INVISIBLE);
        });

        mRepositoryViewModel.getContributorNetworkErrorResponse().observe(this, networkError -> {
            marker_progress.setVisibility(View.INVISIBLE);
            manageNetworkError(networkError);
        });


    }


    private void handleResponse(List<ContributorDataModel> elements) {
        this.cache = elements;
        mAdapter.clearContributors();
        mAdapter.addContributors(elements);
        marker_progress.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void manageNetworkError(NetworkErrorObject networkError) {
        if (((NetworkErrorObject) networkError).getEndpointOrigin().equals(Config.CONTRIBUTOR_ENDPOINT))
            handleError(((NetworkErrorObject) networkError).getErrorMsg());
    }

    private void handleError(String snackMsg) {
        Log.d("STEFANO", "insert custom error");
    }
}

