package cn.itcast.service.impl;

import cn.itcast.service.IProviderService;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class ProviderServiceImpl implements IProviderService {
    @Override
    public String say() {
        return "hello dubbo";
    }
}
