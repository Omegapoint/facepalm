package se.omegapoint.facepalm.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.facepalm.client.adapters.SearchAdapter;
import se.omegapoint.facepalm.client.models.SearchQuery;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class SearchController {

    private final SearchAdapter searchAdapter;

    @Autowired
    public SearchController(final SearchAdapter searchAdapter) {
        this.searchAdapter = notNull(searchAdapter);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(final @RequestParam("query") SearchQuery searchQuery, final Model model) {
        model.addAttribute("searchResult", searchAdapter.findUsersLike(searchQuery));

        return "search";
    }
}
