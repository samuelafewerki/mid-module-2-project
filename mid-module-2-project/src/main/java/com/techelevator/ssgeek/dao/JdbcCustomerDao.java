package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Customer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class JdbcCustomerDao implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcCustomerDao(DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "Select customer_id, name, street_address1, street_address2, city, state," +
                " zip_code) VALUES (?,?,?,?,?,?) RETURNING customer_id, name, street_address1," + " street_address2, city, state, zip_code;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, customerId);
            if (results.next()) {
                customer = mapRowToCustomer((ResultSet) results);
            }
        } catch (
                CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation",e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }

    @Override
    public Customer createCustomer(Customer newCustomer) throws SQLException {
        String sql = "INSERT INTO customer (name, street_address1, street_address2, city, state," +
                " zip_code) VALUES (?,?,?,?,?,?) RETURNING customer_id name, street_address1, street_address2, city, state, zip_code;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, newCustomer.getName(),
                newCustomer.getStreetAddress1(), newCustomer.getStreetAddress2(), newCustomer.getCity(),
                newCustomer.getState(), newCustomer.getZipCode());
        result.next();
        return mapRowToCustomer((ResultSet) result);
    }

    @Override
    public Customer updateCustomer(Customer updateCustomer) {
        String sql = "UPDATE customer SET name = ?, street_address1 = ?, street_address2 = ?, city = ?, " +
                "state = ?, zip_code = ? WHERE customer_id = ?;";
        Customer updatedCustomer = null;
        JdbcTemplate.update(sql, updateCustomer.getName(), updatedCustomer.getStreetAddress1(),
                updatedCustomer.getStreetAddress2(),
        updatedCustomer.getCity(), updatedCustomer.getState(), updatedCustomer.getZipCode(),
                updateCustomer.getCustomerId());

        return getCustomerById(updatedCustomer.getCustomerId());
    }

    @Override
    public Collection<Object> getCustomer() {
        return null;
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setStreetAddress1(rs.getString("street_address1"));
        customer.setStreetAddress2(rs.getString("street_address2"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setZipCode(rs.getString("zip_code"));
        return customer;
    }
}
