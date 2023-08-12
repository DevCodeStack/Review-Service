package com.eatza.review.exception;

import com.eatza.review.util.ErrorCodesEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7037159033071714290L;
	private ErrorCodesEnum error;

	public ReviewException() {
		super(ErrorCodesEnum.INTERNAL_SERVER_ERROR.getMsg());
		this.error = ErrorCodesEnum.INTERNAL_SERVER_ERROR;
	}
	
	public ReviewException(String message) {
		super(message);
		this.error = ErrorCodesEnum.INTERNAL_SERVER_ERROR;
	}
	
	public ReviewException(String message, ErrorCodesEnum error) {
		super(message);
		this.error = error;
	}
	
}
