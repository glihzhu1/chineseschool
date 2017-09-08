package com.xilin.management.school.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.expression.spel.ast.Operator;

import com.xilin.management.school.web.util.Utils;

public class MyCustomFamilySpecs {

	public static Specification<Family> isActiveFamily() {
	    return new Specification<Family>() {
	      public Predicate toPredicate(Root<Family> root, CriteriaQuery<?> query,
	            CriteriaBuilder builder) {
	    	    return builder.and(
	    	    		builder.equal(root.get(Family_.active), Utils.ACTIVE));
	      }
	    };
	  }
	
	
	public static Specification<Family> loadFullSearchFamilies(final String searchTerm, final Map<String, String>myDropdownPredicateMap,
			final Map<String, String>myFilterByPredicateMap) {
	    return new Specification<Family>() {
	      public Predicate toPredicate(Root<Family> root, CriteriaQuery<?> query,
	            CriteriaBuilder builder) {
				Predicate finalPredicate = null;
				query.distinct(true);
				
				/*Predicate isActivePredicate = builder.and(
						builder.equal(root.get(Family_.active), Utils.ACTIVE));
				finalPredicate = isActivePredicate;*/

				// search string predicate
				Predicate searchPredicate = null;
				if (searchTerm != null && searchTerm.trim().length() > 0) {
					searchPredicate = createSearchTermPredicate(root, builder,
							searchTerm);
				}
				if (searchPredicate != null) {
					/*finalPredicate = builder.and(isActivePredicate,
							searchPredicate);*/
					finalPredicate = searchPredicate;
				}

				// search Dropdown
				/*Predicate dropdownFilterPredicate = null;
				if (myDropdownPredicateMap != null && myDropdownPredicateMap.size() > 0) {
					dropdownFilterPredicate = createSearchDropdownPredicate(root,
							builder, myDropdownPredicateMap);
				}
				if (dropdownFilterPredicate != null) {
					finalPredicate = builder.and(finalPredicate,
							dropdownFilterPredicate);
				}*/
              
				// dataTable filterBy
				Predicate tableFilterByPredicate = null;
				if (myFilterByPredicateMap != null && myFilterByPredicateMap.size() > 0) {
					tableFilterByPredicate = createFilterByPredicate(root, builder, myFilterByPredicateMap);
				}
				if (tableFilterByPredicate != null) {
					if (finalPredicate != null) {
						finalPredicate = builder.and(finalPredicate,
							tableFilterByPredicate);
					}
					else
						finalPredicate = tableFilterByPredicate;
				}
              
	    	  
				return finalPredicate;
	    	  //return null;
	      }
	    };
	  }
	
	private static Predicate createSearchTermPredicate(Root<Family> root, CriteriaBuilder builder, String searchTerm) {
		String finalText = searchTerm;
		if (!finalText.contains("%")) {
			finalText = "%" + searchTerm.toUpperCase() + "%";
  	    }
  	    
  	    return builder.or(
  	            builder.like(builder.upper(root.get(Family_.address1)), finalText),
  	            builder.like(builder.upper(root.get(Family_.cellphone)), finalText),
  	            builder.like(builder.upper(root.get(Family_.homephone)), finalText),
  	            builder.like(builder.upper(root.get(Family_.city)), finalText),
  	            builder.like(builder.upper(root.get(Family_.state)), finalText),
  	            builder.like(builder.upper(root.get(Family_.zip)), finalText),
  	            builder.like(builder.upper(root.get(Family_.email)), finalText),
  	            builder.like(builder.upper(root.get(Family_.fatherfirstname)), finalText),
  	            builder.like(builder.upper(root.get(Family_.fatherlastname)), finalText),
  	            builder.like(builder.upper(root.get(Family_.motherlastname)), finalText),
  	            builder.like(builder.upper(root.get(Family_.motherfirstname)), finalText)
  	    );
	}
	
