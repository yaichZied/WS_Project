package tn.iit.ws.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.iit.ws.entities.all.Block;
import tn.iit.ws.entities.time.FixedTimeSlot;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.repositories.BlockRepository;
import tn.iit.ws.repositories.TimeSlotRepository;
import tn.iit.ws.repositories.UserRepository;

@Component
public class InitComponent {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlockRepository blockRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@PostConstruct
	private void init() {
		if(userRepository.count()==0)
		{
			Administrator admin = new Administrator();
			admin.setEmail("admin@admin.com");
			admin.setName("Administrateur");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("password"));
			userRepository.save(admin);
		}
		if(blockRepository.count()==0)
		{
			RandomString rs = new RandomString();
			for (int i = 0; i < 250; i++) {
				Block block = new Block();
				block.setName(rs.nextString());
				blockRepository.save(block);
			}
		}
		if(timeSlotRepository.count()==0) {
			FixedTimeSlot time = new FixedTimeSlot();
			time.setDate(new Date());
			time.getDate().setHours(8);
			time.getDate().setMinutes(0);
			time.getDate().setSeconds(0);
			time.setDuration(120);
			timeSlotRepository.save(time);
		}
	}
}
class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(21);
    }

}
