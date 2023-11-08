package com.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountConst {

    public static final String DELETE_ERROR = "Error while deleting user";
    public static final String RETRIEVE_ERROR = "Error while retrieving user";
    public static final String PERSIST_ERROR = "Error while persisting user";

    public static final String DUPLICATION_EXCEPTION = "Error: user already exist";

    public static final String DEFAULT_PATH = "/";
    public static final String RETRIEVE_USER = "retrieve-user/";
    public static final String UPDATE_USER = "update-user/";
    public static final String CREATE_USER = "create-user/";
    public static final String DELETE_USER = "delete-user/";


}
