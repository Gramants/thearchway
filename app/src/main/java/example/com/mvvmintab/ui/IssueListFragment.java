package example.com.mvvmintab.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.adapters.IssueDataAdapter;
import example.com.mvvmintab.adapters.RecyclerItemClickListener;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.InterFragmentsViewModel;
import example.com.mvvmintab.viewmodels.RootViewModel;


@SuppressLint("ValidFragment")
public class IssueListFragment extends LifecycleFragment {
    int color;
    private RootViewModel mRootViewModel;
    private InterFragmentsViewModel mInterFragmentsViewModel;

    private RecyclerView mRecyclerView;
    private IssueDataAdapter mAdapter;
    private ProgressBar marker_progress;
    private List<IssueDataModel> cache;

    @SuppressLint("ValidFragment")
    public IssueListFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itemlist_fragment, container, false);
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
                if (apiResponse.size()>0)
                {
                handleResponse(apiResponse);
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
            marker_progress.setVisibility(showDialog?View.VISIBLE:View.INVISIBLE);
            mRecyclerView.setVisibility(showDialog?View.INVISIBLE:View.VISIBLE);
            mAdapter.clearIssues();
        });


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        /*
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", ((IssueDataModel) caches.get(position)).getId());
                        intent.putExtras(b);
                        startActivity(intent);
                        */
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Log.e("STEFANO","database id to delete:"+String.valueOf( ((IssueDataModel)  cache.get(position)).getId()));
                        mRootViewModel.deleteIssueRecordById( ((IssueDataModel)  cache.get(position)).getId());


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
                mInterFragmentsViewModel.showItemBody(cache.get(position));
                mRootViewModel.deleteIssueRecordById( ((IssueDataModel)  cache.get(position)).getId());

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }



    private void handleResponse(List<IssueDataModel> elements) {

        Log.e("STEFANO","elementi:"+String.valueOf(elements.size()));


        if (!((IssueDataModel)elements.get(0)).getError().isEmpty()) {
            mAdapter.clearIssues();
            this.cache.clear();
            mRootViewModel.setSnackBar(((IssueDataModel)elements.get(0)).getError());
        } else {
            this.cache=elements;
            mAdapter.clearIssues();
            mAdapter.addIssues(elements);
            marker_progress.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }


    }


}

