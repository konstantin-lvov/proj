package ru.kl.proj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kl.proj.dao.OrganizationDaoImpl;
import ru.kl.proj.dao.SettingsDaoImpl;
import ru.kl.proj.entity.Entity;
import ru.kl.proj.entity.EntityBucket;
import ru.kl.proj.entity.Organization;
import ru.kl.proj.entity.Settings;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class AccountMainPageController {

    @Autowired
    private OrganizationDaoImpl organizationDao;

    @Autowired
    private SettingsDaoImpl settingsDao;

    @GetMapping("/accountMainPage")
    public String getAccountMP(HttpServletRequest request, Model model,
                               @RequestParam(value = "pageMarker", required = false) String pageMarker){

        Organization organization;
        organization = organizationDao.read(request.getRemoteUser());

        String oid = String.valueOf(organization.getOid());
        Settings settings = settingsDao.read(oid);

        ArrayList<Entity> list = new ArrayList<>();
        list.add(organization);
        list.add(settings);
        EntityBucket entityBucket = new EntityBucket(list);
        model.addAttribute("pageMarker", pageMarker);
        model.addAttribute("entityBucket", entityBucket);

        return "accountMainPage";
    }
}
