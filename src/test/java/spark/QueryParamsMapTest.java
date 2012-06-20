package spark;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class QueryParamsMapTest {

    QueryParamsMap queryMap = new QueryParamsMap();
    
    @Test
    public void constructorWithParametersMap() {
        Map<String,String[]> params = new HashMap<String,String[]>();
        
        params.put("user[info][name]",new String[] {"fede"});
        
        QueryParamsMap queryMap = new QueryParamsMap(params);
        
        assertEquals("fede",queryMap.get("user").get("info").get("name").value());
    }
    
    @Test
    public void keyToMap() {
        QueryParamsMap queryMap = new QueryParamsMap();
        
        queryMap.keyToMap(queryMap,"user[info][first_name]",new String[] {"federico"});
        queryMap.keyToMap(queryMap,"user[info][last_name]",new String[] {"dayan"});

        assertFalse(queryMap.queryMap.isEmpty());
        assertFalse(queryMap.queryMap.get("user").queryMap.isEmpty());
        assertFalse(queryMap.queryMap.get("user").queryMap.get("info").queryMap.isEmpty());
        assertEquals("federico",queryMap.queryMap.get("user").queryMap.get("info").queryMap.get("first_name").values[0]);
        assertEquals("dayan",queryMap.queryMap.get("user").queryMap.get("info").queryMap.get("last_name").values[0]);
        
        assertTrue(queryMap.hasKeys());
        assertFalse(queryMap.hasValue());
        assertTrue(queryMap.queryMap.get("user").queryMap.get("info").queryMap.get("last_name").hasValue());
    }
    
    @Test
    public void testDifferentTypesForValue() {
        QueryParamsMap queryMap = new QueryParamsMap();
        
        queryMap.keyToMap(queryMap,"user[age]",new String[] {"10"});
        queryMap.keyToMap(queryMap,"user[agrees]",new String[] {"true"});

        assertEquals(new Integer(10),queryMap.get("user").get("age").integerValue());
        assertEquals(new Float(10),queryMap.get("user").get("age").floatValue());
        assertEquals(new Double(10),queryMap.get("user").get("age").doubleValue());
        assertEquals(new Long(10),queryMap.get("user").get("age").longValue());
        assertEquals(Boolean.TRUE,queryMap.get("user").get("agrees").booleanValue());
    }
    
    @Test
    public void parseKeyShouldParseRootKey() {
        String[] parsed = queryMap.parseKey("user[name][more]");
        
        assertEquals("user",parsed[0]);
        assertEquals("[name][more]",parsed[1]);
    }
    
    @Test
    public void parseKeyShouldParseSubkeys() {
        String[] parsed = null;
        
        parsed = queryMap.parseKey("[name][more]");
        
        assertEquals("name",parsed[0]);
        assertEquals("[more]",parsed[1]);
        
        parsed = queryMap.parseKey("[more]");
        
        assertEquals("more",parsed[0]);
        assertEquals("",parsed[1]);
    }
    
    @Test
    public void itShouldbeNullSafe() {
        QueryParamsMap queryParamsMap = new QueryParamsMap();
        
        String ret = queryParamsMap.get("x").get("z").get("y").value("w");
        
        assertNull(ret);
    }
    
    @Test
    public void testConstructor() {
        QueryParamsMap queryMap = new QueryParamsMap("user[name][more]","fede");

        assertFalse(queryMap.queryMap.isEmpty());
        assertFalse(queryMap.queryMap.get("user").queryMap.isEmpty());
        assertFalse(queryMap.queryMap.get("user").queryMap.get("name").queryMap.isEmpty());
        assertEquals("fede",queryMap.queryMap.get("user").queryMap.get("name").queryMap.get("more").values[0]);
    }
    
    @Test
    public void testDecode() {
        //text=Hello+G%FCnter
    }
}
