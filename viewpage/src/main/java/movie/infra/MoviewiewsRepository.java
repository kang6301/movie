package movie.infra;

import movie.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="moviewiews", path="moviewiews")
public interface MoviewiewsRepository extends PagingAndSortingRepository<Moviewiews, Long> {

    

    
}
