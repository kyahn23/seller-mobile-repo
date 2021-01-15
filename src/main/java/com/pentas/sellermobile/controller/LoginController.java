package com.pentas.sellermobile.controller;

import com.pentas.sellermobile.common.module.util.DevMap;
import com.pentas.sellermobile.service.CommonService;
import com.pentas.sellermobile.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    CommonService commonService;


    @RequestMapping("/afterLoginProc")
    public String afterLoginProc(RedirectAttributes redirect, Principal principal, HttpServletRequest request) {
        request.getSession().invalidate();
        HttpSession session = request.getSession();

        String redirectLogin = "redirect:/#/login";
        String redirectMain = "redirect:/#/main";

        if (principal == null) {
            return "redirect:/#/login";
        } else {
            UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String bnMbrId = userVO.getBnMbrId();
            String inputPwdNo = userVO.getInputPwdNo();
            String pwdNo = userVO.getPwdNo();

            DevMap param = new DevMap();

            // 비밀번호 오류횟수 5회 이상 시 시큐리티 내용 삭제
            String pwerrCnt = userVO.getPwerrCnt();
            if (Integer.parseInt(pwerrCnt) >= 5) {
                SecurityContextHolder.clearContext();
                redirect.addFlashAttribute("loginStat", "FAIL_PWERR_CNT_OVER");
                redirectLogin = StringUtils.join(redirectLogin, "?loginStat=FAIL_PWERR_CNT_OVER");

                return redirectLogin;
            }

            // 비밀번호 일치 시 오류횟수 초기화
            // 비밀번호 불일치 시 시큐리시 내용 삭제, 오류횟수 추가
            if (pwdNo.equals(inputPwdNo)) {
                commonService.updatePwerrCntZero(bnMbrId);
            } else {
                SecurityContextHolder.clearContext();
                commonService.updatePwerrCntPlus(bnMbrId);

                // 비밀번호 오류횟수 5회 이상 시 로그인 불가
                // 오류횟수 5회 미만 시 오류횟수 안내
                if ((Integer.parseInt(pwerrCnt) + 1) >= 5) {
                    commonService.updateLginAvlN(bnMbrId);
                    redirect.addFlashAttribute("loginStat", "FAIL_PWERR_CNT_OVER");
                    redirectLogin = StringUtils.join(redirectLogin, "?loginStat=FAIL_PWERR_CNT_OVER");

                } else {
                    redirect.addFlashAttribute("loginStat", "FAIL_PWERR_CNT_" + Integer.toString((Integer.parseInt(pwerrCnt) + 1)));
                    redirectLogin = StringUtils.join(redirectLogin, "?loginStat=FAIL_PWERR_CNT_", Integer.toString((Integer.parseInt(pwerrCnt) + 1)));

                }

                return redirectLogin;
            }

            String bnCardChkYn = commonService.checkBnCardChk(bnMbrId);

            session.setAttribute("bnMbrId", userVO.getBnMbrId());
            session.setAttribute("mbrNm", userVO.getMbrNm());
            session.setAttribute("pwnoInitYn", userVO.getPwnoInitYn());
            session.setAttribute("mstMbrYn", userVO.getMstMbrYn());
            session.setAttribute("bnCardChkYn", bnCardChkYn);
            redirect.addFlashAttribute("loginStat", "SUCC");
            redirectMain = StringUtils.join(redirectMain, "?bnMbrId=", userVO.getBnMbrId());

            return redirectMain;
        }
    }

    @RequestMapping("/loginFail")
    public String loginFail(RedirectAttributes redirect, ModelMap model, HttpServletRequest request) {
        String redirectLogin = "redirect:/#/login";

        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContextHolder.clearContext();
        redirect.addFlashAttribute("loginStat", "FAIL");
        redirectLogin = StringUtils.join(redirectLogin, "?loginStat=FAIL");

        return redirectLogin;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContextHolder.clearContext();

        return "redirect:/#/login";
    }
}
