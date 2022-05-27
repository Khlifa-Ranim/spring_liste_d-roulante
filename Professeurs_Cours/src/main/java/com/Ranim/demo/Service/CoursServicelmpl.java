package com.Ranim.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Ranim.demo.entities.Cours;

import com.Ranim.demo.repos.CoursRepository;


@Service
public class CoursServicelmpl implements CoursService {

	@Autowired
	private CoursRepository CoursRepository;

	@Override
	public List<Cours> findAll() {
		return CoursRepository.findAll();
	}

	@Override
	public Cours saveCours(Cours c) {
		return CoursRepository.save(c);
	}

	@Override
	public Cours updateCours(Cours c) {
		return CoursRepository.save(c);
	}

	@Override
	public void deleteCours(Cours c) {
		CoursRepository.delete(c);
	}

	@Override
	public void deleteCoursById(Long idCat) {
		CoursRepository.deleteById(idCat);
	}

	@Override
	public Cours getCours(Long idCat) {
		return CoursRepository.findById(idCat).get();
	}

	@Override
	public Page<Cours> getAllCourssParPage(int page, int size) {
		return CoursRepository.findAll(PageRequest.of(page, size)) ;	
		}

}