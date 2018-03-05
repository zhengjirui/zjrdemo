package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-9-2.
 */
public class VideoUploadResult extends POJO {

    private int error;
    private String url;
    private String previewpic;
    private long articleId;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewpic() {
        return previewpic;
    }

    public void setPreviewpic(String previewpic) {
        this.previewpic = previewpic;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "VideoUploadResult{" +
                "error=" + error +
                ", url='" + url + '\'' +
                ", previewpic='" + previewpic + '\'' +
                ", articleId=" + articleId +
                '}';
    }
}
