package pl.emgie.oraclemappingtype.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "test_types")
@GenericGenerator(name = "simpleGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "simpleSequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
public class TestTable {

    @Id
    @GeneratedValue(generator = "simpleGenerator")
    private Long id;

    @Column(name = "adres")
    @Type(type = "pl.emgie.oraclemappingtype.model.AddressUserType")
    private Address address;

    @Column(name = "insert_date")
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @PrePersist
    private void setCurrentDate() {
        date = Calendar.getInstance().getTime();
    }
}
