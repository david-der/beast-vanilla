package beast.scheduled;

import beast.model.FibonacciJob;
import beast.persist.FibonacciJobRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class JobRunner {

    private static Logger logger = LoggerFactory.getLogger(JobRunner.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    FibonacciJobRepository fibonacciJobRepository;

    @Scheduled(fixedRate = 5000)
    public void runPending() {
        logger.info("Job Runner waking up at " + dateFormat.format(new Date()));
        List<FibonacciJob> jobs = fibonacciJobRepository.findByStatus("PENDING");
        for (FibonacciJob each : jobs) {
            each.setStartDate(new Date());
            if (each.getUpperBound() <= 1) {
                logger.error("Job: " + each.getId() + " failed. The upper boundary was too low a number");
                each.setStatus("FAILED");
                fibonacciJobRepository.save(each);
                break;
            }
            each.calculate();
            each.setEndDate(new Date());
            logger.info("Job: " + each.getId() + " has succeeded.");
            each.setStatus("SUCCESS");
            fibonacciJobRepository.save(each);
        }
    }
}