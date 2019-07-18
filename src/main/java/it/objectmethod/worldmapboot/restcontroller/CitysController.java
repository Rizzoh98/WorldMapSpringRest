package it.objectmethod.worldmapboot.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.worldmapboot.config.Constants;
import it.objectmethod.worldmapboot.dao.imp.CityDao;
import it.objectmethod.worldmapboot.dao.imp.NationDao;
import it.objectmethod.worldmapboot.domain.City;
import it.objectmethod.worldmapboot.domain.Nation;

@RestController
public class CitysController {

	@Autowired
	CityDao cityDao;

	@Autowired
	NationDao nationDao;

	@RequestMapping("/citys")
	public List<City> getIndex(ModelMap model, @RequestParam(value = "countrycode", required = false) String countrycode,
			@RequestParam(value = "order", required = false) String order, HttpSession session) {
		List<City> citta = new ArrayList<City>();

		if (countrycode == null) {
			countrycode = (String) session.getAttribute("nation");
		} else {
			session.setAttribute("nation", countrycode);
		}

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

		model.addAttribute("orderName", orderName);
		model.addAttribute("orderPop", orderPop);

		model.addAttribute("result", citta);
		//return "Citta";
		return citta;
	}

	@GetMapping("/Delete")
	public String deleteCity(@RequestParam("id") Integer id) {

		cityDao.deleteCity(id);

		return "forward:/citys";
	}

	@GetMapping("/LoadEditPage")
	public String loadEditPage(@RequestParam("id") Integer idCity,
			@RequestParam(value = "countrycode", required = false) String countrycode, ModelMap map,
			HttpSession session) {

		City city = new City();
		List<Nation> allnation = null;

		if (idCity != 0) {
			city = cityDao.getCityById(idCity);
		} else {
			city.setId(0);
		}

		try {
			allnation = nationDao.getAllNations();
		} catch (Exception e) {
			e.printStackTrace();
		}

		map.addAttribute("result", allnation);
		map.addAttribute("citta", city);
		map.addAttribute("countrycode", countrycode);
		return "EditCity";
	}

	@GetMapping("/Save")
	public String saveCity(@RequestParam("id") Integer idCity, @RequestParam("cityname") String cityName,
			@RequestParam("selectCountry") String countrycode) {

		if (idCity != 0) {
			cityDao.updateCity(idCity, cityName);

		} else {
			cityDao.addCity(cityName, countrycode);
		}

		return "forward:/citys";
	}

}
