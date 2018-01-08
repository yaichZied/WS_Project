package tn.iit.ws.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.iit.ws.entities.all.Block;
import tn.iit.ws.entities.all.ClassRoom;
import tn.iit.ws.entities.all.Course;
import tn.iit.ws.entities.all.Department;
import tn.iit.ws.entities.all.Floor;
import tn.iit.ws.entities.all.Group;
import tn.iit.ws.entities.all.Level;
import tn.iit.ws.entities.all.SubLevel;
import tn.iit.ws.entities.all.Subject;
import tn.iit.ws.entities.time.ContinuousTimeSlot;
import tn.iit.ws.entities.time.Semester;
import tn.iit.ws.entities.time.TimeSlot;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.entities.users.PointingAgent;
import tn.iit.ws.entities.users.Student;
import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.repositories.AdminstratorRepository;
import tn.iit.ws.repositories.BlockRepository;
import tn.iit.ws.repositories.ClassRoomRepository;
import tn.iit.ws.repositories.ContinuousTimeSlotRepository;
import tn.iit.ws.repositories.CourseRepository;
import tn.iit.ws.repositories.DepartmentRepository;
import tn.iit.ws.repositories.FloorRepository;
import tn.iit.ws.repositories.GroupRepository;
import tn.iit.ws.repositories.LevelRepository;
import tn.iit.ws.repositories.PointingAgentRepository;
import tn.iit.ws.repositories.SemesterRepository;
import tn.iit.ws.repositories.StudentRepository;
import tn.iit.ws.repositories.SubLevelRepository;
import tn.iit.ws.repositories.SubjectRepository;
import tn.iit.ws.repositories.TeacherRepository;

@Component
public class InitComponent {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AdminstratorRepository adminstratorRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private PointingAgentRepository pointingAgentRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private SubLevelRepository subLevelRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private BlockRepository blockRepository;
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private ClassRoomRepository classRoomRepository;
	@Autowired
	private ContinuousTimeSlotRepository continuousTimeSlotRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SemesterRepository semesterRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ResourceLoader resourceLoader;
	private List<String> studentNames;
	private List<String> teacherNames;
	private List<String> subjectNames;
	private List<ClassRoom> classRooms;
	private List<Group> groups;
	private List<Subject> subjects;
	private List<Teacher> teachers;
	private List<TimeSlot> timeSlots;

	public InitComponent() {

	}

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initEntities() {

		if (adminstratorRepository.count() > 0)
			return;
		initLists();
		Administrator admin = new Administrator();
		admin.setEmail("admin@admin.com");
		admin.setName("Administrator");
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("password"));
		adminstratorRepository.save(admin);
		
		PointingAgent agent = new PointingAgent();
		agent.setEmail("agent@agent.com");
		agent.setName("Pointing Agent");
		agent.setUsername("agent");
		agent.setPassword(passwordEncoder.encode("password"));
		pointingAgentRepository.save(agent);
		
