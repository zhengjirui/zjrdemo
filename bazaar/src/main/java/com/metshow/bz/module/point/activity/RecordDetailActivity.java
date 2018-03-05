package com.metshow.bz.module.point.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.PointRecord;

public class RecordDetailActivity extends BaseCommonActivity {

	private PointRecord mPointRecord;
	private ImageView iv_icon;
	private TextView tv_name, tv_qm, tv_mm, tv_state,tv_num;
	private LinearLayout ll_state_dzq, ll_state_product;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		mPointRecord = (PointRecord) getIntentData("data");
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_record_detail;
	}

	@Override
	protected void initViews() {
		super.initViews();
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_qm = (TextView) findViewById(R.id.tv_qm);
		tv_mm = (TextView) findViewById(R.id.tv_mm);
		tv_num= (TextView) findViewById(R.id.tv_num);
		tv_state = (TextView) findViewById(R.id.tv_state);
		ll_state_dzq = (LinearLayout) findViewById(R.id.ll_state_dzq);
		ll_state_product = (LinearLayout) findViewById(R.id.ll_state_product);
		tv_num.setText(mPointRecord.getOrderNum());
		mImageUtil.loadFromUrl(mPointRecord.getPointShopIco(),iv_icon);
		switch (mPointRecord.getType()){

			case 1://实物
				ll_state_dzq.setVisibility(View.GONE);
				ll_state_product.setVisibility(View.VISIBLE);
                //State：0为待发货，1为已发货  -1为拒绝
				switch (mPointRecord.getState()) {
					case 1://
						tv_state.setText("已发货");
						ll_state_product.setBackgroundColor(Color.parseColor("#e4ffdc"));

						break;
					case -1://
						tv_state.setText("拒绝");
						ll_state_product.setBackgroundColor(Color.parseColor("#fff1e3"));
						break;
					case 0://
						tv_state.setText("待发货");
						ll_state_product.setBackgroundColor(Color.parseColor("#fff1e3"));
						break;
				}



				break;
			case 2://电子券
				ll_state_dzq.setVisibility(View.VISIBLE);
				ll_state_product.setVisibility(View.GONE);
				tv_qm.setText(mPointRecord.getCouponNum());
				tv_mm.setText(mPointRecord.getCouponPassword());

				break;
		}

	}

	@Override
	protected void initData() {

	}

	@Override
	protected String getTitleTxt() {
		return "订单详情";
	}
}
