package ru.kl.proj.mobileControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kl.proj.dao.AuthTokenDaoImpl;
import ru.kl.proj.dao.OrganizationDaoImpl;
import ru.kl.proj.entity.AuthToken;
import ru.kl.proj.entity.Organization;
import ru.kl.proj.services.TokenGenerator;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MobileLoginController {

    @Autowired
    OrganizationDaoImpl organizationDao;

    @Autowired
    AuthTokenDaoImpl authTokenDao;

    /*
    Входящий запрос имеет поля - имя и пароль
     */
    @RequestMapping(value="/mobileLogin", method=GET)
    public String mobileLogin(@RequestParam(value = "organization", required = true) String organizationName,
                        @RequestParam(value = "password", required = true) String organizationPassword){

        TokenGenerator tokenGenerator = new TokenGenerator();

        try{
            Organization organization = organizationDao.readByName(organizationName);
            String existingToken = authTokenDao.read(organization.getOid()).getToken();

            /*
            Если токен существует и пароль совпал то возвращаем текущий токен
             */
            if(existingToken != null
                    && organizationPassword.equals(organization.getPassword())){
                return existingToken;
            }

            /*
            Если организация существует и пароль совпал то генерируем токен и возвращаем новый
             */
            if (organization != null
                    && organizationPassword.equals(organization.getPassword())){
                String newToken = tokenGenerator.generateNewToken();
                AuthToken authToken = new AuthToken(organization.getOid(), newToken);
                authTokenDao.create(authToken);
                return newToken;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "401";
    }
}
