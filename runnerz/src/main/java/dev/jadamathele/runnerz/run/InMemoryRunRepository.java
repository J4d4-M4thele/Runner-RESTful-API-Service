package dev.jadamathele.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryRunRepository implements RunRepository{

    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);

    private final List<Run> runs = new ArrayList<>();

    @Override
    public List<Run> findAll() {
        return runs;
    }

    @Override
    public Optional<Run> findById(Integer id) {
        return Optional.ofNullable(runs.stream()
                .filter(run -> run.id() == id)
                .findFirst()
                .orElseThrow(RunNotFoundException::new));
    }

    @Override
    public void create(Run run) {

        Run newRun = new Run(
                run.id(),
                run.title(),
                run.startedOn(),
                run.completedOn(),
                run.kms(),
                run.location());
        runs.add(newRun);
    }

    @Override
    public void update(Run newRun, Integer id) {

        Optional<Run> existingRun = findById(id);
        if(existingRun.isPresent()) {
            var r = existingRun.get();
            log.info("Updating Existing Run: " + existingRun.get());
            runs.set(runs.indexOf(r), newRun);
        }
    }

    @Override
    public void delete(Integer id) {

        log.info("Deleting Run: " + id);
        runs.removeIf(run -> run.id().equals(id));
    }

    @Override
    public int count() {
        return runs.size();
    }

    public void saveAll(List<Run> runs) {

        runs.stream()
                .forEach(run -> create(run));
    }

    public List<Run> findByLocation(String location) {
        return runs.stream()
                .filter(run -> Objects.equals(run.location(), location))
                .toList();
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR));

        runs.add(new Run(2,
                "Wednesday Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                6,
                Location.INDOOR));
    }
}
