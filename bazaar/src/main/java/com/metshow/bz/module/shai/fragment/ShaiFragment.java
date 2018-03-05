package com.metshow.bz.module.shai.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.fragment.BaseFragment;
import com.kwan.base.fragment.BasePageItemFragment;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.R;
import com.metshow.bz.module.commons.activity.CameraActivity;
import com.metshow.bz.module.commons.activity.PhotoEditActivity;
import com.metshow.bz.module.commons.activity.RecorderActivity;
import com.metshow.bz.module.commons.activity.SendTopicActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.main.MainActivity;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.PermissionUtil;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;


public class ShaiFragment extends BasePageItemFragment implements MainActivity.OnMainActivtyShaiFragmentChangeListener, PermissionUtil.PermissionListener {

	private ImageView iv_camera;
	private PermissionUtil mPermissionUtil;

	public ShaiFragment() {
	}


	public static ShaiFragment newInstance() {
		ShaiFragment fragment = new ShaiFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		((MainActivity)mBaseActivity).setShaiFragmentChangeListener(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		iv_camera = (ImageView)view.findViewById(R.id.iv_camera);
		iv_camera.setOnClickListener(this);

		mPermissionUtil = new PermissionUtil(mBaseActivity);
		mPermissionUtil.setPermissionListener(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	protected int getContentViewId() {
		return R.layout.fragment_shai;
	}

	private int currentPosition = 0;

	@Override
	public void OnChange(int position) {
		if(currentPosition!=position){
			currentPosition = position;
			switch (position){
				case 0:
					showNavigationFragment("find");
					break;
				case 1:
					showNavigationFragment("circle");
					break;
				case 2:
					showNavigationFragment("activity");
					break;
			}
		}

	}

	private String lastNavItemTag;

	private void showNavigationFragment(String tag) {

		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		//根据最后一个添加的 fragment 的 tag 从 FragmentManager中找到 fragment
		Fragment lastFragment = fm.findFragmentByTag(lastNavItemTag);
		lastNavItemTag = tag;

		//detach 掉
		if (lastFragment != null)
			ft.detach(lastFragment);

		Fragment fragment = fm.findFragmentByTag(tag);

		if (fragment == null) {

			fragment = getItem(tag);
			ft.add(R.id.fl_content, fragment, tag);

		} else {
			ft.attach(fragment);
		}
		ft.commitAllowingStateLoss();
	}

	private BaseFragment getItem(String tag) {

		BaseFragment navigationFragment = CircleFragment.newInstance();

		switch (tag) {

			case "find":
				navigationFragment = FindFragment.newInstance();
				break;
			case "circle":
				navigationFragment = CircleFragment.newInstance();
				break;
			case "activity":
				navigationFragment = ActivityFragment.newInstance();
				break;

		}
		return navigationFragment;
	}

	@Override
	public void fetchData() {
		showNavigationFragment("find");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if(v==iv_camera){
			showTopicDialog();
		}else{
			switch (v.getId()) {

				case R.id.tv_video:
					go2Activity(RecorderActivity.class, null, false);
					mSendTopicDialog.cancel();
					break;

				case R.id.tv_camera:

					if (ContextCompat.checkSelfPermission(mBaseActivity, Manifest.permission.CAMERA)
							!= PackageManager.PERMISSION_GRANTED) {
						//申请WRITE_EXTERNAL_STORAGE权限
						ActivityCompat.requestPermissions(mBaseActivity, new String[]{Manifest.permission.CAMERA},
								CAMERA_REQUEST_CODE);

					} else {
						starCam();
					}
					mSendTopicDialog.cancel();
					break;

				case R.id.tv_photo:

					ImageSelectorActivity.setOnResultListener(new ImageSelectorActivity.OnResultListener() {
						@Override
						public void onResult(ArrayList<String> images) {
							Bundle bundle = new Bundle();
//							bundle.putStringArrayList("output", images);
//							bundle.putInt("mode", 2);
//							go2Activity(SendTopicActivity.class, bundle, false);

							bundle.putSerializable("data",images);
							go2Activity(PhotoEditActivity.class,bundle,false);
						}
					});

					Bundle bundle = new Bundle();
					bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
					bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
					bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, false);
					go2Activity(ImageSelectorActivity.class, bundle, false);
					mSendTopicDialog.cancel();
					break;

				case R.id.tv_cancel:
					mSendTopicDialog.cancel();
					break;

			}
		}

	}

	private Dialog mSendTopicDialog;
	private final int CAMERA_REQUEST_CODE = 1;
	private String cameraPath;


	boolean isCameraGranted = false;
	boolean isAudioGranted = false;

	private void showTopicDialog() {

		if(!SPUtil.getIsLogin()){
			go2ActivityWithLeft(DologinActivity.class,null,false);
			return;
		}

		if (isAudioGranted && isCameraGranted) {
			View parent = mBaseActivity.getLayoutInflater().inflate(R.layout.send_topic_dialog, null);
			TextView tv_video = (TextView) parent.findViewById(R.id.tv_video);
			TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
			TextView tv_photo = (TextView) parent.findViewById(R.id.tv_photo);
			TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);

			tv_video.setOnClickListener(this);
			tv_photo.setOnClickListener(this);
			tv_camera.setOnClickListener(this);
			tv_cancel.setOnClickListener(this);

			mSendTopicDialog = DialogFactory.showMenuDialog(mBaseActivity, parent);
		} else

		mPermissionUtil.requestPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);

	}

