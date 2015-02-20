package me.noahandrews.stationarybarlistview;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;


public class MainActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mHeaderView;
    private View mToolbarView;
    private View mToolbar2View;
    private int mBaseTranslationY;
    String TAG = "SCROLLING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView,getResources().getDimension(R.dimen.toolbar_elevation));
        mToolbarView = findViewById(R.id.toolbar);
        mToolbar2View = findViewById(R.id.toolbar2);
        //hideToolbar();

        String resultHidden = toolbarIsHidden() ? "hidden" : "shown";
        Log.d(TAG, "Using toolbarIsHidden, he toolbar is " + resultHidden);

        String resultShown = toolbarIsShown() ? "shown" : "hidden";
        Log.d(TAG, "Using toolbarIsShown, he toolbar is " + resultShown);

        Log.d(TAG, "TranslationY is " + String.valueOf(mHeaderView.getTranslationY()));

        Log.d(TAG,"mToolbarView.getHeight()* -1 is " + String.valueOf(mToolbarView.getHeight()* -1));
        Log.d(TAG,"mToolbarView.getHeight() is " + String.valueOf(mToolbarView.getHeight()));
        Log.d(TAG,"mToolbarView.getMeasuredHeight() is " + String.valueOf(mToolbarView.getMeasuredHeight()));
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging){
        if(dragging){
            int toolbarHeight = mToolbarView.getHeight();
            float currentHeaderTranslationY = mHeaderView.getTranslationY();
            if(firstScroll){
                if(-toolbarHeight < currentHeaderTranslationY){
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            mHeaderView.animate().cancel();
            mHeaderView.setTranslationY(headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent(){
        return;
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState){
        mBaseTranslationY = 0;

        //Fragment fragment = getCurrentFr

        int toolbarHeight = mToolbarView.getHeight();
        final ObservableListView listView = (ObservableListView) findViewById(R.id.scroll);
        if(listView == null){
            return;
        }
        int scrollY = listView.getCurrentScrollY();
        if(scrollState == ScrollState.DOWN){
            showToolbar();
        } else if(scrollState == ScrollState.UP){
            if(toolbarHeight <= scrollY ){
                hideToolbar();
            }
            else {
                showToolbar();
            }
        } else {
            //Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if(!toolbarIsShown() && !toolbarIsHidden()){
                //This triggers when the toolbar is between states.
                hideToolbar(); //This should be tested both ways.
            }
        }
    }

    private boolean toolbarIsShown(){
        return mHeaderView.getTranslationY() == 0;
    }

    private boolean toolbarIsHidden(){
        if(mHeaderView.getTranslationY() == mToolbarView.getHeight()* -1){
            return true;
        }
        else{
            return false;
        }
        //return mHeaderView.getTranslationY() == -mToolbarView.getHeight();
    }

    private void showToolbar(){
        float headerTranslationY = mHeaderView.getTranslationY();
        if(headerTranslationY != 0){
            mHeaderView.animate().cancel();
            mHeaderView.animate().translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar(){
        float headerTranslationY = mHeaderView.getTranslationY();
        int toolbarHeight = mToolbarView.getHeight();
        if(headerTranslationY != -toolbarHeight){
            mHeaderView.animate().cancel();
            mHeaderView.animate().translationY(-toolbarHeight).setDuration(200).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
