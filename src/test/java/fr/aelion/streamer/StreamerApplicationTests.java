package fr.aelion.streamer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aelion.streamer.controllers.CourseController;
import fr.aelion.streamer.dto.CourseAddDto;
import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.ModuleUpdateDto;
import fr.aelion.streamer.dto.simplerDtos.CourseDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class StreamerApplicationTests {
	@Autowired
	CourseController courseController;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test course entity")
	public void testEntityCourse() throws JsonProcessingException {
		Course course = new Course();
		course.setTitle("bonjour");
		assertEquals(course.getTitle(),"bonjour");
	}
	@Test
	@DisplayName("A course could be added with a title")
	public void testAddCourse() throws JsonProcessingException {
//		CourseAddDto courseToAdd = new CourseAddDto();
//		courseToAdd.setTitle("Titre test");
//		Member creator = new Member();
//		creator.setId(1);
//
//		courseToAdd.setCreator(creator);
//		Set<ModuleUpdateDto> modules = new HashSet<>();
//		courseToAdd.setModules(modules);
//		ResponseEntity<FullCourseDto> course = courseController.add(courseToAdd);
//		System.out.println(course.getBody().getTitle());
//
//		assertEquals(course.getBody().getTitle(),"Titre test");
//		courseController.remove(course.getBody().getId());


	}

}
