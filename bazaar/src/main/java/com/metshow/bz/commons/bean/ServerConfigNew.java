package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-2-28.
 */

public class ServerConfigNew extends POJO {

	private String ApiServer;
	private String ImgServer;
	private int ServerConfigId;//9223372036854775807

	public String getApiServer() {
		return ApiServer;
	}

	public void setApiServer(String apiServer) {
		ApiServer = apiServer;
	}

	public String getImgServer() {
		return ImgServer;
	}

	public void setImgServer(String imgServer) {
		ImgServer = imgServer;
	}

	public int getServerConfigId() {
		return ServerConfigId;
	}

	public void setServerConfigId(int serverConfigId) {
		ServerConfigId = serverConfigId;
	}
}
