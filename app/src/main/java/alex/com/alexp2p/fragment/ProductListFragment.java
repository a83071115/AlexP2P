package alex.com.alexp2p.fragment;

import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.List;

import alex.com.alexp2p.R;
import alex.com.alexp2p.adapter.ProductAdapter;
import alex.com.alexp2p.bean.Product;
import alex.com.alexp2p.common.AppNetConfig;
import alex.com.alexp2p.common.BaseFragment;
import alex.com.alexp2p.ui.MyTextView;
import butterknife.Bind;

/**
 * Created by shkstart on 2016/12/5 0005.
 * ListView的使用：①ListView ②BaseAdapter ③Item Layout ④集合数据 (联网获取数据）
 */
public class ProductListFragment extends BaseFragment {


    @Bind(R.id.tv_product_title)
    MyTextView mTvProductTitle;
    @Bind(R.id.lv_product_list)
    ListView mLvProductList;
    private List<Product.DataBean> productList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {
        Product product = new Gson().fromJson(content,Product.class);
        if(product.isSuccess()){
            //方式四：抽取了，最好的方式.（可以作为选择）
            productList = product.getData();
            ProductAdapter productAdapter3 = new ProductAdapter(productList);
            mLvProductList.setAdapter(productAdapter3);//显示列表
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_productlist;
    }


}
