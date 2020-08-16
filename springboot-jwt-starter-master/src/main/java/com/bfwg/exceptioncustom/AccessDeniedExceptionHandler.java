package com.bfwg.exceptioncustom;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class AccessDeniedExceptionHandler {

	@ExceptionHandler(value = AccessDeniedException.class)
	public void handleConflict(HttpServletResponse response) throws IOException {
		response.sendError(401, "Your not authorize, please sign!");
	}
}
