package com.mehdi.abbes.tm.domain;

import com.mehdi.abbes.tm.repository.ToolRepository;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Configurable
public class ToolDocumentIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ToolDocumentDataOnDemand dod;

	@Autowired
    ToolRepository toolRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", dod.getRandomToolDocument());
        long count = toolRepository.count();
        Assert.assertTrue("Counter for 'ToolDocument' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        ToolDocument obj = dod.getRandomToolDocument();
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to provide an identifier", id);
        obj = toolRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ToolDocument' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ToolDocument' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", dod.getRandomToolDocument());
        long count = toolRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ToolDocument', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ToolDocument> result = toolRepository.findAll();
        Assert.assertNotNull("Find all method for 'ToolDocument' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ToolDocument' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", dod.getRandomToolDocument());
        long count = toolRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ToolDocument> result = toolRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'ToolDocument' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ToolDocument' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", dod.getRandomToolDocument());
        ToolDocument obj = dod.getNewTransientToolDocument(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ToolDocument' identifier to be null", obj.getId());
        toolRepository.save(obj);
        Assert.assertNotNull("Expected 'ToolDocument' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        ToolDocument obj = dod.getRandomToolDocument();
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolDocument' failed to provide an identifier", id);
        obj = toolRepository.findOne(id);
        toolRepository.delete(obj);
        Assert.assertNull("Failed to remove 'ToolDocument' with identifier '" + id + "'", toolRepository.findOne(id));
    }
}
