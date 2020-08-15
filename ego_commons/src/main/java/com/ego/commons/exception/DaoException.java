package com.ego.commons.exception;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.commons.exception
 * @version: 1.0
 * 数据库操作异常
 */
public class DaoException extends RuntimeException{
    public DaoException(String message) {
        super(message);
    }
}
