package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.facepalm.client.adapters.DocumentAdapter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class DocumentController {

    private final DocumentAdapter documentAdapter;

    @Autowired
    public DocumentController(final DocumentAdapter documentAdapter) {
        this.documentAdapter = notNull(documentAdapter);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public String documents(final Model model, @RequestParam(value = "user", required = false) String user) {
        user = isBlank(user) ? "admin" : user;
        model.addAttribute("documents", documentAdapter.documentsFor(user));

        return "documents";
    }
}
