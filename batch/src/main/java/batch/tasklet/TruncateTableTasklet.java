package batch.tasklet;

import dl.service.PersonService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

public class TruncateTableTasklet implements Tasklet {

    @Autowired
    PersonService personService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            personService.truncate();
        } catch (Exception e) {
            stepContribution.setExitStatus(new ExitStatus("Unable to truncate"));
            chunkContext.getStepContext().getStepExecution().setTerminateOnly();
        }

        return RepeatStatus.FINISHED;
    }
}
