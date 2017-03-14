package alex.com.alexp2p.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import alex.com.alexp2p.R;
import alex.com.alexp2p.common.AppNetConfig;
import alex.com.alexp2p.common.BaseActivity;
import alex.com.alexp2p.fragment.HomeFragment;
import alex.com.alexp2p.fragment.InvestFragment;
import alex.com.alexp2p.fragment.MeFragment;
import alex.com.alexp2p.fragment.MoreFragment;
import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    private static final int WHAT_RESET_BACK = 1;
    @Bind(R.id.fl_main_content)
    FrameLayout mFlMainContent;
    @Bind(R.id.rb_main_home)
    RadioButton mRbMainHome;
    @Bind(R.id.rb_main_invest)
    RadioButton mRbMainInvest;
    @Bind(R.id.rb_main_me)
    RadioButton mRbMainMe;
    @Bind(R.id.rb_main_more)
    RadioButton mRbMainMore;
    @Bind(R.id.rg_main)
    RadioGroup mRgMain;
    private FragmentTransaction transaction;



    @Override
    protected void initData() {
        setSelect(0);
        if (AppNetConfig.FANHUI==1){
            mRgMain.check(R.id.rb_main_me);
        }else{
            mRgMain.check(R.id.rb_main_home);
            AppNetConfig.FANHUI =0;
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.rb_main_home,R.id.rb_main_invest,R.id.rb_main_me,R.id.rb_main_more})
    public void showTab(View view){
//        Toast.makeText(MainActivity.this,"选择了具体的Tab",Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.rb_main_home://首页
                setSelect(0);
                break;
            case R.id.rb_main_invest://我的投资
                setSelect(1);

                break;
            case R.id.rb_main_me://我的资产
                setSelect(2);

                break;
            case R.id.rb_main_more: //更多
                setSelect(3);

                break;
        }
    }
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;

    public void setSelect(int i) {
        FragmentManager manager = this.getSupportFragmentManager();
        transaction = manager.beginTransaction();
        hideFragment(); //隐藏所有Fragment
        switch (i){
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main_content, homeFragment);
                }
                transaction.show(homeFragment);
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.fl_main_content, investFragment);
                }
                transaction.show(investFragment);
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main_content, meFragment);
                }
                transaction.show(meFragment);
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.fl_main_content, moreFragment);
                }
                transaction.show(moreFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment() {
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(investFragment != null){
            transaction.hide(investFragment);
        }
        if(meFragment != null){
            transaction.hide(meFragment);
        }
        if(moreFragment != null){
            transaction.hide(moreFragment);
        }
    }
    //重写onKeyUp(),实现连续两次点击方可退出当前应用

    private boolean flag = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_RESET_BACK:
                    flag = true;
                    break;
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && flag){
           Toast.makeText(MainActivity.this,"再点击一次,退出当前应用",Toast.LENGTH_SHORT).show();
            flag = false;
            //发送延迟消息
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK,2000);
            return true;
        }

        return super.onKeyUp(keyCode, event);

    }
    //为了避免出现内存的泄露,需要在onDestroy()中,移除所有未被执行的消息

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //方式一://移除指定id的所有的消息
        handler.removeMessages(WHAT_RESET_BACK);
        //方式二:移除所有的未被执行的消息
        handler.removeCallbacksAndMessages(null);
    }
}
