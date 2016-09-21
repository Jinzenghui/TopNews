package com.example.biac.newstop;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biac.newstop.fragment.TopNewsFragment;

public class MainActivity extends AppCompatActivity implements TopNewsFragment.OnFragmentInteractionListener{

    private TextView mTextView;
    private ImageView mImageView;

    private Fragment currentFragment;

    private FrameLayout mFragmentContainer;

    MenuItem currentMenuItem;

    SimpleArrayMap<Integer, String> mTitleArrayMap = new SimpleArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mFragmentContainer = (FrameLayout)findViewById(R.id.fragment_container);

        currentFragment = new TopNewsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();

//        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
//
//        //起到监听的效果
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        mTextView = (TextView)findViewById(R.id.textView);
//        mImageView = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
//        mImageView.setOnClickListener(this);

//        addfragmentsAndTitle();
    }

//    @Override
//    public void onBackPressed(){
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if(drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }else{
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        currentMenuItem = item;
//
//        int id = item.getItemId();
//        String string = null;
//        switch (id){
//            case R.id.topnewsitem:
//                switchFragment(getFragmentById(currentMenuItem.getItemId()), mTitleArrayMap.get(currentMenuItem.getItemId()));
//                break;
//            case R.id.nav_about:
//                string = "关于";
//                break;
//            case R.id.nav_friend:
//                string = "好友";
//                break;
//            case R.id.nav_manage:
//                string = "通知";
//                break;
//            case R.id.nav_message:
//                string = "私信";
//                break;
//            case R.id.nav_night:
//                string = "夜间模式";
//                break;
//            case R.id.nav_notification:
//                string = "通知";
//                break;
//            case R.id.nav_setting:
//                string= "设置";
//                break;
//            case R.id.nav_suggestion:
//                string = "意见反馈";
//                break;
//            case R.id.nav_theme:
//                string = "主题风格";
//                break;
//        }
////        if (!TextUtils.isEmpty(string))
////            mTextView.setText("你点击了"+string);
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    private Fragment getFragmentById(int id){
//        Fragment fragment = null;
//        switch(id){
//            case R.id.topnewsitem:
//                break;
//        }
//
//        return fragment;
//    }

//    private void addfragmentsAndTitle(){
//        mTitleArrayMap.put(R.id.topnewsitem, getResources().getString(R.string.topnews));
//    }
//
//    private void switchFragment(Fragment fragment, String title){
//
//        if(currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName()))
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//
//        currentFragment = fragment;
//    }

    public interface LoadingMore{
        void loadingStart();
        void loadingfinish();
    }
}
