package me.noahandrews.stationarybarlistview;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;


public class MainActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mHeaderView;
    private View mToolbarView;
    private View mToolbar2View;
    private int mBaseTranslationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView,getResources().getDimension(R.dimen.toolbar_elevation));
        mToolbarView = findViewById(R.id.toolbar);
        mToolbar2View = findViewById(R.id.toolbar2);
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
        }
    }

    @Override
    public void onDownMotionEvent(){
        return;
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState){
        mBaseTranslationY = 0;

        int toolbarHeight = mToolbarView.getHeight();
        final ObservableListView listView = (ObservableListView) findViewById(R.id.scroll);
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
