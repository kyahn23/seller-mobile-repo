package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageList;
import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.CommonService;
import com.pentas.sellermobile.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ShopRestController {

    @Autowired
    ShopService shopService;

    @Autowired
    CommonService commonService;

    /**
     * 매장 공지사항 목록 조회
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/shop/getBnBrdList")
    public DevMap getBnBrdList(HttpServletRequest request, @RequestBody DevMap param) {
        String bnMbrId = param.getString("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        DevMap rslt = new DevMap();
        PageList<DevMap> bnBrdList = shopService.getBnBrdList(param);

        rslt.put("bnBrdList", bnBrdList);
        rslt.put("pageInfo", bnBrdList.getPaginator());
        return rslt;
    }

    /**
     * 매장 공지사항 글 가져오기
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/shop/getBnBrdOne")
    public DevMap getBnBrdOne(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        DevMap rslt = new DevMap();
        rslt = shopService.getBnBrdOne(param);
        return rslt;
    }
}
