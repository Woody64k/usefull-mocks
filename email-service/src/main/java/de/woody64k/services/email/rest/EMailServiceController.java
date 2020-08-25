package de.woody64k.services.email.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.woody64k.services.email.adapter.SmtpAdapter;
import de.woody64k.services.email.data.EMail;

@RestController
@RequestMapping("/ldap")
public class EMailServiceController {

    @Autowired
    private SmtpAdapter smtpAdapter;

    @PostMapping(value = "/email")
    public void getUser(@RequestBody EMail eMail) {
	smtpAdapter.sendMail(eMail);
    }
}
