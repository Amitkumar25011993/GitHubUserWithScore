package com.example.amit.githubuserwithscore;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amit.githubuserwithscore.Adapter.UserAdapter;
import com.example.amit.githubuserwithscore.Interface.GitHubInterface;
import com.example.amit.githubuserwithscore.Model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.github.com/";
    private static final String TAG = "MainActivity";
    SearchView searchView;

    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    List<User.ItemsBean> UserDetails;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler1);

// API Call Using Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubInterface service = retrofit.create(GitHubInterface.class);

        Call<User> call = service.getNameScore("user");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.d("MainActivity", "Status Code = " + response.code());

                if (response.isSuccessful()) {
                    UserDetails = new ArrayList<>();
                    User result = response.body();
                    UserDetails = result.getItems();

                    // Here  data loads
                    mAdapter = new UserAdapter(UserDetails);


                    //Attach to recyclerview
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    // Menu For Search Option Created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        final MenuItem mMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) mMenuItem.getActionView();

        // Default EditText Is Selected whose id is search_src_text, here we enter the text
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                mMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final  List<User.ItemsBean> filtermodelist = filter(UserDetails, newText);
                mAdapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }

    private List<User.ItemsBean> filter(List<User.ItemsBean>p1, String query)
    {
        query =query.toLowerCase();
        final List<User.ItemsBean> filterModeList = new ArrayList<>();
        for (User.ItemsBean model: p1)
        {
            final  String text = model.getLogin().toLowerCase();

            if (text.startsWith(query))
            {
                filterModeList.add(model);
            }
        }
        return filterModeList;
    }


    private void changeSearchViewTextColor(View view){
        if (view != null){
            if (view instanceof TextView){
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            }else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i =0; i<viewGroup.getChildCount(); i++){
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
}
