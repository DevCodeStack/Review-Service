package com.eatza.review.exception;

import com.eatza.review.util.ErrorCodesEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvalidTokenException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7331438765491058928L;
	private ErrorCodesEnum error;
	
	public InvalidTokenException() {
		super(ErrorCodesEnum.INTERNAL_SERVER_ERROR.getMsg());
		this.error = ErrorCodesEnum.INTERNAL_SERVER_ERROR;
	}
	
	public InvalidTokenException(String message) {
		super(message);
		this.error = ErrorCodesEnum.INTERNAL_SERVER_ERROR;
	}
	
	public InvalidTokenException(String message, ErrorCodesEnum error) {
		super(message);
		this.error = error;
	}

}
