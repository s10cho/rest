package com.api.exception;

public class InvalidTokenException extends RuntimeException
{
	public InvalidTokenException() {}
	
	
	public InvalidTokenException(String msg)
	{
		super(msg);
	}
	
}
