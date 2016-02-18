package se.omegapoint.facepalm.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("log")
public class LogController {
    final CopyOnWriteArrayList<String> log;

    public LogController() {
        this.log = new CopyOnWriteArrayList<>();
    }

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(@RequestParam("data") String data) {
        log.add(data);
        return "Data added to log.";
    }

    @ResponseBody
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public List<String> view() {
        return log;
    }

    @ResponseBody
    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public String reset() {
        return "Sniffed data reset.";
    }
}
