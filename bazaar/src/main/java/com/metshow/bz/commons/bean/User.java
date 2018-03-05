package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 * Created by Mr.Kwan on 2016-6-29.
 */


@Entity
public class User extends POJO {

    private String Address;// null,
    @Transient
    private long AdminId;// null,
    private String Avatar;// "http://7xlovw.com1.z0.glb.clouddn.com/cosmoweb/201603/1458637760687_584.jpg",
    private String Birthday;// null,
    private String Contact;// null,
    private String CreateDate;// "/Date(1458666517000)/",
    private String Description;// "热",
    private String Email;// null,
    private String InfoExtent;// null,
    private int IsRecVip;// null,
    private int IsRefuseMessage;// 0,
    private int IsVip;// 1,
    private String LastOpenMsgDate;// null,
    @Property(nameInDb = "NICKNAME")
    private String NickName;// "编辑小QQ",
    private String Password;// "",
    private String PhoneNum;// "13980565638",
    private int Points;// 295,
    private String Rank;// null,
    private String RealName;// null,
    private int Role;// null,
    private long RoleId;// null,
    private int Sex;// null,
    private int State;// null,
    private String Tags;// null,
    private String Tel;// null,
    private String TrueName;// null,
    @Id//@Id 标识主键
    private long UserId;// 30041,
    private String UserName;// "001",
    private int UserType;// null,
    private String VipEndDate;// null,
    private int VipOrderNum;// null,
    private int ArticleCount;// 18,
    private int BeFollowedCount;// 37,
    private int FollowCount;// 20,
    private String Grade;// "LV2",
    private String Token="";// "211p0z3S1M070Q0V3j2r1v2K2W2g053B0T3W21041S3X390x2E1A3X0U3K0C3E1l2j2d073J18132G0Q350f3g3H3q3A3A3l"
    private int IsFollow;// 0,
	private int isSlogin;

	private int FavCount;

	private int FollowedBadgeCount;
	private int ReplyBadgeCount;
	private int FavBadgeCount;
	private int MessageBadgeCount;
	private int SystemMsgBadgeCount;

	public int getFollowedBadgeCount() {
		return FollowedBadgeCount;
	}

	public void setFollowedBadgeCount(int followedBadgeCount) {
		FollowedBadgeCount = followedBadgeCount;
	}

	public int getReplyBadgeCount() {
		return ReplyBadgeCount;
	}

	public void setReplyBadgeCount(int replyBadgeCount) {
		ReplyBadgeCount = replyBadgeCount;
	}

	public int getFavBadgeCount() {
		return FavBadgeCount;
	}

	public void setFavBadgeCount(int favBadgeCount) {
		FavBadgeCount = favBadgeCount;
	}

	public int getMessageBadgeCount() {
		return MessageBadgeCount;
	}

	public void setMessageBadgeCount(int messageBadgeCount) {
		MessageBadgeCount = messageBadgeCount;
	}

	public int getSystemMsgBadgeCount() {
		return SystemMsgBadgeCount;
	}

	public void setSystemMsgBadgeCount(int systemMsgBadgeCount) {
		SystemMsgBadgeCount = systemMsgBadgeCount;
	}

	public int getFavCount() {
		return FavCount;
	}

	public void setFavCount(int favCount) {
		FavCount = favCount;
	}

	public int getIsSlogin() {
		return isSlogin;
	}

