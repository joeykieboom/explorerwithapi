package com.skiefboom.cocktailexplorerwithapi;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.skiefboom.cocktailexplorerwithapi.data.api.Api;
import com.skiefboom.cocktailexplorerwithapi.data.api.ApiCallback;
import com.skiefboom.cocktailexplorerwithapi.data.api.responses.DrinkListResponse;
import com.skiefboom.cocktailexplorerwithapi.data.database.models.Cocktail;
import com.skiefboom.cocktailexplorerwithapi.helpers.PersistentHelper;
import com.skiefboom.cocktailexplorerwithapi.listeners.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_collapsing_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.activity_main_fab) FloatingActionButton fab;

    @BindView(R.id.activity_main_placeholder) LinearLayout viewPlaceHolder;
    @BindView(R.id.activity_main_placeholder_btn) Button btnPlaceHolder;
    @BindView(R.id.activity_main_recycler_view) RecyclerView cocktailRV;
    @BindView(R.id.activity_main_swipe_refresh) SwipeRefreshLayout refreshLayout;

    protected List<Cocktail> cocktails;
    protected CocktailAdapter cocktailAdapter;
    protected LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        long drinkCount = PersistentHelper.getDrinkCount();

        if (drinkCount <= 0) {
            getDrinks(0);
        }

        cocktails = SQLite.select().from(Cocktail.class).queryList();
        cocktailAdapter = new CocktailAdapter(cocktails);
        layoutManager = new LinearLayoutManager(getBaseContext());
        cocktailRV.setLayoutManager(layoutManager);
        cocktailRV.setAdapter(cocktailAdapter);

        cocktailRV.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                int count = PersistentHelper.getDrinkCount();

                Log.i("TAGTAG", "onLoadMore: " + count);
            }
        });

        cocktailRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        viewPlaceHolder.setVisibility(GONE);
        cocktailRV.setVisibility(View.VISIBLE);
    }

    public void getDrinks(final Integer fromCount) {

        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("start", String.valueOf(fromCount));
        attrs.put("pageSize", String.valueOf(10));

        Api.drinks.getCocktails(attrs).enqueue(new ApiCallback<DrinkListResponse>() {
            @Override
            public void onOK(Call<DrinkListResponse> call, Response<DrinkListResponse> response) {

                PersistentHelper.setDrinkCount(fromCount + 10);
                refreshData();
            }

            @Override
            public void onError(Call<DrinkListResponse> call, Throwable t) {

            }
        });
    }

    private void refreshData() {

        cocktails = new ArrayList<>();
        cocktails.addAll(SQLite.select().from(Cocktail.class).queryList());
        cocktailAdapter.updateList(cocktails);
        refreshLayout.setRefreshing(false);
    }

    private class CocktailAdapter extends RecyclerView.Adapter<ViewHolder> {

        protected List<Cocktail> cocktails;

        public CocktailAdapter(List<Cocktail> cocktails) {

            this.cocktails = cocktails;
        }

        public void updateList(List<Cocktail> cocktails) {
            this.cocktails = cocktails;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = getLayoutInflater().inflate(R.layout.listitem_main_cocktail, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Cocktail cocktail = cocktails.get(position);

            holder.cocktailName.setText(cocktail.name);
        }

        @Override
        public int getItemCount() {

            if(cocktails == null){
                return 0;
            }

            return cocktails.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cocktail_name) TextView cocktailName;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
