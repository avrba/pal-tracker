package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/time-entries")
public class TimeEntryController {

    private final CounterService counter;
    private final GaugeService gauge;
//    private final TimeEntryHealthIndicator timeEntryHealthIndicator;
    private TimeEntryRepository repo;

    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            CounterService counter,
            GaugeService gauge
//            TimeEntryHealthIndicator timeEntryHealthIndicator
    )
    {
        this.repo=timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
//        this.timeEntryHealthIndicator = timeEntryHealthIndicator;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry time=repo.create(timeEntryToCreate);
        counter.increment("TimeEntry.created");
        //check health with current repo.list.size?
        //timeEntryHealthIndicator(repo).builder.health();

        gauge.submit("timeEntries.count", repo.list().size());

        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long l) {
        TimeEntry ret =repo.find(l);
        if(ret!=null) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity(ret,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        List ret=repo.list();
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id")Long l,@RequestBody TimeEntry expected) {
        TimeEntry update = repo.update(l, expected);
        if(update==null){
            return  ResponseEntity.notFound().build();
        }
        counter.increment("TimeEntry.updated");
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long l) {
        repo.delete(l);
        //timeEntryHealthIndicator(repo).health();


        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", repo.list().size());

        return ResponseEntity.noContent().build();
    }
}
