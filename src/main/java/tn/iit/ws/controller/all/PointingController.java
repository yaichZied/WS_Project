package tn.iit.ws.controller.all;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Pointing;

@RestController
@RequestMapping("pointing")
public class PointingController extends GenericController<Pointing, Long> {
	@Autowired
    public JavaMailSender emailSender;
	@Autowired
	private ResourceLoader resourceLoader;
	@Override
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	@PreAuthorize("hasPermission(this.ENTITY, 'ADD')")
	public Pointing save(@RequestBody Pointing t) {
		Pointing res = super.save(t);
		MimeMessage message = emailSender.createMimeMessage();
		try 
		{
			message.addFrom(new Address[] {new InternetAddress("Administration","khaledbaklouti8@gmail.com")});
			message.setSubject("Avis d'absence");
			Resource resource = resourceLoader.getResource("classpath:mail.html");
			StringBuilder sb = new StringBuilder();
			try {
				Scanner sc = new Scanner(resource.getInputStream());
				while (sc.hasNext()) {
					sb.append(sc.nextLine());
				}
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String st = DateFormat.getDateInstance().format(res.getDate());
			message.setContent(sb.toString().replace("{{subject}}", res.getCourse().getSubject().getName())
					.replace("{{date}}", st),"text/html");
			message.setRecipient(RecipientType.TO,new InternetAddress(res.getCourse().getTeacher().getEmail(),res.getCourse().getTeacher().getEmail()));
			emailSender.send(message);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}