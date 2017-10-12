package example.com.mvvmintab.ui;


import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;
import example.com.mvvmintab.viewmodels.RootViewModel;

public class ViewPagerActivity extends LifecycleActivity {

    private RootViewModel mViewModel;
    private SearchView searchview;
    private ProgressDialog mDialog;
    private CoordinatorLayout mCoordinator;
    private String mSearchString;
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
        mCoordinator= (CoordinatorLayout) findViewById(R.id.tabanim_maincontent);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        //showToast("One");
                        break;
                    case 1:
                        //showToast("Two");
                        break;
                    case 2:
                        //showToast("Three");
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



        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String repo) {
                        mSearchString=repo;
                        mViewModel.askNetWork();
                        return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        mViewModel.getSearchString().observe(this, searchString -> {
            searchview.setIconified(false);
            searchview.setQuery(new String(searchString),false);

        });


        mViewModel.isInternetConnected().observe(this, isConnected -> {
            manageSearch(isConnected);
        });

        mViewModel.getNetworkErrorResponse().observe(this, networkError -> {
            manageNetworkError(networkError);
        });
        mViewModel.getSnackBar().observe(this, snackMsg -> {
            handleError(snackMsg);
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new IssueListFragment(), "Issues");
        adapter.addFrag(new ContributorListFragment(), "Contributors");
        adapter.addFrag(new FragmentDetail(),"Detail");
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

        searchview.clearFocus();

        getLocalDataIfAny();

    }

    private void manageNetworkError(NetworkErrorObject networkError) {
        handleError(((NetworkErrorObject) networkError).getErrorMsg());
    }

    private void manageSearch(Boolean isConnected) {
        if (isConnected)
            doSearch();
        else
            handleError("No internet connection available!");

    }

    private void getLocalDataIfAny() {
        mViewModel.loadIssues(null, null,false);
       // mViewModel.loadContributor(null, null,false);
    }


    private void handleError(String snackMsg) {
        mViewModel.setDialogTab1(false);
        mViewModel.setDialogTab2(false);
        searchview.clearFocus();
        Snackbar snackbar = Snackbar.make(mCoordinator, snackMsg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }



    private void doSearch()
    {
        if (mSearchString.length() > 0) {
        String[] query = mSearchString.split("/");
        if (query.length == 2) {
            mViewModel.setDialogTab1(true);
            mViewModel.setDialogTab2(true);
            mViewModel.loadIssues(query[0], query[1],true);
            //mViewModel.loadContributor(query[0], query[1],true);
            searchview.clearFocus();
        } else {
            handleError("Error wrong format of input. Required format owner/repository_name");
        }
    } else {
        handleError("IssueRepository name empty. Required format owner/repository_name");
    }
    }


}
