package org.sashkadron.studygram.controller.subject;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.controller.subject.dto.SubjectDto;
import org.sashkadron.studygram.controller.subject.facade.SubjectFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SubjectController {

	private final SubjectFacade subjectFacade;

	@GetMapping("/public/subjects")
	public ResponseEntity<List<SubjectDto>> findSubjects(@RequestParam(required = false) String name) {
		return ResponseEntity.ok(subjectFacade.findSubjects(name));
	}

}
