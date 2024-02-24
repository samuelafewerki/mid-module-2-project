package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCustomerDaoTest {

    private static final Customer CUSTOMER_1 = new Customer(1, "Customer 1", "Addr 1-1", "Addr 1-2", "City 1", "S1", "11111");
    private static final Customer Customer_2 = new Customer(2, "Customer 2", "Addr 2-1", "Addr 2-2", "City 2", "S1", "22222");
    private static final Customer Customer_3 = new Customer(3, "Customer 3", "Addr 3-1", "Addr null", "City 3", "S1", "33333");
    private static final Customer Customer_4 = new Customer(4, "Customer 4", "Addr 4-1", "Addr null", "City 4", "S1", "44444");
    private static final Customer CUSTOMER_5 = new Customer(1, "Customer 1", "Addr 1-1", "Addr null", "City 1", "S1", "11111");
    private JdbcCustomerDao CustomerDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        DataSource dataSource = null;
        JdbcCustomerDao jdbcCustomerDao = new JdbcCustomerDao(dataSource);
    }

    @Test
    public void get_customer_by_id_returns_correct_customer() {

        Object Customer_1 = null;
        com.techelevator.ssgeek.dao.CustomerDao jdbcCustomerDao = null;
        get_customer_by_id_returns_correct_customer((Customer) Customer_1, jdbcCustomerDao.getCustomerById(1));
        get_customer_by_id_returns_correct_customer(Customer_2, jdbcCustomerDao.getCustomerById(2));
        get_customer_by_id_returns_correct_customer(Customer_3, jdbcCustomerDao.getCustomerById(3));
        get_customer_by_id_returns_correct_customer(Customer_4, jdbcCustomerDao.getCustomerById(4));
        Assert.assertNull(jdbcCustomerDao.getCustomerById(99));
    }

    private void get_customer_by_id_returns_correct_customer(Customer customer_2, Customer customerById) {
    }

    @Test
    public void get_customers_returns_customers() {
        com.techelevator.ssgeek.dao.CustomerDao jdbcCustomerDao = null;
        List<Customer> customers = jdbcCustomerDao.getCustomers();
        Assert.assertEquals(4, jdbcCustomerDao.getCustomer().size());
        Assert.assertNotNull("getCustomers(" + customers + ") returned data");
    }
}
