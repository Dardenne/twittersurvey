package org.sophonet.twittersurvey;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.sophonet.twittersurvey.twitter.Configuration;
import org.sophonet.twittersurvey.twitter.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Dardenne
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    private Scheduler scheduler;
    @Autowired
    private TwitterUserRepository userRepository;
    @Autowired
    private Search search;

    public App() {
        init();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Grab the Scheduler instance from the Factory
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        // and start it off
        scheduler.start();
        twitterLoad();
    }

    private void init() {
        Configuration configuration = Configuration.getInstance();

//        try {
//            List<String> list = Search.getInstance().searchTweets();
//            list.forEach((string) -> {
//                System.out.println(string);
//            });
//        } catch (TwitterException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    private void twitterLoad() {
        JobTwitterLoad.search = search;
        // define the job and tie it to our MyJob class
        JobDetail job = newJob(JobTwitterLoad.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(120)
                        .repeatForever())
                .build();

        try {
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
