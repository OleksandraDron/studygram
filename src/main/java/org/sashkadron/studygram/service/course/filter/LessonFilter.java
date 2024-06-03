package org.sashkadron.studygram.service.course.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonFilter {

	private UUID lecturerId;

	private UUID studentId;

	private Instant startDate;

	private Instant entDate;

}
