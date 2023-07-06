package mye030.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mye030.project.model.Measurements;
import mye030.project.repository.MeasurementsRepository;
import mye030.project.service.MeasurementsService;

import org.json.CDL;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MeasurementsController extends HttpServlet{

	@Autowired
	private MeasurementsService measurementsService;
	//@Autowired
	//private MeasurementsRepository eRepo;
	// display list of measurements
	MeasurementsCreation selectForm = new MeasurementsCreation();
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listMeasurements", measurementsService.getAllMeasurements());
		//for(int i=0;i < measurementsService.getAllMeasurements().size();i++) {
		//	System.out.println(measurementsService.getAllMeasurements().get(i).getIndicator());
		//}
		return "index";
	}/*
	@GetMapping({"/list", "/"})
	public ModelAndView getAllEmployees() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("listMeasurements", eRepo.findAll());
		return mav;
	}*/
	
	@RequestMapping("/ShowList")
	public String staticCsvForD3(Model model) {
		List<Measurements> dokimh =new ArrayList<Measurements>(measurementsService.getAllMeasurements());
		List<Integer> xronies =new ArrayList<Integer>();
		List<Double> measur =new ArrayList<Double>();
		List<String> indicator =new ArrayList<String>();
		List<String> cname =new ArrayList<String>();
		//xronies.add(1);
		
		for(Measurements i: dokimh) {
			//System.out.println(i.getYear());
			//for (int j: xronies) {
			if(!xronies.contains(i.getYear())) {
					xronies.add(i.getYear());
					//System.out.println(i.getYear());
					//System.out.println(xronies.size());	
			}
			if(!measur.contains(i.getMeasurement())) {
				if (i.getMeasurement() != null) {
					measur.add(i.getMeasurement());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
				}
			}
			if(!cname.contains(i.getCountry_name())) {
				cname.add(i.getCountry_name());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
			}
			if(!indicator.contains(i.getIndicator())) {
				indicator.add(i.getIndicator());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
			}
		}
		model.addAttribute("xronies", xronies);
		model.addAttribute("cname", cname);
		model.addAttribute("indicators", indicator);
		//model.addAttribute("apotelesmata", dokimh);
		return "/ShowList";
	}
	
	@GetMapping("/create")
	public String showCreateForm(Model model, HttpServletRequest req) {
	    //MeasurementsCreation selectForm = new MeasurementsCreation();
		//List<Measurements> dokimh =new ArrayList<Measurements>();

	    selectForm.addMeasurement(new Measurements());
	    

	    model.addAttribute("form", selectForm);
	    for (int i = 0; i < selectForm.getSize(); i++) {
			System.out.println(selectForm.getNewmes().get(0).getYear());
		}
	    String n1 = req.getParameter("labelValue");
	    System.out.println(n1);
	    //selectForm.getNewmes().get(0).setCountry_name(model.getAttribute(selectForm.getNewmes().get(0).getCountry_name()));
	    return "/create";
	}
	
/*	@PostMapping("/passMeDataNaiveD3")
	@Transactional
	public String saveMeasurements(@ModelAttribute MeasurementsCreation form, Model model) {
		
	    measurementsService.saveAllMeasurements(form.getNewmes());

	    //model.addAttribute("books", measurementsService.findAll());
	    return "redirect:/books/all";
	}*/
	
	@RequestMapping("/passMeDataNaiveD3")
    public ModelAndView passSimpleNaiveData(ModelMap model,HttpServletRequest req,HttpServletRequest request, HttpServletResponse response) {
		
		List<Measurements> dokimh =new ArrayList<Measurements>(measurementsService.getAllMeasurements());
		List<Integer> xronies =new ArrayList<Integer>();
		List<Double> measur =new ArrayList<Double>();
		List<String> indicator =new ArrayList<String>();
		List<String> cname =new ArrayList<String>();
		//xronies.add(1);
		/*MeasurementsService helperMeasurementService;
		
		measurementsService.saveAll(form.getNewmes());

	    model1.addAttribute("books", helperMeasurementService.getAllMeasurements());*/
		
		//selectForm.getNewmes().get(0).setIndicator("Greece");
		//dokimh = measurementsService.saveAllMeasurements(selectForm.getNewmes());
		
		for(Measurements i: dokimh) {
			//System.out.println(i.getYear());
			//for (int j: xronies) {
			if(!xronies.contains(i.getYear())) {
					xronies.add(i.getYear());
					//System.out.println(i.getYear());
					//System.out.println(xronies.size());	
			}
			if(!measur.contains(i.getMeasurement())) {
				if (i.getMeasurement() != null) {
					measur.add(i.getMeasurement());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
				}
			}
			if(!cname.contains(i.getCountry_name())) {
				cname.add(i.getCountry_name());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
			}
			if(!indicator.contains(i.getIndicator())) {
				indicator.add(i.getIndicator());
				//System.out.println(i.getYear());
				//System.out.println(xronies.size());	
			}
		}
		/*for (int j: xronies) {
			System.out.println(j);
		}
		for (double j: measur) {
			System.out.println(j);
		}*/
		//showCreateForm(model1);
        Map<Integer, Double> values = new HashMap<Integer, Double>();
        values.put(2020, 135.0);values.put(2021, 125.0);values.put(2022, 45.0);
        
        /* Sort the map: (a) make a sorted AL; (b) put them in a string in order
         * The string also has a "header" with the names of the attributes in each pair.
         * */
        List<Integer> sortedKeys=new ArrayList<Integer>(values.keySet());
        Collections.sort(sortedKeys);
        
        String s ="key, value\n";
        for(Integer i: sortedKeys) {
        	Double v = values.get(i);
        	s = s + i + ", " + v + "\n";
       }
       /* A couple of extra parameters, to show you can pass several items to the thymeleaf */
       String constructedText = "This is some text that accompanies the data sent to the page. "
       		+ "You can generate such a text to describe the query characteristics.";
       String pageTitle = "Bar Chart";
       
       /* Convert the string to json 
        * */
       //JSONObject jo = new JSONObject(values);
       //model.addAttribute("dataMap", jo);
       //System.out.println ("\n\n" + jo.toString() + "\n\n");
        JSONArray result = CDL.toJSONArray(s);
        model.addAttribute("dataMap", result);
        model.addAttribute("description", constructedText);
        model.addAttribute("title", pageTitle);
        
        response.setContentType("text/html");
		String[] values1 = req.getParameterValues("labelValue");
		String n1 = req.getParameter("labelValue");
		//List<String> helper= new ArrayList<String>(Arrays.asList(values));
        for (int i = 0; i < selectForm.getSize(); i++) {
        	System.out.println(n1);
			//System.out.println(values1[1]);
		}
        return new ModelAndView("passMeDataNaiveD3", model);
    }
}
