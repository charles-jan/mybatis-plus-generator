package com.baomidou.mybatisplus.generator.config.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InflectorTest {

    @Test
    void tableizeTest() {
        assertEquals("customers", Inflector.tableize("Customer"));
        assertEquals("customer_infos", Inflector.tableize("CustomerInfo"));
        assertEquals("team_histories", Inflector.tableize("TeamHistory"));
        assertEquals("warehouses", Inflector.tableize("Warehouse"));
    }

    @Test
    void recordlizeTest() {
        assertEquals("Customer", Inflector.recordlize("customers"));
        assertEquals("Balance", Inflector.recordlize("balances"));
        assertEquals("Sheep", Inflector.recordlize("sheep"));
        assertEquals("Post", Inflector.recordlize("posts"));
    }
}
