package com.metshow.bz.module.me.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.UserInfoPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.Calendar;

import static com.metshow.bz.module.me.activity.UserInfoEditActivity.ADDRESS_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.AREA_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.BIRTH_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.DESCRIBE_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.NICKENAME_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.PHONE_REQUEST_CODE;
import static com.metshow.bz.module.me.activity.UserInfoEditActivity.REALNAME_REQUEST_CODE;

public class UserModifyActivity extends BaseCommonActivity implements BZContract.IUserInfoView {

	private int type;
	private TextView tv_save;
	private EditText et_name;
	private UserInfoPresenter mPresenter;
	private String content;
	private EditText et_content;
	private TextView tv_num;
	private User mUser;
	private AutoLinearLayout ll_content;
	private TextView tv_birth_ok;
	private TextView tv_birth_cancel;
	private int birth_year, birth_month, birth_day;
	private int user_year, user_month, user_day;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mUser = BZSPUtil.getUser();
		type = (int) getIntentData("type");
		mPresenter = new UserInfoPresenter(this);
	}

	@Override
	protected String getTitleTxt() {

		switch (type) {
			case NICKENAME_REQUEST_CODE:
				return "昵称";
			case BIRTH_REQUEST_CODE:
				return "生日";
			case AREA_REQUEST_CODE:
				return "地区";
			case DESCRIBE_REQUEST_CODE:
				return "个人介绍";
			case REALNAME_REQUEST_CODE:
				return "姓名";
			case PHONE_REQUEST_CODE:
				return "手机";
			case ADDRESS_REQUEST_CODE:
				return "地址";
		}

		return null;
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_txt;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_user_modify;
	}

	@Override
	protected void initViews() {
		super.initViews();
		tv_num = (TextView) findViewById(R.id.tv_num);
		et_name = (EditText) findViewById(R.id.et_name);
		et_content = (EditText) findViewById(R.id.et_content);
		ll_content = (AutoLinearLayout) findViewById(R.id.ll_content);

		if (type == BIRTH_REQUEST_CODE) {

			View birthView = getLayoutInflater().inflate(R.layout.activity_birth, null);
			birthDialog = new Dialog(this);
			birthDialog.setContentView(birthView);

			datePicker = (DatePicker) birthView.findViewById(R.id.datePicker);
			tv_birth_ok = (TextView) birthView.findViewById(R.id.tv_birth_ok);
			tv_birth_cancel = (TextView) birthView.findViewById(R.id.tv_birth_cancel);

			Calendar calendar = Calendar.getInstance();
			birth_year = calendar.get(Calendar.YEAR);
			birth_month = calendar.get(Calendar.MONTH);
			birth_day = calendar.get(Calendar.DAY_OF_MONTH);

			datePicker.init(birth_year, birth_month, birth_day, new DatePicker.OnDateChangedListener() {

				public void onDateChanged(DatePicker view, int year,
										  int monthOfYear, int dayOfMonth) {
					birth_year = year;
					birth_month = monthOfYear + 1;
					birth_day = dayOfMonth;
				}

			});
			tv_birth_ok.setOnClickListener(this);
			tv_birth_cancel.setOnClickListener(this);

			String str = mUser.getBirthday();
			if (str != null && !str.isEmpty()) {
				String[] strs = str.split("-");
				user_year = Integer.valueOf(strs[0]);
				user_month = Integer.valueOf(strs[0]);
				user_day = Integer.valueOf(strs[0]);
			}
		}
	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();
		int num = 0;
		switch (type) {
			case NICKENAME_REQUEST_CODE:
				et_name.setHint(mUser.getNickName());
				ll_content.setVisibility(View.GONE);
				break;

			case REALNAME_REQUEST_CODE:
				et_name.setHint(mUser.getTrueName());
				ll_content.setVisibility(View.GONE);
				break;

			case PHONE_REQUEST_CODE:
				et_name.setHint(mUser.getPhoneNum());
				ll_content.setVisibility(View.GONE);
				break;

			case ADDRESS_REQUEST_CODE:
				num = 20;
				et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
				et_name.setVisibility(View.GONE);
				et_content.setHint(mUser.getAddress());
				break;
			case BIRTH_REQUEST_CODE:
				String str = mUser.getBirthday();
				if (str == null || str.isEmpty())
					str = "点击选择日期";
				et_name.setHint(str);
				//et_name.setEnabled(false);
				et_name.setOnClickListener(this);
				ll_content.setVisibility(View.GONE);
				break;
			case AREA_REQUEST_CODE:
				num = 20;
				et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
				et_name.setVisibility(View.GONE);
				et_content.setHint(mUser.getAddress());
				break;
			case DESCRIBE_REQUEST_CODE:
				num = 60;
				et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
				et_name.setVisibility(View.GONE);
				et_content.setHint(mUser.getDescription());
				break;
		}

		String strDescribe = et_content.getText().toString();
		tv_num.setText("" + (num - strDescribe.length()));
		final int finalNum = num;
		et_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				tv_num.setText("" + (finalNum - editable.length()));
			}
		});
	}

	@Override
	protected void setUpTitleRightView(View v) {

		tv_save = (TextView) v;
		tv_save.setText("保存");
		tv_save.setOnClickListener(this);
	}

	private DatePicker datePicker;
	private Dialog birthDialog;

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_save) {
			showProgress("正在保存");
			switch (type) {
				case NICKENAME_REQUEST_CODE:
					content = et_name.getText().toString();
					mPresenter.updataUser(null, 0, content, null, null, null, null, null, null);
					break;
				case BIRTH_REQUEST_CODE:
					content = et_name.getText().toString();
					mPresenter.updataUser(null, 0, null, null, null, null, null, content, null);
					break;
				case AREA_REQUEST_CODE:
					content = et_content.getText().toString();
					mPresenter.updataUser(null, 0, null, null, null, content, null, null, null);
					break;
				case DESCRIBE_REQUEST_CODE:
					content = et_content.getText().toString();
					mPresenter.updataUser(null, 0, null, null, content, null, null, null, null);
					break;

				case ADDRESS_REQUEST_CODE:
					content = et_content.getText().toString();
					mPresenter.updataUser(null, 0, null, null, null, content, null, null, null);
					break;

				case REALNAME_REQUEST_CODE:
					content = et_content.getText().toString();
					mPresenter.updataUser(null, 0, null, content, null, null, null, null, null);
					break;

				case PHONE_REQUEST_CODE:
					content = et_content.getText().toString();
					mPresenter.updataUser(content, 0, null, null, null, null, null, null, null);
					break;
			}
		} else if (v == et_name) {
			birthDialog.show();
		} else if (v == tv_birth_cancel) {
			birthDialog.dismiss();
		} else if (v == tv_birth_ok && (birth_year != user_year || birth_month != user_month || birth_day != user_day)) {

			et_name.setText(birth_year + "-" + birth_month + "-" + birth_day);
			birthDialog.dismiss();

		}
	}

	@Override
	protected void initData() {

	}

	@Override
	public void upDataUserSuccess() {
		toastMsg("更新成功");
		dismissProgress();
		Intent intent = new Intent();
		intent.putExtra("content", content);
		setResult(RESULT_OK, intent);
		onBackPressed();
	}

	@Override
	public void upLoadAvatarSuccess(String url) {

	}

	@Override
	public void upLoadAvatarFailed() {

	}
}
