package com.xilin.management.school.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class States {
	public String[][] getUSAStatesExtended() {
		String states [][] = {
				{"AL", "Alabama"},
				{"AK", "Alaska"},
				{"AZ", "Arizona"},
				{"AR", "Arkansas"},
				{"CA", "California"},
				{"CO", "Colorado"},
				{"CT", "Connecticut"},
				{"DE", "Delaware"},
				{"DC", "District of Columbia"},
				{"FL", "Florida"},
				{"GA", "Georgia"},
				{"GU", "Guam"},
				{"HI", "Hawaii"},
				{"ID", "Idaho"},
				{"IL", "Illinois"},
				{"IN", "Indiana"},
				{"IA", "Iowa"},
				{"KS", "Kansas"},
				{"KY", "Kentucky"},
				{"LA", "Louisiana"},
				{"ME", "Maine"},
				{"MD", "Maryland"},
				{"MA", "Massachusetts"},
				{"MI", "Michigan"},
				{"MN", "Minnesota"},
				{"MS", "Mississippi"},
				{"MO", "Missouri"},
				{"MT", "Montana"},
				{"NE", "Nebraska"},
				{"NV", "Nevada"},
				{"NH", "New Hampshire"},
				{"NJ", "New Jersey"},
				{"NM", "New Mexico"},
				{"NY", "New York"},
				{"NC", "North Carolina"},
				{"ND", "North Dakota"},
				{"OH", "Ohio"},
				{"OK", "Oklahoma"},
				{"OR", "Oregon"},
				{"PW", "Palau"},
				{"PA", "Pennsylvania"},
				{"PR", "Puerto Rico"},
				{"RI", "Rhode Island"},
				{"SC", "South Carolina"},
				{"SD", "South Dakota"},
				{"TN", "Tennessee"},
				{"TX", "Texas"},
				{"UT", "Utah"},
				{"VT", "Vermont"},
				{"VA", "Virginia"},
				{"VI", "Virgin Islands"},
				{"WA", "Washington"},
				{"WV", "West Virginia"},
				{"WI", "Wisconsin"},
				{"WY", "Wyoming"}
		};

		return states;
	}
	
	public String[][] getUSARegions() {
		String regions [][] = {
				{"RNE", "New England"},
				{"RMA", "Mid Atlantic"},
				{"RSE", "Southeast"},
				{"RMW", "Midwest"},
				{"RGP", "Great Plains"},
				{"RSW", "Southwest"},
				{"RWP", "Western & Pacific"}
		};

		return regions;
	}
	
	public static List<SelectItem> getUsStateList() {
		List<SelectItem> usStates = new ArrayList<SelectItem>();

		usStates.add(new SelectItem("", "None Selected"));
		
		String[][] statesArray = (new States()).getUSAStatesExtended();
		for (int iState = 0; iState < statesArray.length; iState++) {
			usStates.add(new SelectItem(statesArray[iState][0], statesArray[iState][1]));
		}
		return usStates;
	}
	
	public static List<SelectItemGroup> getStateList() {
		List<SelectItemGroup> stateList = new ArrayList<SelectItemGroup>();
		SelectItemGroup usGroup = new SelectItemGroup("United States");
		List<SelectItem> usStates = new ArrayList<SelectItem>();

		String[][] statesArray = (new States()).getUSAStatesExtended();
		for (int iState = 0; iState < statesArray.length; iState++) {
			usStates.add(new SelectItem(statesArray[iState][0], statesArray[iState][1]));
		}
		usGroup.setSelectItems((SelectItem[])usStates.toArray(new SelectItem[0]));
		stateList.add(usGroup);
		return stateList;
	}
	
	public static List<SelectItemGroup> getRegionStateList() {
		List<SelectItemGroup> regionStateList = new ArrayList<SelectItemGroup>();
		SelectItemGroup usRegionGroup = new SelectItemGroup("US Region");
		List<SelectItem> usRegions = new ArrayList<SelectItem>();

		String[][] regionsArray = (new States()).getUSARegions();
		for (int iRegion = 0; iRegion < regionsArray.length; iRegion++) {
			usRegions.add(new SelectItem(regionsArray[iRegion][0], regionsArray[iRegion][1]));
		}
		usRegionGroup.setSelectItems((SelectItem[])usRegions.toArray(new SelectItem[0]));
		
		SelectItemGroup usStateGroup = new SelectItemGroup("US States");
		List<SelectItem> usStates = new ArrayList<SelectItem>();

		String[][] statesArray = (new States()).getUSAStatesExtended();
		for (int iState = 0; iState < statesArray.length; iState++) {
			usStates.add(new SelectItem(statesArray[iState][0], statesArray[iState][1]));
		}
		usStateGroup.setSelectItems((SelectItem[])usStates.toArray(new SelectItem[0]));
		
		regionStateList.add(usRegionGroup);
		regionStateList.add(usStateGroup);
		return regionStateList;
	}
}
