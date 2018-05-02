package io.pivotal.pal.tracker;

import java.util.Collection;
import java.util.List;

public interface TimeEntryRepository {
    TimeEntry find(Long id);

    TimeEntry create(TimeEntry timeEntry);

    List<TimeEntry> list();

    TimeEntry update(Long id, TimeEntry timeEntry);

    void delete(Long id);
}
