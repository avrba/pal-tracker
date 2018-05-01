package io.pivotal.pal.tracker;

import java.util.*;

import static java.util.Arrays.asList;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long,TimeEntry> data = new HashMap<>();

    long counter=1;
    public TimeEntry find(Long id) {
        return data.get(id);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(counter);
        data.put(counter++,timeEntry);

        return timeEntry;
    }

    public List<TimeEntry> list() {
        return new ArrayList(data.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry retVal=new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        data.replace(id,retVal);
        return retVal;
    }

    public void delete(long id) {
        data.remove(id);
    }
}
