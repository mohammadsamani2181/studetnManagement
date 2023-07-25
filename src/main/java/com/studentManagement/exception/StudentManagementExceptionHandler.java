package com.studentManagement.exception;

import com.studentManagement.log.LogObject;
import com.studentManagement.log.LogUtil;
import com.studentManagement.log.UnknownLogObject;
import com.studentManagement.message.ErrorMessage;
import com.studentManagement.model.DTO.response.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Calendar;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Slf4j
@ControllerAdvice(basePackages = "com.studentManagement")
public class StudentManagementExceptionHandler extends ResponseEntityExceptionHandler implements AccessDeniedHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .timestamp(Calendar.getInstance().getTime())
                .error(InvalidRequestException.class.getSimpleName())
                .message(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .status(status.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorDTO, headers, status);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            RuntimeException.class,
            InterruptedException.class
    })
    public final ResponseEntity<ApiErrorDTO> handleExceptions(RuntimeException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof org.springframework.security.access.AccessDeniedException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return handleExceptionInternal(ex, headers, status, request);
        } else {
            LogObject unknownLog = UnknownLogObject.builder()
                    .message("Unknown exception type:" + ex.getClass().getName())
                    .build();

            LogUtil.fatal(log, unknownLog);

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(new InternalServerException(ErrorMessage.unknownError()),
                    headers, status, request);
        }
    }


    protected ResponseEntity<ApiErrorDTO> handleExceptionInternal(Exception ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, SCOPE_REQUEST);
        }

        ApiErrorDTO body = ApiErrorDTO.builder()
                .timestamp(Calendar.getInstance().getTime())
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .status(status.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();


        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse,
                       org.springframework.security.access.AccessDeniedException e) throws IOException {

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.sendRedirect("/error/access-denied");
    }
}
