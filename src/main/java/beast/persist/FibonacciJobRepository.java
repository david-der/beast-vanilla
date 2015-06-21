package beast.persist;

import beast.model.FibonacciJob;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by dder on 6/18/15.
 */
public interface FibonacciJobRepository extends PagingAndSortingRepository<FibonacciJob, String> {

    public List<FibonacciJob> findByStatus(String status);
}
