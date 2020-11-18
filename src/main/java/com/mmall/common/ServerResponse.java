package com.mmall.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @package: com.mmall.common
 * @author: luweihong
 * @description: 封装服务端响应类信息
 * @create: 2020/11/15:15:50
 * @version: 1.0
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 信息
	 */
	private String msg;

	/**
	 * 数据
	 */
	private T data;

	private ServerResponse(int status) {
		this.status = status;
	}

	private ServerResponse(int status, T data){
		this.status = status;
		this.data = data;
	}

	private ServerResponse(int status, String msg){
		this.status = status;
		this.msg = msg;
	}

	@JsonIgnore
	public boolean isSuccess(){
		return this.status == ResponseCode.SUCCESS.getCode();
	}

	public int getStatus(){
		return status;
	}

	public T getData(){
		return data;
	}

	public String getMsg(){
		return msg;
	}

	public static <T> ServerResponse<T> createBySuccess(){
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
	}

	public static <T> ServerResponse<T> createBySuccessMessage(String msg){
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg);
	}

	public static <T> ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),data);
	}

	public static <T> ServerResponse<T> createBySuccess(String msg,T data){
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg,data);
	}

	public static <T> ServerResponse<T> createByError(){
		return new ServerResponse<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
	}

	public static <T> ServerResponse<T> createByErrorMessage(String msg){
		return new ServerResponse<>(ResponseCode.ERROR.getCode(),msg);
	}

	public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMsg){
		return new ServerResponse<>(errorCode,errorMsg);
	}
}
