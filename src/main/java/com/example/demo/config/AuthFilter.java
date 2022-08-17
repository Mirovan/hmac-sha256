package com.example.demo.config;

import com.example.demo.model.CompanyData;
import com.example.demo.model.IndividualDataWrapper;
import com.example.demo.service.CompanyTokenService;
import com.example.demo.service.CompanyTokenServiceImpl;
import com.example.demo.util.HMACChecker;
import com.example.demo.util.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public AuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    protected AuthFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
//        super(requiresAuthenticationRequestMatcher, authenticationManager);
//        setAuthenticationManager(authenticationManager);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Получаем заголовок с HMAC подписью и тело запроса
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        String hmacSign = requestWrapper.getHeader("hmac-sign");
        Map<String, String> map = requestWrapper.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        item -> item.getValue()[0]
                ));
        String body = requestWrapper.getReader().lines().collect(Collectors.joining());

        //объект обертка из запроса
        Mapper<IndividualDataWrapper> individualDataWrapperMapper = new Mapper<>();
        IndividualDataWrapper individualDataWrapper =
                individualDataWrapperMapper.jsonToObject(body, IndividualDataWrapper.class);

        //ToDo: подумать как инжектить сервис для получения clientSecret
        CompanyTokenService companyTokenService = new CompanyTokenServiceImpl();
        CompanyData companyData = companyTokenService.findCompanyById(map.get("clientId"));

        //Проверка что данные пришли в теле и проверка HMAC
        if (individualDataWrapper != null && individualDataWrapper.getCompanyId() != null
                && companyData != null
                && HMACChecker.checkHMAC(map, companyData.getSecretToken(), hmacSign)) {
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(companyData.getId(), "", new ArrayList<>());
//            return authenticationManager.authenticate(token);
            Authentication authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(companyData.getId(), null));
            if (authResult.isAuthenticated()){
                // If I have fully authentication instance, add to the security context
                // do not think about the security context for now..
                SecurityContextHolder.getContext().setAuthentication(authResult);

                filterChain.doFilter(request, response);
            }
        } else {
            throw new BadCredentialsException("Error sign");
        }
    }

    /**
     * HMAC подпись передаётся в Headers
     * В теле передается IndividualDataWrapper, включающий ID компании-клиента пользователя API и данные физлица для проверки
     */
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException, IOException {
//        //Получаем заголовок с HMAC подписью и тело запроса
//        String hmacSign = request.getHeader("hmac-sign");
//        Map<String, String> map = request.getParameterMap().entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        item -> item.getValue()[0]
//                ));
//        String body = request.getReader().lines().collect(Collectors.joining());
//
//        //объект обертка из запроса
//        Mapper<IndividualDataWrapper> individualDataWrapperMapper = new Mapper<>();
//        IndividualDataWrapper individualDataWrapper =
//                individualDataWrapperMapper.jsonToObject(body, IndividualDataWrapper.class);
//
//        //ToDo: подумать как инжектить сервис для получения clientSecret
//        CompanyTokenService companyTokenService = new CompanyTokenServiceImpl();
//        CompanyData companyData = companyTokenService.findCompanyById(map.get("clientId"));
//
//        //Проверка что данные пришли в теле и проверка HMAC
//        if (individualDataWrapper != null && individualDataWrapper.getCompanyId() != null
//                && companyData != null
//                && HMACChecker.checkHMAC(map, companyData.getSecretToken(), hmacSign)) {
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(companyData.getId(), "", new ArrayList<>());
//            return getAuthenticationManager().authenticate(token);
//        } else {
//            throw new BadCredentialsException("Error sign");
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//
////        if (request.getAttribute(FILTER_APPLIED) != null) {
////            chain.doFilter(request, response);
////            return ;
////        }
////        //do something
////        SecurityContextHolder.getContext().setAuthentication(authResult);
////
////        request.setAttribute(FILTER_APPLIED,true);
////        chain.doFilter(request, response);
//
//        // Save user principle in security context
////        SecurityContextHolder.getContext().setAuthentication(authResult);
//        super.successfulAuthentication(request, response, chain, authResult);
//
//        chain.doFilter(request, response);
//    }

}