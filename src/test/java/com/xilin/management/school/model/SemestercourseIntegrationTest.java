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
public class SemestercourseIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SemestercourseDataOnDemand dod;

	@Autowired
    SemestercourseRepository semestercourseRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", dod.getRandomSemestercourse());
        long count = semestercourseRepository.count();
        Assert.assertTrue("Counter for 'Semestercourse' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Semestercourse obj = dod.getRandomSemestercourse();
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to provide an identifier", id);
        obj = semestercourseRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Semestercourse' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Semestercourse' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", dod.getRandomSemestercourse());
        long count = semestercourseRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Semestercourse', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Semestercourse> result = semestercourseRepository.findAll();
        Assert.assertNotNull("Find all method for 'Semestercourse' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Semestercourse' failed to return any data", result.size() > 0);
    }

	@Test
    public void findBySemesteridAndFetchEagerly() {
		Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", dod.getRandomSemestercourse());
        long count = semestercourseRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Semestercourse', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Semestercourse> result = semestercourseRepository.findBySemesteridAndFetchEagerly();
        Assert.assertNotNull("Find all method for 'Semestercourse' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Semestercourse' failed to return any data", result.size() > 0);
	}
	
	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", dod.getRandomSemestercourse());
        long count = semestercourseRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Semestercourse> result = semestercourseRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Semestercourse' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Semestercourse' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", dod.getRandomSemestercourse());
        Semestercourse obj = dod.getNewTransientSemestercourse(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Semestercourse' identifier to be null", obj.getId());
        try {
            semestercourseRepository.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        semestercourseRepository.flush();
        Assert.assertNotNull("Expected 'Semestercourse' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Semestercourse obj = dod.getRandomSemestercourse();
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Semestercourse' failed to provide an identifier", id);
        obj = semestercourseRepository.findOne(id);
        semestercourseRepository.delete(obj);
        semestercourseRepository.flush();
        Assert.assertNull("Failed to remove 'Semestercourse' with identifier '" + id + "'", semestercourseRepository.findOne(id));
    }
}
