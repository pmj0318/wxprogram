package controller;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import serviceImpl.WxUserServiceImpl;
import util.GetWeChatSession;
import util.RestTemplateBean;
import util.WeChatSession;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class WxUserController {

    @Autowired
    WxUserServiceImpl wsi;

    @Autowired
    RestTemplateBean rtb;//就是注入他才能使用它

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GetWeChatSession wgs;

    @RequestMapping("/insert")
    public int insert() {
        WxUser wx = new WxUser("1", "小小", "小", 1, "中国", "安徽", "合肥", "中文", "20191223", "123456");
        return wsi.insert(wx);
    }


    @RequestMapping(value = "/wxlogin", method = RequestMethod.POST)//这个post就是小程序,方法
    public String wxlogin(String code, String nickName , String city, String country, Integer gender, String  province, String avatarUrl, String  language, HttpServletResponse resp) { //小程序,不用加@REQUESTparam获取参数,小城徐也不用解决跨域问题,小程序都帮你解决好了
        System.out.println("------" + code);
        System.out.println("---" + nickName);
        System.out.println("----" + language);
        WeChatSession weChatSession = wgs.weChatSession(code);

        WxUser user = new WxUser();

        Cookie cookie = new Cookie("code",weChatSession.getOpenid());//就是放到里面就是或不执行
        cookie.setMaxAge(1000);
        resp.addCookie(cookie);
        cookie.setPath("/");
        System.out.println(cookie);

        if (weChatSession.getOpenid() != null) {//weChatSession.getOpenid() ==openid

            if (wsi.selectByOpenId(weChatSession.getOpenid()) == null) {//就是等于null才插入


                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                user.setOpenid(weChatSession.getOpenid());
                user.setNickname(nickName);
                user.setCity(city);
                user.setCountry(country);
                user.setGender(gender);
                user.setProvince(province);
                user.setAvatarurl(avatarUrl);
                user.setLanguage(language);
                user.setCtime(sdf.format(date));

                wsi.insertSelective(user);
                return "success";
            }

        }

        return "exsit";
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public WxUser getUserInfo(String code, HttpServletRequest req){
       // WeChatSession weChatSession = wgs.weChatSession(code);//腾讯就是不允许我们发送两次请求,就是或报错,所以就是要
        System.out.println(code);
        String  openid = null;
        Cookie[] cookies = req.getCookies();
        System.out.println("++++++"+cookies);
        for(Cookie  c : cookies){
            if(c.getName().equals(code)){
              openid= c.getValue();
            }
        }
        if (openid != null) {
            if (wsi.selectByOpenId(openid) == null) {//就是等于null才插
                return wsi.selectByOpenId(openid);
            }
        }
        return null;
    }

}