		Block blockA = new Block();
		blockA.setName("Block A");
		blockA = blockRepository.save(blockA);
		Block blockB = new Block();
		blockB.setName("Block B");
		blockB = blockRepository.save(blockB);
		subjects = new ArrayList<>();
		for (Iterator<String> iterator = subjectNames.iterator(); iterator.hasNext();) {
			String subjectName = iterator.next();
			Subject subject = new Subject();
			subject.setName(subjectName);
			subject = subjectRepository.save(subject);
			subjects.add(subject);
		}
		List<Floor> floors = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Floor fa = new Floor();
			fa.setBlock(blockA);
			fa.setName("A " + (i + 1));
			fa = floorRepository.save(fa);
			floors.add(fa);
			Floor fb = new Floor();
			fb.setBlock(blockB);
			fb.setName("B " + (i + 1));
			fb = floorRepository.save(fb);
			floors.add(fb);
		}
		classRooms = new ArrayList<>();
		int indexClassRoom = 1;
		for (Iterator<Floor> iterator = floors.iterator(); iterator.hasNext();) {
			Floor floor = iterator.next();
			for (int i = 0; i < 4; i++) {
				ClassRoom cr = new ClassRoom();
				cr.setFloor(floor);
				cr.setName("Salle " + indexClassRoom);
				indexClassRoom++;
				cr = classRoomRepository.save(cr);
				classRooms.add(cr);
			}
		}
		Department info = new Department();
		info.setName("Département Informatique");
		info = departmentRepository.save(info);
		Level level = new Level();
		level.setDepartment(info);
		level.setName("cycle Ingenieur");
		level = levelRepository.save(level);
		groups = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			SubLevel subLevel = new SubLevel();
			subLevel.setName("GLID" + i);
			subLevel.setLevel(level);
			subLevel = subLevelRepository.save(subLevel);
			Group group = new Group();
			group.setName("GLID" + i);
			group.setSubLevel(subLevel);
			group = groupRepository.save(group);
			groups.add(group);
			for (int j = 0; j < 30; j++) {
				String name = studentNames.get(j + (30 * (i - 1)));
				String username = name.replaceAll(" \\.", ".").replaceAll(" ", ".");
				Student st = new Student();
				st.setEmail(username + "@examplemail.com");
				st.setUsername(username);
				st.setGroupe(group);
				st.setName(name);
				st.setPassword(passwordEncoder.encode(username));
				studentRepository.save(st);
			}
		}
		teachers = new ArrayList<>();
		for (int j = 0; j < 30; j++) {
			String name = teacherNames.get(j);
			String username = name.replaceAll(" \\.", ".").replaceAll(" ", ".");
			Teacher teacher = new Teacher();
			teacher.setEmail(username + "@examplemail.com");
			teacher.setUsername(username);
			teacher.setName(name);
			teacher.setPassword(passwordEncoder.encode(username));
			teacher = teacherRepository.save(teacher);
			teachers.add(teacher);
		}
		Semester semester = new Semester();
		semester.setBegin(new Date(118, 0, 1));
		semester.setEnd(new Date(118, 5, 30));
		semester = semesterRepository.save(semester);
		int[] timesBegin = new int[] { 495, 600, 705, 810 ,915};
		int[] timesEnds = new int[] { 585, 690, 795, 900 , 1005};

		timeSlots = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			for (int j = 0; j < 5; j++) {
				ContinuousTimeSlot cts1 = new ContinuousTimeSlot();
				cts1.setDay(i);
				cts1.setBegin(timesBegin[j]);
				cts1.setEnd(timesEnds[j]);
				cts1.setSemester(semester);
				cts1.setWeekly(true);
				cts1 = continuousTimeSlotRepository.save(cts1);
				timeSlots.add(cts1);
				ContinuousTimeSlot cts1A = new ContinuousTimeSlot();
				cts1A.setDay(i);
				cts1A.setBegin(timesBegin[j]);
				cts1A.setEnd(timesEnds[j]);
				cts1A.setSemester(semester);
				cts1A.setWeekly(false);
				cts1A.setWeekA(true);
				cts1A = continuousTimeSlotRepository.save(cts1A);
				timeSlots.add(cts1A);
				ContinuousTimeSlot cts1B = new ContinuousTimeSlot();
				cts1B.setDay(i);
				cts1B.setBegin(timesBegin[j]);
				cts1B.setEnd(timesEnds[j]);
				cts1B.setSemester(semester);
				cts1B.setWeekly(false);
				cts1B.setWeekA(false);
				cts1B = continuousTimeSlotRepository.save(cts1B);
				timeSlots.add(cts1B);
				List<Integer> takenClassRooms = new ArrayList<>();
				List<Integer> takenClassRoomsA = new ArrayList<>();
				List<Integer> takenClassRoomsB = new ArrayList<>();
				List<Integer> takenTeacher = new ArrayList<>();
				List<Integer> takenTeacherA = new ArrayList<>();
				List<Integer> takenTeacherB = new ArrayList<>();
				for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext();) {
					Group group = iterator.next();
					double rand = Math.random();

					if (rand < 0.25) {
						Course course = new Course();
						int a = -1;
						while (a == -1 || takenClassRooms.contains(a) || takenClassRoomsA.contains(a)
								|| takenClassRoomsB.contains(a)) {
							a = (int) (Math.random()*classRooms.size());
						}
						takenClassRooms.add(a);
						course.setClassRoom(classRooms.get(a));
						course.setGroup(group);
						course.setSubject(subjects.get((int) (Math.random()*subjects.size())));
						a = -1;
						while (a == -1 || takenTeacher.contains(a) || takenTeacherA.contains(a)
								|| takenTeacherB.contains(a)) {
							a = (int) (Math.random()*classRooms.size());
						}
						takenTeacher.add(a);
						group = groupRepository.findOne(group.getId());
						Teacher t = teachers.get(a);
						if(!group.getTeachers().contains(t))
							group.getTeachers().add(t);
						group= groupRepository.save(group);
						group = groupRepository.findOne(group.getId());
						course.setTeacher(t);
						course.setTimeSlot(cts1);
						courseRepository.save(course);
					} else {
						if (rand < 0.5) {
							Course course = new Course();
							int a = -1;
							while (a == -1 || takenClassRooms.contains(a) || takenClassRoomsA.contains(a)
									) {
								a = (int) (Math.random()*classRooms.size());
							}
							takenClassRoomsA.add(a);
							course.setClassRoom(classRooms.get(a));
							course.setGroup(group);
							course.setSubject(subjects.get((int) (Math.random()*subjects.size())));
							a = -1;
							while (a == -1 || takenTeacher.contains(a) || takenTeacherA.contains(a)
									) {
								a = (int) (Math.random()*classRooms.size());
							}
							takenTeacherA.add(a);
							group = groupRepository.findOne(group.getId());
							Teacher t = teachers.get(a);
							if(!group.getTeachers().contains(t))
								group.getTeachers().add(t);
							group= groupRepository.save(group);
							group = groupRepository.findOne(group.getId());
							course.setTeacher(t);
							course.setTimeSlot(cts1A);
							courseRepository.save(course);
						} else {
							if (rand < 0.75) {
								Course course = new Course();
								int a = -1;
								while (a == -1 || takenClassRooms.contains(a) || takenClassRoomsB.contains(a)
										) {
									a = (int) (Math.random()*classRooms.size());
								}
								takenClassRoomsB.add(a);
								course.setClassRoom(classRooms.get(a));
								course.setGroup(group);
								course.setSubject(subjects.get((int) (Math.random()*subjects.size())));
								a = -1;
								while (a == -1 || takenTeacher.contains(a) || takenTeacherB.contains(a)
										) {
									a = (int) (Math.random()*classRooms.size());
								}
								takenTeacherB.add(a);
								group = groupRepository.findOne(group.getId());
								Teacher t = teachers.get(a);
								if(!group.getTeachers().contains(t))
									group.getTeachers().add(t);
								group= groupRepository.save(group);
								group = groupRepository.findOne(group.getId());
								course.setTeacher(t);
								course.setTimeSlot(cts1B);
								courseRepository.save(course);
							}
						}
					}
				}
			}
		}
	}

	private void initLists() {
		studentNames = new ArrayList<>();
		Resource resource = resourceLoader.getResource("classpath:names/students");
		try {
			Scanner sc = new Scanner(resource.getInputStream());
			while (sc.hasNext()) {
				String name = sc.nextLine().trim();
				studentNames.add(name);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		teacherNames = new ArrayList<>();
		resource = resourceLoader.getResource("classpath:names/teachers");
		try {
			Scanner sc = new Scanner(resource.getInputStream());
			while (sc.hasNext()) {
				String name = sc.nextLine().trim();
				teacherNames.add(name);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		subjectNames = new ArrayList<>();
		resource = resourceLoader.getResource("classpath:names/subjects");
		try {
			Scanner sc = new Scanner(resource.getInputStream());
			while (sc.hasNext()) {
				String name = sc.nextLine().trim();
				subjectNames.add(name);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
