package example.com.mvvmintab.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.adapters.IssueDataAdapter;
import example.com.mvvmintab.adapters.RecyclerItemClickListener;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.InterFragmentsViewModel;
import example.com.mvvmintab.viewmodels.RootViewModel;


public class IssueListFragment extends LifecycleFragment {
    int color;
    private RootViewModel mRootViewModel;
    private InterFragmentsViewModel mInterFragmentsViewModel;

    private RecyclerView mRecyclerView;
    private IssueDataAdapter mAdapter;
    private ProgressBar marker_progress;
    private List<IssueDataModel> cache;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.dummyfrag_bg);
        relativeLayout.setBackgroundColor(color);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
        mRootViewModel = ViewModelProviders.of(getActivity()).get(RootViewModel.class);
        mInterFragmentsViewModel = ViewModelProviders.of(getActivity()).get(InterFragmentsViewModel.class);
        return view;
    }


    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mRootViewModel.getApiIssueResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.size() > 0) {
                    handleResponse(apiResponse);
                    mRootViewModel.saveSearchString();
                }
            }
            marker_progress.setVisibility(View.INVISIBLE);

        });


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
        mAdapter = new IssueDataAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);


        mRootViewModel.showDialogTab1().observe(this, showDialog -> {
            marker_progress.setVisibility(showDialog ? View.VISIBLE : View.INVISIBLE);
            //mRecyclerView.setVisibility(showDialog ? View.INVISIBLE : View.VISIBLE);
            //mAdapter.clearIssues();
        });

        mRootViewModel.getNetworkErrorResponse().observe(this, networkError -> {
            marker_progress.setVisibility(View.INVISIBLE);
        });

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mInterFragmentsViewModel.loadIssue(((IssueDataModel) cache.get(position)).getId());
                        mRootViewModel.setSnackBar("Body opened in Detail by reading the database");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        mRootViewModel.deleteIssueRecordById(((IssueDataModel) cache.get(position)).getId());
                        mRootViewModel.setSnackBar("Record deleted and NOT OPENED in Detail tab");

                    }
                })
        );

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //swiped position
                mInterFragmentsViewModel.showIssueContent((IssueDataModel) cache.get(position));
                mRootViewModel.deleteIssueRecordById(((IssueDataModel) cache.get(position)).getId());
                mRootViewModel.setSnackBar("Record opened in Detail tab passing the object and deleted");

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


    private void handleResponse(List<IssueDataModel> elements) {
            this.cache = elements;
            mAdapter.clearIssues();
            mAdapter.addIssues(elements);
            marker_progress.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
    }



}

