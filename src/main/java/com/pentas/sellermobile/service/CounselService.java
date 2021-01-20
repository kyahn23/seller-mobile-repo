package com.pentas.sellermobile.service;

import com.pentas.sellermobile.common.dao.CmmnDao;
import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageBounds;
import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageList;
import com.pentas.sellermobile.common.module.util.DevMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounselService {

    @Autowired
    CmmnDao cmmnDao;

    /**
     * 상담리스트 가져오기
     *
     * @param param
     * @return
     */
    public PageList<DevMap> counselList(DevMap param) {
        int page = Integer.parseInt(param.getString("page"));
        int pageSize = 10;

        PageBounds pageBounds = new PageBounds(page, pageSize);

        return cmmnDao.selectListPage("sellermobile.counsel.counselList", param, pageBounds);
    }

    /**
     * 현재 정책 가져오기
     *
     * @param param
     * @return
     */
    public DevMap getCurrPolicy(DevMap param) {
        return cmmnDao.selectOne("sellermobile.counsel.currPolicy", param);
    }

    /**
     * 블랙리스트 등록여부 확인
     *
     * @param param
     */
    public DevMap blackYnChk(DevMap param) {
        return cmmnDao.selectOne("sellermobile.counsel.blackYnChk", param);
    }

}
