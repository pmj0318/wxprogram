package util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//用来发送请求获得的数据,
@Component//就是帮你在spring加入bean
public class RestTemplateBean {

    @Value("wxc0a75e1ce6269042")//就是身份确认信息
    private String appid;
    private String code;//需要前端获取
    @Value("6c0cbbdccbc0ae6b9fc8bb15cd351cd3")
    private String secret;
    @Value("authorization_code")//就是固定的
    private  String grant_type;

    @Bean//就是代用接口
    public  RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
