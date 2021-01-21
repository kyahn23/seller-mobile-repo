package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.dao.CmmnDao;
import com.pentas.sellermobile.common.exception.UserException;
import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.CommonService;
import com.pentas.sellermobile.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CommonRestController {

    @Autowired
    CommonService commonService;

    @Autowired
    CmmnDao cmmnDao;

    /**
     * 파일 다운로드
     *
     * @param request
     * @param response
     * @throws UserException
     */
    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws UserException {
        String fileName = request.getParameter("fileName");
        commonService.downloadFile(response, fileName);
    }

    /**
     * 로그인후 메인에서 유저 정보 확인
     *
     * @param param
     * @return
     */
    @PostMapping("/getinfo")
    public DevMap getinfo(@RequestBody DevMap param) {
        DevMap result = new DevMap();
        String bnMbrId = param.getString("bnMbrId");
        // 사업자등록증 확인여부
        String bnCardChkYn = commonService.checkBnCardChk(bnMbrId);

        // 업체정보
        DevMap temp = cmmnDao.selectOne("sellermobile.shop.getShopInfo", param);
        String bnName = temp.getString("bnNm");

        UserVO user = new UserVO();
        user = cmmnDao.selectOne("sellermobile.common.selectUserInfo", bnMbrId);

        result.put("mbrNm", user.getMbrNm());
        result.put("mstMbrYn", user.getMstMbrYn());
        result.put("bnName", bnName);
//        result.put("user", user);
        return result;
    }

    @RequestMapping("/image")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws UserException {
        String filename = request.getParameter("filename");
        commonService.imageSrc(response, filename);
    }

}
