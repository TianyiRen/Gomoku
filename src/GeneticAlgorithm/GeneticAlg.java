/**
 * @author Tianyi Ren
 */
package GeneticAlgorithm;
import GUI.BoardT;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlg {
	public BoardT currentBoard;
	public int currnetPlayer;
	public boolean black;
	double crossoverRate;
	double mutationRate;
	int originalPopulationSize;
	int individualSize;
	int maxGeneration;
	
	public ArrayList<Individual> currentPopulation = new ArrayList<Individual>();
	public ArrayList<Individual> nextPopulation = new ArrayList<Individual>();
	
	double totalFitness;
	public Individual bestIndividual;
	
	//default constructor
	public GeneticAlg() {
		this.crossoverRate = 0.8;
		this.mutationRate = 0.05;
		this.originalPopulationSize = 20;
		this.individualSize = 10;
		this.maxGeneration = 10;
		
		//currentPopulation = new ArrayList<Individual>();
		//nextPopulation = new ArrayList<Individual>();
	}
	public GeneticAlg(BoardT board) {
		this.crossoverRate = 0.8;
		this.mutationRate = 0.05;
		this.originalPopulationSize = 200;
		this.individualSize = 5;
		this.maxGeneration = 5;
		currentBoard = board;//new Board(board);
		this.currnetPlayer = board.currentPlayer;
		this.black = board.black;
		//currentPopulation = new ArrayList<Individual>();
		//nextPopulation = new ArrayList<Individual>();
		//this.currnetPlayer=board.black?1:-1;
	}
	//constructor
	public GeneticAlg(int crossoverRate, int mutationRate, int originalPopulationSize, int individualSize, int maxGeneration){
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.originalPopulationSize = originalPopulationSize;
		this.individualSize = individualSize;
		this.maxGeneration = maxGeneration;
		//createFirstPopulation(); from minimax
	}
	
	
	public double getCrossoverRate() {
		return crossoverRate;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public int getOriginalPopulationSize() {
		return originalPopulationSize;
	}

	public int getIndividualSize() {
		return individualSize;
	}

	public int getMaxGeneration() {
		return maxGeneration;
	}

	public List<Individual> getCurrentPopulation() {
		return currentPopulation;
	}
	

	public List<Individual> getNextPopulation() {
		return nextPopulation;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}
	
	public Point bestMove()
	{
		if(currentBoard.possibleMoves.size()==0)
			return new Point(9,9);
		firstGeneration();
		
		updateFitness();
		for(int i=0; i<maxGeneration; i++) {
			//nextPopulation = new ArrayList<Individual>(currentPopulation);		
			Selection();
					
			CrossOver();
					
			//Mutation();
					
			currentPopulation = nextPopulation;
			updateFitness();
					
		}
		
		return this.bestIndividual.genes[0];
	}
	
	public void firstGeneration()
	{

	    //this.currentBoard.updatePossibleMoves();
		Random random = new Random();
//		int count = validPosition.size();
		for (int i=0; i<this.originalPopulationSize;i++)
		{
			Individual individual = new Individual(this.individualSize);
			int j = 0;
			this.currentBoard.updatePossibleMoves();
			//System.out.println("Individual:"+i);
			while(j<this.individualSize)
			{
			    
			    //Point[] set = (Point[])this.currentBoard.possibleMoves.toArray();
			    boolean go  =true;
			    while(go)
			    {
			        int index = random.nextInt(this.currentBoard.possibleMoves.size());
			        int n = 0;
			        for(Point p : this.currentBoard.possibleMoves)
			        {
			            if(n==index)
			            {
			                //Point p = set[index];
			                if(this.currentBoard.board[p.x][p.y]==0)
			                {
			                    individual.genes[j]=p;
			                    this.currentBoard.move(p);
			                    go=false;
			                    
			                }
			                break;
			            }
			            n++;
			        }
			    }
				j++;
				
			}
			for(int k=0;k<individualSize;k++)
			{
			    this.currentBoard.board[individual.genes[k].x][individual.genes[k].y]=0;
			}
			this.currentBoard.black = this.black;
			this.currentBoard.currentPlayer = this.currnetPlayer;
			this.currentBoard.updatePossibleMoves();
			//individual.print();
			this.currentPopulation.add(individual);	
			
		}
		
		
	}
	
	public void GeneticAlgorithm() {

	    updateFitness();
		for(int i=0; i<10; i++) {
			//System.out.println("Round:"+i);
			
			Selection();
			
			CrossOver();
			
			//Mutation();
			
			currentPopulation = nextPopulation;
			updateFitness();
		}
		bestIndividual.print();
	}
	private void updateFitness() {
		double BestFitness = Double.MIN_VALUE;
	    for(Individual individual : currentPopulation) {
			individual.fitness = 0;
			for(Point p : individual.genes)
			{
				int player = currentBoard.currentPlayer;
			    this.currentBoard.move(p);
			    if(player == this.currnetPlayer)
			        individual.fitness+= currentBoard.evaluate(player)- currentBoard.evaluate(-player);
			    else
			        individual.fitness-= currentBoard.evaluate(player)- currentBoard.evaluate(-player);
			}
			if(individual.fitness > BestFitness)
			    this.bestIndividual = individual;
			for(Point p: individual.genes)
			{
			    currentBoard.board[p.x][p.y]=0;
			    
			}
			currentBoard.black = this.black;
            currentBoard.currentPlayer = this.currnetPlayer;
		}
		
	}
	
	// did not finish breed
	private void Selection() {
		nextPopulation = new ArrayList<Individual>();
		Random r = new Random();
		//fitness proportional selection
		totalFitness = 0;
		for(int i=0; i<currentPopulation.size(); i++) {
			totalFitness += currentPopulation.get(i).fitness;
		}
		double size = currentPopulation.size();
		if (size <2)
		    return;
		double averageFitness = totalFitness/size;
		//double maxFitness=Double.MIN_VALUE;
		//this.bestIndividual = null;
		for(Individual individual : currentPopulation) {
		    //System.out.println("Individual Fitness:"+individual.fitness);
			//double p = individual.fitness/totalFitness;
			if(individual.fitness > averageFitness){
			//if(r.nextDouble() < p) {
				nextPopulation.add(individual);  
//				if(currentPopulation.get(i).fitness > maxFitness)
//				{
//					maxFitness = currentPopulation.get(i).fitness;
//					this.bestIndividual = currentPopulation.get(i);
//				}
			}		
		}
	}
	//100% crossover
	private void CrossOver() {
		Random seed = new Random();
		if(nextPopulation.size()<2)
		    return;
		int loop = nextPopulation.size()-1;
		for(int i=0; i<loop; i=i+2) {
		    //System.out.println("Cross Over Run:"+i);
			Individual parent1 = nextPopulation.get(i);
			Individual parent2 = nextPopulation.get(i+1);
			boolean[] unchange = new boolean[individualSize];
			int staticGene = seed.nextInt(individualSize);
			for(int j=0; j<staticGene;j++)
			{
				int index = seed.nextInt(individualSize);
				unchange[index]=true;
			}
			//check same gene
			for(int m =0;m<individualSize; m++)
			{
				for(int n=0; n<individualSize; n++)
				{
					if(parent1.genes[m].equals(parent2.genes[n]))
					{
						unchange[m]=true;
						unchange[n]=true;
					}
				}
			}
//			for (Point p1 : parent1.genes)
//			{
//			    for(Point p2 : parent2.genes)
//			    {
//			        if(p1.equals(p2))
//			        {
//			            
//			        }
//			    }
//			}
			Individual child1 = new Individual(individualSize);
			Individual child2 = new Individual(individualSize);
			for( int j =0;j<individualSize;j++)
			{
				if(unchange[j])
				{
					child1.genes[j]=parent1.genes[j];
					child2.genes[j]=parent2.genes[j];
				}
				else
				{
					child1.genes[j]=parent2.genes[j];
					child2.genes[j]=parent1.genes[j];
				}
			}
			nextPopulation.add(child1);
			nextPopulation.add(child2);
			
			//parent1.Crossover(parent2, this.lamda);
		}
	}
	//100% mutation
	private void Mutation() {
		Random r = new Random();
		for(int i=0; i<nextPopulation.size(); i++) {
			if(r.nextDouble()<this.mutationRate)
			{
				Individual individual = nextPopulation.get(i);
				individual.Mutate();
			}
		}
	}
	
}
