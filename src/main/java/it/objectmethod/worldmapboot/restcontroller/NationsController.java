package it.objectmethod.worldmapboot.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.worldmapboot.dao.INationDao;
import it.objectmethod.worldmapboot.domain.Nation;

@RestController
public class NationsController {

	@Autowired
	INationDao nationDao;

	@RequestMapping("/continents/list/")
	public List<String> getIndex() {

		List<String> continenti = new ArrayList<String>();

		try {
			continenti = nationDao.getAllContinent();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return continenti;
	}

	@RequestMapping("/nation/list/")
	public List<Nation> getIndex(@RequestParam("continent") String continent) {

		List<Nation> nazioni = null;

		try {
			nazioni = nationDao.getAllNation(continent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nazioni;

	}

}
