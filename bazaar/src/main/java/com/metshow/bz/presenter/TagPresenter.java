package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr.Kwan on 2016-9-1.
 */
public class TagPresenter extends BzPresenter {


	BZContract.ITagView mITagView;

	public TagPresenter(BZContract.ITagView iTagView) {
		super(iTagView);
		mITagView = iTagView;

	}


	public void getTags() {
		mModel.getTags();
	}

	public void getHotTags(){
		mModel.gettag_hotlist4photo();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.TAG_LIST_VOCATIONAL_ID:
				mITagView.getTagsSuccess((List<Tag>) s.getResult());
				break;

            case ServerAPI.TAG_HOTLIST4PHOTO_VOCATIONAL_ID:
                mITagView.getHotTagsSuccess((List<Tag>) s.getResult());
                break;

		}
	}
}
