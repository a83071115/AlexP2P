package alex.com.alexp2p.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import alex.com.alexp2p.R;
import alex.com.alexp2p.common.BaseFragment;
import alex.com.alexp2p.util.UIUtils;
import butterknife.Bind;

/**
 * Created by shkstart on 2016/11/30 0030.
 */
public class InvestFragment extends BaseFragment {

    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @Bind(R.id.tabpage_invest)
    TabPageIndicator mTabpageInvest;
    @Bind(R.id.vp_invest)
    ViewPager mVpInvest;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //添加3个Fragment到集合中
        initFragment();
        //设置viewpager适配器

        MyAdapter adapter = new MyAdapter(getFragmentManager());
        mVpInvest.setAdapter(adapter);
        mTabpageInvest.setVisibility(View.VISIBLE);
        mTabpageInvest.setViewPager(mVpInvest);
    }
    private List<Fragment> fragmentList = new ArrayList<>();
    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommondFragment);
        fragmentList.add(productHotFragment);
    }

    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }




    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList ==null ? 0 : fragmentList.size();
        }

        //提供ViewPagerIndicator显示内容

        @Override
        public CharSequence getPageTitle(int position) {
            return UIUtils.getStringArr(R.array.invest_tab)[position];
        }
    }
}
