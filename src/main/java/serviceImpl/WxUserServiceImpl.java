package serviceImpl;

import entity.WxUser;
import mapper.WxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.WxUserService;
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    WxUserMapper  wm;

    @Override
    public WxUser selectByOpenId(String openid) {
        return wm.selectByOpenId(openid);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return wm.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WxUser record) {
        return wm.insert(record);
    }

    @Override
    public int insertSelective(WxUser record) {
        return wm.insertSelective(record);
    }

    @Override
    public WxUser selectByPrimaryKey(Integer id) {
        return wm.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WxUser record) {
        return wm.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WxUser record) {
        return wm.updateByPrimaryKeySelective(record);
    }
}
