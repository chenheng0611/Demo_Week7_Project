package com.xray.demo_week7_project;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout = null;
    private LinearLayout llDrawer = null;
    private ActionBarDrawerToggle toggle = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private ListView listView = null;
    private PullToRefreshListView refreshListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        llDrawer = (LinearLayout) findViewById(R.id.ll_drawer);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        listView = (ListView) findViewById(R.id.listView);
        refreshListView = (PullToRefreshListView) findViewById(R.id.refresh_list);
        refreshListView(0);

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView(1);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(MainActivity.this,"Refreshing",Toast.LENGTH_SHORT).show();
                refreshListView.setRefreshing(false);
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(MainActivity.this,"Load More",Toast.LENGTH_SHORT).show();
                refreshListView.setRefreshing(false);
                refreshListView.onRefreshComplete();
            }
        });
//        toggle = new ActionBarDrawerToggle(this,drawerLayout,
//                R.string.open,R.string.close){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle("Open");
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                getSupportActionBar().setTitle("Close");
//                invalidateOptionsMenu();
//            }
//        };
//        drawerLayout.setDrawerListener(toggle);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void refreshListView(int flag){
        List<String> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            list.add("Item:"+new Random().nextDouble());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        if(flag == 0){
            listView.setAdapter(adapter);
        }else{
            refreshListView.setAdapter(adapter);
        }

    }

    public void openDrawer(View view) {
        if(drawerLayout.isDrawerOpen(llDrawer)){
            drawerLayout.closeDrawer(llDrawer);
        }else{
            drawerLayout.openDrawer(llDrawer);
        }
    }
}
