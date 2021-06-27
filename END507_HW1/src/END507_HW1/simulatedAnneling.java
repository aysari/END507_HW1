package END507_HW1;

import java.io.*;
import java.util.*;

public class simulatedAnneling {
	private static final double COOLING_RATE = 0.003;
	private static final double INITIAL_TEMP = 10000;
	private static final int MAX_ITERATION = 1000;        
	private static stopwatchCPU timer1 = new stopwatchCPU();
	
	public static void main(String[] args) throws IOException{
		
		//create stopwatchCPU object to calculate CPU time
        
		
		int cnt = 0;
		//int runNumber = 0;
		//int cnt2 = 0;
		//int cnt3 = 0;
		
		//List<Integer> maxIterForAll = new ArrayList<>();
		//List<Integer> maxIterationOfImp = new ArrayList<>();
		
		int[][] distanceMatrix = { {9999, 1, 1, 2, 3},
							       {1, 9999, 2, 1, 2},
							       {1, 2, 9999, 1, 2},
							       {2, 1, 1, 9999, 1},
							       {3, 2, 2, 1, 9999} };
		
		int[][] flowMatrix = { {0, 5, 2, 4, 1},
			   			   	   {5, 0, 3, 0, 2},
			   			   	   {2, 3, 0, 0, 0},
			   			   	   {4, 0, 0, 0, 5},
			   			   	   {1, 2, 0, 5, 0} };
		
		
		int numberOfDepartment = distanceMatrix.length;
		int numberOfLocation = flowMatrix.length;
		int currentEnergy;
		int neighborEnergy;
		
		//create new solution object and find initial solution 
		Assignment initialSolution = new Assignment(numberOfDepartment,numberOfLocation);
		initialSolution.combinationRepresentation(distanceMatrix, flowMatrix);
		Assignment.sortAsc(initialSolution.getdistanceArr(), 2);
		Assignment.sortDes(initialSolution.getdistanceArr(), 2);
		initialSolution.findInitialSolution();
		initialSolution.calculateObjValue(distanceMatrix, flowMatrix);
		System.out.println(initialSolution);
		
		Assignment currentSolution = new Assignment(initialSolution,numberOfLocation);
		currentSolution.calculateObjValue(distanceMatrix, flowMatrix);
		
		
		Assignment best = new Assignment(currentSolution, numberOfLocation);
		best.calculateObjValue(distanceMatrix, flowMatrix);
		
		double temprature = INITIAL_TEMP;
			
		/*while(runNumber<MAX_ITERATION) {
			temprature = INITIAL_TEMP;
			cnt = 0;
			cnt2 = 0;
			String str = "";
			
			currentSolution = new Assignment(initialSolution,numberOfLocation);
			currentSolution.calculateObjValue(distanceMatrix, flowMatrix);
			
			best = new Assignment(currentSolution, numberOfLocation);
			best.calculateObjValue(distanceMatrix, flowMatrix);
			
			//System.out.println("x "+ runNumber);
			maxIterationOfImp.clear();*/
			
			while(temprature>1&&cnt<MAX_ITERATION) {
				
				Assignment newSolution = new Assignment(currentSolution,numberOfLocation);
				newSolution.calculateObjValue(distanceMatrix, flowMatrix);
				
				simulatedAnneling.findNeighbor(newSolution);
				
				newSolution.calculateObjValue(distanceMatrix, flowMatrix);
				
				currentEnergy = currentSolution.getObjValue();
				neighborEnergy = newSolution.getObjValue();
				
				if(acceptanceProb(currentEnergy,neighborEnergy,temprature)>Math.random()) {
					currentSolution = new Assignment(newSolution,numberOfLocation);
					currentSolution.calculateObjValue(distanceMatrix, flowMatrix);
					
					//System.out.println(acceptanceProb(currentEnergy,neighborEnergy,temprature)+" "+Math.random());
					//cnt1++;
				}
		
				if(currentSolution.getObjValue()<best.getObjValue()) {
					best = new Assignment(currentSolution,numberOfLocation);
					best.calculateObjValue(distanceMatrix, flowMatrix);
					
					//System.out.println(cnt2+1);
					//maxIterationOfImp.add(Integer.valueOf(cnt2+1));
					//cnt2=0;
				}
				
				temprature *=1-COOLING_RATE;
				cnt++;
				
				//cnt2++;
				
				/*if(!maxIterationOfImp.isEmpty()) {
					
					//Integer a = Collections.max(maxIterationOfImp);
					//System.out.println(a);
					maxIterForAll.add(Collections.max(maxIterationOfImp));
				}*/

			}
			
			//maxIterForAll.add(Collections.max(maxIterationOfImp));
			
			
			System.out.println("---------------Best Solution---------------\n");
			System.out.println(best);
			
	        double time1 = timer1.elapsedTime();
	        System.out.printf("CPU Time in seconds: %f",time1);
			
			/*runNumber++;
			str = "Run number:	" +runNumber+"	Iteration number in which the best value is last updated: "+Collections.max(maxIterationOfImp);
			/*
			for(int i = 0;i<maxIterForAll.size();i++) {
				str+= "	"+ maxIterForAll.get(i);
				
			}
			System.out.println(str);
		}
		
		System.out.println(Collections.max(maxIterForAll));*/
	}
	
	public static void findNeighbor(Assignment ass) {
		
		//create new random object
		Random gen1 = new Random();
		
		int rand1=0;
		int rand2=0;
		
		int temp1 = 0;
		
		//create 2 random numbers between [1,5]
		while(rand1==rand2) {
			rand1 = gen1.nextInt(ass.getAssignmentArray().length)+1;
			rand2 = gen1.nextInt(ass.getAssignmentArray().length)+1;
		}
		
		//swap locations
		temp1 = ass.getAssignmentArray()[rand1-1][0];
		
		ass.setAssignmentArray(ass.getAssignmentArray()[rand2-1][0], rand1);
		ass.setAssignmentArray(temp1, rand2);
	}
	
	public static double acceptanceProb(int energy, int newEnergy, double temprature) {
		
		if(newEnergy<energy) {
			return 1.0;
		}
		
		return Math.exp((energy-newEnergy)/temprature);
	}
}
