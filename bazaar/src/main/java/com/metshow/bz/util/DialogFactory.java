package com.metshow.bz.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kwan.base.util.ViewUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;


public class DialogFactory {

	private static Dialog dialog;

	/**
	 * 底部弹出菜单选择对话框(用于如底部弹出：照片选取形式选择，分销经纪公司列表选择)
	 * 
	 * @param act
	 * @param view
	 * @return
	 */
	public static Dialog showMenuDialog(final Activity act, View view) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = new Dialog(act, R.style.MenuDialogStyle);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = App.SCREEN_WIDTH;
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
		dialog.show();
		return dialog;
	}



    public static Dialog showCustomDialog(final Activity act, View view) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = new Dialog(act, R.style.MenuDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        int dialog_width = (int) (ViewUtil.getScreenWidth(act) * 9 / 10.0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dialog_width;
        window.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return dialog;
    }

    public static Dialog showMenuDialog(final Activity act, View view,boolean isShow) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new Dialog(act, R.style.MenuDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = App.SCREEN_WIDTH;
        dialog.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画

        return dialog;
    }



	
	/**
	 * 隐藏
	 * 
	 * @param mDialog
	 */
	public static void dismissDialog(Dialog mDialog) {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	/**
	 * 彻底销毁，包括实例置空
	 * 
	 * @param mDialog
	 */
	public static void destoryDialog(Dialog mDialog) {
		dismissDialog(mDialog);
	}
	
	/**
	 * 销毁一切已出现的对话框
	 */
	public static void destoryAllDialog(){
		destoryDialog(dialog);
	}



}
