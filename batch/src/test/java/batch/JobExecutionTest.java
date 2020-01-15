package batch;

import batch.SpringBatchIntegrationTest;
import dl.entity.Person;
import dl.repository.PersonRepository;
import dl.service.TruncateTableService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

public class JobExecutionTest extends SpringBatchIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private TruncateTableService truncateTableService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void givenTheTruncateTableStepIsLaunched_whenItIsSuccessfull_thenTableShouldContainZeroRecords() throws Exception {

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("cleanupTable");
        ExitStatus actualStepExitStatus = null;

        Collection actualStepExecution = jobExecution.getStepExecutions();

        if (actualStepExecution.size() == 1) {
            actualStepExitStatus = ((StepExecution) actualStepExecution.iterator().next()).getExitStatus();
        }

        List<Person> orgStructures = personRepository.findAll();

        //then
        Assert.assertEquals(1, actualStepExecution.size());
        Assert.assertEquals("COMPLETED", actualStepExitStatus.getExitCode());
        Assert.assertEquals(orgStructures.size(), 0);
    }
}