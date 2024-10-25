package com.example.OneToOneMappingExample1;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
	@Autowired
	private ApplicantRepository appRepo;
	@GetMapping
	public List<Applicant> getAllApplicant(){
		return appRepo.findAll();
	}
	@GetMapping("/{id}")
	public Applicant getApplicantByID(@PathVariable int id) {
		return appRepo.findById(id).orElse(null);
	}
	@PostMapping
	public Applicant saveApplicant(@RequestBody Applicant app) {
		return appRepo.save(app);
	}
	@PutMapping("/{id}")
	public Applicant updateApplicant(@PathVariable int id, @RequestBody Applicant app) {
		Applicant applicant=appRepo.findById(id).orElse(null);
		if(app.getName()!=null) {
			applicant.setName(app.getName());
		}
		if(app.getAge()!=0) {
			applicant.setAge(app.getAge());
		}
		if(app.getCity()!=null) {
			applicant.setCity(app.getCity());
		}
		if(app.getPassport()!=null) {
			applicant.setPassport(app.getPassport());
		}
		return appRepo.save(applicant);
	}
	@DeleteMapping("/{id}")
	public String deleteApplicant(@PathVariable int id) {
		if(appRepo.existsById(id)) {
			appRepo.deleteById(id);
			return "Applicant deleted";
		}else {
			return "Applicant not found";
		}
	}
	@GetMapping("/page/{pageNo}/{pageSize}")
	public List<Applicant> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Applicant> result=appRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<Applicant>();
	}
	@GetMapping("/sort")
	public List<Applicant> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sortDirection=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return appRepo.findAll(Sort.by(sortDirection, field));
	}
}
