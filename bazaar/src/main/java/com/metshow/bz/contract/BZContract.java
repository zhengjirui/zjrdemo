package com.metshow.bz.contract;

import com.kwan.base.presenter.IBaseView;
import com.metshow.bz.commons.bean.Adforrec;
import com.metshow.bz.commons.bean.AdminUser;
import com.metshow.bz.commons.bean.Brand;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.Category;
import com.metshow.bz.commons.bean.Channel;
import com.metshow.bz.commons.bean.DaySign;
import com.metshow.bz.commons.bean.FilterBean;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.PointDetail;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.commons.bean.PointRecord;
import com.metshow.bz.commons.bean.PointResult;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.commons.bean.StickerType;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.commons.bean.TriProduct;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.UserType;
import com.metshow.bz.commons.bean.VideoUploadResult;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.commons.bean.message.MsgChatDetail;
import com.metshow.bz.commons.bean.message.MsgChatItem;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.commons.bean.topic.FindTag;
import com.metshow.bz.commons.bean.topic.StickerBean;
import com.metshow.bz.commons.bean.topic.Topic;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-6-12.
 */

public class BZContract {

	public interface IInFragmentView extends IBaseView {
		void showChannelList(List<Channel> channels);
	}

	public interface IInItemFragmentView extends IBaseView {

		void showRecListData(List<Article> articles);

		void getBannerSuccess(List<Banner> result);

		void getArticlesSuccess(List<Article> items);

		void getInRecommendSuccess(Adforrec result);
	}

	public interface IFollowView extends IBaseView {

		void getFollowListSuccess(List<FollowItem> followItems);

		void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime);

		void getDeleteFollowSuccess(Boolean result, int position);

