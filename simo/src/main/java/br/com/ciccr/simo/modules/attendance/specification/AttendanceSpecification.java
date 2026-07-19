package br.com.ciccr.simo.modules.attendance.specification;

import br.com.ciccr.simo.modules.attendance.dto.request.AttendanceFilterRequest;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class AttendanceSpecification {

    private AttendanceSpecification() {
    }

    public static Specification<Attendance> byFilter(
            AttendanceFilterRequest filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return cb.conjunction();
            }

            if (filter.forceId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("force").get("id"),
                                filter.forceId()
                        )
                );
            }

            if (filter.status() != null) {
                predicates.add(
                        cb.equal(
                                root.get("status"),
                                filter.status()
                        )
                );
            }

            if (filter.protocol() != null &&
                    !filter.protocol().isBlank()) {

                predicates.add(
                        cb.like(
                                cb.upper(root.get("protocol")),
                                "%" + filter.protocol().trim().toUpperCase() + "%"
                        )
                );
            }

            if (filter.initialDate() != null) {

                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("attendanceDateTime"),
                                filter.initialDate().atStartOfDay()
                        )
                );
            }

            if (filter.finalDate() != null) {

                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("attendanceDateTime"),
                                filter.finalDate().atTime(23, 59, 59)
                        )
                );
            }

            if (filter.active() != null) {

                predicates.add(
                        cb.equal(
                                root.get("active"),
                                filter.active()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}