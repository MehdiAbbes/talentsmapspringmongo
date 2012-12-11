package com.mehdi.abbes.tm.domain;

import com.mehdi.abbes.tm.repository.SkillRepository;
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
public class SkillDocumentIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private SkillDocumentDataOnDemand dod;

	@Autowired
    SkillRepository skillRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", dod.getRandomSkillDocument());
        long count = skillRepository.count();
        Assert.assertTrue("Counter for 'SkillDocument' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        SkillDocument obj = dod.getRandomSkillDocument();
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to provide an identifier", id);
        obj = skillRepository.findOne(id);
        Assert.assertNotNull("Find method for 'SkillDocument' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SkillDocument' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", dod.getRandomSkillDocument());
        long count = skillRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'SkillDocument', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SkillDocument> result = skillRepository.findAll();
        Assert.assertNotNull("Find all method for 'SkillDocument' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SkillDocument' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", dod.getRandomSkillDocument());
        long count = skillRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SkillDocument> result = skillRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'SkillDocument' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SkillDocument' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", dod.getRandomSkillDocument());
        SkillDocument obj = dod.getNewTransientSkillDocument(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SkillDocument' identifier to be null", obj.getId());
        skillRepository.save(obj);
        Assert.assertNotNull("Expected 'SkillDocument' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        SkillDocument obj = dod.getRandomSkillDocument();
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillDocument' failed to provide an identifier", id);
        obj = skillRepository.findOne(id);
        skillRepository.delete(obj);
        Assert.assertNull("Failed to remove 'SkillDocument' with identifier '" + id + "'", skillRepository.findOne(id));
    }
}
