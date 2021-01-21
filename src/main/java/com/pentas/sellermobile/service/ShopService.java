package com.pentas.sellermobile.service;

import com.pentas.sellermobile.common.dao.CmmnDao;
import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageBounds;
import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageList;
import com.pentas.sellermobile.common.module.util.DevMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    CmmnDao cmmnDao;

    @Autowired
    CommonService commonService;

    /**
     * 매장 공지사항 목록 가져오기
     *
     * @param param
     * @return
     */
    public PageList<DevMap> getBnBrdList(DevMap param) {
        int page = Integer.parseInt(param.getString("page"));
        int pageSize = 10;

        PageBounds pageBounds = new PageBounds(page, pageSize);

        return cmmnDao.selectListPage("sellermobile.shop.getBnBrdList", param, pageBounds);
    }

    /**
     * 매장 공지사항 글 가져오기
     *
     * @param param
     * @return
     */
    public DevMap getBnBrdOne(DevMap param) {
        DevMap bnBrdOne = new DevMap();
        bnBrdOne = cmmnDao.selectOne("sellermobile.shop.getBnBrdOne", param);

        // 첨부파일 정보 가져오기
        String brdAttFile1 = "";
        String brdAttFile2 = "";
        if (!bnBrdOne.isEmpty()) {
            brdAttFile1 = bnBrdOne.getString("brdAttFile1");
            brdAttFile2 = bnBrdOne.getString("brdAttFile2");
        }

        List<DevMap> brdOneCurFileInfo = new ArrayList<>();
        if (!"".equals(brdAttFile1) && brdAttFile1 != null) {
            DevMap brdOneFile1 = new DevMap();
            brdOneFile1 = commonService.getFileInfo(brdAttFile1);
            brdOneFile1.put("filStat", "CUR");
            brdOneCurFileInfo.add(brdOneFile1);
        }
        if (!"".equals(brdAttFile2) && brdAttFile2 != null) {
            DevMap brdOneFile2 = new DevMap();
            brdOneFile2 = commonService.getFileInfo(brdAttFile2);
            brdOneFile2.put("filStat", "CUR");
            brdOneCurFileInfo.add(brdOneFile2);
        }

        DevMap rslt = new DevMap();
        rslt.put("bnBrdOne", bnBrdOne);
        rslt.put("brdOneCurFileInfo", brdOneCurFileInfo);
        return rslt;
    }

}
