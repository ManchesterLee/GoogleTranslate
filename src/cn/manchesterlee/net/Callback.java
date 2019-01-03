package cn.manchesterlee.net;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2018/12/27
 */
public interface Callback<T> {

    void onSuccess(String result);

    void onFailure(String message);

}
