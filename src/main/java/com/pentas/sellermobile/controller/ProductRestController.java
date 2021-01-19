package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;

    /**
     * 요금제현황 가져오기
     *
     * @param param
     * @return
     */
    @PostMapping("/carr/getMntrtList")
    public DevMap getMntrtList(@RequestBody DevMap param, HttpServletRequest request) {
//        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String mbrId = (String) session.getAttribute("bnMbrId");
        param.put("BN_MBR_ID", mbrId);      // 회원아이디

        DevMap rslt = new DevMap();
        List<DevMap> resultList = null;

        resultList = productService.mntrtList(param);
        rslt.put("mntrtList", resultList);
        return rslt;
    }

    /**
     * 요금제 사용
     *
     * @param param
     * @return
     */
    @PostMapping("/carr/insertUseMnt")
    public DevMap insertUseMnt(@RequestBody List<DevMap> param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String mbrId = (String) session.getAttribute("bnMbrId");
        DevMap rslt = new DevMap();
        for (DevMap list : param) {
            list.put("BN_MBR_ID", mbrId);      // 회원아이디
        }
        productService.insertUseMnt(param);

        // 정상적으로 insert
        rslt.put("succ", "Y");
        return rslt;
    }

    /**
     * 요금제 미사용처리
     *
     * @param param
     * @return
     */
    @PostMapping("/carr/deleteUseMnt")
    public DevMap deleteUseMnt(@RequestBody List<DevMap> param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String mbrId = (String) session.getAttribute("bnMbrId");
        DevMap rslt = new DevMap();
        for (DevMap list : param) {
            list.put("BN_MBR_ID", mbrId);      // 회원아이디
        }

        productService.deleteUseMnt(param);
        rslt.put("succ", "Y");

        return rslt;
    }

    /**
     * 제품현황 리스트 가져오기
     *
     * @param param
     * @return
     */
    @PostMapping("/carr/getSellDeviceList")
    public DevMap getSellDeviceList(@RequestBody DevMap param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String mbrId = (String) session.getAttribute("bnMbrId");
        param.put("BN_MBR_ID", mbrId);      // 회원아이디

        DevMap rslt = new DevMap();
        List<DevMap> resultList = null;

        resultList = productService.getSellDeviceList(param);
        rslt.put("sellDeviceList", resultList);
        return rslt;
    }

    /**
     * 사용중인 요금제 가져오기
     *
     * @param param
     * @return
     */
    @PostMapping("/product/getUseMntByDevice")
    public DevMap getUseMntByDevice(@RequestBody DevMap param, HttpServletRequest request) {
        //        세션에서 회원아이디 가져오기
        HttpSession session = request.getSession();
        String mbrId = (String) session.getAttribute("bnMbrId");
        param.put("BN_MBR_ID", mbrId);      // 회원아이디                               // 회원아이디

        DevMap rslt = new DevMap();
        List<DevMap> moveCarrList = null;
        moveCarrList = productService.getmoveCarrList(param);
        List<DevMap> chgDeviceList = null;
        chgDeviceList = productService.getchgDeviceList(param);
        List<DevMap> newSignUpList = null;
        newSignUpList = productService.getnewSignUpList(param);

        rslt.put("moveCarrList", moveCarrList);
        rslt.put("chgDeviceList", chgDeviceList);
        rslt.put("newSignUpList", newSignUpList);

        return rslt;
    }

    /**
     * 기기 목록 조회
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/product/getDeviceList")
    public DevMap getDeviceList(HttpServletRequest request, @RequestBody DevMap param) {
        DevMap rslt = new DevMap();
        List<DevMap> phoneList = productService.deviceList(param);
        rslt.put("phoneList", phoneList);
        return rslt;
    }

}
