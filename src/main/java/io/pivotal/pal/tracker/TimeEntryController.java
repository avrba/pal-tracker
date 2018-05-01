package io.pivotal.pal.tracker;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/time-entries")
public class TimeEntryController {
    TimeEntryRepository repo;
    public TimeEntryController(TimeEntryRepository timeEntryRepository)
    {
        repo=timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry time=repo.create(timeEntryToCreate);
        return new ResponseEntity(time, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long l) {
        TimeEntry ret =repo.find(l);
        if(ret!=null) return new ResponseEntity(ret,HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List ret=repo.list();
        return new ResponseEntity(ret,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id")Long l,@RequestBody TimeEntry expected) {
        TimeEntry update = repo.update(l, expected);
        if(update==null){return ResponseEntity.notFound().build();}
        return new ResponseEntity(update,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long l) {
        repo.delete(l);
        return ResponseEntity.noContent().build();
    }
}
