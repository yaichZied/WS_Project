package tn.iit.ws.controller.all;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Course;
import tn.iit.ws.entities.all.Pointing;

@RestController
@RequestMapping("pointing")
public class PointingController extends GenericController<Pointing, Long> {
	
}