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

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
public class SemesterpodIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    SemesterpodDataOnDemand dod;

	@Autowired
    SemesterpodRepository semesterpodRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", dod.getRandomSemesterpod());
        long count = semesterpodRepository.count();
        Assert.assertTrue("Counter for 'Semesterpod' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Semesterpod obj = dod.getRandomSemesterpod();
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to provide an identifier", id);
        obj = semesterpodRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Semesterpod' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Semesterpod' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", dod.getRandomSemesterpod());
        long count = semesterpodRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Semesterpod', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Semesterpod> result = semesterpodRepository.findAll();
        Assert.assertNotNull("Find all method for 'Semesterpod' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Semesterpod' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", dod.getRandomSemesterpod());
        long count = semesterpodRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Semesterpod> result = semesterpodRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Semesterpod' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Semesterpod' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", dod.getRandomSemesterpod());
        Semesterpod obj = dod.getNewTransientSemesterpod(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Semesterpod' identifier to be null", obj.getId());
        try {
            semesterpodRepository.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        semesterpodRepository.flush();
        Assert.assertNotNull("Expected 'Semesterpod' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Semesterpod obj = dod.getRandomSemesterpod();
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Semesterpod' failed to provide an identifier", id);
        obj = semesterpodRepository.findOne(id);
        semesterpodRepository.delete(obj);
        semesterpodRepository.flush();
        Assert.assertNull("Failed to remove 'Semesterpod' with identifier '" + id + "'", semesterpodRepository.findOne(id));
    }
}
