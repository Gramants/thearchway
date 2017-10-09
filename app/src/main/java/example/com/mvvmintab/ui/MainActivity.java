package example.com.mvvmintab.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.mvvmintab.adapters.IssueDataAdapter;
import example.com.mvvmintab.R;
import example.com.mvvmintab.adapters.RecyclerItemClickListener;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.RootViewModel;

public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private IssueDataAdapter mAdapter;
    private EditText mSearchEditText;
    private RootViewModel mViewModel;
    private List<IssueDataModel> caches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(RootViewModel.class);
        setupView();

        mSearchEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String repo = mSearchEditText.getText().toString();
                if (repo.length() > 0) {
                    String[] query = repo.split("/");
                    if (query.length == 2) {
                        hideSoftKeyboard(MainActivity.this, v);
                        setProgress(true);
                        mViewModel.loadIssues(query[0], query[1],true);
                        mViewModel.loadContributor(query[0], query[1],true);
                        mViewModel.saveSearchString(query[0]+"/"+query[1]);
                    } else {
                        handleError("Error wrong format of input. Required format owner/repository_name");
                    }
                } else {
                    handleError("IssueRepository name empty. Required format owner/repository_name");
                }
                return true;
            }
            return false;
        });

        // Handle changes emitted by LiveData
        mViewModel.getApiIssueResponse().observe(this, apiResponse -> {
            if (apiResponse== null) {
                handleError("No data to show!");
            } else {
                this.caches=apiResponse;
                handleResponse(apiResponse);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSearchEditText = (EditText) findViewById(R.id.et_search);

        // Setup Progress Dialog to show loading state
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle(getString(R.string.progress_title));
        mDialog.setMessage(getString(R.string.progress_body));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mAdapter = new IssueDataAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", ((IssueDataModel) caches.get(position)).getId());
                        intent.putExtras(b);
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
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

                if (direction == ItemTouchHelper.LEFT) { //swipe left

                    Toast.makeText(getApplicationContext(),"Swipped to left",Toast.LENGTH_SHORT).show();

                }else if(direction == ItemTouchHelper.RIGHT){//swipe right

                    Toast.makeText(getApplicationContext(),"Swipped to right",Toast.LENGTH_SHORT).show();

                }

                mViewModel.deleteIssueRecordById( ((IssueDataModel)  caches.get(position)).getId());

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void handleResponse(List<IssueDataModel> issues) {
        setProgress(false);
        if (issues != null && issues.size() > 0) {
            mAdapter.addIssues(issues);

        } else {

            mAdapter.clearIssues();
            Toast.makeText(
                    this,
                    "No data!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(String msg) {
        setProgress(false);
        mAdapter.clearIssues();
        Log.e(TAG, msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void setProgress(boolean flag) {
        if (flag) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.loadIssues("fromdb", "fromdb",false);
        mViewModel.getApiIssueResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                this.caches=apiResponse;
                handleResponse(apiResponse);

            }
        });

        mViewModel.getSearchString().observe(this, searchString -> {
            mSearchEditText.setText(searchString);
        });


    }





}


//https://caster.io/lessons/android-architecture-components-transforming-livedata/