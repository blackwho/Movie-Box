package com.example.appjo.moviebox;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appjo.moviebox.Fragments.DashboardFragment;
import com.example.appjo.moviebox.Fragments.SearchFragment;

public class DashboardActivity extends AppCompatActivity {
    public EditText searchBox;
    public FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardAdd();
        searchBox = findViewById(R.id.search_box);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showSearchResults(v.getText().toString());
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
    }

    //manipulating the SearchFragment
    private void showSearchResults(String searchTerm){
        SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentByTag("SearchFrag");
        if(searchFragment != null){
            searchFragment.setSearchTerm(searchTerm);
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            SearchFragment newSearchFragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("searchTerm", searchTerm);
            newSearchFragment.setArguments(bundle);
            transaction.replace(R.id.dashboard_fragment_container, newSearchFragment, "SearchFrag")
                    .addToBackStack(null)
                    .commit();
        }

    }

    //adds the dashboard fragment
    private void dashboardAdd(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashboardFragment dashboardFrag = new DashboardFragment();
        fragmentTransaction.add(R.id.dashboard_fragment_container, dashboardFrag);
        fragmentTransaction.commit();
    }

    public int getInteger(){
        return 3;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
}