		void addFollowSuccess(int position);
	}

	public interface IFindFragmentView extends IBaseView {
		void showFindTags(List<FindTag> result);
	}

	public interface IFindItemFragmentView extends IBaseView {
		void getTopicsSuccess(List<Topic> items);

		void deleteFavSuccess(String result, int type, int position, int fragmentId);

		void favSuccess(String result, int position);

		void getBannerSuccess(List<Banner> result);

		void getRecommendSuccess(List<RecommendUser> result);
	}

	public interface ICommentView extends IBaseView {

		void getMyCommentSuccess(List<Comment> comments);

		void getCommentListSuccess(List<Comment> comments);

		void addCommnetSuccess(int result);

		void reportSuccess(Integer integer);
	}


	public interface IBannerTopicView extends IBaseView {

		void getNewsTopicSuccess(List<Topic> items);

		void getHotsTopicSuccess(List<Topic> items);

		void deleteFavSuccess(String result, int type, int position);

		void favSuccess(String result, int position);

		void getBannerTopicSuccess(TopicSubject result);

		void addFollowSuccess(String result);

		void deleteFollowSuccess(String result);
	}

	public interface IRecommendUserListView extends IBaseView {
		void getUserListSuccess(List<RecommendUser> items);

		void getUserTypeSuccess(List<UserType> items);
	}

	public interface ISendTopicView extends IBaseView {

		void uploadImgSuccess(List<String> backImageUrl);

		void uploadImgFailed();

		void addTopicSuccess(String result);

		void addTopicSuccess(VideoUploadResult result);


	}

	public interface ITagView extends IBaseView {
		void getTagsSuccess(List<Tag> result);

        void getHotTagsSuccess(List<Tag> result);
    }

	public interface ITopicSubjectView extends IBaseView {
		void getHotSubjectSuccess(List<TopicSubject> items);

		void searchSubject(List<TopicSubject> items);
	}

	public interface IDoLoginView extends IBaseView {

		void loginSuccess(User user);

		void sloginSuccess(User result);
	}

	public interface IRegOrForgotView extends IBaseView {
		void getPhoneCodeSuccess(String result);

		void regSuccess(User result);

		void findSuccess(User result);

		void getValidatecodeSuccess(byte[] result);
	}

	public interface IUserInfoView extends IBaseView {

		void upDataUserSuccess();

		void upLoadAvatarSuccess(String url);

		void upLoadAvatarFailed();

	}

	public interface IChatView extends IBaseView {

		void showMsgChatDiaog(List<MsgChatDetail> details);

		void addMsgSuccess(MsgChatDetail detail);

		void uploadImgSuccess(String url);

		void uploadImgFailed();

		void getAdminInfoSuccess(AdminUser user);
	}

	public interface ICircleView extends IBaseView {


		void getTopicSuccess(List<Topic> items);

		void addBannerFollowSuccess(String result, int position);

	}

	public interface IShaiActivityView extends IBaseView {

		void getActivitysSuccess(List<BzActivity> items);

		void getTriProductSuccess(List<TriProduct> items);

	}

	public interface IBuyFragmentView extends IBaseView {
		void showCategoryList(List<Category> categories);
	}

	public interface IBuyItemView extends IBaseView {

		void getSpecialsSuccess(List<Special> result);

		void getProductsSuccess(List<Product> result);

		void favSuccess(int result, int position);

		void deleteFavSuccess(int result, int position);

		void getBrandListSuccess(List<Brand> items);
	}

	public interface ISpecialView extends IBaseView {

		void getSpecialSuccess(Special result);

		void getCommentsSuccess(List<Comment> result);

		void favSuccess(int result, int position);

		void deleteFavSuccess(int result, int position);

	}

	public interface IArticleView extends IBaseView {

		void getArticleSuccess(Article result);

		void favSuccess(String result, int position);

		void deleteFavSuccess(String result, int position);
	}

	public interface IFavArticleView extends IBaseView {

		void getFavArticleSuccess(List<FavArticle> items);

	}

	public interface IFavProductView extends IBaseView {

		void getFavProductSuccess(List<Product> items);

	}

	public interface IFavTopicView extends IBaseView {

		void getFavTopicSuccess(List<Topic> items);

	}

	public interface IMainView extends IBaseView {

		void getDaySignSuccess(DaySign result);

	}

	public interface IArticleSearchView extends IBaseView {
		void getArticleSuccess(List<Article> items);

		void showTags(String[] tags);
	}


	public interface ITopicView extends IBaseView {
		void getTopicDetailSuccess(Topic topic);

		void addFollowSuccess(String result, int position);

		void favSuccess(String result, int position);

		void deleteFavSuccess(String result, int position);
	}

	public interface ITopicMenuView extends IBaseView {
		void deleteFollowSuccess(Boolean result, int position);

		void disViewSuccess(int result, int position);

		void reportSuccess(Boolean result, int position);

		void deleteTopicSuccess(Boolean result, int position);
	}

	public interface ITopicListView extends IBaseView {

		void getUserTopicsSuccess(List<Topic> items);

		void favSuccess(String result, int position);

		void deleteFavSuccess(String result, int position);

		void addFollowSuccess(String result, int position);
	}

	public interface IProductView extends IBaseView {
		void getProductSuccess(Product result);

		void favSuccess(String result, int position);

		void deleteFavSuccess(String result, int position);
	}

	public interface IMyActivityView extends IBaseView {
		void getMyActivitySuccess(List<BzActivity> activities);
	}

	public interface ISearchView extends IBaseView {
		void getTagSuccess(List<Tag> tags);

		void getUserSuccess(List<User> users);

		void getTopicSuccsess(List<Topic> topics);
	}

	public interface IFriendDetailView extends IBaseView {

		void getUserInfoSuccess(User user);

		void getUserTopicsSuccess(List<Topic> topics);

		void getUserTagsSuccess(List<Tag> tags);

		void addFollowSuccess();

		void deleteFollowSuccess();

		void favSuccess(String result, int position);

		void deleteFavSuccess(String result, int type, int position, int fragmentId);

		void reportSuccess(Boolean result, int position);
	}

	public interface IBZActivityInfoView extends IBaseView {
		void getBZActivityInfoSuccess(BzActivity info);

		void getTopicSuccess(List<Topic> items);
	}

	public interface IMyTopicView extends IBaseView {
		void getUserTagsSuccess(List<Tag> tags);

		void getTopicsSuccess(List<Topic> topics);

		void deleteFavSuccess(String result, int type, int position, int fragmentId);

		void favSuccess(String result, int position);
	}

	public interface IMessageListView extends IBaseView{

		void getBeFollowListSuccess(List<FollowItem> items, String servertime);

		void getMeCommentSuccess(List<Comment> items, String servertime);

		void getMsgChatItemSuccess(List<MsgChatItem> items);

		void getFavArticleSuccess(List<FavArticle> items);
	}

	public interface ISettingView extends IBaseView{
		void setStateSuccess(String result);
	}

	public interface IUserFeedBackView extends IBaseView{
		void uploadImgSuccess(String url);

		void uploadImgFailed();

		void addFeedBackSuccess(String result);
	}

	public interface ITagTopicListView extends IBaseView {

		void getTopicSuccess(List<Topic> items);
	}

	public interface IPhotoEditView extends IBaseView{

		void getStickerTypeSuccess(List<StickerType> items);

        void getStickerSuccess(List<StickerBean> items);

		void getFilterSuccess(List<FilterBean> items);
	}

	public interface IPointProductListView extends IBaseView{

		void getPointProductListSuccess(List<PointProduct> items);

	}

	public interface IPointDetailView extends IBaseView{

		void getPointDetailSuccess(List<PointDetail> items);

	}

	public interface IPointRecordlView extends IBaseView{

		void getPointRecordSuccess(List<PointRecord> items);

	}

	public interface IPointResultView extends IBaseView{

		void getPointResultSuccess(PointResult result);

	}
}
