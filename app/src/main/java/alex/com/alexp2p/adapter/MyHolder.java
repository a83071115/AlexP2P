package alex.com.alexp2p.adapter;

import android.view.View;
import android.widget.TextView;

import alex.com.alexp2p.R;
import alex.com.alexp2p.bean.Product;
import alex.com.alexp2p.ui.RoundProgress;
import alex.com.alexp2p.util.UIUtils;
import butterknife.Bind;


/**
 * Created by shkstart on 2016/12/5 0005.
 */
public class MyHolder extends BaseHolder<Product.DataBean> {

    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;

    @Override
    protected View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.item_product_list, null);
    }

    @Override
    protected void refreshData() {
        Product.DataBean data = this.getData();

        //装数据
        pMinnum.setText(data.getMemberNum());
        pMinzouzi.setText(data.getMinTouMoney());
        pMoney.setText(data.getMoney());
        pName.setText(data.getName());
        pProgresss.setProgress(Integer.parseInt(data.getProgress()));
        pSuodingdays.setText(data.getSuodingDays());
        pYearlv.setText(data.getYearRate());

    }
}
