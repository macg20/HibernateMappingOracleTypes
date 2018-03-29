package pl.emgie.oraclemappingtype.model;

import oracle.jdbc.OracleConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class AddressUserType implements UserType {
    private static final int SQL_TYPE = Types.STRUCT;

    private static final String DB_OBJECT_TYPE = "CUST_ADDRESS_TYP";

    public int[] sqlTypes() {

        return new int[]{SQL_TYPE};

    }

    @Override
    public Class returnedClass() {
        return Address.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        final Struct struct = (Struct) resultSet.getObject(strings[0]);

        if (resultSet.wasNull()) {
            return null;
        }

        final Address address = new Address();
        address.setStreetAddress((String) struct.getAttributes()[0]);
        address.setCity((String) struct.getAttributes()[2]);
        address.setPostalCode(((String) struct.getAttributes()[1]));
        address.setStateProvince((String) struct.getAttributes()[3]);
        address.setCountryId((char[]) struct.getAttributes()[4]);

        return address;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null)
            preparedStatement.setNull(i, SQL_TYPE, DB_OBJECT_TYPE);
        else {
            Address address = (Address) o;
            Object[] values = new Object[]{
                    address.getStreetAddress(), address.getPostalCode(), address.getCity(),
                    address.getStateProvince(), address.getCountryId()
            };

            final Connection connection = unwrapConnection(preparedStatement.getConnection());

            final STRUCT struct = new STRUCT(StructDescriptor.createDescriptor(DB_OBJECT_TYPE,

                    connection), connection, values);

            preparedStatement.setObject(i, struct, SQL_TYPE);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        final Address address = (Address) o;
        final Address clone = new Address();

        clone.setStreetAddress(address.getStreetAddress());
        clone.setCity(address.getCity());
        clone.setPostalCode(address.getPostalCode());
        clone.setStateProvince(address.getStateProvince());
        clone.setCountryId(address.getCountryId());

        return clone;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return null;
    }

    /*
     See https://stackoverflow.com/questions/6489514/apache-commons-dbcp-connection-object-problem-thread-classcastexception-in-org
     */

    private Connection unwrapConnection(Connection connection) throws SQLException {
        if (connection.isWrapperFor(OracleConnection.class)){
            return connection.unwrap(OracleConnection.class);
        }
        return connection;
    }

}