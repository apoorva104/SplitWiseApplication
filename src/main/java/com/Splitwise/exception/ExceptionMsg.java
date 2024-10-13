package com.Splitwise.exception;

public class ExceptionMsg {
    public static final int SUCCESS_CODE = 0;
    public static final int FAILURE_CODE = 500;
    public static final String SUCCESS_MESSAGE= "Success";
    public static final int USER_NOT_FOUND_CODE = 1001;
    public static final String USER_NOT_FOUND_MESSAGE= "User Not Found";
    public static final int USER_ALREADY_PRESENT_CODE = 1002;
    public static final String USER_ALREADY_PRESENT_MESSAGE= "User/Email Already present";

    public static final int GROUP_NOT_FOUND_CODE = 1003;
    public static final String GROUP_NOT_FOUND_MESSAGE= "Invalid Group Id";

    public static final int JWT_TOKEN_NOT_FOUND_CODE = 1004;
    public static final String JWT_TOKEN_NOT_FOUND_MESSAGE= "JWT Token is missing";

    public static final int INVALID_TOKEN_CODE = 1005;
    public static final String INVALID_TOKEN_MESSAGE= "Invalid or Expired JWT Token";
    public static final int INVALID_GROUP_NAME_CODE = 1006;
    public static final String INVALID_GROUP_NAME_MESSAGE= "Invalid Group Name";


//    public static final int USER_ALREADY_PRESENT_CODE = 1004;
//    public static final String USER_ALREADY_PRESENT_MESSAGE= "User Already present!!!!!";
}
