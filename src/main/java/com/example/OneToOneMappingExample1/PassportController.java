package com.example.OneToOneMappingExample1;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/passport")
public class PassportController {
	@Autowired
	private PassportRepository passRepo;
	@GetMapping
	public List<Passport> getAllPassports(){
		return passRepo.findAll();
	}
	@GetMapping("/{id}")
	public Passport getPassportByID(@PathVariable int id) {
		return passRepo.findById(id).orElse(null);
	}
	@PostMapping
	public Passport savePassport(@RequestBody Passport pass) {
		return passRepo.save(pass);
	}
	@PutMapping("/{id}")
	public Passport updateApplicant(@PathVariable int id, @RequestBody Passport pass) {
		Passport passport=passRepo.findById(id).orElse(null);
		if(pass.getNumber()!=null) {
			passport.setNumber(pass.getNumber());
		}
		if(pass.getIssueDate()!=null) {
			passport.setIssueDate(pass.getIssueDate());
		}
		if(pass.getExpiryDate()!=null) {
			passport.setExpiryDate(pass.getExpiryDate());
		}
		return passRepo.save(passport);
	}
	@DeleteMapping("/{id}")
	public String deleteApplicant(@PathVariable int id) {
		if(passRepo.existsById(id)) {
			passRepo.deleteById(id);
			return "Passport deleted";
		}else {
			return "Passport not found";
		}
	}
	@GetMapping("/page/{pageNo}/{pageSize}")
	public List<Passport> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Passport> result=passRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<Passport>();
	}
	@GetMapping("/sort")
	public List<Passport> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sortDirection=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return passRepo.findAll(Sort.by(sortDirection, field));
	}
}
