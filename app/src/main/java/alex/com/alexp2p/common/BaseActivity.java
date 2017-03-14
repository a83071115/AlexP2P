package alex.com.alexp2p.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

import alex.com.alexp2p.bean.User;
import butterknife.ButterKnife;

/**
 * Created by shkstart on 2016/12/6 0006.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        ButterKnife.bind(this);

        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);

        initTitle();

        initData();

    }

    protected abstract void initData();

    protected abstract void initTitle();

    protected abstract int getLayoutId();

    public AsyncHttpClient client = new AsyncHttpClient();

    //启动新的activity
    public void goToActivity(Class Activity,Bundle bundle){
        Intent intent = new Intent(this,Activity);
        //携带数据
        if(bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);
        }

        startActivity(intent);
    }

    //销毁当前的Activity
    public void removeCurrentActivity(){
        ActivityManager.getInstance().removeCurrentA();
    }

    //销毁所有的activity
    public void removeAll(){
        ActivityManager.getInstance().removeAll();
    }

    //保存用户信息
    public void saveUser(User user){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String name = user.getName();
        Toast.makeText(getApplicationContext(), name+",欢迎您来到Max+!", Toast.LENGTH_SHORT).show();

        try {
            name = new String(name.getBytes(),"UTF-8");
            Log.e("alex",name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        editor.putString("name",name);
        editor.putString("imageurl",user.getImageurl());
        editor.putBoolean("iscredit", user.isCredit());
        editor.putString("phone",user.getPhone());
        editor.commit();//必须提交，否则保存不成功
    }

    //读取用户信息
    public User readUser(){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString("name",""));
        user.setImageurl(sp.getString("imageurl", ""));
        user.setPhone(sp.getString("phone", ""));
        user.setCredit(sp.getBoolean("iscredit",false));

        return user;
    }
}
