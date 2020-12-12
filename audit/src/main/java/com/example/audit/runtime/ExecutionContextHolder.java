/**
 * 
 */
package com.example.audit.runtime;


/**
 * @author moalaoui
 *
 */
public final class ExecutionContextHolder {

	private ExecutionContextHolder(){}
	
	private static ThreadLocal<ExecutionContext> context = new InheritableThreadLocal<ExecutionContext>() {
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected ExecutionContext initialValue() {
			return new ExecutionContext();
		}
	};

	/**
	 * 
	 * @return
	 */
	public static ExecutionContext get() {
		return context.get();
	}

	/**
	 * 
	 * @return
	 */
	public static void set(ExecutionContext executionContext) {
		context.set(executionContext);
	}
	
	/**
	 * 
	 */
	public static void cleanup() {
		context.remove();
	}
	
	
}
