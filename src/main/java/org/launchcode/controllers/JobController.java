package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view //

        Job aParticularJob = jobData.findById(id);
        model.addAttribute("aParticularJob",aParticularJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if(errors.hasErrors()){
            return "new-job";
        }
        String jobName = jobForm.getName();
        Employer jobEmp = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location jobLoc = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType jobPosition = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency jobCore = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        Job newJob = new Job(jobName, jobEmp, jobLoc, jobPosition, jobCore);
        jobData.add(newJob);
        String id = String.valueOf(newJob.getId());

        return "redirect:?id=" + id;

    }
}
