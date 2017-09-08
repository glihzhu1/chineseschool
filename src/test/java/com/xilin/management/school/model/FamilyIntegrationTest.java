package com.xilin.management.school.model;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class FamilyIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    FamilyDataOnDemand dod;

	@Autowired
    FamilyRepository familyRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", dod.getRandomFamily());
        long count = familyRepository.count();
        Assert.assertTrue("Counter for 'Family' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Family obj = dod.getRandomFamily();
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Family' failed to provide an identifier", id);
        obj = familyRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Family' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Family' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", dod.getRandomFamily());
        long count = familyRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Family', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Family> result = familyRepository.findAll();
        Assert.assertNotNull("Find all method for 'Family' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Family' failed to return any data", result.size() > 0);
    }

	@Test
    public void testSearchFamilyPage() {
		PageRequest pageable;
		pageable = new PageRequest(0, 10);
		//HashMap<String, String> mydropdownMap = new HashMap<String, String>();
		//mydropdownMap.put("tcandidateExperiences.intExpJob", "1");
		//Page<Family> pageData = familyRepository.findAll(
		//		MyCustomFamilySpecs.loadFullSearchTcandidate(null, mydropdownMap, null), pageable);
		
		Page<Family> pageData = familyRepository.findAll(
				MyCustomFamilySpecs.loadFullSearchFamilies("athe", null, null), pageable);
		
		long totalElements = pageData.getTotalElements();
		List<Family> result = pageData.getContent();
		
		Assert.assertNotNull("Find all method for 'Family' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Family' failed to return any data", result.size() > 0);
        
    }
	
	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", dod.getRandomFamily());
        long count = familyRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Family> result = familyRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Family' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Family' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", dod.getRandomFamily());
        Family obj = dod.getNewTransientFamily(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Family' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Family' identifier to be null", obj.getId());
        try {
            familyRepository.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        familyRepository.flush();
        Assert.assertNotNull("Expected 'Family' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Family obj = dod.getRandomFamily();
        Assert.assertNotNull("Data on demand for 'Family' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Family' failed to provide an identifier", id);
        obj = familyRepository.findOne(id);
        familyRepository.delete(obj);
        familyRepository.flush();
        Assert.assertNull("Failed to remove 'Family' with identifier '" + id + "'", familyRepository.findOne(id));
    }
}
