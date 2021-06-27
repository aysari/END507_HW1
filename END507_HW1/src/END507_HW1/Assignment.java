package END507_HW1;

import java.util.*;

public class Assignment {
	
	private int numberOfLocation;
	private int numberOfDepartment;
	//first column represents locations, second column represents assigned departments
	private int [][] assignmentArray;
	private int[][] flowArr; //represents flows of the combinations
	private int[][] distanceArr; //represents distances of the combinations
	private int objValue;

	//constructor
	public Assignment (int numberOfDepartment, int numberOfLocation){
		this.numberOfLocation = numberOfLocation;
		this.numberOfDepartment = numberOfDepartment;
		assignmentArray = new int [this.numberOfLocation][2];
				
		//initialize assignment array
		for(int i = 0;i<numberOfLocation;i++) {
				assignmentArray[i][0]=i+1;
		}
	}
	
	//constructor
	public Assignment (Assignment ass, int numberOfLocation){
		
		this.numberOfDepartment = numberOfLocation;
		this.numberOfLocation = numberOfLocation;
		
		this.assignmentArray = new int [numberOfLocation][2];
		
		for(int i = 0;i<numberOfLocation;i++) {
			this.assignmentArray[i][0]=ass.getAssignmentArray()[i][0];;
			this.assignmentArray[i][1]=ass.getAssignmentArray()[i][1];
		}
	}
	
	
	// getter methods
	public int[][] getflowArr () {
		return flowArr;
	}
	
	public int[][] getdistanceArr () {
		return distanceArr;
	}
	
	public int[][] getAssignmentArray () {
		return assignmentArray;
	}

	public int getObjValue () {
		return objValue;
	}
	// setter methods
	public void setAssignmentArray (int newLoc,int dep) {
		assignmentArray[dep-1][0]=newLoc;
	}
	
	/*method that represent flow matrix as a combination of departments
	/						distance matrix as a combination of locations such that
	 * (for distance){{12,5}
	 * 				  {13,2}
	 * 					 .
	 * 					 .
	 * 					 .
	 * 				  {45,5}}*/
	 
	public void combinationRepresentation(int[][] distanceMatrix,int[][] flowMatrix) {
		
		//
		distanceArr = new int[distanceMatrix.length*(distanceMatrix.length-1)/2][2];
		flowArr = new int[flowMatrix.length*(distanceMatrix.length-1)/2][2];
		
		int cnt = 0;
		
		for(int i=1;i<flowMatrix.length;i++) {
			for(int j=i;j<flowMatrix.length;j++) { 
				
				String str1 = Integer.toString(i);
				String str2 = Integer.toString(j+1);
				String s = str1+str2;

				distanceArr[cnt][0]=Integer.valueOf(s);
				distanceArr[cnt][1]=distanceMatrix[i-1][j];
				
				flowArr[cnt][0]=Integer.valueOf(s);
				flowArr[cnt][1]=flowMatrix[i-1][j];

				cnt++;
				
			}
		}		
	}
	
    public static void sortAsc(int[][] array, final int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] > second[columnNumber-1]) return 1;
               else return -1;
            }
        });
    }
    
    public static void sortDes(int[][] array, final int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] < second[columnNumber-1]) return 1;
               else return -1;
            }
        });
    }    
    
    /*method that gets flow with maximum flow and
      assigns the departmants to mininum value distance.
      solution is represented as a string object so that
      only the departments that are not assigned are forced to
      be assigned.
     */
    
	public void findInitialSolution() {
		
		int toBeAssigned1;
		int toBeAssigned2;		
	
		int candLoc1;
		int candLoc2;
		
		String assignedDep = "";
		String assignedLoc = "";
		
		int sum = 15;
		int sumAssignedLoc = 0;
		int sumAssignedDep = 0;
		
		for(int i = 0;i<flowArr.length;i++) {
			for(int j = 0;j<flowArr.length;j++) {
				
				toBeAssigned1 = flowArr[i][0]/10;
				toBeAssigned2 = flowArr[i][0]%10;
				
				candLoc1 = distanceArr[j][0]/10;
				candLoc2 = distanceArr[j][0]%10;
				
				if(assignedDep.length()==4)
					assignmentArray[sum-sumAssignedDep-1][1]=sum-sumAssignedLoc;
				
				if(!assignedDep.contains(String.valueOf(toBeAssigned1))&&
				   !assignedDep.contains(String.valueOf(toBeAssigned2))&&
				   !assignedLoc.contains(String.valueOf(candLoc1))&&
				   !assignedLoc.contains(String.valueOf(candLoc2))){
					
					sumAssignedLoc += toBeAssigned1;
					sumAssignedLoc += toBeAssigned2;
					
					sumAssignedDep += candLoc1;
					sumAssignedDep += candLoc2;
					
					assignedDep += String.valueOf(toBeAssigned1);
					assignedDep += String.valueOf(toBeAssigned2);
					assignedLoc += String.valueOf(candLoc1);
					assignedLoc += String.valueOf(candLoc2);
					
					assignmentArray [candLoc1-1][1]=toBeAssigned1;
					assignmentArray [candLoc2-1][1]=toBeAssigned2;
					
					j=flowArr.length;
				}				
			}
		}
		System.out.println("-------------Initial Solution-------------\n");
	}
	
	public void calculateObjValue(int[][] distanceMatrix,int[][] flowMatrix) {
		
		Assignment.sortAsc(assignmentArray, 2);
		
		objValue = 0;
		
		for(int i = 0;i<numberOfLocation;i++) {
			for(int j = i+1;j<numberOfLocation;j++) {
				if(i!=j) {
					objValue += flowMatrix[i][j]*distanceMatrix[assignmentArray[i][0]-1][assignmentArray[j][0]-1];
				}
			}	
		}
	}
	

	public String toString(){
		
		String str = "";
		
		for(int i=0;i<numberOfDepartment;i++) {
					str += "  Department " + assignmentArray[i][1] + " is assigned to location " + assignmentArray[i][0] +".";
					str += "\n";				
			}
		
		str += "\n------------Objective value: " + objValue+"-----------\n";

		return str;
	}

}
