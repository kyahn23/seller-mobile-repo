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
import javax.servlet.http.HttpSession;

@RestController
public class CommonRestController {

    @Autowired
    CommonService commonService;

    @Autowired
    CmmnDao cmmnDao;

    /**
     * 멤버 ID (이메일) 중복 확인 (return 객체에 중복 여부)
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/member/checkId")
    public DevMap checkMemberId(HttpServletRequest request, @RequestBody DevMap param) {
        String chkMbrId = commonService.checkMemberId(param);

        DevMap rslt = new DevMap();
        rslt.put("chkMbrId", chkMbrId);
        return rslt;
    }

    /**
     * 초기화 비밀번호 변경
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/member/updateMbrPw")
    public DevMap updateMbrPw(HttpServletRequest request, @RequestBody DevMap param) {
        HttpSession session = request.getSession();
        String bnMbrId = (String) session.getAttribute("bnMbrId");
        param.put("bnMbrId", bnMbrId);
        commonService.updateMbrPw(param);

        DevMap rslt = new DevMap();
        rslt.put("rsltStat", "SUCC");
        return rslt;
    }

    /**
     * 이미지 태그의 src에 소스정보를 제공한다.
     *
     * @param request
     * @param response
     * @throws UserException
     */
    @RequestMapping("/imageSrc")
    public void downloadImage(HttpServletRequest request, HttpServletResponse response) throws UserException {
        String fileName = request.getParameter("fileName");
        commonService.imageSrc(response, fileName);
    }

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

        UserVO user = new UserVO();
//        user = commonService.getUserInfo(param);
        user = cmmnDao.selectOne("sellermobile.common.selectUserInfo", bnMbrId);
        result.put("bnCardChkYn", bnCardChkYn);
        result.put("user", user);
        return result;
    }



}