	@Override
	public void onGranted(String permission) {

		if (permission.equals(Manifest.permission.CAMERA))
			isCameraGranted = true;

		if (permission.equals(Manifest.permission.RECORD_AUDIO))
			isAudioGranted = true;

		if (isCameraGranted && isAudioGranted) {

			View parent = mBaseActivity.getLayoutInflater().inflate(R.layout.send_topic_dialog, null);
			TextView tv_video = (TextView) parent.findViewById(R.id.tv_video);
			TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
			TextView tv_photo = (TextView) parent.findViewById(R.id.tv_photo);
			TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);

			tv_video.setOnClickListener(this);
			tv_photo.setOnClickListener(this);
			tv_camera.setOnClickListener(this);
			tv_cancel.setOnClickListener(this);

			mSendTopicDialog = DialogFactory.showMenuDialog(mBaseActivity, parent);
		}
		//doShowCamera();
	}

	@Override
	public void onShouldShowRequestPermissionRationale(String permission) {
		showRationaleDialog(getResources().getString(com.kwan.base.R.string.app_name) + "需要请求相关权限" + "", permission);
	}

	@Override
	public void onDenied(String permission) {

	}

	private void showRationaleDialog(String message, final String permission) {
		new AlertDialog.Builder(mBaseActivity)
				.setPositiveButton("请求", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(@NonNull DialogInterface dialog, int which) {
						//
						mPermissionUtil.requestPermissions(permission);
						dialog.cancel();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(@NonNull DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setCancelable(false)
				.setMessage(message)
				.show();
	}





	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				starCam();
			} else {
				toastMsg( "没有相机权限");
			}
		}
	}

	private void starCam() {

//		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File cameraFile = FileUtils.createCameraFile(mBaseActivity);
//		cameraPath = cameraFile.getAbsolutePath();
//		i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
//
//
//		startActivityForResult(i, CAMERA_REQUEST_CODE);


		go2Activity(CameraActivity.class,null,false);




	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_REQUEST_CODE) {

			mBaseActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));
			ArrayList<String> output = new ArrayList<>();
			output.add(cameraPath);
			Bundle bundle = new Bundle();
			bundle.putInt("mode", 2);
			bundle.putStringArrayList("output", output);

			go2Activity(SendTopicActivity.class, bundle, false);
		}
	}
}
