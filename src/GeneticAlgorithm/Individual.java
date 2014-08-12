/**
 * @author Tianyi Ren
 */
package GeneticAlgorithm;

import java.awt.Point;
import java.util.Random;

public class Individual {
	public Point[] genes;
	public double fitness;
	public Individual(int count)
	{
		this.genes = new Point[count];		
	}

	public Individual(Point[] genes)
	{
		this.genes = genes;
	}

	
	public void print()
	{
		for(int i=0; i<genes.length; i++) {
			System.out.print(genes[i] + "; ");
		}
		System.out.print('\n');
	}

	
	public void Mutate() {
		Random rand = new Random();
		//int count = rand.nextInt(4);
		int a = rand.nextInt(genes.length);
		int b = rand.nextInt(genes.length);
		Point temp = genes[a];
		genes[a] = genes[b];
		genes[b] = temp;	
	}
	
//	public int compareFitness(Individual other)
//	{
//		if(this.fitness == other.fitness)
//			return 0;
//		if(this.fitness > other.fitness)
//			return 1;
//		else
//			return -1;
//			
//	}
	
}
