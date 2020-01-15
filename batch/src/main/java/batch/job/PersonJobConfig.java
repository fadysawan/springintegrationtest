package batch.job;

import batch.tasklet.TruncateTableTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = {"dl.service","dl.entity"})
public class PersonJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public TruncateTableTasklet truncateTableTasklet() {
        return new TruncateTableTasklet();
    }

    @Bean
    public Job testJob() throws Exception {
        return jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .flow(truncateStep())
                .end().build();
    }

    protected Step truncateStep() {
        return stepBuilderFactory.get("cleanupTable")
                .tasklet(this.truncateTableTasklet())
                .build();
    }

}
