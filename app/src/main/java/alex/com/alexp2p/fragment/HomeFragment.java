package alex.com.alexp2p.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;

import alex.com.alexp2p.R;
import alex.com.alexp2p.bean.Index;
import alex.com.alexp2p.common.AppNetConfig;
import alex.com.alexp2p.common.BaseFragment;
import alex.com.alexp2p.ui.RoundProgress;
import alex.com.alexp2p.util.UIUtils;
import butterknife.Bind;

/**
 * Created by shkstart on 2016/11/30 0030.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.textView1)
    TextView mTextView1;
    @Bind(R.id.roundPro_home)
    RoundProgress roundProHome;
    @Bind(R.id.p_yearlv)
    TextView mPYearlv;
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.myscrollview)
    ScrollView mMyscrollview;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            roundProHome.setMax(100);
            for (int i = 0; i < currentProress; i++) {
                roundProHome.setProgress(i + 1);

                SystemClock.sleep(20);
                //强制重绘
//                roundProHome.invalidate();//只有主线程才可以如此调用
                roundProHome.postInvalidate();//主线程、分线程都可以如此调用
            }
        }
    };
    private int currentProress;

    @Override
    protected RequestParams getParams() {
//        return new RequestParams();//皆可
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
//        return null;
    }

    private Index index;

    @Override
    protected void initData(String content) {
        if (!TextUtils.isEmpty(content)) {
            index = new Index();
            //解析Json数据
            index = new Gson().fromJson(content, Index.class);
            //更新页面数据
            mTextView1.setText(index.getProInfo().getName());
            mPYearlv.setText(index.getProInfo().getYearRate() + "%");
            currentProress = Integer.parseInt(index.getProInfo().getProgress());
            Log.e("TAG", "onSuccess: "+index.getProInfo().getProgress());
//                //设置viewpager
//                mVpBarner.setAdapter(new MyAdapter());
//                //设置小圆点
//                mCircleBarner.setViewPager(mVpBarner);

            //在分线程中，实现进度的动态变化
            new Thread(runnable).start();


            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE); //小红点
//                banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);//数字 例:1/4
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());

            //设置图片集合
            ArrayList<String> imageUrl = new ArrayList<String>(index.getImageArr().size());
            for (int i = 0; i < index.getImageArr().size(); i++) {
                imageUrl.add(index.getImageArr().get(i).getIMAURL());
            }
            banner.setImages(imageUrl);
            Log.e("imageUrl",""+imageUrl);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"赚了钱去马尔代夫", "人脉总动员", "想不到你是这样的app", "购物节，爱不单行"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    UIUtils.toast(""+position,false);
                }
            });

            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }

    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.GONE);
        mTvTitle.setText("首页");
        mIvTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：
             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */


            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }
}
