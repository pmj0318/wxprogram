package util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetWeChatSession {
    //需要contole?,不需要就是携程封装一个工具类,就行了,
    @Autowired
    RestTemplateBean rtb;//就是注入他才能使用它

    @Autowired
    RestTemplate restTemplate;

    public  WeChatSession weChatSession(String code) {
        rtb.setCode(code);//要把他set进去才能获取
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + rtb.getAppid() +
                "&secret=" + rtb.getSecret() + "&js_code=" + rtb.getCode() + "&grant_type=" + rtb.getGrant_type();
        String openid = null;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);//String.class是响应的类型
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            Gson gson = new Gson();
            //解析从微信服务器获得的openid和session_key;
            WeChatSession weChatSession = gson.fromJson(sessionData, WeChatSession.class);
            //获取用户的唯一标识
            openid = weChatSession.getOpenid();
            //获取会话秘钥
            String session_key = weChatSession.getSession_key();
            //下面就可以写自己的业务代码了
            //最后要返回一个自定义的登录态,用来做后续数据传输的验证

            System.out.println("====" + openid + "*****" + session_key);//sessionkey有时候是不一样

            return weChatSession;
        }
        return null;
    }





}