	/*
	private static Predicate createSearchDropdownPredicate(Root<Family> root, CriteriaBuilder builder, Map<String, String>myPredicateMap) {
		final List<Predicate> predicates = new ArrayList<Predicate>();

        for (final Entry<String, String> e : myPredicateMap.entrySet()) {
        	Path expression = null;
        	String fieldName = e.getKey();
        	final String value = e.getValue();

            if (fieldName.contains(".")) {
                String[] names = StringUtils.split(fieldName, ".");
                
                if(fieldName.contains("intPinvAmt")) {
                	expression = root.join(names[0], JoinType.LEFT).get(names[1]);
            		predicates.add(builder.ge(expression, Integer.parseInt(value)));
            	}
                else if(fieldName.contains("strPinvGeo")) {
                	List<Predicate> geoPredicates = new ArrayList<Predicate>();
                	expression = root.join(names[0], JoinType.LEFT).get(names[1] + "1");
                	geoPredicates.add(builder.equal(expression, value));
                	
                	expression = root.join(names[0], JoinType.LEFT).get(names[1] + "2");
                	geoPredicates.add(builder.equal(expression, value));
                	
                	expression = root.join(names[0], JoinType.LEFT).get(names[1] + "3");
                	geoPredicates.add(builder.equal(expression, value));
                	
                	predicates.add(builder.or(geoPredicates.toArray(new Predicate[geoPredicates.size()])));
                }
                else {
                	expression = root.join(names[0], JoinType.LEFT).get(names[1]);
                	predicates.add(builder.equal(expression, value));
                }
            } else {
                expression = root.get(fieldName);
                predicates.add(builder.equal(expression, value));
            }
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	*/
	private static Predicate createFilterByPredicate(Root<Family> root, CriteriaBuilder builder, Map<String, String>myPredicateMap) {
		final List<Predicate> predicates = new ArrayList<Predicate>();

        for (final Entry<String, String> e : myPredicateMap.entrySet()) {
        	String fieldName = e.getKey();
            String value = e.getValue();

            Path<String> expression = null;
            if ((fieldName != null) && (value != null)) {
            	expression = root.get(fieldName);
            	if(fieldName.contains("state")) {
            		predicates.add(builder.equal(expression, value));
            	}
            	else {
            		if (!value.contains("%")) {
                    	value = "%" + value.toUpperCase() + "%";
              	    }
            		predicates.add(builder.like(builder.upper(expression), value));
            	}
            }
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	
	public static Specification<Family> searchfieldsLikeFamilies(final String searchTerm) {
	    return new Specification<Family>() {
	      public Predicate toPredicate(Root<Family> root, CriteriaQuery<?> query,
	            CriteriaBuilder builder) {
	    	  Predicate searchPredicate = null;
	    	  if(searchTerm!=null && searchTerm.trim().length()>0) {
	    		  searchPredicate = createSearchTermPredicate(root, builder, searchTerm);
	    	  }
	    	  return searchPredicate;
	      }
	    };
	  }
	/*
	public static Specification<Family> multiFieldsEqualSpecsFamilies(final Map<String, Integer>myPredicateMap) {
        return new Specification<Family>() {
            @Override
            public Predicate toPredicate(Root<Family> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                final List<Predicate> predicates = new ArrayList<Predicate>();

                for (final Entry<String, Integer> e : myPredicateMap.entrySet()) {
                	Path expression = null;
                	String fieldName = e.getKey();
                    if (fieldName.contains(".")) {
                        String[] names = StringUtils.split(fieldName, ".");
                        expression = root.join(names[0], JoinType.LEFT).get(names[1]);
                        //predicate = cb.equal(root.join("documents").get("type"), type);
                    } else {
                        expression = root.get(fieldName);
                    }
               
                    final Integer value = e.getValue();

                    if ((fieldName != null) && (value != null)) {
                    	if(fieldName.contains("Pinvests")) {
                    		predicates.add(builder.ge(expression, value));
                    	}
                    	else {
                            predicates.add(builder.equal(expression, value));
                    	}
                    }
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }*/
	
}
