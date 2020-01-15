package dl.service;


import dl.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan("com.example.demo.dl.repository")
public class PersonService implements TruncateTableService {

    @Autowired
    PersonRepository personRepository;

    @Override
    @Transactional
    public void truncate() {
        try {
            personRepository.truncatePerson();
        } catch (Exception e) {
            System.out.println("ERROR ENCOUNTERED");
            throw e;
        }
    }
}
