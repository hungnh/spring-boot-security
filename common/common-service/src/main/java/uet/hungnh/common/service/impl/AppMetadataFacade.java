package uet.hungnh.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uet.hungnh.common.service.IAppMetadataFacade;

import javax.servlet.http.HttpServletRequest;

@Component
public class AppMetadataFacade implements IAppMetadataFacade {

    @Autowired
    private HttpServletRequest request;

    public String getAppUrl() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
