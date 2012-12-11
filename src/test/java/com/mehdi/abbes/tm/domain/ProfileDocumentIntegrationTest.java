package com.mehdi.abbes.tm.domain;

import com.mehdi.abbes.tm.repository.ProfileRepository;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ProfileDocumentIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ProfileDocumentDataOnDemand dod;

	@Autowired
    ProfileRepository profileRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", dod.getRandomProfileDocument());
        long count = profileRepository.count();
        Assert.assertTrue("Counter for 'ProfileDocument' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        ProfileDocument obj = dod.getRandomProfileDocument();
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to provide an identifier", id);
        obj = profileRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ProfileDocument' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProfileDocument' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", dod.getRandomProfileDocument());
        long count = profileRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProfileDocument', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProfileDocument> result = profileRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProfileDocument' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProfileDocument' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", dod.getRandomProfileDocument());
        long count = profileRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ProfileDocument> result = profileRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'ProfileDocument' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProfileDocument' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", dod.getRandomProfileDocument());
        ProfileDocument obj = dod.getNewTransientProfileDocument(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProfileDocument' identifier to be null", obj.getId());
        profileRepository.save(obj);
        Assert.assertNotNull("Expected 'ProfileDocument' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        ProfileDocument obj = dod.getRandomProfileDocument();
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProfileDocument' failed to provide an identifier", id);
        obj = profileRepository.findOne(id);
        profileRepository.delete(obj);
        Assert.assertNull("Failed to remove 'ProfileDocument' with identifier '" + id + "'", profileRepository.findOne(id));
    }
}
