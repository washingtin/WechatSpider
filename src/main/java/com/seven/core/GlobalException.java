package com.seven.core;

/**
 * 自定义异常类 <br>
 * 封装各种系统级别异常， <br>
 * 
 * @author 最爱吃小鱼
 */
@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

  public GlobalException(String message) {
    super(message);
  }

  public GlobalException(String message, Object... args) {
    super(String.format(message, args));
  }

  public GlobalException(Throwable throwable) {
    super(throwable);
  }

  public GlobalException(Throwable throwable, String frdMessage) {
    super(throwable);
  }

}
