package beast.controller;

import beast.model.FibonacciJob;
import beast.persist.FibonacciJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/jobs")
public class FibonacciJobController {

    @Autowired
    FibonacciJobRepository fibonacciJobRepository;

    @RequestMapping("/new")
    public String newJob() {
        return "newjob";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createJob(HttpServletRequest request, Model model) {

        FibonacciJob fibonacciJob = new FibonacciJob();
        fibonacciJob.setRequestDate(new Date());
        fibonacciJob.setUpperBound(Integer.parseInt(request.getParameter("upperLimit")));
        fibonacciJob.setStatus("PENDING");

        fibonacciJobRepository.save(fibonacciJob);

        return "createConfirm";
    }

    @RequestMapping("/list")
    public String listJobs(Model model) {
        model.addAttribute("jobs", fibonacciJobRepository.findAll());
        return "jobList";
    }
}
