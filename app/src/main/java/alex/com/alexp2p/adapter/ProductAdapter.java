package alex.com.alexp2p.adapter;


import java.util.List;

import alex.com.alexp2p.bean.Product;

/**
 * Created by shkstart on 2016/12/5 0005.
 */
public class ProductAdapter extends MyBaseAdapter<Product.DataBean> {
    public ProductAdapter(List<Product.DataBean> list) {
        super(list);
    }

    @Override
    protected BaseHolder<Product.DataBean> getHolder() {
        return new MyHolder();
    }
}
