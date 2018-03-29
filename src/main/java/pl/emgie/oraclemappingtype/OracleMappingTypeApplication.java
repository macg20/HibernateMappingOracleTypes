package pl.emgie.oraclemappingtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.emgie.oraclemappingtype.model.Address;
import pl.emgie.oraclemappingtype.model.TestTable;
import pl.emgie.oraclemappingtype.repository.TestTableRepository;

@SpringBootApplication
public class OracleMappingTypeApplication implements CommandLineRunner {

    private TestTableRepository testTableRepository;

    @Autowired
    public OracleMappingTypeApplication(TestTableRepository testTableRepository) {
        this.testTableRepository = testTableRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OracleMappingTypeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Address address = new Address();
        address.setCity("Warsaw");
        address.setCountryId(new char[]{'W','A'});
        address.setPostalCode("00-999");
        address.setStateProvince("Masovian");
        address.setStreetAddress("KarolkowaStreet");

        TestTable testTable = new TestTable();
        testTable.setAddress(address);

        testTableRepository.save(testTable);
    }
}
