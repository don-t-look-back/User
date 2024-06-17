package com.example.dlbuser.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    TEST_EMPTY_COMMENT(false, HttpStatus.BAD_REQUEST.value(), "코멘트를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),
    POST_TEST_EXISTS_MEMO(false,HttpStatus.BAD_REQUEST.value(),"중복된 메모입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    INVALID_MEMO(false,HttpStatus.NOT_FOUND.value(), "존재하지 않는 메모입니다."),
    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"없는 아이디거나 비밀번호가 틀렸습니다."),
    INVALID_MEMBER_NAME(false,HttpStatus.NOT_FOUND.value(),"없는 아이디입니다."),
    INVALID_MEMBER_PASSWORD(false,HttpStatus.NOT_FOUND.value(),"비밀번호가 일치하지 않습니다."),
    MISMATCH_MEMBER_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효기간이 만료된 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    NOT_APPROVED_USER(false,HttpStatus.FORBIDDEN.value(),"관리자의 승인이 필요합니다."),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"일치하는 유저가 없습니다."),
    NOT_FIND_LINE(false,HttpStatus.NOT_FOUND.value(),"일치하는 생산라인이 없습니다."),
    INVALID_OAUTH_TYPE(false, HttpStatus.BAD_REQUEST.value(), "알 수 없는 소셜 로그인 형식입니다."),
    INVALID_USER_IP(false,HttpStatus.FORBIDDEN.value(),"올바르지 않은 형식입니다."),
    NOT_PERMITTED_USER(false,HttpStatus.FORBIDDEN.value(),"실행권한이 없는 유저입니다."),
    COMPANY_NOT_EXIST(false, HttpStatus.FORBIDDEN.value(), "고객사가 등록되지 않은 고객입니다."),
    NOT_FIND(false,HttpStatus.NOT_FOUND.value(),"일치하는 값이 없습니다."),
    NOT_FOUND_LINE(false,HttpStatus.NOT_FOUND.value(),"일치하는 생산라인이 없습니다."),
    NOT_FOUND_ITEM(false,HttpStatus.NOT_FOUND.value(),"일치하는 품목이 없습니다."),

    INVALID_FILE_EXTENSION(false,HttpStatus.BAD_REQUEST.value(),"지원하지 않는 파일 확장자입니다."),
    INVALID_DATE_FORMAT(false,HttpStatus.BAD_REQUEST.value(),"올바르지 않은 날짜입니다."),
    INVALID_TIME_FORMAT(false,HttpStatus.BAD_REQUEST.value(),"올바르지 않은 시간입니다."),
    INVALID_FORMAT(false,HttpStatus.BAD_REQUEST.value(),"올바르지 않은 형식입니다."),
    INVALID_PERIOD(false,HttpStatus.BAD_REQUEST.value(),"올바르지 않은 기간입니다."),
    INVALID_TARGET_FORMAT(false,HttpStatus.BAD_REQUEST.value(),"올바르지 않은 목표값 입니다."),


    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),


    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
