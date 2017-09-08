package com.xilin.management.school.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import com.xilin.management.school.model.MyCustomFamilySpecs;
import com.xilin.management.school.web.util.Utils;

public class MyLazyFamilyDataModel extends LazyDataModel<Family> {
	private static final long serialVersionUID = 1L;

	private List<Family> datasource;

	FamilyRepository familyRepository;
	String strSearchTerm;
	//private Map<String, String> searchDropdownMap;

	public FamilyRepository getFamilyRepository() {
		return familyRepository;
	}

	public void setFamilyRepository(FamilyRepository familyRepository) {
		this.familyRepository = familyRepository;
	}

	public MyLazyFamilyDataModel(FamilyRepository familyRepository, String strSearchTerm, Map<String, String> searchDropdownMap) {
		this.familyRepository = familyRepository;
		this.strSearchTerm = strSearchTerm;
		//this.searchDropdownMap = searchDropdownMap;
	}
	
	@Override
	public Family getRowData(String rowKey) {
		for (Family can : datasource) {
			if (can.getId().equals(Integer.valueOf(rowKey)))
				return can;
		}
		return null;
	}

	@Override
	public Object getRowKey(Family can) {
		return can.getId();
	}

	@Override
	public List<Family> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<Family> data = new ArrayList<Family>();
		this.setPageSize(maxPerPage);
		this.setRowIndex(startingAt);

		try {
			int startingPage = startingAt / maxPerPage;
			//PageRequest pageable = new PageRequest(startingPage, maxPerPage);
			
			Sort srt;
			if (sortField == null || sortField.isEmpty()) {
				sortField = Utils.DEFAULT_FAMILY_SORT_FIELD;
				srt = new Sort(new Sort.Order(Sort.Direction.DESC, sortField));
			}
			else {
				srt = new Sort(new Sort.Order(Sort.Direction.ASC, sortField));
			
				if (sortOrder.equals(SortOrder.DESCENDING)) {
					srt = new Sort(new Sort.Order(Sort.Direction.DESC, sortField));
				}
			}
			PageRequest	pageable = new PageRequest(startingPage, maxPerPage, srt);
			
			HashMap<String, String> myfilterMap = new HashMap<String, String>();
			//myfilterMap.put("strFirstName","ca");   
			if (filters != null && !filters.isEmpty()) {
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						if (filterProperty != null && filterProperty.trim().length()>0
								&& filterValue != null && filterValue.toString().length() > 0) {
							myfilterMap.put(filterProperty, filterValue.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
			
			Page<Family> pageData = familyRepository.findAll(
					MyCustomFamilySpecs.loadFullSearchFamilies(strSearchTerm, null, myfilterMap), pageable);
			data = pageData.getContent();
			this.setRowCount((int)pageData.getTotalElements());
			
			datasource = pageData.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setPageSize(maxPerPage);

		return data;
	}
	
}