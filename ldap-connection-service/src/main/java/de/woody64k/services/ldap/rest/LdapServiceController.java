package de.woody64k.services.ldap.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.woody64k.services.ldap.adapter.LdapAdapter;
import de.woody64k.services.ldap.data.User;

@RestController
@RequestMapping("/ldap")
public class LdapServiceController {

    @Autowired
    private LdapAdapter ldapAdapter;

    @GetMapping(value = "/user")
    public List<User> getUser(@RequestParam String eMail) {
	return ldapAdapter.loadDataByEMail(eMail);
    }
}
