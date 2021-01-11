package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.exception.UserException;
import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.CommonService;
import com.pentas.sellermobile.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ShopRestController {

    @Autowired
    ShopService shopService;

    @Autowired
    CommonService commonService;

    /**
     * 매장정보 조회
     *
     * @param request
     * @param param
     * @return rslt
     */
    @PostMapping("/shop/getShopInfo")
    public DevMap getShopInfo(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        DevMap rslt = new DevMap();
        rslt = shopService.getShopInfo(param);
        return rslt;
    }

    /**
     * 매장정보 수정
     *
     * @param request
     * @param param
     * @return rslt
     */
    @PostMapping("/shop/modiShopInfo")
    public DevMap modiShopInfo(HttpServletRequest request, @RequestBody DevMap param) throws IllegalStateException, UserException {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("mbrId", bnMbrId);
        shopService.modiShopInfo(param);

        DevMap rslt = new DevMap();
        rslt.put("rsltStat", "SUCC");
        return rslt;
    }

    /**
     * 직원정보 조회
     *
     * @param request
     * @param param
     * @return rslt
     */
    @PostMapping("/shop/getEmpList")
    public DevMap getEmpList(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        DevMap rslt = new DevMap();
        List<DevMap> resultList = null;
        resultList = shopService.getEmpList(param);
        rslt.put("empList", resultList);
        return rslt;
    }

    /**
     * 직원 비밀번호 재발급
     *
     * @param request
     * @param param
     * @return rslt
     */
    @PostMapping("/shop/modiEmpPwd")
    public DevMap modiEmpPwd(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("amdMbrId", bnMbrId);

        shopService.modiEmpPwd(param);

        DevMap rslt = new DevMap();
        rslt.put("rsltStat", "SUCC");
        return rslt;
    }

    /**
     * 직원 권한 수정
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/shop/modiEmpPrms")
    public DevMap modiEmpPrms(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("amdMbrId", bnMbrId);

        shopService.modiEmpPrms(param);

        DevMap rslt = new DevMap();
        rslt.put("rsltStat", "SUCC");
        return rslt;
    }

    /**
     * 매장 공지사항 목록 조회
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/shop/getBnBrdList")
    public DevMap getBnBrdList(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        DevMap rslt = new DevMap();
        List<DevMap> bnBrdList = null;

        bnBrdList = shopService.getBnBrdList(param);
        rslt.put("bnBrdList", bnBrdList);
        return rslt;
    }

    /**
     * 매장 공지사항 게시여부 숨김으로 변경
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/shop/modiBnBrdHide")
    public DevMap modiBnBrdHide(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);

        shopService.modiBnBrdHide(param);

        DevMap rslt = new DevMap();
        rslt.put("rsltStat", "SUCC");
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
