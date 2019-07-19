package it.objectmethod.worldmapboot.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.worldmapboot.config.Constants;
import it.objectmethod.worldmapboot.dao.imp.CityDao;
import it.objectmethod.worldmapboot.dao.imp.NationDao;
import it.objectmethod.worldmapboot.domain.City;

@RestController
public class CitysController {

	@Autowired
	CityDao cityDao;

	@Autowired
	NationDao nationDao;

	@RequestMapping("/city/list/")
	public List<City> getIndex(@RequestParam("countrycode") String countrycode, @RequestParam("order") String order) {

		List<City> citta = new ArrayList<City>();

		String orderName;
		String orderPop;

		if (order != null) {
			switch (order) {
			case Constants.ORDER_AZ:
				order = Constants.ORDER_ZA;
				orderName = Constants.ORDER_ZA;
				orderPop = Constants.ORDER_POP_ASC;
				break;
			case Constants.ORDER_ZA:
				order = Constants.ORDER_AZ;
				orderName = Constants.ORDER_AZ;
				orderPop = Constants.ORDER_POP_ASC;
				break;
			case Constants.ORDER_POP_ASC:
				order = Constants.ORDER_POP_DESC;
				orderName = Constants.ORDER_AZ;
				orderPop = Constants.ORDER_POP_DESC;
				break;
			case Constants.ORDER_POP_DESC:
				order = Constants.ORDER_POP_ASC;
				orderName = Constants.ORDER_AZ;
				orderPop = Constants.ORDER_POP_ASC;
				break;
			default:
				orderName = Constants.ORDER_AZ;
				orderPop = Constants.ORDER_POP_ASC;
			}
		} else {
			order = Constants.ORDER_AZ;
			orderName = Constants.ORDER_AZ;
			orderPop = Constants.ORDER_POP_ASC;
		}

		try {
			citta = cityDao.orderCity(countrycode, order);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return citta;
	}

	@GetMapping("/city/delete")
	public ResponseEntity<Integer> deleteCity(@PathParam("id") Integer id) {

		if (id != 0) {
			cityDao.deleteCity(id);
			return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/city/update/")
	public ResponseEntity<Integer> updateCity(@PathParam("id") Integer id, @RequestParam("cityName") String cityName) {

		if (id != 0) {
			cityDao.updateCity(id, cityName);
			return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/city/add/")
	public ResponseEntity<String> addCity(@RequestParam("newCityName") String newCityName,
			@RequestParam("countrycode") String countrycode) {

		if (countrycode != "") {
			cityDao.addCity(newCityName, countrycode);
			return new ResponseEntity<>(countrycode, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
