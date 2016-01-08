package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.facepalm.client.adapters.PolicyAdapter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class PolicyController {

    private final PolicyAdapter policyAdapter;

    @Autowired
    public PolicyController(final PolicyAdapter policyAdapter) {
        this.policyAdapter = notNull(policyAdapter);
    }

    @RequestMapping(value = "policy", method = RequestMethod.GET)
    public String policies(final @RequestParam(value = "file", required = false) String filename, final Model model) {
        if (isBlank(filename)) {
            return "policies";
        }

        model.addAttribute("fileContents", policyAdapter.retrievePolicy(filename));
        return "policies";
    }
}
