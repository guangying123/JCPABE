package cpabe.benchmark;

public abstract class Benchmark {
	/**
	 * Is called once before the benchmark starts.
	 * Is not timed.
	 */
	public void initializeBenchmark() {
		
	}
	
	/**
	 * Is called at the start of every iteration, but not at the start of every run.
	 * Is not timed.
	 * @param iteration
	 */
	public void initializeIteration(int iteration) {
		
	}
	
	
	/**
	 * Is called exactly numWarmupRuns() + numBenchmarkRuns() * numRunsPerRun() times.
	 * @param the current iteration, begins at 0, also 0 for warmup
	 */
	public abstract void singleRun(int iteration);
	
	public void destroyIteration(int iteration) {
		
	}
	
	public void destroyBenchmark() {
		
	}
	
	/**
	 * How many times should the singleRun be started, before being timed? see warm up related to java
	 * @return
	 */
	public abstract int numWarmupRuns();
	
	/**
	 * The number of iterations in this benchmark. During each iteration the singleRun method is called numRunsPerIteration times.
	 * @return
	 */
	public abstract int numIterations();
	
	/**
	 * The number of runs of singleRun per iteration.
	 * @return
	 */
	public abstract int numRunsPerIteration();
}