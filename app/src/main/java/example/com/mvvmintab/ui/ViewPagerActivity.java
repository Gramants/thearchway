package example.com.mvvmintab.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.viewmodels.RootViewModel;

public class ViewPagerActivity extends LifecycleActivity {

    private RootViewModel mViewModel;
    private SearchView searchview;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        mViewModel = ViewModelProviders.of(this).get(RootViewModel.class);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        searchview = (SearchView) findViewById(R.id.searchstring); // inititate a search view

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        showToast("One");
                        break;
                    case 1:
                        showToast("Two");
                        break;
                    case 2:
                        showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




// perform set on query text listener event
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String repo) {

                if (repo.length() > 0) {
                    String[] query = repo.split("/");
                    if (query.length == 2) {
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                return false;
            }
        });


        mDialog = new ProgressDialog(ViewPagerActivity.this);
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle(getString(R.string.progress_title));
        mDialog.setMessage(getString(R.string.progress_body));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new IssueListFragment(ContextCompat.getColor(this, R.color.colorAccent)), "Issues");
        adapter.addFrag(new ContributorListFragment(ContextCompat.getColor(this, R.color.colorGrey)), "Contributors");
        adapter.addFrag(new DummyFragment2(ContextCompat.getColor(this, R.color.colorPrimary)), "tre");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_switch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.loadIssues("fromdb", "fromdb",false);
        mViewModel.loadContributor("fromdb", "fromdb",false);

        mViewModel.getSearchString().observe(this, searchString -> {
             searchview.setIconified(false);
             searchview.setQuery(new String(searchString),false);

         });

        mViewModel.showDialog().observe(this, showDialog -> {
            setProgress(showDialog);
        });

    }


    private void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public void setProgress(boolean flag) {
        if (flag) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }
    private void handleError(String msg) {
        setProgress(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