	public void setIsSlogin(int isSlogin) {
		this.isSlogin = isSlogin;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	private int platform;

    @Generated(hash = 815201288)
    public User(String Address, String Avatar, String Birthday, String Contact, String CreateDate, String Description, String Email,
            String InfoExtent, int IsRecVip, int IsRefuseMessage, int IsVip, String LastOpenMsgDate, String NickName, String Password,
            String PhoneNum, int Points, String Rank, String RealName, int Role, long RoleId, int Sex, int State, String Tags,
            String Tel, String TrueName, long UserId, String UserName, int UserType, String VipEndDate, int VipOrderNum,
            int ArticleCount, int BeFollowedCount, int FollowCount, String Grade, String Token, int IsFollow, int isSlogin,
            int platform) {
        this.Address = Address;
        this.Avatar = Avatar;
        this.Birthday = Birthday;
        this.Contact = Contact;
        this.CreateDate = CreateDate;
        this.Description = Description;
        this.Email = Email;
        this.InfoExtent = InfoExtent;
        this.IsRecVip = IsRecVip;
        this.IsRefuseMessage = IsRefuseMessage;
        this.IsVip = IsVip;
        this.LastOpenMsgDate = LastOpenMsgDate;
        this.NickName = NickName;
        this.Password = Password;
        this.PhoneNum = PhoneNum;
        this.Points = Points;
        this.Rank = Rank;
        this.RealName = RealName;
        this.Role = Role;
        this.RoleId = RoleId;
        this.Sex = Sex;
        this.State = State;
        this.Tags = Tags;
        this.Tel = Tel;
        this.TrueName = TrueName;
        this.UserId = UserId;
        this.UserName = UserName;
        this.UserType = UserType;
        this.VipEndDate = VipEndDate;
        this.VipOrderNum = VipOrderNum;
        this.ArticleCount = ArticleCount;
        this.BeFollowedCount = BeFollowedCount;
        this.FollowCount = FollowCount;
        this.Grade = Grade;
        this.Token = Token;
        this.IsFollow = IsFollow;
        this.isSlogin = isSlogin;
        this.platform = platform;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public long getAdminId() {
        return AdminId;
    }

    public void setAdminId(long adminId) {
        AdminId = adminId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getInfoExtent() {
        return InfoExtent;
    }

    public void setInfoExtent(String infoExtent) {
        InfoExtent = infoExtent;
    }

    public int getIsRecVip() {
        return IsRecVip;
    }

    public void setIsRecVip(int isRecVip) {
        IsRecVip = isRecVip;
    }

    public int getIsRefuseMessage() {
        return IsRefuseMessage;
    }

    public void setIsRefuseMessage(int isRefuseMessage) {
        IsRefuseMessage = isRefuseMessage;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }

    public String getLastOpenMsgDate() {
        return LastOpenMsgDate;
    }

    public void setLastOpenMsgDate(String lastOpenMsgDate) {
        LastOpenMsgDate = lastOpenMsgDate;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public long getRoleId() {
        return RoleId;
    }

    public void setRoleId(long roleId) {
        RoleId = roleId;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getVipEndDate() {
        return VipEndDate;
    }

    public void setVipEndDate(String vipEndDate) {
        VipEndDate = vipEndDate;
    }

    public int getVipOrderNum() {
        return VipOrderNum;
    }

    public void setVipOrderNum(int vipOrderNum) {
        VipOrderNum = vipOrderNum;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public int getBeFollowedCount() {
        return BeFollowedCount;
    }

    public void setBeFollowedCount(int beFollowedCount) {
        BeFollowedCount = beFollowedCount;
    }

    public int getFollowCount() {
        return FollowCount;
    }

    public void setFollowCount(int followCount) {
        FollowCount = followCount;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }


	@Override
	public String toString() {
		return "User{" +
				"Address='" + Address + '\'' +
				", AdminId=" + AdminId +
				", Avatar='" + Avatar + '\'' +
				", Birthday='" + Birthday + '\'' +
				", Contact='" + Contact + '\'' +
				", CreateDate='" + CreateDate + '\'' +
				", Description='" + Description + '\'' +
				", Email='" + Email + '\'' +
				", InfoExtent='" + InfoExtent + '\'' +
				", IsRecVip=" + IsRecVip +
				", IsRefuseMessage=" + IsRefuseMessage +
				", IsVip=" + IsVip +
				", LastOpenMsgDate='" + LastOpenMsgDate + '\'' +
				", NickName='" + NickName + '\'' +
				", Password='" + Password + '\'' +
				", PhoneNum='" + PhoneNum + '\'' +
				", Points=" + Points +
				", Rank='" + Rank + '\'' +
				", RealName='" + RealName + '\'' +
				", Role=" + Role +
				", RoleId=" + RoleId +
				", Sex=" + Sex +
				", State=" + State +
				", Tags='" + Tags + '\'' +
				", Tel='" + Tel + '\'' +
				", TrueName='" + TrueName + '\'' +
				", UserId=" + UserId +
				", UserName='" + UserName + '\'' +
				", UserType=" + UserType +
				", VipEndDate='" + VipEndDate + '\'' +
				", VipOrderNum=" + VipOrderNum +
				", ArticleCount=" + ArticleCount +
				", BeFollowedCount=" + BeFollowedCount +
				", FollowCount=" + FollowCount +
				", Grade='" + Grade + '\'' +
				", Token='" + Token + '\'' +
				", IsFollow=" + IsFollow +
				", isSlogin=" + isSlogin +
				", FavCount=" + FavCount +
				", FollowedBadgeCount=" + FollowedBadgeCount +
				", ReplyBadgeCount=" + ReplyBadgeCount +
				", FavBadgeCount=" + FavBadgeCount +
				", MessageBadgeCount=" + MessageBadgeCount +
				", SystemMsgBadgeCount=" + SystemMsgBadgeCount +
				", platform=" + platform +
				'}';
	}
}
