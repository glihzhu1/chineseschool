package com.xilin.management.school.model;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class SchoolannouncementIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SchoolannouncementDataOnDemand dod;

	@Autowired
    SchoolannouncementRepository schoolannouncementRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", dod.getRandomSchoolannouncement());
        long count = schoolannouncementRepository.count();
        Assert.assertTrue("Counter for 'Schoolannouncement' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Schoolannouncement obj = dod.getRandomSchoolannouncement();
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to provide an identifier", id);
        obj = schoolannouncementRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Schoolannouncement' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Schoolannouncement' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", dod.getRandomSchoolannouncement());
        long count = schoolannouncementRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Schoolannouncement', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Schoolannouncement> result = schoolannouncementRepository.findAll();
        Assert.assertNotNull("Find all method for 'Schoolannouncement' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Schoolannouncement' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", dod.getRandomSchoolannouncement());
        long count = schoolannouncementRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Schoolannouncement> result = schoolannouncementRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Schoolannouncement' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Schoolannouncement' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", dod.getRandomSchoolannouncement());
        Schoolannouncement obj = dod.getNewTransientSchoolannouncement(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Schoolannouncement' identifier to be null", obj.getId());
        try {
            schoolannouncementRepository.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        schoolannouncementRepository.flush();
        Assert.assertNotNull("Expected 'Schoolannouncement' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Schoolannouncement obj = dod.getRandomSchoolannouncement();
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Schoolannouncement' failed to provide an identifier", id);
        obj = schoolannouncementRepository.findOne(id);
        schoolannouncementRepository.delete(obj);
        schoolannouncementRepository.flush();
        Assert.assertNull("Failed to remove 'Schoolannouncement' with identifier '" + id + "'", schoolannouncementRepository.findOne(id));
    }
}
