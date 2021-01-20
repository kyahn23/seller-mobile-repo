package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.module.mybatis.paginator.domain.PageList;
import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.CounselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CounselRestController {

    @Autowired
    CounselService counselService;

    /**
     * 상담관리현황페이지
     * @param param
     * @return
     */
    @PostMapping("/status/getAllCounselList")
    public DevMap getAllCounselList(@RequestBody DevMap param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String loginMbrId = (String) session.getAttribute("bnMbrId");
        param.put("LOGIN_MBR_ID", loginMbrId);

        DevMap rslt = new DevMap();
        param.put("CALL_ST_CD", "all");

        PageList<DevMap> listPage = counselService.counselList(param);

        rslt.put("counselList", listPage);
        rslt.put("pageInfo", listPage.getPaginator());
        return rslt;
    }

    /**
     * 방문예정 상담목록 가져오기
     * @param param
     * @return
     */
    @PostMapping("/service/getVisitCounselList")
    public DevMap getVisitCounselList(@RequestBody DevMap param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String loginMbrId = (String) session.getAttribute("bnMbrId");
        param.put("LOGIN_MBR_ID", loginMbrId);

        DevMap rslt = new DevMap();
        param.put("CALL_ST_CD", "P");   // 방문예정(상담상태 진행중-P) 상담만 보기위해

        PageList<DevMap> listPage = counselService.counselList(param);

        rslt.put("counselList", listPage);
        rslt.put("pageInfo", listPage.getPaginator());
        return rslt;
    }

    /**
     * 선택한 상담접수건의 현재 판매정책정보
     * @param param
     * @return
     */
    @PostMapping("/status/getCurrPolicy")
    public DevMap getCurrPolicy(@RequestBody DevMap param, HttpServletRequest request) {
        // 션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String loginMbrId = (String) session.getAttribute("bnMbrId");
        param.put("LOGIN_MBR_ID", loginMbrId);

        DevMap rslt = new DevMap();
        rslt = counselService.getCurrPolicy(param);
        return rslt;
    }



}
