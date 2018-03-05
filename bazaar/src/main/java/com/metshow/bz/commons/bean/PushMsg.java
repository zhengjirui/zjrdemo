package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-11-16.
 */

public class PushMsg extends POJO {

    /**
     * text：推送消息内容
     * key_type：对应后台的类型：
     * 0为普通
     * 1为关联文章
     * 2为跳转到晒
     * 3跳转到个人主页，
     * 4跳转到单品
     * 5跳转一篇帖子
     * 6android升级消息
     * <p>
     * key_refid:对应的编号
     */

    private String ticker;//;// "芭莎In",
    private String title;// "芭莎In",
    private String text;// "从配角到如今“漫威之父”为她量身打造中国女英雄电影，这是李冰冰的女神时代，也是她的英雄时代。",
    private String icon;// null,
    private String largeIcon;// null,
    private String img;// null,
    private String sound;// "default",
    private String builder_id;// 0,
    private String play_vibrate;// null,
    private String play_lights;// null,
    private String play_sound;// null,
    private String after_open;// "go_custom",
    private String url;// null,
    private String activity;// null,
    private String custom;// "comment-notify"

    //extra:

    private String key_type;// 1,
    private String key_refid;// 8188,
    private String key_url;// "",
    private String key_messageid;// 275,
    private String key_refType;// null

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(String builder_id) {
        this.builder_id = builder_id;
    }

    public String getPlay_vibrate() {
        return play_vibrate;
    }

    public void setPlay_vibrate(String play_vibrate) {
        this.play_vibrate = play_vibrate;
    }

    public String getPlay_lights() {
        return play_lights;
    }

    public void setPlay_lights(String play_lights) {
        this.play_lights = play_lights;
    }

    public String getPlay_sound() {
        return play_sound;
    }

    public void setPlay_sound(String play_sound) {
        this.play_sound = play_sound;
    }

    public String getAfter_open() {
        return after_open;
    }

    public void setAfter_open(String after_open) {
        this.after_open = after_open;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getKey_type() {
        return key_type;
    }

    public void setKey_type(String key_type) {
        this.key_type = key_type;
    }

    public String getKey_refid() {
        return key_refid;
    }

    public void setKey_refid(String key_refid) {
        this.key_refid = key_refid;
    }

    public String getKey_url() {
        return key_url;
    }

    public void setKey_url(String key_url) {
        this.key_url = key_url;
    }

    public String getKey_messageid() {
        return key_messageid;
    }

    public void setKey_messageid(String key_messageid) {
        this.key_messageid = key_messageid;
    }

    public String getKey_refType() {
        return key_refType;
    }

    public void setKey_refType(String key_refType) {
        this.key_refType = key_refType;
    }
}
