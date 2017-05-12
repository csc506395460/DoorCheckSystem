package com.csc.cm.doorchecksystem.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.data.model.LoginResult;
import com.csc.cm.doorchecksystem.ui.fragment.MainFragment;
import com.csc.cm.doorchecksystem.ui.fragment.PersonnalCenterFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private MainFragment mainFragment;
    private PersonnalCenterFragment personFragment;

    private FragmentTransaction transaction = null;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentManager fragmentManager = null;

    private long exitTime = 0;

    private LoginResult.UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mUserInfo = (LoginResult.UserInfo) getIntent().getSerializableExtra("userInfo");
        setContentView(R.layout.activity_main);

        initView();
        initEvents();
    }

    private void initView() {
        mainFragment = new MainFragment();
        personFragment = new PersonnalCenterFragment();

        fragments.add(mainFragment);
        fragments.add(personFragment);

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment, mainFragment);
        transaction.add(R.id.main_fragment, personFragment);
        transaction.show(mainFragment).hide(personFragment);
        transaction.commitAllowingStateLoss();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
//            finish();
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public LoginResult.UserInfo getUserInfo () {
        return mUserInfo;
    }

    /**
     * 打开侧滑菜单
     */
    public void OpenLeftMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);
    }

    /**
     * fragment切换并隐藏菜单栏。
     * @param type 0：跳转到主页， 1:跳转到个人中心.
     */
    public void jump(int type) {
        transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragments.size(); i++) {
            if (type == i) {
                transaction.show(fragments.get(type));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }


}
