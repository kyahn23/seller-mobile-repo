package com.pentas.sellermobile.common.springsecurity;

import com.pentas.sellermobile.common.dao.CmmnDao;
import com.pentas.sellermobile.common.module.util.CmmnUtil;
import com.pentas.sellermobile.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	CmmnDao cmmnDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userId = authentication.getName();
        String userPw = authentication.getCredentials().toString();

        // 사용자 정보 가져오기
		UserVO userVO = new UserVO();
		try {
        	userVO = cmmnDao.selectOne("sellermobile.common.selectUserInfo", userId);
        } catch(RuntimeException ex) {
        	log.error(CmmnUtil.getExceptionMsg(ex));
        	throw new BadCredentialsException("로그 확인이 필요합니다.");
        }
        if(userVO == null) {
        	throw new BadCredentialsException("로그인 실패");
        }

        userVO.setInputPwdNo(userPw);

		return new UsernamePasswordAuthenticationToken(userVO, userPw);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
